package com.tcc.drakes.controllers;

import com.tcc.drakes.dtos.SalaDTO;
import com.tcc.drakes.services.SalaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaDTO>> findAll() {
        List<SalaDTO> list = salaService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> findById(@PathVariable Long id) {
        SalaDTO dto = salaService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // ✅ NOVO ENDPOINT: Para buscar a sala pelo código de 6 dígitos.
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<SalaDTO> findByCodigo(@PathVariable String codigo) {
        SalaDTO dto = salaService.findByCodigo(codigo);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<SalaDTO> insert(@RequestBody SalaDTO dto) {
        SalaDTO newSala = salaService.insert(dto);
        return ResponseEntity.ok(newSala);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> update(@PathVariable Long id, @RequestBody SalaDTO dto) {
        SalaDTO updatedSala = salaService.update(id, dto);
        return ResponseEntity.ok(updatedSala);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/fechar")
    public ResponseEntity<SalaDTO> fecharSala(@PathVariable Long id) {
        SalaDTO salaFechada = salaService.fecharSala(id);
        return ResponseEntity.ok(salaFechada);
    }

    @PostMapping("{codigoSala}/entrar/{idUsuario}")
    public ResponseEntity<String> entrarNaSala(
            @PathVariable String codigoSala,
            @PathVariable Long idUsuario) {

        String mensagem = salaService.entrarNaSala(codigoSala, idUsuario);
        return ResponseEntity.ok(mensagem);
    }
}