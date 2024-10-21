package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.entidades.Transacao;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTransacao {

	private static final String CAMINHO_ARQUIVO = "src/main/java/br/com/cesarschool/poo/titulos/repositorios/Transacao.txt";


	public void incluir(Transacao transacao) {
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO, true))) {
			String frase = formatarTransacao(transacao);
			escritor.write(frase);
			escritor.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Transacao[] buscarPorEntidadeDebito(int identificadorEntidadeDebito) {
		List<Transacao> transacoesEncontradas = new ArrayList<>();

		try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
			String linha;
			while ((linha = leitor.readLine()) != null) {
				Transacao transacao = converterStringParaTransacao(linha);
				if (transacao.getEntidadeDebito().getIdentificador() == identificadorEntidadeDebito) {
					transacoesEncontradas.add(transacao);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return transacoesEncontradas.toArray(new Transacao[0]);
	}

	public Transacao[] buscarPorEntidadeCredora(int identificadorEntidadeCredito) {
		List<Transacao> transacoesEncontradas = new ArrayList<>();

		try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
			String linha;
			while ((linha = leitor.readLine()) != null) {
				Transacao transacao = converterStringParaTransacao(linha);
				if (transacao.getEntidadeCredito().getIdentificador() == identificadorEntidadeCredito) {
					transacoesEncontradas.add(transacao);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return transacoesEncontradas.toArray(new Transacao[0]);
	}


	private Transacao converterStringParaTransacao(String linha) {
		String[] dados = linha.split(";");
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");

		EntidadeOperadora entidadeCredito = new EntidadeOperadora(
				Integer.parseInt(dados[0]), dados[1], Boolean.parseBoolean(dados[2]));
		entidadeCredito.creditarSaldoAcao(Double.parseDouble(dados[3].replace(",", ".")));
		entidadeCredito.creditarSaldoTituloDivida(Double.parseDouble(dados[4].replace(",", ".")));

		EntidadeOperadora entidadeDebito = new EntidadeOperadora(
				Integer.parseInt(dados[5]), dados[6], Boolean.parseBoolean(dados[7]));
		entidadeDebito.creditarSaldoAcao(Double.parseDouble(dados[8].replace(",", ".")));
		entidadeDebito.creditarSaldoTituloDivida(Double.parseDouble(dados[9].replace(",", ".")));

		Acao acao = null;
		if (!dados[10].equals("null")) {
			LocalDate dataAcao = LocalDate.parse(dados[12], formatoData);
			acao = new Acao(Integer.parseInt(dados[10]), dados[11], dataAcao, Double.parseDouble(dados[13].replace(",", ".")));
		}

		TituloDivida tituloDivida = null;
		if (!dados[14].equals("null")) {
			LocalDate dataTitulo = LocalDate.parse(dados[16], formatoData);
			tituloDivida = new TituloDivida(Integer.parseInt(dados[14]), dados[15], dataTitulo, Double.parseDouble(dados[17].replace(",", ".")));
		}

		double valorOperacao = Double.parseDouble(dados[dados.length - 2].replace(",", "."));
		LocalDateTime dataHoraOperacao = LocalDateTime.parse(dados[dados.length - 1], formatoDataHora);

		return new Transacao(entidadeCredito, entidadeDebito, acao, tituloDivida, valorOperacao, dataHoraOperacao);
	}

	private String formatarTransacao(Transacao transacao) {
		String entidadeCredito = formatarEntidade(transacao.getEntidadeCredito());
		String entidadeDebito = formatarEntidade(transacao.getEntidadeDebito());

		String acao = transacao.getAcao() != null
				? String.format("%d;%s;%s;%.2f", transacao.getAcao().getIdentificador(),
				transacao.getAcao().getNome(), transacao.getAcao().getDataDeValidade(),
				transacao.getAcao().getValorUnitario())
				: "null";

		String tituloDivida = transacao.getTituloDivida() != null
				? String.format("%d;%s;%s;%.2f", transacao.getTituloDivida().getIdentificador(),
				transacao.getTituloDivida().getNome(), transacao.getTituloDivida().getDataDeValidade(),
				transacao.getTituloDivida().getTaxaJuros())
				: "null";

		return String.format("%s;%s;%s;%s;%.2f;%s",
				entidadeCredito, entidadeDebito, acao, tituloDivida,
				transacao.getValorOperacao(), transacao.getDataHoraOperacao().toString());
	}

	private String formatarEntidade(EntidadeOperadora entidade) {
		return String.format("%d;%s;%b;%.2f;%.2f",
				entidade.getIdentificador(), entidade.getNome(),
				entidade.getAutorizacaoAcao(), entidade.getSaldoAcao(), entidade.getSaldoTituloDivida());
	}
}
