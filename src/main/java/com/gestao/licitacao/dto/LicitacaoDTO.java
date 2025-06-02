package com.gestao.licitacao.dto;

public class LicitacaoDTO {
	
    private Long id;
    private String codigo_uasg;
    private String numero_pregao;
    private String objeto;
    private String data_proposta;
    private boolean visualizada;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo_uasg() {
		return codigo_uasg;
	}
	public void setCodigo_uasg(String codigo_uasg) {
		this.codigo_uasg = codigo_uasg;
	}
	public String getNumero_pregao() {
		return numero_pregao;
	}
	public void setNumero_pregao(String numero_pregao) {
		this.numero_pregao = numero_pregao;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	public String getData_proposta() {
		return data_proposta;
	}
	public void setData_proposta(String data_proposta) {
		this.data_proposta = data_proposta;
	}
	public boolean isVisualizada() {
		return visualizada;
	}
	public void setVisualizada(boolean visualizada) {
		this.visualizada = visualizada;
	}
    
}
