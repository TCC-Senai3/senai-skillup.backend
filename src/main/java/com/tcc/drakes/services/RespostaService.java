package com.tcc.drakes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// É uma boa prática usar a anotação @Transactional do Spring
import org.springframework.transaction.annotation.Transactional; 

import com.tcc.drakes.dtos.RespostaDTO;
import com.tcc.drakes.entities.Alternativa;
import com.tcc.drakes.entities.Pergunta;
import com.tcc.drakes.entities.Resposta;
import com.tcc.drakes.entities.Sala;
import com.tcc.drakes.entities.Usuario;
import com.tcc.drakes.repositories.AlternativaRepository;
import com.tcc.drakes.repositories.PerguntaRepository;
import com.tcc.drakes.repositories.RespostaRepository;
import com.tcc.drakes.repositories.SalaRepository;
import com.tcc.drakes.repositories.UsuarioRepository;

@Service
public class RespostaService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RankingService rankingService;

	@Autowired
	private PerguntaRepository perguntaRepository;

	@Autowired
	private AlternativaRepository alternativaRepository;

	@Autowired
	private RespostaRepository respostaRepository;

	@Autowired
	private SalaRepository salaRepository;

	@Transactional
	public RespostaDTO salvarResposta(RespostaDTO dto) {
		
		//System.out.println("--- DEBUG: [RespostaService] Iniciando salvamento de resposta para o usuário ID: " + dto.getIdUsuario());

		Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		Pergunta pergunta = perguntaRepository.findById(dto.getIdPergunta())
				.orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));

		Alternativa alternativa = alternativaRepository.findById(dto.getIdAlternativaSelecionada())
				.orElseThrow(() -> new RuntimeException("Alternativa não encontrada"));

		Sala sala = salaRepository.findById(dto.getIdSala())
				.orElseThrow(() -> new RuntimeException("Sala não encontrada"));

		boolean correta = alternativa.isCorreta();

		
		//System.out.println("--- DEBUG: [RespostaService] Alternativa ID " + alternativa.getIdAlternativa() + " é correta? " + correta);

		if (correta) {
			//System.out.println("--- DEBUG: [RespostaService] RESPOSTA CORRETA! Chamando rankingService.adicionarPontuacao...");
			rankingService.adicionarPontuacao(dto.getIdUsuario(), dto.getIdSala());
			//System.out.println("--- DEBUG: [RespostaService] rankingService.adicionarPontuacao FOI EXECUTADO.");
		} else {
			//System.out.println("--- DEBUG: [RespostaService] Resposta INCORRETA. Nenhuma pontuação será adicionada.");
		}

		Resposta resposta = new Resposta();
		resposta.setUsuario(usuario);
		resposta.setPergunta(pergunta);
		resposta.setAlternativaSelecionada(alternativa);
		resposta.setTempoGasto(dto.getTempoGasto());
		resposta.setRespostaCorreta(correta);
		resposta.setSala(sala);

		Resposta respostaSalva = respostaRepository.save(resposta);
		
		//System.out.println("--- DEBUG: [RespostaService] Resposta salva no banco de dados com sucesso.");

		return new RespostaDTO(respostaSalva);
	}

	public List<RespostaDTO> listarRespostas() {
		return respostaRepository.findAll().stream().map(RespostaDTO::new).collect(Collectors.toList());
	}
}