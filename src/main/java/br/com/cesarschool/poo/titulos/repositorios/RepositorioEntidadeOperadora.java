package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class RepositorioEntidadeOperadora extends RepositorioGeral {

    private static final String CAMINHO_DIRETORIO = "EntidadeOperadora";

    public RepositorioEntidadeOperadora() {
        File diretorio = new File(CAMINHO_DIRETORIO);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    @Override
    public Class<?> getClasseEntidade() {
        return EntidadeOperadora.class;
    }

    public boolean incluir(EntidadeOperadora entidadeOperadora) {
        if (procurarId(entidadeOperadora.getIdentificador())) {
            return false; // Identificador já existe
        }

        // Configura a data e hora de inclusão truncada para segundos
        if (entidadeOperadora.getDataHoraInclusao() == null) {
            entidadeOperadora.setDataHoraInclusao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(getCaminhoArquivo(entidadeOperadora), false))) {
            escritor.write(formatarEntidadeOperadora(entidadeOperadora));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean alterar(EntidadeOperadora entidadeOperadora) {
        File arquivo = new File(getCaminhoArquivo(entidadeOperadora));
        if (!arquivo.exists()) {
            return false; // Não é possível alterar um registro inexistente
        }

        // Preserva a dataHoraInclusao e define dataHoraUltimaAlteracao
        EntidadeOperadora existente = buscar(entidadeOperadora.getIdentificador());
        if (existente == null) {
            return false;
        }

        entidadeOperadora.setDataHoraInclusao(existente.getDataHoraInclusao());
        entidadeOperadora.setDataHoraUltimaAlteracao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        // Sobrescreve o arquivo com os dados atualizados
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(getCaminhoArquivo(entidadeOperadora), false))) {
            escritor.write(formatarEntidadeOperadora(entidadeOperadora));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean excluir(long identificador) {
        File arquivo = new File(getCaminhoArquivo(identificador));
        return arquivo.delete();
    }

    public EntidadeOperadora buscar(long identificador) {
        File arquivo = new File(getCaminhoArquivo(identificador));
        if (!arquivo.exists()) {
            return null;
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha = leitor.readLine();
            if (linha != null) {
                return converterStringParaEntidadeOperadora(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean procurarId(long identificador) {
        File arquivo = new File(getCaminhoArquivo(identificador));
        return arquivo.exists();
    }

    private String getCaminhoArquivo(EntidadeOperadora entidadeOperadora) {
        return getCaminhoArquivo(entidadeOperadora.getIdentificador());
    }

    private String getCaminhoArquivo(long identificador) {
        return CAMINHO_DIRETORIO + File.separator + identificador;
    }

    private String formatarEntidadeOperadora(EntidadeOperadora entidade) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        return String.format("%d;%s;%b;%.2f;%.2f;%s;%s",
                entidade.getIdentificador(),
                entidade.getNome(),
                entidade.getAutorizacaoAcao(),
                entidade.getSaldoAcao(),
                entidade.getSaldoTituloDivida(),
                entidade.getDataHoraInclusao().format(dateTimeFormatter),
                entidade.getDataHoraUltimaAlteracao() != null
                        ? entidade.getDataHoraUltimaAlteracao().format(dateTimeFormatter)
                        : "null");
    }


    private EntidadeOperadora converterStringParaEntidadeOperadora(String linha) {
        String[] partes = linha.split(";");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        long identificador = Long.parseLong(partes[0]);
        String nome = partes[1];
        boolean autorizacaoAcao = Boolean.parseBoolean(partes[2]);

        // Substitui vírgulas por pontos para evitar erro na conversão
        double saldoAcao = Double.parseDouble(partes[3].replace(",", "."));
        double saldoTituloDivida = Double.parseDouble(partes[4].replace(",", "."));

        LocalDateTime dataHoraInclusao = LocalDateTime.parse(partes[5], dateTimeFormatter).truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime dataHoraUltimaAlteracao = partes[6].equals("null")
                ? null
                : LocalDateTime.parse(partes[6], dateTimeFormatter).truncatedTo(ChronoUnit.SECONDS);

        EntidadeOperadora entidade = new EntidadeOperadora(identificador, nome, autorizacaoAcao);
        entidade.creditarSaldoAcao(saldoAcao);
        entidade.creditarSaldoTituloDivida(saldoTituloDivida);
        entidade.setDataHoraInclusao(dataHoraInclusao);
        entidade.setDataHoraUltimaAlteracao(dataHoraUltimaAlteracao);

        return entidade;
    }

}
