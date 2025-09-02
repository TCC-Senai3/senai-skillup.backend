package com.tcc.drakes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.drakes.dtos.RespostaDTO;
import com.tcc.drakes.entities.Resposta;
import com.tcc.drakes.repositories.AlternativaRepository;
import com.tcc.drakes.repositories.PerguntaRepository;
import com.tcc.drakes.repositories.RespostaRepository;
import com.tcc.drakes.repositories.SalaRepository;
import com.tcc.drakes.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

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
		var usuario = usuarioRepository.findById(dto.getIdUsuario())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		var pergunta = perguntaRepository.findById(dto.getIdPergunta())
				.orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));

		var alternativa = alternativaRepository.findById(dto.getIdAlternativaSelecionada())
				.orElseThrow(() -> new RuntimeException("Alternativa não encontrada"));

		var sala = salaRepository.findById(dto.getIdSala())
				.orElseThrow(() -> new RuntimeException("Sala não encontrada"));

		boolean correta = alternativa.isCorreta();

		if (correta) {
			rankingService.adicionarPontuacao(dto.getIdUsuario(), dto.getIdSala());
		}

		Resposta resposta = new Resposta();
		resposta.setUsuario(usuario);
		resposta.setPergunta(pergunta);
		resposta.setAlternativaSelecionada(alternativa);
		resposta.setTempoGasto(dto.getTempoGasto());
		resposta.setRespostaCorreta(correta);
		resposta.setSala(sala);

		Resposta respostaSalva = respostaRepository.save(resposta);

		return new RespostaDTO(respostaSalva);
	}

	public List<RespostaDTO> listarRespostas() {
		return respostaRepository.findAll().stream().map(RespostaDTO::new).collect(Collectors.toList());
	}

}
