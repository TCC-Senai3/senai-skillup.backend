package com.tcc.drakes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcc.drakes.dtos.AlternativaDTO;
import com.tcc.drakes.entities.Alternativa;
import com.tcc.drakes.entities.Pergunta; 
import com.tcc.drakes.repositories.AlternativaRepository;
import com.tcc.drakes.repositories.PerguntaRepository; 

@Service
public class AlternativaService {

	@Autowired
	private AlternativaRepository alternativaRepository;
	
	@Autowired
	private PerguntaRepository perguntaRepository; 
	
	@Transactional
	public AlternativaDTO criarAlternativa(AlternativaDTO alternativaDTO) {
		Alternativa alternativa = new Alternativa();
		alternativa.setTextoAlternativa(alternativaDTO.getTextoAlternativa());
		alternativa.setCorreta(alternativaDTO.isCorreta());
		
		
		if (alternativaDTO.getIdPergunta() != null) {
			Pergunta pergunta = perguntaRepository.findById(alternativaDTO.getIdPergunta())
					.orElseThrow(() -> new RuntimeException("Pergunta não encontrada com ID: " + alternativaDTO.getIdPergunta()));
			alternativa.setPergunta(pergunta);
		} else {
			throw new IllegalArgumentException("Uma alternativa deve estar associada a uma Pergunta.");
		}
		
		Alternativa savedAlternativa = alternativaRepository.save(alternativa);
		return new AlternativaDTO(savedAlternativa);
	}
	
	@Transactional(readOnly = true)
	public List<AlternativaDTO> findAllAlternativas() {
		List<Alternativa> alternativas = alternativaRepository.findAll();
		return alternativas.stream().map(AlternativaDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public AlternativaDTO findAlternativaById(Long id) {
	    Alternativa alternativa = alternativaRepository.findById(id)
	                                 .orElseThrow(() -> new RuntimeException("Alternativa não encontrada com ID: " + id));
	    return new AlternativaDTO(alternativa);
	}
	
	@Transactional(readOnly = true)
	public List<AlternativaDTO> findAlternativasByPerguntaId(Long perguntaId) {
	    
	    perguntaRepository.findById(perguntaId)
	                  .orElseThrow(() -> new RuntimeException("Pergunta não encontrada com ID: " + perguntaId));
	    
	    List<Alternativa> alternativas = alternativaRepository.findByPerguntaIdPergunta(perguntaId); 
	    return alternativas.stream().map(AlternativaDTO::new).collect(Collectors.toList());
	}
	
	@Transactional
    public AlternativaDTO updateAlternativa(Long id, AlternativaDTO alternativaDTO) {
        Alternativa alternativa = alternativaRepository.findById(id)
                                 .orElseThrow(() -> new RuntimeException("Alternativa não encontrada com ID: " + id));
        
        alternativa.setTextoAlternativa(alternativaDTO.getTextoAlternativa());
        alternativa.setCorreta(alternativaDTO.isCorreta());
        
        
        if (alternativaDTO.getIdPergunta() != null && 
        		!alternativaDTO.getIdPergunta().equals(alternativa.getPergunta().getIdPergunta())) {
        	Pergunta novaPergunta = perguntaRepository.findById(alternativaDTO.getIdPergunta())
        			.orElseThrow(() -> new RuntimeException("Nova Pergunta não encontrada com ID: " + alternativaDTO.getIdPergunta()));
        	alternativa.setPergunta(novaPergunta);
        }

        Alternativa updatedAlternativa = alternativaRepository.save(alternativa);
        return new AlternativaDTO(updatedAlternativa);
    }

    @Transactional
    public void deleteAlternativa(Long id) {
        if (!alternativaRepository.existsById(id)) {
            throw new RuntimeException("Alternativa não encontrada com ID: " + id);
        }
        alternativaRepository.deleteById(id);
    }
}