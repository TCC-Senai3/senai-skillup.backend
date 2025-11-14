package com.tcc.drakes.dtos;

public class RankingDTO {
	private String nomeUsuario;
	private Long pontuacao;
	private String avatar;
	private Long idUsuario; // ✅ CAMPO NOVO

	public RankingDTO() {
	}

	// ✅ CONSTRUTOR ATUALIZADO (com 4 argumentos)
	public RankingDTO(String nomeUsuario, Long pontuacao, String avatar, Long idUsuario) {
		this.nomeUsuario = nomeUsuario;
		this.pontuacao = pontuacao;
		this.avatar = avatar;
		this.idUsuario = idUsuario; // ✅ LINHA NOVA
	}

	// ✅ GETTER E SETTER PARA O CAMPO NOVO
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	// ... (Seus getters e setters antigos) ...
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Long getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Long pontuacao) {
		this.pontuacao = pontuacao;
	}
}