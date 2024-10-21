package br.com.cesarschool.poo.titulos;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.mediators.MediatorAcao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ProgramaAcaoSwing {
    private JFrame frame;
    private JTextField identificadorField, nomeField, dataValidadeField, valorUnitarioField;
    private JTextArea outputArea;
    private JButton incluirButton, alterarButton, excluirButton, buscarButton;

    private MediatorAcao mediatorAcao = MediatorAcao.getInstance();

    public ProgramaAcaoSwing() {
        prepareGUI();
    }

    private void prepareGUI() {
        frame = new JFrame("Gerenciamento de Ações");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2));
        frame.add(panel, BorderLayout.CENTER);

        identificadorField = new JTextField();
        nomeField = new JTextField();
        dataValidadeField = new JTextField();
        valorUnitarioField = new JTextField();

        panel.add(new JLabel("Identificador:"));
        panel.add(identificadorField);
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Data de Validade (yyyy-MM-dd):"));
        panel.add(dataValidadeField);
        panel.add(new JLabel("Valor Unitário:"));
        panel.add(valorUnitarioField);

        incluirButton = new JButton("Incluir");
        incluirButton.addActionListener(this::handleIncluir);
        alterarButton = new JButton("Alterar");
        alterarButton.addActionListener(this::handleAlterar);
        excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(this::handleExcluir);
        buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(this::handleBuscar);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(incluirButton);
        buttonPanel.add(alterarButton);
        buttonPanel.add(excluirButton);
        buttonPanel.add(buscarButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        outputArea = new JTextArea(4, 40);
        frame.add(new JScrollPane(outputArea), BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
    }

    private void handleIncluir(ActionEvent e) {
        try {
            int identificador = Integer.parseInt(identificadorField.getText().trim());
            String nome = nomeField.getText().trim();
            LocalDate dataDeValidade = LocalDate.parse(dataValidadeField.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            double valorUnitario = Double.parseDouble(valorUnitarioField.getText().trim());

            Acao acao = new Acao(identificador, nome, dataDeValidade, valorUnitario);
            String mensagem = mediatorAcao.incluir(acao);

            if (mensagem == null) {
                outputArea.setText("Ação incluída com sucesso!");
            } else {
                outputArea.setText("Erro: " + mensagem);
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Erro de formatação numérica.");
        } catch (DateTimeParseException ex) {
            outputArea.setText("Erro de formatação de data.");
        } catch (Exception ex) {
            outputArea.setText("Erro ao incluir ação: " + ex.getMessage());
        }
    }

    private void handleAlterar(ActionEvent e) {
        // Similar to handleIncluir, but calls mediatorAcao.alterar
    }

    private void handleExcluir(ActionEvent e) {
        // Similar to handleExcluir in ProgramaTituloDividaSwing
    }

    private void handleBuscar(ActionEvent e) {
        // Similar to handleBuscar in ProgramaTituloDividaSwing
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProgramaAcaoSwing::new);
    }
}

