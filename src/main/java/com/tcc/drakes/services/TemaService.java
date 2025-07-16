package com.tcc.drakes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcc.drakes.dtos.TemaDTO;
import com.tcc.drakes.entities.Tema;
import com.tcc.drakes.repositories.TemaRepository;

@Service
public class TemaService {
	
	@Autowired
	private TemaRepository temaRepository;
	
	@Transactional 
	public TemaDTO criarTema(TemaDTO temaDTO) {
	    Tema tema = new Tema();
	    tema.setNomeTema(temaDTO.getNomeTema());
	    
	    
	    Tema savedTema = temaRepository.save(tema);
	    return new TemaDTO(savedTema); 
	}

	@Transactional(readOnly = true) 
	public List<TemaDTO> findAllTemas() {
		List<Tema> temas = temaRepository.findAll();
		return temas.stream().map(TemaDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public TemaDTO findTemaById(Long id) {
	    Tema tema = temaRepository.findById(id)
	                             .orElseThrow(() -> new RuntimeException("Tema não encontrado com ID: " + id));
	    return new TemaDTO(tema);
	}
	
	@Transactional
    public TemaDTO updateTema(Long id, TemaDTO temaDTO) {
        Tema tema = temaRepository.findById(id)
                                 .orElseThrow(() -> new RuntimeException("Tema não encontrado com ID: " + id));
        tema.setNomeTema(temaDTO.getNomeTema());
        Tema updatedTema = temaRepository.save(tema);
        return new TemaDTO(updatedTema);
    }

    @Transactional
    public void deleteTema(Long id) {
        if (!temaRepository.existsById(id)) {
            throw new RuntimeException("Tema não encontrado com ID: " + id);
        }
        temaRepository.deleteById(id);
    }
}