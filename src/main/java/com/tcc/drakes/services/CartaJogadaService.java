package com.tcc.drakes.services;

import com.tcc.drakes.entities.CartaJogada;
import com.tcc.drakes.repositories.CartaJogadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaJogadaService {

    @Autowired
    private CartaJogadaRepository repository;

    public List<CartaJogada> findAll() {
        return repository.findAll();
    }

    public Optional<CartaJogada> findById(long id) {
        return repository.findById(id);
    }

    public CartaJogada save(CartaJogada cartaJogada) {
        return repository.save(cartaJogada);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
