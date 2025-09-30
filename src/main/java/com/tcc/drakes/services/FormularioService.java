package com.tcc.drakes.services;

import com.tcc.drakes.dtos.AlternativaDTO;
import com.tcc.drakes.dtos.FormularioDTO;
import com.tcc.drakes.dtos.PerguntaDTO;
import com.tcc.drakes.entities.Alternativa;
import com.tcc.drakes.entities.Formulario;
import com.tcc.drakes.entities.Pergunta;
import com.tcc.drakes.entities.Tema;
import com.tcc.drakes.repositories.AlternativaRepository;
import com.tcc.drakes.repositories.FormularioRepository;
import com.tcc.drakes.repositories.PerguntaRepository;
import com.tcc.drakes.repositories.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormularioService {

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    // Injeções adicionadas para o novo método
    @Autowired
    private TemaRepository temaRepository;

    @Autowired
    private AlternativaRepository alternativaRepository;


    @Transactional(readOnly = true)
    public List<FormularioDTO> findAll() {
        List<Formulario> formularios = formularioRepository.findAll();
        return formularios.stream().map(FormularioDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FormularioDTO findById(Long id) {
        Formulario formulario = formularioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));
        return new FormularioDTO(formulario);
    }

    @Transactional
    public FormularioDTO create(FormularioDTO dto) {
        Formulario entity = new Formulario();
        entity.setTitulo(dto.getTitulo());
        formularioRepository.save(entity);
        return new FormularioDTO(entity);
    }

    @Transactional
    public FormularioDTO associarPerguntaExistente(Long idFormulario, Long idPergunta) {
        Formulario formulario = formularioRepository.findById(idFormulario)
                .orElseThrow(() -> new RuntimeException("Formulário não encontrado"));

        Pergunta pergunta = perguntaRepository.findById(idPergunta)
                .orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));

        pergunta.setFormulario(formulario);
        perguntaRepository.save(pergunta);

        return new FormularioDTO(formulario);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!formularioRepository.existsById(id)) {
            throw new RuntimeException("Formulário não encontrado");
        }
        formularioRepository.deleteById(id);
    }

    // --- NOVO MÉTODO PARA CRIAR TUDO DE UMA VEZ ---
    @Transactional
    public FormularioDTO criarFormularioCompleto(FormularioDTO dto) {
        // 1. Criar e salvar a entidade principal: Formulário
        Formulario formulario = new Formulario();
        formulario.setTitulo(dto.getTitulo());
        formulario = formularioRepository.save(formulario);

        // Lista para guardar as perguntas criadas
        List<Pergunta> perguntasSalvas = new ArrayList<>();

        // 2. Iterar sobre a lista de DTOs de Pergunta
        for (PerguntaDTO perguntaDto : dto.getPerguntas()) {
            Pergunta pergunta = new Pergunta();
            pergunta.setTextoPergunta(perguntaDto.getTextoPergunta());
            pergunta.setFormulario(formulario); // Associa ao formulário recém-criado

            // Busca e associa o Tema
            Tema tema = temaRepository.findById(perguntaDto.getTema().getIdTema())
                    .orElseThrow(() -> new RuntimeException("Tema não encontrado com ID: " + perguntaDto.getTema().getIdTema()));
            pergunta.setTema(tema);

            // Salva a pergunta para obter seu ID
            pergunta = perguntaRepository.save(pergunta);
            
            List<Alternativa> alternativasSalvas = new ArrayList<>();
            // 3. Iterar sobre a lista de DTOs de Alternativa da pergunta atual
            if (perguntaDto.getAlternativas() != null) {
                for (AlternativaDTO alternativaDto : perguntaDto.getAlternativas()) {
                    Alternativa alternativa = new Alternativa();
                    alternativa.setTextoAlternativa(alternativaDto.getTextoAlternativa());
                    alternativa.setCorreta(alternativaDto.isCorreta());
                    alternativa.setPergunta(pergunta); // Associa à pergunta recém-criada

                    alternativa = alternativaRepository.save(alternativa);
                    alternativasSalvas.add(alternativa);
                }
            }
            pergunta.setAlternativas(alternativasSalvas);
            perguntasSalvas.add(pergunta);
        }

        formulario.setPerguntas(perguntasSalvas);

        // Retorna o DTO do formulário completo que foi salvo
        return new FormularioDTO(formulario);
    }
}