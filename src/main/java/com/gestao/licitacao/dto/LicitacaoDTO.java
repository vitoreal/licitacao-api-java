package com.gestao.licitacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados públicos de uma licitação retornada pela API")
public class LicitacaoDTO {

    @Schema(description = "ID da licitação", example = "1")
    private Long id;

    @Schema(description = "Código da UASG", example = "130002")
    private String codigo_uasg;

    @Schema(description = "Número do pregão", example = "5/2025")
    private String numero_pregao;

    @Schema(description = "Descrição do objeto da licitação", example = "Aquisição de materiais de informática")
    private String objeto;

    @Schema(description = "Data e hora da entrega da proposta", example = "03/06/2025 10:00")
    private String data_proposta;

    @Schema(description = "Indica se a licitação já foi visualizada", example = "false")
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
