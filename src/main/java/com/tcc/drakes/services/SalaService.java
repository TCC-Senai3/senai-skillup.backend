package com.tcc.drakes.services;

import com.tcc.drakes.dtos.SalaDTO;
import com.tcc.drakes.entities.Formulario;
import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.entities.SalaUsuario; // Importar a nova entidade
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.repositories.FormularioRepository;
import com.tcc.drakes.repositories.SalaRepository;
import com.tcc.drakes.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SalaService {

    @Autowired
    private SalaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FormularioRepository formularioRepository;

    public List<SalaDTO> findAll() {
        List<Sala> salas = repository.findAll();
        return salas.stream().map(SalaDTO::new).collect(Collectors.toList());
    }

    public SalaDTO findById(Long id) {
        Optional<Sala> sala = repository.findById(id);
        return sala.map(SalaDTO::new).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
    }

    public SalaDTO findByCodigo(String codigo) {
        Sala sala = repository.findByCodigoSala(codigo)
                .orElseThrow(() -> new RuntimeException("Sala com o código '" + codigo + "' não encontrada"));
        return new SalaDTO(sala);
    }

    public SalaDTO insert(SalaDTO dto) {
        Sala sala = new Sala();
        copyDtoToEntity(dto, sala);

        if (dto.getIdFormulario() != null) {
            Formulario formulario = formularioRepository.findById(dto.getIdFormulario())
                    .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));
            sala.setFormulario(formulario);
        } else {
            Formulario formulario = new Formulario();
            formulario.setTitulo("Formulário da sala: " + sala.getNomeSala());
            sala.setFormulario(formulario);
        }

        sala.setDataCriacao(LocalDate.now());
        sala.setCodigoSala(gerarCodigoUnico());

        sala = repository.save(sala);
        return new SalaDTO(sala);
    }

    public SalaDTO update(Long id, SalaDTO dto) {
        Sala sala = repository.findById(id).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
        copyDtoToEntity(dto, sala);
        sala = repository.save(sala);
        return new SalaDTO(sala);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<Sala> buscarSalaPorId(Long idSala) {
        return repository.findById(idSala);
    }

    // Método entrarNaSala corrigido
    public String entrarNaSala(String codigoSala, Long idUsuario) {
        Sala sala = repository.findByCodigoSala(codigoSala)
                .orElseThrow(() -> new RuntimeException("Sala com o código '" + codigoSala + "' não encontrada"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Lógica de verificação corrigida
        boolean usuarioJaParticipa = sala.getParticipantes().stream()
                .anyMatch(su -> su.getUsuario().getId() == usuario.getId());

        // Lógica de adição corrigida
        if (!usuarioJaParticipa) {
            SalaUsuario novaAssociacao = new SalaUsuario(sala, usuario);
            sala.getParticipantes().add(novaAssociacao);
            repository.save(sala);
        }

        return "Usuário " + usuario.getNome() + " entrou na sala: " + sala.getNomeSala();
    }

    private void copyDtoToEntity(SalaDTO dto, Sala entity) {
        entity.setIdUsuario(dto.getIdUsuario());
        entity.setIdTema(dto.getIdTema());
        entity.setNomeSala(dto.getNomeSala());
        entity.setStatusSala(dto.getStatusSala());
    }

    private String gerarCodigoUnico() {
        String codigo;
        do {
            int numeroAleatorio = ThreadLocalRandom.current().nextInt(100000, 1000000);
            codigo = String.valueOf(numeroAleatorio);
        } while (repository.existsByCodigoSala(codigo));

        return codigo;
    }
}