package com.tcc.drakes.services;

import com.tcc.drakes.dtos.SalaDTO;
import com.tcc.drakes.entities.Formulario;
import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.entities.SalaUsuario;
import com.tcc.drakes.entities.StatusSala;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.repositories.FormularioRepository;
import com.tcc.drakes.repositories.SalaRepository;
import com.tcc.drakes.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SalaService {

	private static final int LIMITE_PARTICIPANTES = 50;

	@Autowired
	private SalaRepository repository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FormularioRepository formularioRepository;

	// --- Métodos Existentes ---

	public List<SalaDTO> findAll() {
		List<Sala> salas = repository.findAll();
		return salas.stream().map(SalaDTO::new).collect(Collectors.toList());
	}

	public SalaDTO findById(Long id) {
		Sala sala = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Sala com ID " + id + " não encontrada"));
		return new SalaDTO(sala);
	}

	public SalaDTO findByCodigo(String codigo) {
		Sala sala = repository.findByCodigoSala(codigo)
				.orElseThrow(() -> new EntityNotFoundException("Sala com o código '" + codigo + "' não encontrada"));
		return new SalaDTO(sala);
	}

	@Transactional
	public SalaDTO insert(SalaDTO dto) {
		Sala sala = new Sala();
		copyDtoToEntity(dto, sala); // Seta idUsuario e associa Formulário

		// 1. Extrai o idUsuario para uma variável local (efetivamente final)
		// Assume getIdUsuario() retorna long.
		final long idUsuarioCriador = sala.getIdUsuario();

		// Validações
		if (idUsuarioCriador == 0L) { // Usa a variável local
			throw new RuntimeException("ID do usuário criador é obrigatório.");
		}

		usuarioRepository.findById(idUsuarioCriador)
				.orElseThrow(() -> new EntityNotFoundException("Usuário criador com ID " + idUsuarioCriador + " não encontrado."));


		if (sala.getFormulario() == null) {
			throw new RuntimeException("ID do formulário é obrigatório.");
		}

		sala.setDataCriacao(LocalDate.now());
		sala.setCodigoSala(gerarCodigoUnico());
		sala.setStatusSala(StatusSala.DISPONIVEL);


		sala = repository.save(sala);
		return new SalaDTO(sala);
	}

	@Transactional
	public SalaDTO update(Long id, SalaDTO dto) {
		Sala sala = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Sala com ID " + id + " não encontrada"));
		// Atualiza campos permitidos
		if (dto.getNomeSala() != null) {
			sala.setNomeSala(dto.getNomeSala());
		}
		sala = repository.save(sala);
		return new SalaDTO(sala);
	}

	@Transactional
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new EntityNotFoundException("Sala com ID " + id + " não encontrada para deletar.");
		}
		repository.deleteById(id);
	}

	public Optional<Sala> buscarSalaPorId(Long idSala) {
		return repository.findById(idSala);
	}

	@Transactional
	public SalaDTO fecharSala(Long id) {
		Sala sala = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Sala com ID " + id + " não encontrada"));
		if (sala.getStatusSala() == StatusSala.FECHADA) {
			return new SalaDTO(sala);
		}
		sala.setStatusSala(StatusSala.FECHADA);
		sala = repository.save(sala);
		return new SalaDTO(sala);
	}

	// =========================================================================
	// ✅ NOVO MÉTODO: INICIAR SALA (COM CORREÇÃO PARA LAZY INITIALIZATION)
	// =========================================================================
	/**
	 * Busca uma sala pelo código, força o carregamento de participantes,
	 * muda o status para INICIADA e retorna a Sala.
	 * * @param codigoSala O código da sala a ser iniciada.
	 * @return A entidade Sala completa, pronta para ser enviada via WebSocket.
	 */
	@Transactional
	public Sala iniciarSala(String codigoSala) {
		Sala sala = repository.findByCodigoSala(codigoSala)
				.orElseThrow(() -> new EntityNotFoundException("Sala com o código '" + codigoSala + "' não encontrada"));

		// 1. Força o carregamento da coleção de participantes DENTRO da transação ativa.
		// Isso evita o erro 'LazyInitializationException: No Session'.
		// A coleção `participantes` agora estará populada.
		sala.getParticipantes().size();

		// 2. Lógica para iniciar a sala (se necessário, mude o status)
		if (sala.getStatusSala() != StatusSala.INICIADA) {
			sala.setStatusSala(StatusSala.INICIADA);
			sala = repository.save(sala); // Persiste a mudança de status
		}

		// 3. Retorna a entidade Sala, agora com os participantes carregados
		return sala;
	}
	// =========================================================================
	// FIM: NOVO MÉTODO
	// =========================================================================

	@Transactional
	public String entrarNaSala(String codigoSala, Long idUsuario) { // idUsuario (parâmetro) é Long (Wrapper)
		Sala sala = repository.findByCodigoSala(codigoSala)
				.orElseThrow(() -> new EntityNotFoundException("Sala com o código '" + codigoSala + "' não encontrada"));

		// Busca o objeto Usuario completo para adicionar à associação
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + idUsuario + " não encontrado"));

		if (sala.getStatusSala() == StatusSala.FECHADA) {
			throw new IllegalStateException("Acesso negado. A sala '" + sala.getNomeSala() + "' está fechada.");
		}

		// Verifica se o usuário já participa
		// Assume que su.getUsuario().getId() retorna 'long' (primitivo)
		boolean usuarioJaParticipa = sala.getParticipantes().stream()
				.anyMatch(su -> su.getUsuario() != null && su.getUsuario().getId() == idUsuario); // <<< COMPARAÇÃO CORRIGIDA

		if (usuarioJaParticipa) {
			System.out.println("Usuário " + usuario.getNome() + " já está na sala: " + sala.getNomeSala());
			return "Você já está nesta sala.";
		}

		// Verifica limite
		if (sala.getParticipantes().size() >= LIMITE_PARTICIPANTES) {
			if (sala.getStatusSala() != StatusSala.CHEIA) {
				sala.setStatusSala(StatusSala.CHEIA);
				// Será salvo no final
			}
			throw new IllegalStateException("Acesso negado. A sala '" + sala.getNomeSala() + "' está cheia.");
		}

		// Adiciona participante
		SalaUsuario novaAssociacao = new SalaUsuario(sala, usuario);
		sala.getParticipantes().add(novaAssociacao);

		// Atualiza status se necessário
		if (sala.getParticipantes().size() >= LIMITE_PARTICIPANTES) {
			sala.setStatusSala(StatusSala.CHEIA);
		}

		repository.save(sala);
		return "Usuário " + usuario.getNome() + " entrou na sala: " + sala.getNomeSala();
	}


	@Transactional
	public void removerParticipante(String codigoSala, Long idUsuario) { // idUsuario é Long (Wrapper)

		Sala sala = repository.findByCodigoSala(codigoSala)
				.orElseThrow(() -> new EntityNotFoundException("Sala com código " + codigoSala + " não encontrada."));

		// 1. Verifica se é o dono
		// Assume sala.getIdUsuario() retorna long
		if (idUsuario != null && sala.getIdUsuario() == idUsuario) { // <<< CORRETO (== com auto-unboxing)
			throw new RuntimeException("O dono da sala não pode sair. Use a opção de desmanchar.");
		}

		// 2. Encontra a associação SalaUsuario correspondente
		// Assume su.getUsuario().getId() retorna long
		Optional<SalaUsuario> associacaoParaRemover = sala.getParticipantes().stream()
				.filter(su -> su.getUsuario() != null && idUsuario != null && su.getUsuario().getId() == idUsuario) // <<< CORREÇÃO APLICADA AQUI (== com auto-unboxing)
				.findFirst();

		// 3. Remove se encontrou
		if (associacaoParaRemover.isPresent()) {
			sala.getParticipantes().remove(associacaoParaRemover.get()); // Remove da coleção

			if (sala.getStatusSala() == StatusSala.CHEIA) { // Atualiza status
				sala.setStatusSala(StatusSala.DISPONIVEL);
			}

			repository.save(sala); // Salva a sala
			System.out.println("Usuário " + idUsuario + " removido com sucesso da sala " + codigoSala);

		} else {
			System.out.println("Usuário " + idUsuario + " não encontrado na lista de participantes da sala " + codigoSala + ".");
			// Não lança erro
		}
	}
	// --- FIM DA CORREÇÃO removerParticipante ---


	// --- copyDtoToEntity (Garante que seta o ID do usuário) ---
	private void copyDtoToEntity(SalaDTO dto, Sala entity) {
		// ESSENCIAL: Copia o ID do usuário (long) do DTO para a entidade
		if (dto.getIdUsuario() == null) {
			// Se o ID do usuário no DTO for Long (Wrapper), verifica null
			throw new IllegalArgumentException("ID do usuário criador não pode ser nulo no DTO.");
		}
		// Assume setIdUsuario(long) na entidade Sala
		entity.setIdUsuario(dto.getIdUsuario()); // <<< Seta o ID primitivo 'long'

		entity.setIdTema(dto.getIdTema());
		entity.setNomeSala(dto.getNomeSala());
		entity.setStatusSala(dto.getStatusSala());

		// Associa o formulário
		if (dto.getIdFormulario() != null) {
			if (entity.getFormulario() == null || !entity.getFormulario().getIdFormulario().equals(dto.getIdFormulario())) {
				Formulario formulario = formularioRepository.findById(dto.getIdFormulario())
						.orElseThrow(() -> new EntityNotFoundException("Formulário ID " + dto.getIdFormulario() + " não encontrado."));
				entity.setFormulario(formulario);
			}
		} else {
			// Lança erro se formulário for obrigatório e não veio no DTO
			throw new IllegalArgumentException("ID do formulário não pode ser nulo no DTO.");
		}
	}
	// --- FIM DA CORREÇÃO copyDtoToEntity ---


	// --- gerarCodigoUnico (sem alterações) ---
	private String gerarCodigoUnico() {
		String codigo;
		do {
			int numeroAleatorio = ThreadLocalRandom.current().nextInt(100000, 1000000);
			codigo = String.valueOf(numeroAleatorio);
		} while (repository.existsByCodigoSala(codigo));
		return codigo;
	}
}
