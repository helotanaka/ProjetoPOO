package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import br.gov.cesarschool.poo.daogenerico.DAOSerializadorObjetos;

public class RepositorioTituloDivida extends RepositorioGeral {

	private static final String CAMINHO_ARQUIVO = "TituloDivida";

	public DAOSerializadorObjetos getDao() {
		return dao;
	}

	@Override
	public Class<?> getClasseEntidade() {
		return TituloDivida.class;
	}

	public boolean incluir(TituloDivida tituloDivida) {
		if (procurarId(tituloDivida.getIdentificador())) {
			return false; // Identificador já existe
		}

		tituloDivida.setDataHoraInclusao(LocalDateTime.now()); // Define a data de inclusão

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO, true))) {
			String linha = formatarTituloDivida(tituloDivida);
			escritor.write(linha);
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
				TituloDivida existente = converterStringParaTituloDivida(linha);

				if (existente.getIdentificador() == tituloDivida.getIdentificador()) {
					tituloDivida.setDataHoraInclusao(existente.getDataHoraInclusao()); // Mantém a data de inclusão original
					tituloDivida.setDataHoraUltimaAlteracao(LocalDateTime.now()); // Define a data da última alteração
					linhas.add(formatarTituloDivida(tituloDivida));
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
				TituloDivida existente = converterStringParaTituloDivida(linha);

				if (existente.getIdentificador() == identificador) {
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
		try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
			String linha;
			while ((linha = leitor.readLine()) != null) {
				TituloDivida titulo = converterStringParaTituloDivida(linha);
				if (titulo.getIdentificador() == identificador) {
					return titulo;
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
				TituloDivida titulo = converterStringParaTituloDivida(linha);
				if (titulo.getIdentificador() == identificador) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String formatarTituloDivida(TituloDivida tituloDivida) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		return String.format("%d;%s;%s;%.2f;%s;%s",
				tituloDivida.getIdentificador(),
				tituloDivida.getNome(),
				tituloDivida.getDataDeValidade(),
				tituloDivida.getTaxaJuros(),
				tituloDivida.getDataHoraInclusao() != null ? tituloDivida.getDataHoraInclusao().format(formatter) : "",
				tituloDivida.getDataHoraUltimaAlteracao() != null ? tituloDivida.getDataHoraUltimaAlteracao().format(formatter) : "");
	}

	private TituloDivida converterStringParaTituloDivida(String linha) {
		String[] partes = linha.split(";");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		int identificador = Integer.parseInt(partes[0]);
		String nome = partes[1];
		LocalDate dataDeValidade = LocalDate.parse(partes[2]);
		double taxaJuros = Double.parseDouble(partes[3]);
		LocalDateTime dataHoraInclusao = partes[4].isEmpty() ? null : LocalDateTime.parse(partes[4], formatter);
		LocalDateTime dataHoraUltimaAlteracao = partes[5].isEmpty() ? null : LocalDateTime.parse(partes[5], formatter);

		TituloDivida tituloDivida = new TituloDivida(identificador, nome, dataDeValidade, taxaJuros);
		tituloDivida.setDataHoraInclusao(dataHoraInclusao);
		tituloDivida.setDataHoraUltimaAlteracao(dataHoraUltimaAlteracao);

		return tituloDivida;
	}
}
