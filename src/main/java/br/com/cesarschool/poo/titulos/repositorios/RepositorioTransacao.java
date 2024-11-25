package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import br.com.cesarschool.poo.titulos.entidades.Transacao;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.io.*;
		import java.util.ArrayList;
import java.util.List;

public class RepositorioTransacao extends RepositorioGeral {

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

	private String formatarTransacao(Transacao transacao) {
		StringBuilder sb = new StringBuilder();
		sb.append("Entidade Crédito: ").append(transacao.getEntidadeCredito().getNome()).append("\n");
		sb.append("Entidade Débito: ").append(transacao.getEntidadeDebito().getNome()).append("\n");
		sb.append("Valor: ").append(transacao.getValorOperacao()).append("\n");
		sb.append("Data e Hora: ").append(transacao.getDataHoraOperacao()).append("\n");
		return sb.toString();
	}

	private static final String CAMINHO_ARQUIVO = "Transacao";

	@Override
	public DAOSerializadorObjetos getDao() {
		return dao;
	}

	// Method to fetch transactions where the specified ID matches the debit entity's ID
	public Transacao[] buscarPorEntidadeDebito(int identificadorEntidadeDebito) {
		File folder = new File(CAMINHO_ARQUIVO);
		File[] listOfFiles = folder.listFiles();
		List<Transacao> transactions = new ArrayList<>();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				Transacao transacao = readTransactionFromFile(file);
				if (transacao != null && transacao.getEntidadeDebito().getIdentificador() == identificadorEntidadeDebito) {
					transactions.add(transacao);
				}
			}
		}
		return transactions.toArray(new Transacao[0]);
	}

	// Method to fetch transactions where the specified ID matches the credit entity's ID
	public Transacao[] buscarPorEntidadeCredora(int identificadorEntidadeCredito) {
		File folder = new File(CAMINHO_ARQUIVO);
		File[] listOfFiles = folder.listFiles();
		List<Transacao> transactions = new ArrayList<>();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				Transacao transacao = readTransactionFromFile(file);
				if (transacao != null && transacao.getEntidadeCredito().getIdentificador() == identificadorEntidadeCredito) {
					transactions.add(transacao);
				}
			}
		}
		return transactions.toArray(new Transacao[0]);
	}

	// Helper method to deserialize a transaction from a file
	private Transacao readTransactionFromFile(File file) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			return (Transacao) ois.readObject();
		} catch (Exception e) {
			System.err.println("Error reading transaction from file: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Class<?> getClasseEntidade() {
		return Transacao.class;
	}
}

