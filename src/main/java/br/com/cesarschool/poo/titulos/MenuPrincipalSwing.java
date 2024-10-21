package br.com.cesarschool.poo.titulos;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalSwing {
    private JFrame frame;
    private JButton tituloDividaButton, acaoButton, entidadeOperadoraButton, transacaoButton;

    public MenuPrincipalSwing() {
        criarInterface();
    }

    private void criarInterface() {
        frame = new JFrame("Sistema de Gestão de Ativos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(2, 2, 10, 10));  // Grid layout para organizar os botões

        tituloDividaButton = new JButton("Título Dívida");
        tituloDividaButton.addActionListener(e -> abrirTituloDivida());
        frame.add(tituloDividaButton);

        acaoButton = new JButton("Ação");
        acaoButton.addActionListener(e -> abrirAcao());
        frame.add(acaoButton);

        entidadeOperadoraButton = new JButton("Entidade Operadora");
        entidadeOperadoraButton.addActionListener(e -> abrirEntidadeOperadora());
        frame.add(entidadeOperadoraButton);

        transacaoButton = new JButton("Transação");
        transacaoButton.addActionListener(e -> abrirTransacao());
        frame.add(transacaoButton);

        frame.setVisible(true);
    }

    private void abrirTituloDivida() {
        // Abre a interface de gerenciamento de Título de Dívida
        SwingUtilities.invokeLater(ProgramaTituloDividaSwing::new);
    }

    private void abrirAcao() {
        // Abre a interface de gerenciamento de Ações
        SwingUtilities.invokeLater(ProgramaAcaoSwing::new);
    }

    private void abrirEntidadeOperadora() {
        // Abre a interface de gerenciamento de Entidade Operadora
       // SwingUtilities.invokeLater(ProgramaEntidadeOperadoraSwing::new);
    }

    private void abrirTransacao() {
        // Abre a interface de gerenciamento de Transações
        //SwingUtilities.invokeLater(ProgramaTransacaoSwing::new);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipalSwing::new);
    }


}

