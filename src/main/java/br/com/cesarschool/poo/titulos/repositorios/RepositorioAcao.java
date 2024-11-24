package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RepositorioAcao extends RepositorioGeral {
	private static final String BASE_DIRECTORY = "Acao/";
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public Class<?> getClasseEntidade() {
		return Acao.class;
	}

	public DAOSerializadorObjetos getDao() {
		return dao;
	}

	private String getFilePath(int identificador) {
		return BASE_DIRECTORY + identificador + ".txt";
	}

	public boolean incluir(Acao acao) {
		String filePath = getFilePath(acao.getIdentificador());
		if (procurarId(acao.getIdentificador(), filePath)) {
			return false;
		}

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(filePath))) {
			escritor.write(formatAcao(acao));
			escritor.newLine();
			return true;
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
			return false;
		}
	}

	public boolean alterar(Acao acao) {
		String filePath = getFilePath(acao.getIdentificador());
		List<String> linhas = new ArrayList<>();
		boolean alterado = false;

		try (BufferedReader leitor = new BufferedReader(new FileReader(filePath))) {
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");
				if (Integer.parseInt(divisao[0]) == acao.getIdentificador()) {
					linhas.add(formatAcao(acao));
					alterado = true;
				} else {
					linhas.add(linha);
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading from file: " + e.getMessage());
			return false;
		}

		if (alterado) {
			return rewriteFile(linhas, filePath);
		}
		return false;
	}

	public boolean excluir(int identificador) {
		String filePath = getFilePath(identificador);
		return new File(filePath).delete();
	}

	public Acao buscar(int identificador) {
		String filePath = getFilePath(identificador);
		try (BufferedReader leitor = new BufferedReader(new FileReader(filePath))) {
			String linha = leitor.readLine();
			if (linha != null) {
				String[] divisao = linha.split(";");
				return new Acao(identificador, divisao[1], LocalDate.parse(divisao[2], formatter), Double.parseDouble(divisao[3]));
			}
		} catch (IOException | DateTimeParseException e) {
			System.err.println("Error processing file for search: " + e.getMessage());
		}
		return null;
	}

	private boolean procurarId(int identificador, String filePath) {
		return new File(filePath).exists();
	}

	private String formatAcao(Acao acao) {
		return acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataDeValidade().format(formatter) + ";" + acao.getValorUnitario();
	}

	private boolean rewriteFile(List<String> linhas, String filePath) {
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(filePath))) {
			for (String linha : linhas) {
				escritor.write(linha);
				escritor.newLine();
			}
			return true;
		} catch (IOException e) {
			System.err.println("Error rewriting file: " + e.getMessage());
			return false;
		}
	}
}
