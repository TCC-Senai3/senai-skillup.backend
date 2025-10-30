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
import org.springframework.messaging.simp.SimpMessagingTemplate;

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

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate; // Para enviar mensagens via WebSocket

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
        copyDtoToEntity(dto, sala);

        final long idUsuarioCriador = sala.getIdUsuario();

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
    // ✅ NOVO MÉTODO: INICIAR SALA
    // =========================================================================
    @Transactional
    public Sala iniciarSala(String codigoSala, Long idUsuario) {
        Sala sala = repository.findByCodigoSala(codigoSala)
                .orElseThrow(() -> new EntityNotFoundException("Sala com o código '" + codigoSala + "' não encontrada"));

        // Somente o dono pode iniciar
        if (!sala.getIdUsuario().equals(idUsuario)) {
            throw new IllegalStateException("Apenas o dono da sala pode iniciar o jogo.");
        }

        if (sala.getStatusSala() == StatusSala.INICIADA) {
            throw new IllegalStateException("A sala já foi iniciada.");
        }

        // Força o carregamento dos participantes
        sala.getParticipantes().size();

        // Altera status
        sala.setStatusSala(StatusSala.INICIADA);
        sala = repository.save(sala);

        // Envia notificação via WebSocket
        simpMessagingTemplate.convertAndSend(
                "/topic/sala/" + codigoSala,
                new SalaMensagem("JOGO_INICIADO", sala.getFormulario().getIdFormulario(), sala.getIdSala(), codigoSala)
        );

        return sala;
    }
    // =========================================================================

    @Transactional
    public String entrarNaSala(String codigoSala, Long idUsuario) {
        Sala sala = repository.findByCodigoSala(codigoSala)
                .orElseThrow(() -> new EntityNotFoundException("Sala com o código '" + codigoSala + "' não encontrada"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + idUsuario + " não encontrado"));

        if (sala.getStatusSala() == StatusSala.FECHADA) {
            throw new IllegalStateException("A sala está fechada.");
        }

        boolean usuarioJaParticipa = sala.getParticipantes().stream()
                .anyMatch(su -> su.getUsuario() != null && su.getUsuario().getId() == idUsuario);

        if (usuarioJaParticipa) return "Você já está nesta sala.";

        if (sala.getParticipantes().size() >= LIMITE_PARTICIPANTES) {
            sala.setStatusSala(StatusSala.CHEIA);
            throw new IllegalStateException("A sala está cheia.");
        }

        SalaUsuario novaAssociacao = new SalaUsuario(sala, usuario);
        sala.getParticipantes().add(novaAssociacao);

        if (sala.getParticipantes().size() >= LIMITE_PARTICIPANTES) {
            sala.setStatusSala(StatusSala.CHEIA);
        }

        repository.save(sala);
        return "Usuário " + usuario.getNome() + " entrou na sala: " + sala.getNomeSala();
    }

    @Transactional
    public void removerParticipante(String codigoSala, Long idUsuario) {
        Sala sala = repository.findByCodigoSala(codigoSala)
                .orElseThrow(() -> new EntityNotFoundException("Sala com código " + codigoSala + " não encontrada."));

        if (sala.getIdUsuario() == idUsuario) {
            throw new RuntimeException("O dono da sala não pode sair. Use desmanchar.");
        }

        Optional<SalaUsuario> associacaoParaRemover = sala.getParticipantes().stream()
                .filter(su -> su.getUsuario() != null && su.getUsuario().getId() == idUsuario)
                .findFirst();

        associacaoParaRemover.ifPresent(su -> {
            sala.getParticipantes().remove(su);
            if (sala.getStatusSala() == StatusSala.CHEIA) sala.setStatusSala(StatusSala.DISPONIVEL);
            repository.save(sala);
        });
    }

    // --- copyDtoToEntity ---
    private void copyDtoToEntity(SalaDTO dto, Sala entity) {
        if (dto.getIdUsuario() == null) throw new IllegalArgumentException("ID do usuário criador não pode ser nulo.");
        entity.setIdUsuario(dto.getIdUsuario());
        entity.setIdTema(dto.getIdTema());
        entity.setNomeSala(dto.getNomeSala());
        entity.setStatusSala(dto.getStatusSala());

        if (dto.getIdFormulario() != null) {
            Formulario formulario = formularioRepository.findById(dto.getIdFormulario())
                    .orElseThrow(() -> new EntityNotFoundException("Formulário ID " + dto.getIdFormulario() + " não encontrado."));
            entity.setFormulario(formulario);
        } else throw new IllegalArgumentException("ID do formulário não pode ser nulo.");
    }

    private String gerarCodigoUnico() {
        String codigo;
        do {
            int numeroAleatorio = ThreadLocalRandom.current().nextInt(100000, 1000000);
            codigo = String.valueOf(numeroAleatorio);
        } while (repository.existsByCodigoSala(codigo));
        return codigo;
    }

    // --- Classe auxiliar para WebSocket ---
    public static class SalaMensagem {
        public String type;
        public Long idFormulario;
        public Long idSala;
        public String codigoSala;

        public SalaMensagem(String type, Long idFormulario, Long idSala, String codigoSala) {
            this.type = type;
            this.idFormulario = idFormulario;
            this.idSala = idSala;
            this.codigoSala = codigoSala;
        }
    }
}
