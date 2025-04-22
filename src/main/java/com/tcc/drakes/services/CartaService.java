package com.tcc.drakes.services;


import com.tcc.drakes.entities.Carta;
import com.tcc.drakes.repositories.CartaRepository;

import jakarta.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaService {
	

		@Id
	    private final CartaRepository cartaRepository;

	    @Autowired
	    public CartaService(CartaRepository cartaRepository) {
	        this.cartaRepository = cartaRepository;
	    }

	    // Criar uma nova carta
	    public Carta criarCarta(Carta carta) {
	        return cartaRepository.save(carta);
	    }

	    // Obter todas as cartas
	    public List<Carta> obterTodasCartas() {
	        return cartaRepository.findAll();
	    }

	    // Obter uma carta por ID
	    public Optional<Carta> obterCartaPorId(Long id) {
	        return cartaRepository.findAllById(id);
	    }

	    // Atualizar uma carta
	    public Carta atualizarCarta(Long id, Carta cartaAtualizada) {
	        if (cartaRepository.existsById(id)) {
	            cartaAtualizada.setId_carta(id);
	            return cartaRepository.save(cartaAtualizada);
	        }
	        return null;  // Ou lançar uma exceção personalizada
	    }

	    // Deletar uma carta
	    public boolean deletarCarta(Long id) {
	        if (cartaRepository.existsById(id)) {
	            cartaRepository.deleteById(id);
	            return true;
	        }
	        return false;
	    }
	}



