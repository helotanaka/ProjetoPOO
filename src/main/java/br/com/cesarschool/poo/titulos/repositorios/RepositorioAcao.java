package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.Transacao;
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

public class RepositorioAcao extends RepositorioGeral{

	@Override
	public Class<?> getClasseEntidade() {
		return Acao.class;
	}

	public DAOSerializadorObjetos getDao() {
		return dao;
	}

	//private static final String CAMINHO_ARQUIVO = "src/main/java/br/com/cesarschool/poo/titulos/repositorios/Acao.txt";
	//private static final String CAMINHO_ARQUIVO = "Acao.txt"; // Diretório para armazenar arquivos
	private static final String CAMINHO_ARQUIVO = "Acao.txt";

	public boolean incluir(Acao acao) {
		if (procurarId(acao.getIdentificador())) {
			return false; // Identificador já existe
		}

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO, true))) {
			String frase = acao.getIdentificador() + ";" + acao.getNome() + ";" +
					acao.getDataDeValidade() + ";" + acao.getValorUnitario();
			escritor.write(frase);
			escritor.newLine();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean alterar(Acao acao) {

		List<String> linhas = new ArrayList<>();
		boolean alterado = false;

		try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");

				if (Integer.parseInt(divisao[0]) == acao.getIdentificador()) {
					String novaLinha = acao.getIdentificador() + ";" + acao.getNome() + ";" +
							acao.getDataDeValidade() + ";" + acao.getValorUnitario();
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

	public Acao buscar(int identificador) {

		try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
			String linha;
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");

				if (Integer.parseInt(divisao[0]) == identificador) {
					LocalDate data = LocalDate.parse(divisao[2], formato);
					return new Acao(identificador, divisao[1], data, Double.parseDouble(divisao[3]));
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
