package br.com.cesarschool.poo.titulos;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntdadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Programa {
    static Scanner ENTRADA = new Scanner(System.in);
    static MediatorAcao mediatorAcao = MediatorAcao.getInstance();
    static MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getInstance();


    public static void main(String[] args) {
        System.out.println("Atividade POO");

        int selecaoFora, selecaoDentro;

        System.out.println("\nDigite o número da opção desejada:\n1.Ação\n2.Entidade Operadora\n3.Título Dívida\n4.Transação");
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

            System.out.println("\nDigite o número da opção desejada:\n1.Incluir\n2.Alterar\n3.Excluir\n4.Buscar\n5.Visualizar preço da Transação\n6. Ajustar apenas Taxa de Juros\n7. Consultar apenas Taxa de Juros\n"); // ainda incluirget taxa juros , set taxa juros , calcularPrecoTransacao
            selecaoDentro = ENTRADA.nextInt();

            if (selecaoDentro == 1){
                //incluir
                System.out.println("\\nDigite o identificador (1-99999):");
                int identificador = ENTRADA.nextInt();
                ENTRADA.nextLine(); // nosso clear de todo dia, já q teve o enter

                System.out.println("\nDigite o nome (10-100 caracteres): ");
                String nome = ENTRADA.nextLine();

                System.out.println("\nDigite a data de validade (yyyy-MM-dd): ");
                String dataTexto = ENTRADA.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDeValidade = LocalDate.parse(dataTexto, formatter);

                System.out.println("\nDigite a taxa de juros: ");
                double taxaJuros = ENTRADA.nextDouble();

                TituloDivida titulo = new TituloDivida(identificador, nome, dataDeValidade, taxaJuros);
                String mensagem = mediatorTituloDivida.incluir(titulo);

                if(mensagem.equals("Título já existente")){
                    System.out.println(mensagem);
                }else if (mensagem == null){
                    System.out.println("Título de Dívida incluído com sucesso!");
                }


            }

            if (selecaoDentro == 2){
                //alterar

                System.out.println("\\nDigite o identificador (1-99999):");
                int identificador = ENTRADA.nextInt();
                ENTRADA.nextLine(); // nosso clear de todo dia, já q teve o enter

                System.out.println("\nDigite o nome (10-100 caracteres): ");
                String nome = ENTRADA.nextLine();

                System.out.println("\nDigite a data de validade (yyyy-MM-dd): ");
                String dataTexto = ENTRADA.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDeValidade = LocalDate.parse(dataTexto, formatter);

                System.out.println("\nDigite a taxa de juros: ");
                double taxaJuros = ENTRADA.nextDouble();

                TituloDivida titulo = new TituloDivida(identificador, nome, dataDeValidade, taxaJuros);
                String mensagem = mediatorTituloDivida.alterar(titulo);

                if(mensagem.equals("Título inexistente")){
                    System.out.println(mensagem);
                }else if (mensagem == null){
                    System.out.println("Título de Dívida atualizado com sucesso!");
                }


            }

            if (selecaoDentro == 3){
//excluir
                System.out.println("\nDigite o identificador do título a ser excluído: ");
                int identificador = ENTRADA.nextInt();
                String mensagem = mediatorTituloDivida.excluir(identificador);

                if(mensagem == null){
                    System.out.println("Título de Dívida excluído com sucesso!");
                } else {
                    System.out.println(mensagem);
                }

            }

            if (selecaoDentro == 4){
                //buscar
                System.out.println("\nDigite o identificador do título a ser buscado: ");
                int identificador = ENTRADA.nextInt();
                TituloDivida tituloEncontrado = mediatorTituloDivida.buscar(identificador);

                if(tituloEncontrado != null){
                    System.out.println("Título de Dívida encontrado:");
                    System.out.println("Identificador: " + tituloEncontrado.getIdentificador());
                    System.out.println("Nome: " + tituloEncontrado.getNome());
                    System.out.println("Data de Validade: " + tituloEncontrado.getDataDeValidade());
                    System.out.println("Taxa de Juros: " + tituloEncontrado.getTaxaJuros());
                } else {
                    System.out.println("Título de Dívida não encontrado.");
                }

            }

            if (selecaoDentro == 5){

                // Calcular preço de transação
                System.out.println("\nDigite o identificador do título para cálculo: ");
                int identificador = ENTRADA.nextInt();
                TituloDivida titulo = mediatorTituloDivida.buscar(identificador);
                if (titulo != null) {
                    System.out.println("\nDigite o montante para cálculo: ");
                    double montante = ENTRADA.nextDouble();
                    double precoTransacao = titulo.calcularPrecoTransacao(montante);
                    System.out.println("Preço após transação: " + precoTransacao);
                } else {
                    System.out.println("Título de Dívida não encontrado.");
                }

            }

            if (selecaoDentro == 6){
                //Ajuste taxa de juros
                System.out.println("\nDigite o identificador do título para ajuste da taxa de juros: ");
                int identificador = ENTRADA.nextInt();
                TituloDivida titulo = mediatorTituloDivida.buscar(identificador);
                if (titulo != null) {
                    System.out.println("\nDigite a nova taxa de juros (%): ");
                    double novaTaxa = ENTRADA.nextDouble();
                    titulo.setTaxaJuros(novaTaxa);
                    System.out.println("Taxa de juros atualizada com sucesso!");
                } else {
                    System.out.println("Título de Dívida não encontrado.");
                }
            }

            if (selecaoDentro == 7){
                //consultar taxa de juros
                System.out.println("\nDigite o identificador do título para consulta de taxa de juros: ");
                int identificador = ENTRADA.nextInt();
                TituloDivida titulo = mediatorTituloDivida.buscar(identificador);
                if (titulo != null) {
                    System.out.println("Taxa de juros atual: " + titulo.getTaxaJuros() + "%");
                } else {
                    System.out.println("Título de Dívida não encontrado.");
                }

            }



        }else if(selecaoFora == 4){

        }
    }
}
