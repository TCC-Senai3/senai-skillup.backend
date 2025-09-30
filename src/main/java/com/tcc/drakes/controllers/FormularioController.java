package com.tcc.drakes.controllers;

import com.tcc.drakes.dtos.FormularioDTO;
import com.tcc.drakes.services.FormularioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formularios")
public class FormularioController {

    @Autowired
    private FormularioService formularioService;

    @GetMapping
    public ResponseEntity<List<FormularioDTO>> findAll() {
        return ResponseEntity.ok(formularioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormularioDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(formularioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FormularioDTO> create(@RequestBody FormularioDTO dto) {
        return ResponseEntity.ok(formularioService.create(dto));
    }

    @PostMapping("/{idFormulario}/perguntas/{idPergunta}")
    public ResponseEntity<FormularioDTO> associarPerguntaExistente(
            @PathVariable Long idFormulario,
            @PathVariable Long idPergunta) {
        return ResponseEntity.ok(formularioService.associarPerguntaExistente(idFormulario, idPergunta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        formularioService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/completo")
    public ResponseEntity<FormularioDTO> createCompleto(@RequestBody FormularioDTO dto) {
        FormularioDTO formularioSalvo = formularioService.criarFormularioCompleto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(formularioSalvo);
    }
    
}