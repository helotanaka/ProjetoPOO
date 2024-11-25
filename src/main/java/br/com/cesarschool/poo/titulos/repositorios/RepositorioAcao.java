package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RepositorioAcao extends RepositorioGeral {
	private static final String BASE_DIRECTORY = "Acao/";
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	public RepositorioAcao() {
		File baseDir = new File(BASE_DIRECTORY);
		if (!baseDir.exists()) {
			baseDir.mkdirs(); // Cria o diretório se ele não existir
		}
	}

	@Override
	public Class<?> getClasseEntidade() {
		return Acao.class;
	}

	public DAOSerializadorObjetos getDao() {
		return dao;
	}

	private String getFilePath(int identificador) {
		return BASE_DIRECTORY + identificador; // Gera o caminho correto
	}

	public boolean incluir(Acao acao) {
		String filePath = getFilePath(acao.getIdentificador());

		if (procurarId(acao.getIdentificador(), filePath)) {
			return false; // Identificador já existe
		}

		if (acao.getDataHoraInclusao() == null) {
			acao.setDataHoraInclusao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
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
					// Mantém a data de inclusão original e define a última alteração
					Acao existente = parseAcao(linha);
					acao.setDataHoraInclusao(existente.getDataHoraInclusao());
					acao.setDataHoraUltimaAlteracao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
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
				return parseAcao(linha);
			}
		} catch (IOException e) {
			System.err.println("Error processing file for search: " + e.getMessage());
		}
		return null;
	}

	private Acao parseAcao(String linha) {
		String[] partes = linha.split(";");

		int identificador = Integer.parseInt(partes[0]);
		String nome = partes[1];
		LocalDate dataDeValidade = LocalDate.parse(partes[2], formatter);
		double valorUnitario = Double.parseDouble(partes[3].replace(",", "."));

		// Trunca as datas ao carregar
		LocalDateTime dataHoraInclusao = partes[4].equals("null")
				? null
				: LocalDateTime.parse(partes[4], dateTimeFormatter).truncatedTo(ChronoUnit.SECONDS);
		LocalDateTime dataHoraUltimaAlteracao = partes[5].equals("null")
				? null
				: LocalDateTime.parse(partes[5], dateTimeFormatter).truncatedTo(ChronoUnit.SECONDS);

		Acao acao = new Acao(identificador, nome, dataDeValidade, valorUnitario);
		acao.setDataHoraInclusao(dataHoraInclusao);
		acao.setDataHoraUltimaAlteracao(dataHoraUltimaAlteracao);

		return acao;
	}

	private boolean procurarId(int identificador, String filePath) {
		return new File(filePath).exists();
	}

	private String formatAcao(Acao acao) {
		return acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataDeValidade().format(formatter) + ";"
				+ acao.getValorUnitario() + ";" + acao.getDataHoraInclusao().format(dateTimeFormatter) + ";"
				+ (acao.getDataHoraUltimaAlteracao() != null ? acao.getDataHoraUltimaAlteracao().format(dateTimeFormatter) : "null");
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
