package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.util.ArrayList;
import java.util.List;

public class RepositorioTransacao extends RepositorioGeral {

	private static final String CAMINHO_ARQUIVO = "Transacao";

	@Override
	public DAOSerializadorObjetos getDao() {
		return dao;
	}

	public void incluir(Transacao transacao) {
		dao.incluir(transacao);
	}

	public Transacao[] buscarTodos() {
		List<Transacao> transacoes = new ArrayList<>();
		for (Object obj : dao.buscarTodos()) {
			transacoes.add((Transacao) obj);
		}
		return transacoes.toArray(new Transacao[0]);
	}

	public Transacao[] buscarPorEntidadeDebito(long identificadorEntidadeDebito) {
		List<Transacao> resultado = new ArrayList<>();
		for (Object obj : dao.buscarTodos()) {
			Transacao transacao = (Transacao) obj;
			if (transacao.getEntidadeDebito().getIdentificador() == identificadorEntidadeDebito) {
				resultado.add(transacao);
			}
		}
		return resultado.toArray(new Transacao[0]);
	}

	public Transacao[] buscarPorEntidadeCredora(long identificadorEntidadeCredito) {
		List<Transacao> resultado = new ArrayList<>();
		for (Object obj : dao.buscarTodos()) {
			Transacao transacao = (Transacao) obj;
			if (transacao.getEntidadeCredito().getIdentificador() == identificadorEntidadeCredito) {
				resultado.add(transacao);
			}
		}
		return resultado.toArray(new Transacao[0]);
	}

	@Override
	public Class<?> getClasseEntidade() {
		return Transacao.class;
	}
}
