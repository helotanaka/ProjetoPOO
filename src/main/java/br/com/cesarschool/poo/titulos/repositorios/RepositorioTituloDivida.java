package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

public class RepositorioTituloDivida extends RepositorioGeral {

	private static final String CAMINHO_ARQUIVO = "src/main/java/br/com/cesarschool/poo/titulos/repositorios/TituloDivida.txt";

	public DAOSerializadorObjetos getDao() {
		return dao;
	}
	@Override
	public Class<?> getClasseEntidade() {
		return Transacao.class;
	}

	public boolean incluir(TituloDivida tituloDivida) {

		if (procurarId(tituloDivida.getIdentificador())) {
			return false; // Identificador já existe
		}

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO, true))) {
			String frase = tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";" +
					tituloDivida.getDataDeValidade() + ";" + tituloDivida.getTaxaJuros();
			escritor.write(frase);
			escritor.newLine();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean alterar(TituloDivida tituloDivida) {
		List<String> linhas = new ArrayList<>();
		boolean alterado = false;

		try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");

				if (Integer.parseInt(divisao[0]) == tituloDivida.getIdentificador()) {
					String novaLinha = tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";" +
							tituloDivida.getDataDeValidade() + ";" + tituloDivida.getTaxaJuros();
					linhas.add(novaLinha);
					alterado = true;
				} else {
					linhas.add(linha);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (alterado) {
			try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
				for (String linha : linhas) {
					escritor.write(linha);
					escritor.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return alterado;
	}

	public boolean excluir(int identificador) {

		List<String> linhas = new ArrayList<>();
		boolean deletado = false;

		try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");

				if (Integer.parseInt(divisao[0]) == identificador) {
					deletado = true;
				} else {
					linhas.add(linha);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (deletado) {
			try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
				for (String linha : linhas) {
					escritor.write(linha);
					escritor.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return deletado;
	}

	public TituloDivida buscar(int identificador) {

		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");

				if (Integer.parseInt(divisao[0]) == identificador) {
					LocalDate data = LocalDate.parse(divisao[2], formato);
					return new TituloDivida(
							identificador,
							divisao[1],
							data,
							Double.parseDouble(divisao[3])
					);
				}
			}
		} catch (IOException | DateTimeParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean procurarId(int identificador) {

		try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] partes = linha.split(";");
				if (Integer.parseInt(partes[0]) == identificador) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
