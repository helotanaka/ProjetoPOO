package br.com.cesarschool.poo.titulos;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;

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
                nome = ENTRADA.nextLine();

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
            }
        }
    }
}
