package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioAcao;

public class MediatorAcao {

    // Singleton: Inst�ncia �nica da classe
    private static MediatorAcao instancia;

    // Atributo RepositorioAcao inicializado na declara��o
    private final RepositorioAcao repositorioAcao = new RepositorioAcao();

    // Construtor privado para garantir o padr�o Singleton
    private MediatorAcao() {}

    // M�todo para obter a inst�ncia �nica do Mediator
    public static MediatorAcao getInstance() {
        if (instancia == null) {
            instancia = new MediatorAcao();
        }
        return instancia;
    }

    // M�todo privado para validar uma a��o
    private String validar(Acao acao) {
        if (acao.getIdentificador() <= 0 || acao.getIdentificador() >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        if (acao.getNome() == null || acao.getNome().isBlank()) {
            return "Nome deve ser preenchido.";
        }
        if (acao.getNome().length() < 10 || acao.getNome().length() > 100) {
            return "Nome deve ter entre 10 e 100 caracteres.";
        }
        if (acao.getDataDeValidade().isBefore(LocalDate.now().plusDays(30))) {
            return "Data de validade deve ter pelo menos 30 dias na frente da data atual.";
        }
        if (acao.getValorUnitario() <= 0) {
            return "Valor unit�rio deve ser maior que zero.";
        }
        return null;
    }

    // M�todo para incluir uma a��o no reposit�rio
    public String incluir(Acao acao) {
        String mensagemValidacao = validar(acao);
        if (mensagemValidacao != null) {
            return mensagemValidacao;
        }
        boolean incluido = repositorioAcao.incluir(acao);
        return incluido ? null : "A��o j� existente";
    }

    // M�todo para alterar uma a��o no reposit�rio
    public String alterar(Acao acao) {
        String mensagemValidacao = validar(acao);
        if (mensagemValidacao != null) {
            return mensagemValidacao;
        }
        boolean alterado = repositorioAcao.alterar(acao);
        return alterado ? null : "A��o inexistente";
    }

    // M�todo para excluir uma a��o pelo identificador
    public String excluir(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        boolean excluido = repositorioAcao.excluir(identificador);
        return excluido ? null : "A��o inexistente";
    }

    // M�todo para buscar uma a��o pelo identificador
    public Acao buscar(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioAcao.buscar(identificador);
    }
}


