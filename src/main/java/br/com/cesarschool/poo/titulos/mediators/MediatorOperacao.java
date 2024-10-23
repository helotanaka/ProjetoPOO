package br.com.cesarschool.poo.titulos.mediators;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;
import java.time.LocalDateTime;
import java.util.Arrays;

public class MediatorOperacao {

    private static MediatorOperacao instance;

    private final MediatorAcao mediatorAcao = MediatorAcao.getInstance();
    private final MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getInstance();
    private final MediatorEntidadeOperadora mediatorEntidadeOperadora = MediatorEntidadeOperadora.getInstance();
    private final RepositorioTransacao repositorioTransacao = new RepositorioTransacao();

    private MediatorOperacao() {}

    public static synchronized MediatorOperacao getInstance() {
        if (instance == null) {
            instance = new MediatorOperacao();
        }
        return instance;
    }

    public String realizarOperacao(boolean ehAcao, int entidadeCredito, int entidadeDebito, int idAcaoOuTitulo, double valor) {
        if (valor <= 0) {
            return "Valor inválido";
        }

        EntidadeOperadora credito = mediatorEntidadeOperadora.buscar(entidadeCredito);
        if (credito == null) {
            return "Entidade crédito inexistente";
        }

        EntidadeOperadora debito = mediatorEntidadeOperadora.buscar(entidadeDebito);
        if (debito == null) {
            return "Entidade débito inexistente";
        }

        if (ehAcao && !credito.getAutorizacaoAcao()) {
            return "Entidade de crédito não autorizada para ação";
        }

        if (ehAcao && !debito.getAutorizacaoAcao()) {
            return "Entidade de débito não autorizada para ação";
        }

        Object acaoOuTitulo = ehAcao ? mediatorAcao.buscar(idAcaoOuTitulo) : mediatorTituloDivida.buscar(idAcaoOuTitulo);
        if (acaoOuTitulo == null) {
            return "Ação ou título inexistente";
        }

        double saldo = ehAcao ? debito.getSaldoAcao() : debito.getSaldoTituloDivida();
        if (saldo < valor) {
            return "Saldo da entidade débito insuficiente";
        }

        if (ehAcao && ((Acao) acaoOuTitulo).getValorUnitario() > valor) {
            return "Valor da operação é menor do que o valor unitário da ação";
        }

        double valorOperacao = ehAcao ? valor : ((TituloDivida) acaoOuTitulo).calcularPrecoTransacao(valor);

        if (ehAcao) {
            credito.creditarSaldoAcao(valorOperacao);
            debito.debitarSaldoAcao(valorOperacao);
        } else {
            credito.creditarSaldoTituloDivida(valorOperacao);
            debito.debitarSaldoTituloDivida(valorOperacao);
        }

        String mensagemCredito = mediatorEntidadeOperadora.alterar(credito);
        if (mensagemCredito != null) {
            return mensagemCredito;
        }

        String mensagemDebito = mediatorEntidadeOperadora.alterar(debito);
        if (mensagemDebito != null) {
            return mensagemDebito;
        }

        Transacao transacao = new Transacao(
                credito,
                debito,
                ehAcao ? (Acao) acaoOuTitulo : null,
                !ehAcao ? (TituloDivida) acaoOuTitulo : null,
                valorOperacao,
                LocalDateTime.now()
        );

        repositorioTransacao.incluir(transacao);
        return null;
    }

    public Transacao[] gerarExtrato(int entidade) {
        Transacao[] credoras = repositorioTransacao.buscarPorEntidadeCredora(entidade);
        Transacao[] devedoras = repositorioTransacao.buscarPorEntidadeDebito(entidade);

        // Verifique se ambos os arrays são nulos e trate como vazio.
        if (credoras == null) credoras = new Transacao[0];
        if (devedoras == null) devedoras = new Transacao[0];

        return combineAndSort(credoras, devedoras);
    }


    private Transacao[] combineAndSort(Transacao[] credoras, Transacao[] devedoras) {
        Transacao[] result = new Transacao[credoras.length + devedoras.length];

        System.arraycopy(credoras, 0, result, 0, credoras.length);
        System.arraycopy(devedoras, 0, result, credoras.length, devedoras.length);

        Arrays.sort(result, (a, b) -> b.getDataHoraOperacao().compareTo(a.getDataHoraOperacao()));
        return result;
    }

}
