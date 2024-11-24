package br.com.cesarschool.poo.titulos.relatorios;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.com.cesarschool.poo.titulos.repositorios.RepositorioTransacao;
import br.com.cesarschool.poo.titulos.utils.ComparadorTransacaoPorNomeCredora;
import br.com.cesarschool.poo.titulos.utils.Ordenador;

//Tem os dois métodos mencionados acima, que devem ler
//do repositório de transações todas as transações e ordenar
//o retorno desta leitura por nome da entidade credora e por
//data hora da operação, retornando os respectivos arrays
//ordenados.

public class RelatorioTransacaoBroker {
    private final RepositorioTransacao repositorioTransacao;

    public RelatorioTransacaoBroker() {
        this.repositorioTransacao = new RepositorioTransacao();
    }

    public Transacao[] relatorioTransacaoPorNomeEntidadeCredora() {
        Transacao[] transacoes = repositorioTransacao.buscarPorEntidadeCredora(0);
        Ordenador.ordenar(transacoes, new ComparadorTransacaoPorNomeCredora());
        return transacoes;
    }

    public Transacao[] relatorioTransacaoPorDataHora() {
        Transacao[] transacoes = repositorioTransacao.buscarPorEntidadeCredora(0);
        Ordenador.ordenar(transacoes);
        return transacoes;
    }
}
