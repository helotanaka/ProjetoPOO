package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/*
 * Deve gravar em e ler de um arquivo texto chamado TituloDivida.txt os dados dos objetos do tipo
 * TituloDivida. Seguem abaixo exemplos de linhas (identificador, nome, dataValidade, taxaJuros).
 *
    1;BRASIL;2024-12-12;10.5
    2;EUA;2026-01-01;1.5
    3;FRANCA;2027-11-11;2.5 
 * 
 * A inclus�o deve adicionar uma nova linha ao arquivo. N�o � permitido incluir 
 * identificador repetido. Neste caso, o m�todo deve retornar false. Inclus�o com 
 * sucesso, retorno true.
 * 
 * A altera��o deve substituir a linha atual por uma nova linha. A linha deve ser 
 * localizada por identificador que, quando n�o encontrado, enseja retorno false. 
 * Altera��o com sucesso, retorno true.  
 *   
 * A exclus�o deve apagar a linha atual do arquivo. A linha deve ser 
 * localizada por identificador que, quando n�o encontrado, enseja retorno false. 
 * Exclus�o com sucesso, retorno true.
 * 
 * A busca deve localizar uma linha por identificador, materializar e retornar um 
 * objeto. Caso o identificador n�o seja encontrado no arquivo, retornar null.   
 */
public class RepositorioTituloDivida {
	public boolean incluir(TituloDivida tituloDivida) {
		if(procurarId(tituloDivida.getIdentificador()) == true){ // se achar id igual
			return false;
		}

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter("TituloDivida.txt", true))) {// escrever no arquivo
			String frase = tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";" + tituloDivida.getDataDeValidade() +";" + tituloDivida.getTaxaJuros();
			escritor.write(frase); //Coloca a frase no txt
			escritor.newLine(); // Adiciona uma nova linha
			return true; // Inclus�o com sucesso
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean alterar(TituloDivida tituloDivida) {
		List<String> linhas = new ArrayList<>();
		boolean alterado = false;

		try (BufferedReader leitor = new BufferedReader(new FileReader("TituloDivida.txt"))){
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");

				// Verifica se o identificador da linha corresponde ao identificador da a��o a ser alterada
				if (Integer.parseInt(divisao[0]) == tituloDivida.getIdentificador()) {
					// Monta a nova linha com os dados da a��o fornecida
					String novaLinha = tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";" + tituloDivida.getDataDeValidade() +";" + tituloDivida.getTaxaJuros();
					linhas.add(novaLinha);  // Adiciona a nova linha no lugar da antiga
					alterado = true;  // Marca que a altera��o foi feita
				} else {
					linhas.add(linha);  // Mant�m a linha original
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		if (alterado == true) {
			try (BufferedWriter escritor = new BufferedWriter(new FileWriter("TituloDivida.txt"))) {
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

		try (BufferedReader leitor = new BufferedReader(new FileReader("TituloDivida.txt"))){
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");

				// Verifica se o identificador da linha corresponde ao identificador da a��o a ser alterada
				if (Integer.parseInt(divisao[0]) == identificador) {
					deletado = true;  // Marca que a altera��o foi feita
				} else {
					linhas.add(linha);  // Mant�m a linha original
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		if (deletado == true) {
			try (BufferedWriter escritor = new BufferedWriter(new FileWriter("TituloDivida.txt"))) {
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
		if(procurarId(identificador) == false){ // se n�o achar id igual
			return null;
		}

		try (BufferedReader leitor = new BufferedReader(new FileReader("TituloDivida.txt"))) {
			String linha;
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");

				if (Integer.parseInt(divisao[0]) == identificador) {
					// Converte a string para LocalDate
					LocalDate data;
					try {
						data = LocalDate.parse(divisao[2], formato); // converte a string da data
					} catch (DateTimeParseException e) {
						e.printStackTrace();
						return null;
					}

					return new TituloDivida(identificador, divisao[1], data, Double.parseDouble(divisao[3]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private boolean procurarId(int identificador){
		try (BufferedReader leitor = new BufferedReader(new FileReader("Acao.txt"))) { //l� o texto
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] partes = linha.split(";");
				if (Integer.parseInt(partes[0]) == identificador) { //converte string em valor int
					return true; // Identificador encontrado
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false; // Identificador n�o encontrado
	}
}
