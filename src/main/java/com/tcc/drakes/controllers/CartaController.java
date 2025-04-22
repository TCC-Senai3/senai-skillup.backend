package com.tcc.drakes.controllers;

import com.tcc.drakes.entities.Carta;
import com.tcc.drakes.services.CartaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartas")
public class CartaController {

    private final CartaService cartaService;

    @Autowired
    public CartaController(CartaService cartaService) {
        this.cartaService = cartaService;
    }

    // Criar uma nova carta
    @PostMapping
    public ResponseEntity<Carta> criarCarta(@RequestBody Carta carta) {
        Carta cartaCriada = cartaService.criarCarta(carta);
        return new ResponseEntity<>(cartaCriada, HttpStatus.CREATED);
    }

    // Obter todas as cartas
    @GetMapping
    public ResponseEntity<List<Carta>> obterTodasCartas() {
        List<Carta> cartas = cartaService.obterTodasCartas();
        return new ResponseEntity<>(cartas, HttpStatus.OK);
    }

    // Obter uma carta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Carta> obterCartaPorId(@PathVariable Long id) {
        Optional<Carta> carta = cartaService.obterCartaPorId(id);
        return carta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar uma carta
    @PutMapping("/{id}")
    public ResponseEntity<Carta> atualizarCarta(@PathVariable Long id, @RequestBody Carta carta) {
        Carta cartaAtualizada = cartaService.atualizarCarta(id, carta);
        return cartaAtualizada != null ?
                new ResponseEntity<>(cartaAtualizada, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Deletar uma carta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCarta(@PathVariable Long id) {
        boolean deletado = cartaService.deletarCarta(id);
        return deletado ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
