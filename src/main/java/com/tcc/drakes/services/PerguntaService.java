package com.tcc.drakes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcc.drakes.dtos.PerguntaDTO;
import com.tcc.drakes.entities.Pergunta;
import com.tcc.drakes.entities.Tema; // Importar Tema
import com.tcc.drakes.repositories.PerguntaRepository;
import com.tcc.drakes.repositories.TemaRepository; // Importar TemaRepository para buscar o Tema

@Service
public class PerguntaService {

	@Autowired
	private PerguntaRepository perguntaRepository;
	
	@Autowired
	private TemaRepository temaRepository; // Para buscar o Tema ao qual a pergunta pertence
	
	@Transactional
	public PerguntaDTO criarPergunta(PerguntaDTO perguntaDTO) {
		Pergunta pergunta = new Pergunta();
		pergunta.setTextoPergunta(perguntaDTO.getTextoPergunta());
		
		
		if (perguntaDTO.getTema() != null && perguntaDTO.getTema().getIdTema() != null) {
			Tema tema = temaRepository.findById(perguntaDTO.getTema().getIdTema())
					.orElseThrow(() -> new RuntimeException("Tema não encontrado com ID: " + perguntaDTO.getTema().getIdTema()));
			pergunta.setTema(tema);
		} else {
			
			throw new IllegalArgumentException("Uma pergunta deve estar associada a um Tema.");
		}
		
		
		
		Pergunta savedPergunta = perguntaRepository.save(pergunta);
		return new PerguntaDTO(savedPergunta);
	}
	
	@Transactional(readOnly = true)
	public List<PerguntaDTO> findAllPerguntas() {
		List<Pergunta> perguntas = perguntaRepository.findAll();
		return perguntas.stream().map(PerguntaDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public PerguntaDTO findPerguntaById(Long id) {
	    Pergunta pergunta = perguntaRepository.findById(id)
	                                 .orElseThrow(() -> new RuntimeException("Pergunta não encontrada com ID: " + id));
	    return new PerguntaDTO(pergunta);
	}
	
	@Transactional(readOnly = true)
	public List<PerguntaDTO> findPerguntasByTemaId(Long temaId) {
	    
	    temaRepository.findById(temaId)
	                  .orElseThrow(() -> new RuntimeException("Tema não encontrado com ID: " + temaId));
	    
	    List<Pergunta> perguntas = perguntaRepository.findByTemaIdTema(temaId); 
	    return perguntas.stream().map(PerguntaDTO::new).collect(Collectors.toList());
	}

	@Transactional
    public PerguntaDTO updatePergunta(Long id, PerguntaDTO perguntaDTO) {
        Pergunta pergunta = perguntaRepository.findById(id)
                                 .orElseThrow(() -> new RuntimeException("Pergunta não encontrada com ID: " + id));
        
        pergunta.setTextoPergunta(perguntaDTO.getTextoPergunta());
        
     
        if (perguntaDTO.getTema() != null && perguntaDTO.getTema().getIdTema() != null && 
        		!perguntaDTO.getTema().getIdTema().equals(pergunta.getTema().getIdTema())) {
        	Tema novoTema = temaRepository.findById(perguntaDTO.getTema().getIdTema())
        			.orElseThrow(() -> new RuntimeException("Novo Tema não encontrado com ID: " + perguntaDTO.getTema().getIdTema()));
        	pergunta.setTema(novoTema);
        }

        Pergunta updatedPergunta = perguntaRepository.save(pergunta);
        return new PerguntaDTO(updatedPergunta);
    }

    @Transactional
    public void deletePergunta(Long id) {
        if (!perguntaRepository.existsById(id)) {
            throw new RuntimeException("Pergunta não encontrada com ID: " + id);
        }
        perguntaRepository.deleteById(id);
    }
}