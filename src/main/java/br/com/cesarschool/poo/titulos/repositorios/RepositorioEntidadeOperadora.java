package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RepositorioEntidadeOperadora {

    private static final String CAMINHO_ARQUIVO = "src/main/java/br/com/cesarschool/poo/titulos/repositorios/EntidadeOperadora.txt";

    public boolean incluir(EntidadeOperadora entidadeOperadora) {

        if (procurarId(entidadeOperadora.getIdentificador())) {
            return false; // Identificador já existe
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO, true))) {
            String frase = entidadeOperadora.getIdentificador() + ";" + entidadeOperadora.getNome() + ";" +
                    entidadeOperadora.getAutorizacaoAcao() + ";" + entidadeOperadora.getSaldoAcao() + ";" +
                    entidadeOperadora.getSaldoTituloDivida();
            escritor.write(frase);
            escritor.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean alterar(EntidadeOperadora entidadeOperadora) {

        List<String> linhas = new ArrayList<>();
        boolean alterado = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] divisao = linha.split(";");

                if (Long.parseLong(divisao[0]) == entidadeOperadora.getIdentificador()) {
                    String novaLinha = entidadeOperadora.getIdentificador() + ";" + entidadeOperadora.getNome() + ";" +
                            entidadeOperadora.getAutorizacaoAcao() + ";" + entidadeOperadora.getSaldoAcao() + ";" +
                            entidadeOperadora.getSaldoTituloDivida();
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

    public boolean excluir(long identificador) {

        List<String> linhas = new ArrayList<>();
        boolean deletado = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] divisao = linha.split(";");

                if (Long.parseLong(divisao[0]) == identificador) {
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

    public EntidadeOperadora buscar(long identificador) {

        try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] divisao = linha.split(";");

                if (Long.parseLong(divisao[0]) == identificador) {
                    EntidadeOperadora entidadeOperadora = new EntidadeOperadora(
                            identificador,
                            divisao[1],
                            Boolean.parseBoolean(divisao[2])
                    );
                    entidadeOperadora.creditarSaldoAcao(Double.parseDouble(divisao[3]));
                    entidadeOperadora.creditarSaldoTituloDivida(Double.parseDouble(divisao[4]));
                    return entidadeOperadora;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean procurarId(long identificador) {

        try (BufferedReader leitor = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(";");
                if (Long.parseLong(partes[0]) == identificador) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
