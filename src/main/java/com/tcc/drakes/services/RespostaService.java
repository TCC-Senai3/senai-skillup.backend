package com.tcc.drakes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
		
		Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		Pergunta pergunta = perguntaRepository.findById(dto.getIdPergunta())
				.orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));

		Alternativa alternativa = alternativaRepository.findById(dto.getIdAlternativaSelecionada())
				.orElseThrow(() -> new RuntimeException("Alternativa não encontrada"));

		Sala sala = salaRepository.findById(dto.getIdSala())
				.orElseThrow(() -> new RuntimeException("Sala não encontrada"));

		boolean correta = alternativa.isCorreta();

		if (correta) {
			

			long pontuacaoMaxima = 100L;
			long pontuacaoMinima = 50L; 
			
			Integer tempoGasto = dto.getTempoGasto() != null ? dto.getTempoGasto() : 0;
			
			
			long penalidade = (tempoGasto / 2) * 10L;
			
			
			long pontuacaoFinal = pontuacaoMaxima - penalidade;
			
			
			if (pontuacaoFinal < pontuacaoMinima) {
				pontuacaoFinal = pontuacaoMinima;
			}
			
			
			rankingService.adicionarPontuacao(dto.getIdUsuario(), dto.getIdSala(), pontuacaoFinal);
			
			
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