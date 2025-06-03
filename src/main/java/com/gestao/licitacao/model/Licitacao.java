package com.gestao.licitacao.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Schema(description = "Entidade que representa uma licitação do ComprasNet")
@Table(name = "licitacao")
public class Licitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da licitação", example = "1")
    private Long id;
    
    @Column(name = "codigo_uasg")
    @Schema(description = "Código da UASG", example = "130002")
    private String codigoUasg;
    
    @Column(name = "numero_pregao")
    @Schema(description = "Número do pregão", example = "5/2025")
    private String numeroPregao;
    
    @Column(name = "objeto")
    @Schema(description = "Objeto da licitação", example = "Aquisição de materiais de escritório")
    private String objeto;
    
    @Column(name = "data_proposta")
    @Schema(description = "Data de entrega da proposta", example = "03/06/2025")
    private LocalDateTime dataProposta;
    
    @Column(name = "visualizada")
    @Schema(description = "Indica se a licitação já foi visualizada", example = "true")
    private boolean visualizada;

    public Licitacao() {
    }

    public Licitacao(String codigoUasg, String numeroPregao, String objeto, LocalDateTime dataProposta, boolean visualizada) {
        this.codigoUasg = codigoUasg;
        this.numeroPregao = numeroPregao;
        this.objeto = objeto;
        this.dataProposta = dataProposta;
        this.visualizada = visualizada;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getCodigoUasg() {
        return codigoUasg;
    }

    public void setCodigoUasg(String codigoUasg) {
        this.codigoUasg = codigoUasg;
    }

    public String getNumeroPregao() {
        return numeroPregao;
    }

    public void setNumeroPregao(String numeroPregao) {
        this.numeroPregao = numeroPregao;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public LocalDateTime getDataProposta() {
        return dataProposta;
    }

    public void setDataProposta(LocalDateTime dataProposta) {
        this.dataProposta = dataProposta;
    }

    public boolean isVisualizada() {
        return visualizada;
    }

    public void setVisualizada(boolean visualizada) {
        this.visualizada = visualizada;
    }

    @Override
    public String toString() {
        return "Licitacao{" +
                "id=" + id +
                ", codigoUasg='" + codigoUasg + '\'' +
                ", numeroPregao='" + numeroPregao + '\'' +
                ", objeto='" + objeto + '\'' +
                ", dataProposta=" + dataProposta +
                ", visualizada=" + visualizada +
                '}';
    }
}
