package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
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

public class RepositorioAcao {
	// Caminho constante para o arquivo
	private static final String CAMINHO_ARQUIVO = "src/main/java/br/com/cesarschool/poo/titulos/repositorios/Acao.txt";

	// Método para garantir que o arquivo exista
	private void verificarOuCriarArquivo() {
		File arquivo = new File(CAMINHO_ARQUIVO);
		if (!arquivo.exists()) {
			try {
				if (arquivo.createNewFile()) {
					System.out.println("Arquivo Acao.txt criado com sucesso.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean incluir(Acao acao) {
		verificarOuCriarArquivo(); // Garante que o arquivo existe

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
		verificarOuCriarArquivo();

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
		verificarOuCriarArquivo();

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
		verificarOuCriarArquivo();

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
		verificarOuCriarArquivo();

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
