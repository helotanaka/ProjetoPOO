package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RepositorioEntidadeOperadora {

    public boolean incluir(EntidadeOperadora entidadeOperadora) {
        if(procurarId(entidadeOperadora.getIdentificador()) == true){ // se achar id igual
            return false;
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("EntidadeOperadora.txt", true))) {// escrever no arquivo
            String frase = entidadeOperadora.getIdentificador() + ";" + entidadeOperadora.getNome() + ";" + entidadeOperadora.getAutorizacaoAcao() +";" + entidadeOperadora.getSaldoAcao() + ";" + entidadeOperadora.getSaldoTituloDivida();
            escritor.write(frase); //Coloca a frase no txt
            escritor.newLine(); // Adiciona uma nova linha
            return true; // Inclusão com sucesso
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean alterar(EntidadeOperadora entidadeOperadora) {
        List<String> linhas = new ArrayList<>();
        boolean alterado = false;

        try (BufferedReader leitor = new BufferedReader(new FileReader("EntidadeOperadora.txt"))){
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] divisao = linha.split(";");

                // Verifica se o identificador da linha corresponde ao identificador da ação a ser alterada
                if (Integer.parseInt(divisao[0]) == entidadeOperadora.getIdentificador()) {
                    // Monta a nova linha com os dados da ação fornecida
                    String novaLinha = entidadeOperadora.getIdentificador() + ";" + entidadeOperadora.getNome() + ";" + entidadeOperadora.getAutorizacaoAcao() +";" + entidadeOperadora.getSaldoAcao() + ";" + entidadeOperadora.getSaldoTituloDivida();
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

        try (BufferedReader leitor = new BufferedReader(new FileReader("EntidadeOperadora.txt"))){
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
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter("EntidadeOperadora.txt"))) {
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

    public EntidadeOperadora buscar(int identificador) {
        if(procurarId(identificador) == false){ // se não achar id igual
            return null;
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader("EntidadeOperadora.txt"))) {
            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] divisao = linha.split(";");

                if (Integer.parseInt(divisao[0]) == identificador) {
                    return new EntidadeOperadora(identificador, divisao[1], divisao[2].equals("true"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean procurarId(long identificador){ // had to change to LONG here instead of INT
        try (BufferedReader leitor = new BufferedReader(new FileReader("EntidadeOperadora.txt"))) { //lê o texto
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
        return false; // Identificador não encontrado
    }
}
