package com.gestao.licitacao.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "licitacao")
public class Licitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo_uasg")
    private String codigoUasg;
    
    @Column(name = "numero_pregao")
    private String numeroPregao;
    
    @Column(name = "objeto")
    private String objeto;
    
    @Column(name = "data_proposta")
    private LocalDateTime dataProposta;
    
    @Column(name = "visualizada")
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
