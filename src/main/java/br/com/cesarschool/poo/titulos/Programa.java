package br.com.cesarschool.poo.titulos;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;
import br.com.cesarschool.poo.titulos.mediators.MediatorEntidadeOperadora;

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

        if (selecaoFora == 1) {
            System.out.println("\nDigite o número da opção desejada:\n1.Incluir\n2.Alterar\n3.Excluir\n4.Buscar\n5.Preço Transação");
            selecaoDentro = ENTRADA.nextInt();

            if (selecaoDentro == 1) {
                System.out.println("\nDigite o identificador: ");
                int identificador = ENTRADA.nextInt();
                ENTRADA.nextLine();

                System.out.println("\nDigite o nome (entre 10 e 100 caracteres): ");
                String nome = ENTRADA.nextLine();

                System.out.println("\nDigite a data (yyyy-MM-dd): ");
                String dataTexto = ENTRADA.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDeValidade = LocalDate.parse(dataTexto, formatter);

                System.out.println("\nDigite o valor unitário: ");
                double valorUnitario = ENTRADA.nextDouble();

                Acao acao = new Acao(identificador, nome, dataDeValidade, valorUnitario);
                String mensagem = mediatorAcao.incluir(acao);

                if (mensagem == null) {
                    System.out.println("Ação incluída com sucesso!");
                } else {
                    System.out.println("Erro: " + mensagem);
                }
            } else if (selecaoDentro == 2) {
                System.out.println("\nDigite o identificador da ação a ser alterada: ");
                int identificador = ENTRADA.nextInt();
                ENTRADA.nextLine();

                System.out.println("\nDigite o novo nome: ");
                String nome = ENTRADA.nextLine();

                System.out.println("\nDigite a nova data (yyyy-MM-dd): ");
                String dataTexto = ENTRADA.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDeValidade = LocalDate.parse(dataTexto, formatter);

                System.out.println("\nDigite o novo valor unitário: ");
                double valorUnitario = ENTRADA.nextDouble();

                Acao acao = new Acao(identificador, nome, dataDeValidade, valorUnitario);
                String mensagem = mediatorAcao.alterar(acao);

                if (mensagem == null) {
                    System.out.println("Ação alterada com sucesso!");
                } else {
                    System.out.println("Erro: " + mensagem);
                }
            } else if (selecaoDentro == 3) {
                System.out.println("\nDigite o identificador da ação a ser excluída: ");
                int identificador = ENTRADA.nextInt();

                String mensagem = mediatorAcao.excluir(identificador);

                if (mensagem == null) {
                    System.out.println("Ação excluída com sucesso!");
                } else {
                    System.out.println("Erro: " + mensagem);
                }
            } else if (selecaoDentro == 4) {
                System.out.println("\nDigite o identificador da ação a ser buscada: ");
                int identificador = ENTRADA.nextInt();

                Acao acao = mediatorAcao.buscar(identificador);

                if (acao != null) {
                    System.out.println("Ação encontrada:");
                    System.out.println("Identificador: " + acao.getIdentificador());
                    System.out.println("Nome: " + acao.getNome());
                    System.out.println("Data de Validade: " + acao.getDataDeValidade());
                    System.out.println("Valor Unitário: " + acao.getValorUnitario());
                } else {
                    System.out.println("Ação não encontrada.");
                }
            }else if (selecaoDentro == 5){
                System.out.println("\nDigite o identificador da ação a ser utilizada no cálculo: ");
                int identificador = ENTRADA.nextInt();

                Acao acao = mediatorAcao.buscar(identificador);

                System.out.println("\nDigite o valor do montante: ");
                int montante = ENTRADA.nextInt();

                System.out.println("\nPreço Transação: " + acao.calcularPrecoTransacao(montante));
            }
        }
        else if(selecaoFora == 2){
            System.out.println("\nDigite o número da opção desejada:\n1.Incluir\n2.Alterar\n3.Excluir\n4.Buscar\n5.Operações Saldo Ação\n6.Operações Título Divida");

            selecaoDentro = ENTRADA.nextInt();

            MediatorEntidadeOperadora mediatorEntidade = MediatorEntidadeOperadora.getInstance();
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

            if (selecaoDentro == 4){ // search
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

            if (selecaoDentro == 5){
                System.out.println("\nDigite o número da opção desejada:\n1.Creditar\n2.Debitar");
                int opcao = ENTRADA.nextInt();
                if(opcao == 1){
                    System.out.println("\nDigite o identificador da entidade a ser buscada: ");
                    long identificador = ENTRADA.nextLong();

                    EntidadeOperadora entidade = mediatorEntidade.buscar((int) identificador);

                    System.out.println("\nDigite valor a ser creditado: ");
                    double valor = ENTRADA.nextDouble();

                    entidade.creditarSaldoAcao(valor);

                    System.out.println("Valor creditado com sucesso! ");
                }else if(opcao == 2){
                    System.out.println("\nDigite o identificador da entidade a ser buscada: ");
                    long identificador = ENTRADA.nextLong();

                    EntidadeOperadora entidade = mediatorEntidade.buscar((int) identificador);

                    System.out.println("\nDigite valor a ser debitado: ");
                    double valor = ENTRADA.nextDouble();

                    entidade.debitarSaldoAcao(valor);

                    System.out.println("Valor debitado com sucesso! ");
                }
            }

            if (selecaoDentro == 6){
                System.out.println("\nDigite o número da opção desejada:\n1.Creditar\n2.Debitar");
                int opcao = ENTRADA.nextInt();
                if(opcao == 1){
                    System.out.println("\nDigite o identificador da entidade a ser buscada: ");
                    long identificador = ENTRADA.nextLong();

                    EntidadeOperadora entidade = mediatorEntidade.buscar((int) identificador);

                    System.out.println("\nDigite valor a ser creditado: ");
                    double valor = ENTRADA.nextDouble();

                    entidade.creditarSaldoTituloDivida(valor);

                    System.out.println("Valor creditado com sucesso! ");
                }else if(opcao == 2){
                    System.out.println("\nDigite o identificador da entidade a ser buscada: ");
                    long identificador = ENTRADA.nextLong();

                    EntidadeOperadora entidade = mediatorEntidade.buscar((int) identificador);

                    System.out.println("\nDigite valor a ser debitado: ");
                    double valor = ENTRADA.nextDouble();

                    entidade.debitarSaldoTituloDivida(valor);

                    System.out.println("Valor debitado com sucesso! ");
                }
            }
        }
        else if(selecaoFora == 3){

        }else if(selecaoFora == 4){

        }
    }
}
