package com.tcc.drakes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.drakes.dtos.TemaDTO;
import com.tcc.drakes.entities.Tema;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.repositories.TemaRepository;

@Service
public class TemaService {
	
	@Autowired
	private TemaRepository temaRepository;
	
	 public Tema criarTema(TemaDTO temaDTO) {
	      
	        Tema tema = new Tema();
	        tema.setNomeTema(temaDTO.getNomeTema());
	        
	        return temaRepository.save(tema);
	    }

}
