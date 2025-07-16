package com.tcc.drakes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.drakes.dtos.TemaDTO;
import com.tcc.drakes.entities.Tema;
import com.tcc.drakes.services.TemaService;

@RestController
@RequestMapping("/tema")
public class TemaController {
	
	@Autowired
	private TemaService temaService;
	
	  @PostMapping("/cadastro")
	    public Tema cadastrarTema(@RequestBody TemaDTO temaDTO) {
	        return temaService.criarTema(temaDTO);
	    }

}
