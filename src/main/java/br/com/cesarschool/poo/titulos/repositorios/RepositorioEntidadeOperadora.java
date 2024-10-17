package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
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
 * Acao. Seguem abaixo exemplos de linhas.
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
 *
 * "ent eu fiz praticamente a mesma coisa da classe acao só que com a classe EntidadeOperadora"
 *
 */


public class RepositorioEntidadeOperadora {

    public boolean incluir(EntidadeOperadora entidadeoperadora){
        if (procurarId(entidadeoperadora.getIdentificador())){

            return false;

        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("Acao.txt", true))){ // how would I handle this part?
            String frase = entidadeoperadora.getIdentificador() + ";" + entidadeoperadora.getNome() + ";" + entidadeoperadora.getDataDeValidade() + entidadeoperadora.getValorUnitario();
            escritor.write(frase);

        }


    }

    /*U
        If the procurarId function is used in the same way in two different classes and performs the same task,
        a good practice to avoid code duplication is to implement this method in a shared utility class or as a static
        method in a parent class if there's a logical inheritance relationship between the classes.
        Doesn't seem like I can do either of those things, based on what the teacher asked for.
     */

    private boolean procurarId(long identificador){ // had to change to LONG here instead of INT
        try (BufferedReader leitor = new BufferedReader(new FileReader("Acao.txt"))) { //lê o texto
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
