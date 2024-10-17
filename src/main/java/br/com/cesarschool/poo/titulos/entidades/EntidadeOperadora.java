package br.com.cesarschool.poo.titulos.entidades;
/*
 * Esta classe deve ter os seguintes atributos:
 * identificador, do tipo long
 * nome, do tipo String
 * autorizadoAcao, do tipo double
 * saldoAcao, do tipo double
 * saldoTituloDivida, do tipo double //EVERYTHING OK HERE
 *
 * Deve ter um construtor p�blico que inicializa os atributos identificador, nome
 * e autorizadoAcao. //OK
 *
 * Deve ter m�todos set/get p�blicos para os atributos identificador, nome
 * e autorizadoAcao. O atributo identificador � read-only fora da classe.
 *
 * Os atributos saldoAcao e saldoTituloDivida devem ter apenas m�todos get p�blicos.
 *
 * Outros m�todos p�blicos:
 *
 *  void creditarSaldoAcao(double valor): deve adicionar valor ao saldoAcao
 *  void debitarSaldoAcao(double valor): deve diminuir valor de saldoAcao
 *  void creditarSaldoTituloDivida(double valor): deve adicionar valor ao saldoTituloDivida
 *  void debitarSaldoTituloDivida(double valor): deve diminuir valor de saldoTituloDivida // EVERYTHING OK
 */
public class EntidadeOperadora {
    private long identificador;
    private String nome;
    private boolean autorizacaoAcao;
    private double saldoAcao;
    private double saldoTituloDivida;

    public EntidadeOperadora(long identificador, String nome, boolean autorizacaoAcao){
        this.identificador = identificador;
        this.nome = nome;
        this.autorizacaoAcao = autorizacaoAcao;
    }

    private void setIdentificador(long identificador) {
        this.identificador = identificador;
    }

    public long getIdentificador() {
        return identificador;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setAutorizacaoAcao(boolean autorizacaoAcao) {
        this.autorizacaoAcao = autorizacaoAcao;
    }

    public boolean getAutorizacaoAcao() {
        return autorizacaoAcao;
    }

    public double getSaldoAcao() {
        return saldoAcao;
    }

    public double getSaldoTituloDivida() {
        return saldoTituloDivida;
    }

    void creditarSaldoAcao(double valor){
        saldoAcao += valor;
    }

    void debitarSaldoAcao(double valor){
        saldoAcao -= valor;
    }

    void creditarSaldoTituloDivida(double valor){
        saldoTituloDivida += valor;
    }

    void debitarSaldoTituloDivida(double valor){
        saldoTituloDivida -= valor;
    }
}
