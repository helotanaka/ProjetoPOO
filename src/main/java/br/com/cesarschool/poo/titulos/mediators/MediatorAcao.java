package br.com.cesarschool.poo.titulos.mediators;

import java.time.LocalDate;
import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioAcao;

public class MediatorAcao {

    // Singleton: Instância única da classe
    private static MediatorAcao instancia;

    // Atributo RepositorioAcao inicializado na declaração
    private final RepositorioAcao repositorioAcao = new RepositorioAcao();

    // Construtor privado para garantir o padrão Singleton
    private MediatorAcao() {}

    // Método para obter a instância única do Mediator
    public static MediatorAcao getInstance() {
        if (instancia == null) {
            instancia = new MediatorAcao();
        }
        return instancia;
    }

    // Método privado para validar uma ação
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
            return "Valor unitário deve ser maior que zero.";
        }
        return null;
    }

    // Método para incluir uma ação no repositório
    public String incluir(Acao acao) {
        String mensagemValidacao = validar(acao);
        if (mensagemValidacao != null) {
            return mensagemValidacao;
        }
        boolean incluido = repositorioAcao.incluir(acao);
        return incluido ? null : "Ação já existente";
    }

    // Método para alterar uma ação no repositório
    public String alterar(Acao acao) {
        String mensagemValidacao = validar(acao);
        if (mensagemValidacao != null) {
            return mensagemValidacao;
        }
        boolean alterado = repositorioAcao.alterar(acao);
        return alterado ? null : "Ação inexistente";
    }

    // Método para excluir uma ação pelo identificador
    public String excluir(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return "Identificador deve estar entre 1 e 99999.";
        }
        boolean excluido = repositorioAcao.excluir(identificador);
        return excluido ? null : "Ação inexistente";
    }

    // Método para buscar uma ação pelo identificador
    public Acao buscar(int identificador) {
        if (identificador <= 0 || identificador >= 100000) {
            return null;
        }
        return repositorioAcao.buscar(identificador);
    }
}


