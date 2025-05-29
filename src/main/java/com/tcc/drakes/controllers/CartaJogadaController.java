package com.tcc.drakes.controllers;

import com.tcc.drakes.entities.CartaJogada;
import com.tcc.drakes.services.CartaJogadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cartas-jogadas")
public class CartaJogadaController {

    @Autowired
    private CartaJogadaService service;

    @GetMapping
    public List<CartaJogada> listarTodos() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<CartaJogada> buscarPorId(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    public CartaJogada criar(@RequestBody CartaJogada cartaJogada) {
        return service.save(cartaJogada);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable long id) {
        service.delete(id);
    }
}
