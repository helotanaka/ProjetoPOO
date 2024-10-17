package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/*
 * Deve gravar em e ler de um arquivo texto chamado Acao.txt os dados dos objetos do tipo
 * Acao. Seguem abaixo exemplos de linhas (identificador, nome, dataValidade, valorUnitario)
 *
    1;PETROBRAS;2024-12-12;30.33
    2;BANCO DO BRASIL;2026-01-01;21.21
    3;CORREIOS;2027-11-11;6.12 
 * 
 * A inclusão deve adicionar uma nova linha ao arquivo. Não é permitido incluir 
 * identificador repetido. Neste caso, o método deve retornar false. Inclusão com 
 * sucesso, retorno true.
 * 
 * A alteração deve substituir a linha atual por uma nova linha. A linha deve ser 
 * localizada por identificador que, quando não encontrado, enseja retorno false. 
 * Alteração com sucesso, retorno true.  
 *   
 * A exclusão deve apagar a linha atual do arquivo. A linha deve ser 
 * localizada por identificador que, quando não encontrado, enseja retorno false. 
 * Exclusão com sucesso, retorno true.
 * 
 * A busca deve localizar uma linha por identificador, materializar e retornar um 
 * objeto. Caso o identificador não seja encontrado no arquivo, retornar null.   
 */
public class RepositorioAcao {
	public boolean incluir(Acao acao) {
		if(procurarId(acao.getIdentificador()) == true){ // se achar id igual
			return false;
		}

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter("Acao.txt", true))) {// escrever no arquivo
			String frase = acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataDeValidade() +";" + acao.getValorUnitario();
			escritor.write(frase); //Coloca a frase no txt
			escritor.newLine(); // Adiciona uma nova linha
			return true; // Inclusão com sucesso
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean alterar(Acao acao) {
		List<String> linhas = new ArrayList<>();
		boolean alterado = false;

		try (BufferedReader leitor = new BufferedReader(new FileReader("Acao.txt"))){
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");

				// Verifica se o identificador da linha corresponde ao identificador da ação a ser alterada
				if (Integer.parseInt(divisao[0]) == acao.getIdentificador()) {
					// Monta a nova linha com os dados da ação fornecida
					String novaLinha = acao.getIdentificador() + ";" + acao.getNome() + ";" + acao.getDataDeValidade() +";" + acao.getValorUnitario();
					linhas.add(novaLinha);  // Adiciona a nova linha no lugar da antiga
					alterado = true;  // Marca que a alteração foi feita
				} else {
					linhas.add(linha);  // Mantém a linha original
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		if (alterado == true) {
			try (BufferedWriter escritor = new BufferedWriter(new FileWriter("Acao.txt"))) {
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

		try (BufferedReader leitor = new BufferedReader(new FileReader("Acao.txt"))){
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");

				// Verifica se o identificador da linha corresponde ao identificador da ação a ser alterada
				if (Integer.parseInt(divisao[0]) == identificador) {
					deletado = true;  // Marca que a alteração foi feita
				} else {
					linhas.add(linha);  // Mantém a linha original
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		if (deletado == true) {
			try (BufferedWriter escritor = new BufferedWriter(new FileWriter("Acao.txt"))) {
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
		if(procurarId(identificador) == false){ // se não achar id igual
			return null;
		}

		try (BufferedReader leitor = new BufferedReader(new FileReader("Acao.txt"))) {
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

					return new Acao(identificador, divisao[1], data, Double.parseDouble(divisao[3]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}


}
