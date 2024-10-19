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

/*
 * Deve gravar em e ler de um arquivo texto chamado Transacao.txt os dados dos objetos do tipo
 * Transacao. Seguem abaixo exemplos de linhas
 * De entidadeCredito: identificador, nome, autorizadoAcao, saldoAcao, saldoTituloDivida.
 * De entidadeDebito: identificador, nome, autorizadoAcao, saldoAcao, saldoTituloDivida.
 * De acao: identificador, nome, dataValidade, valorUnitario OU null
 * De tituloDivida: identificador, nome, dataValidade, taxaJuros OU null.
 * valorOperacao, dataHoraOperacao
 *
 *   002192;BCB;true;0.00;1890220034.0;001112;BOFA;true;12900000210.00;3564234127.0;1;PETROBRAS;2024-12-12;30.33;null;100000.0;2024-01-01 12:22:21
 *   002192;BCB;false;0.00;1890220034.0;001112;BOFA;true;12900000210.00;3564234127.0;null;3;FRANCA;2027-11-11;2.5;100000.0;2024-01-01 12:22:21
 *
 * A inclusão deve adicionar uma nova linha ao arquivo.
 *
 * A busca deve retornar um array de transações cuja entidadeCredito tenha identificador igual ao
 * recebido como parâmetro.
 * BASICAMENTE É PRA FAZER A MESMA COISA SÓ QUE COM ENTIDADEOPERADORA
 */
public class RepositorioTransacao {
	public void incluir(Transacao transacao) {
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter("Transacao.txt", true))) {
			String frase = formatarTransacao(transacao);
			escritor.write(frase);
			escritor.newLine(); // Adiciona uma nova linha
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Transacao[] buscarPorEntidadeCredora(int identificadorEntidadeCredito) {
		List<Transacao> transacoesEncontradas = new ArrayList<>();

		try (BufferedReader leitor = new BufferedReader(new FileReader("Transacao.txt"))) {
			String linha;
			while ((linha = leitor.readLine()) != null) {
				String[] divisao = linha.split(";");
				if (Integer.parseInt(divisao[0]) == identificadorEntidadeCredito) {
					// Adiciona a transação encontrada à lista
					transacoesEncontradas.add(converterStringParaTransacao(linha));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Converte a lista para um array de Transacao e retorna
		return transacoesEncontradas.toArray(new Transacao[0]);
	}

	private Transacao converterStringParaTransacao(String linha) {
		String[] dados = linha.split(";");

		// Criação da Entidade Credito
		EntidadeOperadora entidadeCredito = new EntidadeOperadora(
				Integer.parseInt(dados[0]),
				dados[1],
				Boolean.parseBoolean(dados[2])
		);
		entidadeCredito.creditarSaldoAcao(Double.parseDouble(dados[3]));
		entidadeCredito.creditarSaldoTituloDivida(Double.parseDouble(dados[4]));

		// Criação da Entidade Debito
		EntidadeOperadora entidadeDebito = new EntidadeOperadora(
				Integer.parseInt(dados[5]),
				dados[6],
				Boolean.parseBoolean(dados[7])
		);
		entidadeDebito.creditarSaldoAcao(Double.parseDouble(dados[8]));
		entidadeDebito.creditarSaldoTituloDivida(Double.parseDouble(dados[9]));

		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		Acao acao = null;
		TituloDivida tituloDivida = null;

		// Caso: Acao disponível e TituloDivida não disponível
		if (!dados[10].equals("null") && dados[14].equals("null")) {
			LocalDate data = LocalDate.parse(dados[12], formatoData);
			acao = new Acao(
					Integer.parseInt(dados[10]),
					dados[11],
					data,
					Double.parseDouble(dados[13])
			);
		}

		// Caso: TituloDivida disponível e Acao não disponível
		if (dados[10].equals("null") && !dados[14].equals("null")) {
			LocalDate data = LocalDate.parse(dados[16], formatoData);
			tituloDivida = new TituloDivida(
					Integer.parseInt(dados[14]),
					dados[15],
					data,
					Double.parseDouble(dados[17])
			);
		}

		// Caso: Ambos disponíveis
		if (!dados[10].equals("null") && !dados[14].equals("null")) {
			LocalDate dataAcao = LocalDate.parse(dados[12], formatoData);
			acao = new Acao(
					Integer.parseInt(dados[10]),
					dados[11],
					dataAcao,
					Double.parseDouble(dados[13])
			);

			LocalDate dataTitulo = LocalDate.parse(dados[16], formatoData);
			tituloDivida = new TituloDivida(
					Integer.parseInt(dados[14]),
					dados[15],
					dataTitulo,
					Double.parseDouble(dados[17])
			);
		}

		// Definindo valor e data/hora da operação
		double valorOperacao = Double.parseDouble(dados[dados.length - 2]);
		LocalDateTime dataHoraOperacao = LocalDateTime.parse(dados[dados.length - 1], formatoDataHora);

		// Criação do objeto Transacao
		Transacao transacao = new Transacao(
				entidadeCredito, entidadeDebito, acao, tituloDivida, valorOperacao, dataHoraOperacao
		);

		return transacao;
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

		return String.format("%s;%s;%s;%s;%.2f;%s", entidadeCredito, entidadeDebito, acao, tituloDivida,
				transacao.getValorOperacao(), transacao.getDataHoraOperacao().toString());
	}

	private String formatarEntidade(EntidadeOperadora entidade) {
		return String.format("%d;%s;%b;%.2f;%.2f", entidade.getIdentificador(), entidade.getNome(),
				entidade.getAutorizacaoAcao(), entidade.getSaldoAcao(), entidade.getSaldoTituloDivida());
	}
}
