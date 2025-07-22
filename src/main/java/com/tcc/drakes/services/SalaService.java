package com.tcc.drakes.services;

import com.tcc.drakes.dtos.SalaDTO;
import com.tcc.drakes.entities.Formulario;
import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.repositories.FormularioRepository;
import com.tcc.drakes.repositories.SalaRepository;
import com.tcc.drakes.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaService {

    @Autowired
    private SalaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FormularioRepository formularioRepository; // ✅ Novo

    public List<SalaDTO> findAll() {
        List<Sala> salas = repository.findAll();
        return salas.stream().map(SalaDTO::new).collect(Collectors.toList());
    }

    public SalaDTO findById(Long id) {
        Optional<Sala> sala = repository.findById(id);
        return sala.map(SalaDTO::new).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
    }

    public SalaDTO insert(SalaDTO dto) {
        Sala sala = new Sala();
        copyDtoToEntity(dto, sala);

        if (dto.getIdFormulario() != null) {
            // ✅ Vincula formulário existente
            Formulario formulario = formularioRepository.findById(dto.getIdFormulario())
                    .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));
            sala.setFormulario(formulario);
        } else {
            // ✅ Cria novo formulário
            Formulario formulario = new Formulario();
            formulario.setTitulo("Formulário da sala: " + sala.getNomeSala());
            sala.setFormulario(formulario);
        }

        sala.setDataCriacao(LocalDate.now());

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

    // ✅ Adiciona um usuário à lista de participantes da sala
    public String entrarNaSala(Long idSala, Long idUsuario) {
        Sala sala = repository.findById(idSala)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!sala.getParticipantes().contains(usuario)) {
            sala.getParticipantes().add(usuario);
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
}
