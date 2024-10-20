package br.com.cesarschool.poo.titulos;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntdadeOperadora;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Programa {
    static Scanner ENTRADA = new Scanner(System.in);
    static MediatorAcao mediatorAcao = MediatorAcao.getInstance();
    public static void main(String[] args) {
        System.out.println("Atividade POO");

        int selecaoFora, selecaoDentro;

        System.out.println("\nDigite o número da opção desejada:\n1.Ação\n2.Entidade Operadora\n3.Tútulo Dívida\n4.Transação");
        selecaoFora = ENTRADA.nextInt();

        if(selecaoFora == 1){
            System.out.println("\nDigite o número da opção desejada:\n1.Incluir\n2.Alterar\n3.Excluir\n4.Buscar");
            selecaoDentro = ENTRADA.nextInt();
            if(selecaoDentro == 1){
                int identificador;
                String nome;
                LocalDate dataDeValidade;
                double valorUnitario;

                System.out.println("\nDigite o identificador: ");
                identificador = ENTRADA.nextInt();
                ENTRADA.nextLine();

                System.out.println("\nDigite o nome: ");
                nome = ENTRADA.nextLine();

                System.out.println("\nDigite a data (yyyy-MM-dd): ");
                String dataTexto = ENTRADA.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dataDeValidade = LocalDate.parse(dataTexto, formatter);

                System.out.println("\nDigite o valor unitário: ");
                valorUnitario = ENTRADA.nextDouble();

                Acao acao = new Acao(identificador, nome, dataDeValidade, valorUnitario);

                String mensagem = mediatorAcao.incluir(acao);

                if(mensagem == null){
                    System.out.println("Operação concluída com sucesso!");
                }else{
                    System.out.println(mensagem);
                }
            } else if (selecaoDentro == 2) {
                int identificador;
                String nome;
                LocalDate dataDeValidade;
                double valorUnitario;

                System.out.println("\nDigite o identificador da linha a ser alterada: ");
                identificador = ENTRADA.nextInt();
                ENTRADA.nextLine();

                System.out.println("\nDigite o nome: ");
                nome = ENTRADA.nextLine();

                System.out.println("\nDigite a data (yyyy-MM-dd): ");
                String dataTexto = ENTRADA.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dataDeValidade = LocalDate.parse(dataTexto, formatter);

                System.out.println("\nDigite o valor unitário: ");
                valorUnitario = ENTRADA.nextDouble();

                Acao acao = new Acao(identificador, nome, dataDeValidade, valorUnitario);

                String mensagem = mediatorAcao.alterar(acao);

                if(mensagem == null){
                    System.out.println("Operação concluída com sucesso!");
                }else{
                    System.out.println(mensagem);
                }
            } else if (selecaoDentro == 3) {
                int identificador;

                System.out.println("\nDigite o identificador da linha a ser excluída: ");
                identificador = ENTRADA.nextInt();
                ENTRADA.nextLine();

                String mensagem = mediatorAcao.excluir(identificador);

                if(mensagem == null){
                    System.out.println("Operação concluída com sucesso!");
                }else{
                    System.out.println(mensagem);
                }
            } else if (selecaoDentro == 4) {
                int identificador;
                String nome;
                LocalDate dataDeValidade;
                double valorUnitario;

                System.out.println("\nDigite o identificador da linha a ser alterada: ");
                identificador = ENTRADA.nextInt();
                ENTRADA.nextLine();

                System.out.println("\nDigite o nome: ");
                nome = ENTRADA.nextLine();

                System.out.println("\nDigite a data (yyyy-MM-dd): ");
                String dataTexto = ENTRADA.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dataDeValidade = LocalDate.parse(dataTexto, formatter);

                System.out.println("\nDigite o valor unitário: ");
                valorUnitario = ENTRADA.nextDouble();

                Acao acao = new Acao(identificador, nome, dataDeValidade, valorUnitario);

                String mensagem = mediatorAcao.alterar(acao);

                if(mensagem == null){
                    System.out.println("Operação concluída com sucesso!");
                }else{
                    System.out.println(mensagem);
                }
            }

        }else if(selecaoFora == 2){
            System.out.println("\nDigite o número da opção desejada:\n1.Incluir\n2.Alterar\n3.Excluir\n4.Buscar");

            selecaoDentro = ENTRADA.nextInt();

            MediatorEntdadeOperadora mediatorEntidade = MediatorEntdadeOperadora.getInstance();
            if (selecaoDentro == 1){
                    // Incluir
                    System.out.println("\nDigite o identificador(DEVE ESTAR ENTRE 100 E 1000): ");
                    long identificador = ENTRADA.nextLong();
                    ENTRADA.nextLine(); // consume line JUST IN CASE

                    System.out.println("\nDigite o nome(Deve ter entre 10 e 100 caracteres): ");
                    String nome = ENTRADA.nextLine();

                    System.out.println("\nDigite se tem autorização de ação (true/false): ");
                    boolean autorizacaoAcao = ENTRADA.nextBoolean(); // hmmmm o cara tem que escrever certinho true ou false

                    EntidadeOperadora entidade = new EntidadeOperadora(identificador, nome, autorizacaoAcao);
                    String mensagem = mediatorEntidade.incluir(entidade);

                    if (mensagem == null) {
                        System.out.println("Entidade incluída com sucesso!");
                    } else {
                        System.out.println("Erro: " + mensagem);
                    }
            }

            if (selecaoDentro == 2){
                // Alterar
                System.out.println("\nDigite o identificador da entidade a ser alterada: ");
                long identificador = ENTRADA.nextLong();
                ENTRADA.nextLine();

                System.out.println("\nDigite o novo nome: ");
                String novoNome = ENTRADA.nextLine();

                System.out.println("\nDigite se tem autorização de ação (true/false): ");
                boolean novaAutorizacaoAcao = ENTRADA.nextBoolean();

                EntidadeOperadora entidade = new EntidadeOperadora(identificador, novoNome, novaAutorizacaoAcao);
                String mensagem = mediatorEntidade.alterar(entidade);

                if (mensagem == null) {
                    System.out.println("Entidade alterada com sucesso!");
                } else {
                    System.out.println("Erro: " + mensagem);
                }
            }

            if (selecaoDentro == 3){
                // Excluir
                System.out.println("\nDigite o identificador da entidade a ser excluída: ");
                long identificador = ENTRADA.nextLong();

                String mensagem = mediatorEntidade.excluir((int) identificador);

                if (mensagem == null) {
                    System.out.println("Entidade excluída com sucesso!");
                } else {
                    System.out.println("Erro: " + mensagem);
                }
            }

            if ( selecaoDentro == 4){ // search

                System.out.println("\nDigite o identificador da entidade a ser buscada: ");

                long identificador = ENTRADA.nextLong();

                EntidadeOperadora entidade = mediatorEntidade.buscar((int) identificador);

                if (entidade != null) {
                    System.out.println("Entidade encontrada:");
                    System.out.println("Identificador: " + entidade.getIdentificador());
                    System.out.println("Nome: " + entidade.getNome());
                    System.out.println("Autorização de Ação: " + entidade.getAutorizacaoAcao());
                    System.out.println("Saldo de Ação: " + entidade.getSaldoAcao());
                    System.out.println("Saldo de Título de Dívida: " + entidade.getSaldoTituloDivida());
                } else {
                    System.out.println("Entidade não encontrada.");
                }
            }
        }else if(selecaoFora == 3){

        }else if(selecaoFora == 4){

        }
    }
}
