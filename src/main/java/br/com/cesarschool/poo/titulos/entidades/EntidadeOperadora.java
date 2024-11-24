package br.com.cesarschool.poo.titulos.entidades;

import br.gov.cesarschool.poo.daogenerico.Entidade;

public class EntidadeOperadora extends Entidade {
    private long identificador;
    private String nome;
    private boolean autorizacaoAcao;
    private double saldoAcao;
    private double saldoTituloDivida;

    public EntidadeOperadora(long identificador, String nome, boolean autorizacaoAcao) {
        this.identificador = identificador;
        this.nome = nome;
        this.autorizacaoAcao = autorizacaoAcao;
    }

    public EntidadeOperadora(long identificador, String nome, double autorizadoAcao) {
        super();
        this.identificador = identificador;
        this.nome = nome;
    }

    public long getIdentificador() {
        return identificador;
    }

    private void setIdentificador(long identificador) {
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean getAutorizacaoAcao() {
        return autorizacaoAcao;
    }

    public void setAutorizacaoAcao(boolean autorizacaoAcao) {
        this.autorizacaoAcao = autorizacaoAcao;
    }

    public double getSaldoAcao() {
        return saldoAcao;
    }

    public double getSaldoTituloDivida() {
        return saldoTituloDivida;
    }

    public void creditarSaldoAcao(double valor) {
        saldoAcao += valor;
    }

    public void debitarSaldoAcao(double valor) {
        saldoAcao -= valor;
    }

    public void creditarSaldoTituloDivida(double valor) {
        saldoTituloDivida += valor;
    }

    public void debitarSaldoTituloDivida(double valor) {
        saldoTituloDivida -= valor;
    }

    @Override
    public String getIdUnico() {
        return String.valueOf(identificador);
    }
}
