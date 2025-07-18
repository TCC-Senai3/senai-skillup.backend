//package com.tcc.drakes.dtos;
//
//import com.tcc.drakes.entities.Ranking;
//
//import java.time.LocalDateTime;
//
//public class RankingDTO {
//
//    private Long idRanking;
//    private Long idUsuario;
//    private String nomeUsuario; // útil para exibir no ranking
//    private Long idSala;
//    private String nomeSala; // útil para exibir o nome da sala
//    private Long pontuacao;
//
//    public RankingDTO() {
//    }
//
//    public RankingDTO(Ranking entity) {
//        this.idRanking = entity.getIdRanking();
//        this.idUsuario = entity.getUsuario() != null ? entity.getUsuario().getId() : null;
//        this.nomeUsuario = entity.getUsuario() != null ? entity.getUsuario().getNome() : null;
//        this.idSala = entity.getSala() != null ? entity.getSala().getIdSala() : null;
//        this.nomeSala = entity.getSala() != null ? entity.getSala().getNomeSala() : null;
//        this.pontuacao = entity.getPontuacao();
//       
//    }
//    
//
//    public RankingDTO(Long idRanking, Long idUsuario, String nomeUsuario, Long idSala, String nomeSala,
//			Long pontuacao) {
//		this.idRanking = idRanking;
//		this.idUsuario = idUsuario;
//		this.nomeUsuario = nomeUsuario;
//		this.idSala = idSala;
//		this.nomeSala = nomeSala;
//		this.pontuacao = pontuacao;
//	}
//
//	// Getters e Setters
//
//    public Long getIdRanking() {
//        return idRanking;
//    }
//
//    public void setIdRanking(Long idRanking) {
//        this.idRanking = idRanking;
//    }
//
//    public Long getIdUsuario() {
//        return idUsuario;
//    }
//
//    public void setIdUsuario(Long idUsuario) {
//        this.idUsuario = idUsuario;
//    }
//
//    public String getNomeUsuario() {
//        return nomeUsuario;
//    }
//
//    public void setNomeUsuario(String nomeUsuario) {
//        this.nomeUsuario = nomeUsuario;
//    }
//
//    public Long getIdSala() {
//        return idSala;
//    }
//
//    public void setIdSala(Long idSala) {
//        this.idSala = idSala;
//    }
//
//    public String getNomeSala() {
//        return nomeSala;
//    }
//
//    public void setNomeSala(String nomeSala) {
//        this.nomeSala = nomeSala;
//    }
//
//    public Long getPontuacao() {
//        return pontuacao;
//    }
//
//    public void setPontuacao(Long pontuacao) {
//        this.pontuacao = pontuacao;
//    }
//
// 
//}
