package com.tcc.drakes.services;

import java.time.LocalDate; 
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcc.drakes.dtos.SalaDTO;
import com.tcc.drakes.entities.Formulario;
import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.entities.SalaUsuario; 
import com.tcc.drakes.entities.StatusSala; 
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.repositories.FormularioRepository;
import com.tcc.drakes.repositories.SalaRepository;
import com.tcc.drakes.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

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
	private SimpMessagingTemplate simpMessagingTemplate; 


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
        sala.setDataCriacao(LocalDate.now()); // LocalDate requer import
        sala.setCodigoSala(gerarCodigoUnico());
        sala.setStatusSala(StatusSala.DISPONIVEL); // StatusSala requer import
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

        // Altera status para FECHADA
        sala.setStatusSala(StatusSala.FECHADA);
        sala = repository.save(sala);

        try {
            String destination = "/topic/sala/" + sala.getCodigoSala();
            simpMessagingTemplate.convertAndSend(destination, new SalaFechadaMensagem(sala.getCodigoSala()));
            System.out.println("Notificação WS enviada: SALA_FECHADA para " + destination);
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação WebSocket (fecharSala): " + e.getMessage());
        }

        return new SalaDTO(sala);
    }

	 // Método 'iniciarSala' (Sem Alterações)

	@Transactional
    public Sala iniciarSala(String codigoSala, Long idUsuario) {
        Sala sala = repository.findByCodigoSala(codigoSala)
                .orElseThrow(() -> new EntityNotFoundException("Sala com o código '" + codigoSala + "' não encontrada"));
        if (!sala.getIdUsuario().equals(idUsuario)) {
            throw new IllegalStateException("Apenas o dono da sala pode iniciar o jogo.");
        }
        if (sala.getStatusSala() == StatusSala.INICIADA) {
            throw new IllegalStateException("A sala já foi iniciada.");
        }
        // Aqui precisamos garantir que a coleção 'participantes' foi inicializada se for Lazy
        // sala.getParticipantes().size(); // Esta linha pode causar LazyInitializationException se não houver @Transactional
        sala.setStatusSala(StatusSala.INICIADA);
        sala = repository.save(sala);
        simpMessagingTemplate.convertAndSend(
                "/topic/sala/" + codigoSala,
                new SalaMensagem("JOGO_INICIADO", sala.getFormulario().getIdFormulario(), sala.getIdSala(), codigoSala)
        );
        return sala;
    }

	
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

        SalaUsuario novaAssociacao = new SalaUsuario(sala, usuario); // SalaUsuario requer import
        sala.getParticipantes().add(novaAssociacao);

        if (sala.getParticipantes().size() >= LIMITE_PARTICIPANTES) {
            sala.setStatusSala(StatusSala.CHEIA);
        }
        repository.save(sala);

        // Envio de notificação WS (USUARIO_ENTROU)
        try {
            String destination = "/topic/sala/" + codigoSala;
            simpMessagingTemplate.convertAndSend(destination, new UsuarioEntrouMensagem(usuario));
            System.out.println("Notificação WS enviada: USUARIO_ENTROU para " + destination);
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação WebSocket (entrarNaSala): " + e.getMessage());
        }

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
            Usuario usuarioExpulso = su.getUsuario(); // Pega o objeto Usuario para a notificação privada

            sala.getParticipantes().remove(su);
            if (sala.getStatusSala() == StatusSala.CHEIA) sala.setStatusSala(StatusSala.DISPONIVEL);
            repository.save(sala);

            // 1. Envia notificação pública (USUARIO_SAIU) para os restantes
            try {
                String destination = "/topic/sala/" + codigoSala;
                simpMessagingTemplate.convertAndSend(destination, new UsuarioSaiuMensagem(idUsuario));
                System.out.println("Notificação WS enviada: USUARIO_SAIU para " + destination);
            } catch (Exception e) {
                System.err.println("Erro ao enviar notificação WebSocket (removerParticipante): " + e.getMessage());
            }

            // 2. Envia notificação PRIVADA (EXPULSO) APENAS para o usuário removido
            if (usuarioExpulso != null && usuarioExpulso.getEmail() != null) {
                try {
                    // Destino: /user/{username}/queue/expulso
                    simpMessagingTemplate.convertAndSendToUser(
                        usuarioExpulso.getEmail(),
                        "/queue/expulso", // Canal privado que o Front-end deve assinar
                        new ExpulsoMensagem(sala.getIdSala())
                    );
                    System.out.println("Notificação WS PRIVADA enviada: EXPULSO para " + usuarioExpulso.getEmail());
                } catch (Exception e) {
                    System.err.println("Erro ao enviar notificação WebSocket PRIVADA (EXPULSO): " + e.getMessage());
                }
            }
        });
    }

	@Transactional
    public void expulsarUsuario(String codigoSala, Long idUsuarioExpulso, String usernameDono)
            throws IllegalAccessException {

        Sala sala = repository.findByCodigoSala(codigoSala)
                .orElseThrow(() -> new EntityNotFoundException("Sala com código " + codigoSala + " não encontrada."));

        Usuario dono = usuarioRepository.findByEmail(usernameDono)
                .orElseThrow(() -> new EntityNotFoundException("Usuário dono logado não encontrado."));

        if (!sala.getIdUsuario().equals(dono.getId())) {
            throw new IllegalAccessException("Apenas o dono da sala pode expulsar participantes.");
        }

        if (sala.getIdUsuario().equals(idUsuarioExpulso)) {
            throw new IllegalAccessException("O dono da sala não pode ser expulso.");
        }
        removerParticipante(codigoSala, idUsuarioExpulso);
    }


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

	 // --- gerarCodigoUnico (Mantido) ---

	private String gerarCodigoUnico() {
        String codigo;
        do {
            int numeroAleatorio = ThreadLocalRandom.current().nextInt(100000, 1000000);
            codigo = String.valueOf(numeroAleatorio);
        } while (repository.existsByCodigoSala(codigo));
        return codigo;
    }




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

	 // Payload para enviar os dados de um usuário (Mantido)

	public static class UsuarioPayload {

		public long id;
		public String nome;
		public String avatar;

		public UsuarioPayload(long id, String nome, String avatar) {
            this.id = id;
            this.nome = nome;
            this.avatar = avatar;
        }
	}

	 // Mensagem para quando um usuário ENTRA (Mantida)

	public static class UsuarioEntrouMensagem {

		public String type = "USUARIO_ENTROU";
		public UsuarioPayload usuario;

		public UsuarioEntrouMensagem(Usuario usuario) {
            // Assumindo que você tem um método getAvatar() ou que o campo 'avatar' seja acessível na entidade Usuario
            this.usuario = new UsuarioPayload(usuario.getId(), usuario.getNome(), null); // Ajuste o 'null' se tiver o avatar
        }
	}

	 // Mensagem para quando um usuário SAI (Mantida)

	public static class UsuarioSaiuMensagem {

		public String type = "USUARIO_SAIU";
		public Long idUsuario;

		public UsuarioSaiuMensagem(Long idUsuario) {
            this.idUsuario = idUsuario;
        }
	}


	public static class ExpulsoMensagem {

		public String type = "EXPULSO";
		public Long idSala;

		public ExpulsoMensagem(Long idSala) {
            this.idSala = idSala;
        }
	}


	public static class SalaFechadaMensagem {

		public String type = "SALA_FECHADA";
		public String codigoSala;

		public SalaFechadaMensagem(String codigoSala) {
            this.codigoSala = codigoSala;
        }
	}

}