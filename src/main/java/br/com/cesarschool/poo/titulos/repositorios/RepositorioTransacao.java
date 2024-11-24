package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.io.*;

public class RepositorioTransacao extends RepositorioGeral {

	private static final String CAMINHO_ARQUIVO = "Transacao"; // Diretório para armazenar arquivos

	@Override
	public DAOSerializadorObjetos getDao() {
		return dao;
	}

	public void incluir(Transacao transacao) {
		// Garante que o diretório exista
		File diretorio = new File(CAMINHO_ARQUIVO);
		if (!diretorio.exists()) {
			diretorio.mkdirs(); // Cria o diretório se ele não existir
		}

		// Nome do arquivo baseado no ID único da transação
		String caminhoArquivo = diretorio + File.separator + transacao.getIdUnico();

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoArquivo))) {
			String conteudo = formatarTransacao(transacao); // Formata a transação
			escritor.write(conteudo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Método para formatar a transação para salvar no arquivo
	private String formatarTransacao(Transacao transacao) {
		StringBuilder sb = new StringBuilder();
		sb.append("Entidade Crédito: ").append(transacao.getEntidadeCredito().getNome()).append("\n");
		sb.append("Entidade Débito: ").append(transacao.getEntidadeDebito().getNome()).append("\n");
		sb.append("Valor: ").append(transacao.getValorOperacao()).append("\n");
		sb.append("Data e Hora: ").append(transacao.getDataHoraOperacao()).append("\n");
		return sb.toString();
	}

	// Método para buscar transações por entidade de débito
	public Transacao[] buscarPorEntidadeDebito(int identificadorEntidadeDebito) {
		// Lógica para buscar transações associadas à entidade de débito
		return new Transacao[0]; // Retorna vazio por enquanto (implementação futura)
	}

	// Método para buscar transações por entidade credora
	public Transacao[] buscarPorEntidadeCredora(int identificadorEntidadeCredito) {
		// Lógica para buscar transações associadas à entidade credora
		return new Transacao[0]; // Retorna vazio por enquanto (implementação futura)
	}

	@Override
	public Class<?> getClasseEntidade() {
		return Transacao.class;
	}
}
