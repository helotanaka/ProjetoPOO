package br.gov.cesarschool.poo.daogenerico;
/*
 * Esta classe representa uma superclasse de todas as entidades.
 *
 * Deve ter os seguintes atributos (com respectivos set e get):
 *	LocalDateTime dataHoraInclusao,
 *  LocalDateTime dataHoraUltimaAlteracao,
 *	String usuarioInclusao e
 *	String usuarioUltimaAlteracao
 *
 * Deve ter um único construtor sem parâmetros.
 *
 * Deve ser abstrata.
 *
 * Deve ter um método abstrato getIdUnico().
 *
 * Deve implementar a interface Serializable do JAVA
 */

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Entidade implements Serializable {

    private static final long serialVersionUID = 1L; // Versão serial para garantir compatibilidade na serialização

    private LocalDateTime dataHoraInclusao;
    private LocalDateTime dataHoraUltimaAlteracao;
    private String usuarioInclusao;
    private String usuarioUltimaAlteracao;

    // Construtor sem parâmetros
    public Entidade() {
    }

    // Getters e Setters
    public LocalDateTime getDataHoraInclusao() {
        return dataHoraInclusao;
    }

    public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
        this.dataHoraInclusao = dataHoraInclusao;
    }

    public LocalDateTime getDataHoraUltimaAlteracao() {
        return dataHoraUltimaAlteracao;
    }

    public void setDataHoraUltimaAlteracao(LocalDateTime dataHoraUltimaAlteracao) {
        this.dataHoraUltimaAlteracao = dataHoraUltimaAlteracao;
    }

    public String getUsuarioInclusao() {
        return usuarioInclusao;
    }

    public void setUsuarioInclusao(String usuarioInclusao) {
        this.usuarioInclusao = usuarioInclusao;
    }

    public String getUsuarioUltimaAlteracao() {
        return usuarioUltimaAlteracao;
    }

    public void setUsuarioUltimaAlteracao(String usuarioUltimaAlteracao) {
        this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
    }

    // Método abstrato para retornar o ID único da entidade
    public abstract Object getIdUnico();
}