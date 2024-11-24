package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class RepositorioTituloDivida extends RepositorioGeral {

	private static final String CAMINHO_DIRETORIO = "TituloDivida";

	public RepositorioTituloDivida() {
		File diretorio = new File(CAMINHO_DIRETORIO);
		if (!diretorio.exists()) {
			diretorio.mkdirs();
		}
	}

	@Override
	public Class<?> getClasseEntidade() {
		return TituloDivida.class;
	}

	public boolean incluir(TituloDivida tituloDivida) {
		if (procurarId(tituloDivida.getIdentificador())) {
			return false; // Identificador já existe
		}

		// Configura a data e hora de inclusão truncada para segundos
		if (tituloDivida.getDataHoraInclusao() == null) {
			tituloDivida.setDataHoraInclusao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		}

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(getCaminhoArquivo(tituloDivida), false))) {
			escritor.write(formatarTituloDivida(tituloDivida));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean alterar(TituloDivida tituloDivida) {
		File arquivo = new File(getCaminhoArquivo(tituloDivida.getIdentificador()));
		if (!arquivo.exists()) {
			return false; // Não é possível alterar um registro inexistente
		}

		// Preserva os dados originais antes da atualização
		TituloDivida existente = buscar(tituloDivida.getIdentificador());
		if (existente == null) {
			return false;
		}

		// Configura os campos adicionais necessários
		tituloDivida.setDataHoraInclusao(existente.getDataHoraInclusao()); // Mantém a data de inclusão original
		tituloDivida.setDataHoraUltimaAlteracao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)); // Atualiza a última alteração

		// Atualiza o arquivo
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, false))) {
			escritor.write(formatarTituloDivida(tituloDivida));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean excluir(int identificador) {
		File arquivo = new File(getCaminhoArquivo(identificador));
		return arquivo.delete();
	}

	public TituloDivida buscar(int identificador) {
		File arquivo = new File(getCaminhoArquivo(identificador));
		if (!arquivo.exists()) {
			return null;
		}

		try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
			String linha = leitor.readLine();
			if (linha != null) {
				return converterStringParaTituloDivida(linha);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean procurarId(int identificador) {
		File arquivo = new File(getCaminhoArquivo(identificador));
		return arquivo.exists();
	}

	private String getCaminhoArquivo(TituloDivida tituloDivida) {
		return getCaminhoArquivo(tituloDivida.getIdentificador());
	}

	private String getCaminhoArquivo(int identificador) {
		return CAMINHO_DIRETORIO + File.separator + identificador;
	}

	private String formatarTituloDivida(TituloDivida tituloDivida) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		return String.format("%d;%s;%s;%.2f;%s;%s",
				tituloDivida.getIdentificador(),
				tituloDivida.getNome(),
				tituloDivida.getDataDeValidade().format(dateFormatter),
				tituloDivida.getTaxaJuros(),
				tituloDivida.getDataHoraInclusao().format(dateTimeFormatter),
				tituloDivida.getDataHoraUltimaAlteracao() != null
						? tituloDivida.getDataHoraUltimaAlteracao().format(dateTimeFormatter)
						: "null"); // Garante que null seja registrado
	}

	private TituloDivida converterStringParaTituloDivida(String linha) {
		String[] partes = linha.split(";");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		int identificador = Integer.parseInt(partes[0]);
		String nome = partes[1];
		LocalDate dataDeValidade = LocalDate.parse(partes[2], dateFormatter);

		// Substituir vírgulas por pontos para evitar erro ao converter para Double
		double taxaJuros = Double.parseDouble(partes[3].replace(",", "."));

		// Recupera a dataHoraInclusao truncada
		LocalDateTime dataHoraInclusao = LocalDateTime.parse(partes[4], dateTimeFormatter).truncatedTo(ChronoUnit.SECONDS);

		// Recupera a dataHoraUltimaAlteracao, se existir
		LocalDateTime dataHoraUltimaAlteracao = partes[5].equals("null")
				? null
				: LocalDateTime.parse(partes[5], dateTimeFormatter).truncatedTo(ChronoUnit.SECONDS);

		// Cria o objeto e define os campos adicionais
		TituloDivida titulo = new TituloDivida(identificador, nome, dataDeValidade, taxaJuros);
		titulo.setDataHoraInclusao(dataHoraInclusao);
		titulo.setDataHoraUltimaAlteracao(dataHoraUltimaAlteracao);

		return titulo;
	}
}
