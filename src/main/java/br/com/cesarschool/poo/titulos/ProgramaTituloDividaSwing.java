package br.com.cesarschool.poo.titulos;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ProgramaTituloDividaSwing {
    private JFrame frame;
    private JTextField identificadorField, nomeField, dataValidadeField, taxaJurosField, montanteField;
    private JTextArea outputArea;
    private JButton incluirButton, alterarButton, excluirButton, buscarButton, calcularPrecoButton, ajustarTaxaButton, consultarTaxaButton;

    private MediatorTituloDivida mediator = MediatorTituloDivida.getInstance();

    public ProgramaTituloDividaSwing() {
        prepareGUI();
    }

    private void prepareGUI() {
        frame = new JFrame("Gerenciamento de Títulos de Dívida");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setLayout(new BorderLayout(5, 5));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);  // margin around components
        gbc.anchor = GridBagConstraints.NORTHWEST;

        frame.add(panel, BorderLayout.NORTH);

        panel.add(new JLabel("Identificador:"), gbc);
        identificadorField = new JTextField(10);
        panel.add(identificadorField, gbc);

        panel.add(new JLabel("Nome:"), gbc);
        nomeField = new JTextField(10);
        panel.add(nomeField, gbc);

        panel.add(new JLabel("Data de Validade (yyyy-MM-dd):"), gbc);
        dataValidadeField = new JTextField(10);
        panel.add(dataValidadeField, gbc);

        panel.add(new JLabel("Taxa de Juros (%):"), gbc);
        taxaJurosField = new JTextField(10);
        panel.add(taxaJurosField, gbc);

        panel.add(new JLabel("Montante para Cálculo:"), gbc);
        montanteField = new JTextField(10);
        panel.add(montanteField, gbc);

        // Setting up buttons in the next row
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        incluirButton = new JButton("Incluir");
        incluirButton.addActionListener(this::handleIncluir);
        panel.add(incluirButton, gbc);

        alterarButton = new JButton("Alterar");
        alterarButton.addActionListener(this::handleAlterar);
        panel.add(alterarButton, gbc);

        excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(this::handleExcluir);
        panel.add(excluirButton, gbc);

        buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(this::handleBuscar);
        panel.add(buscarButton, gbc);

        calcularPrecoButton = new JButton("Calcular Preço pós transação");
        calcularPrecoButton.addActionListener(this::handleCalcularPreco);
        panel.add(calcularPrecoButton, gbc);

        ajustarTaxaButton = new JButton("Alterar Taxa");
        ajustarTaxaButton.addActionListener(this::handleAjustarTaxa);
        panel.add(ajustarTaxaButton, gbc);

        consultarTaxaButton = new JButton("Consultar Taxa");
        consultarTaxaButton.addActionListener(this::handleConsultarTaxa);
        panel.add(consultarTaxaButton, gbc);

        outputArea = new JTextArea(5, 20);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        frame.pack();  // Pack frame to respect preferred sizes
        frame.setVisible(true);
    }


    private void handleIncluir(ActionEvent e) {
        try {
            int identificador = Integer.parseInt(identificadorField.getText().trim());
            String nome = nomeField.getText().trim();
            LocalDate dataDeValidade = LocalDate.parse(dataValidadeField.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            double taxaJuros = Double.parseDouble(taxaJurosField.getText().trim());

            TituloDivida titulo = new TituloDivida(identificador, nome, dataDeValidade, taxaJuros);
            String mensagem = mediator.incluir(titulo);

            if (mensagem == null) {
                outputArea.setText("Título de Dívida incluído com sucesso!");
            } else {
                outputArea.setText("Erro: " + mensagem);
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Erro de formatação: Por favor, verifique os dados numéricos.");
        } catch (DateTimeParseException ex) {
            outputArea.setText("Erro de formatação de data: Por favor, use o formato correto (yyyy-MM-dd).");
        } catch (Exception ex) {
            outputArea.setText("Erro ao incluir título: " + ex.getMessage());
        }
    }

    private void handleAlterar(ActionEvent e) {
        try {
            int identificador = Integer.parseInt(identificadorField.getText().trim());
            String nome = nomeField.getText().trim();
            LocalDate dataDeValidade = LocalDate.parse(dataValidadeField.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            double taxaJuros = Double.parseDouble(taxaJurosField.getText().trim());

            TituloDivida titulo = new TituloDivida(identificador, nome, dataDeValidade, taxaJuros);
            String mensagem = mediator.alterar(titulo);

            if (mensagem == null) {
                outputArea.setText("Título de Dívida atualizado com sucesso!");
            } else {
                outputArea.setText("Erro: " + mensagem);
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Erro de formatação: Verifique os dados numéricos.");
        } catch (DateTimeParseException ex) {
            outputArea.setText("Erro de formatação de data: Use o formato correto (yyyy-MM-dd).");
        } catch (Exception ex) {
            outputArea.setText("Erro ao atualizar título: " + ex.getMessage());
        }
    }


    private void handleExcluir(ActionEvent e) {
        try {
            int identificador = Integer.parseInt(identificadorField.getText().trim());
            String mensagem = mediator.excluir(identificador);

            if (mensagem == null) {
                outputArea.setText("Título de Dívida excluído com sucesso!");
            } else {
                outputArea.setText("Erro: " + mensagem);
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Erro: Identificador inválido.");
        }
    }


    private void handleBuscar(ActionEvent e) {
        try {
            int identificador = Integer.parseInt(identificadorField.getText().trim());
            TituloDivida titulo = mediator.buscar(identificador);

            if (titulo != null) {
                outputArea.setText("Encontrado: ID " + titulo.getIdentificador() + ", Nome: " + titulo.getNome() + ", Data: " + titulo.getDataDeValidade() + ", Juros: " + titulo.getTaxaJuros() + "%");
            } else {
                outputArea.setText("Título de Dívida não encontrado.");
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Erro: Identificador inválido.");
        }
    }

    private void handleCalcularPreco(ActionEvent e) {
        try {
            int identificador = Integer.parseInt(identificadorField.getText().trim());
            double montante = Double.parseDouble(montanteField.getText().trim());
            TituloDivida titulo = mediator.buscar(identificador);

            if (titulo != null) {
                double precoTransacao = titulo.calcularPrecoTransacao(montante);
                outputArea.setText("Preço após transação: " + precoTransacao);
            } else {
                outputArea.setText("Título de Dívida não encontrado.");
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Erro: Verifique os valores inseridos.");
        }
    }


    private void handleAjustarTaxa(ActionEvent e) {
        try {
            int identificador = Integer.parseInt(identificadorField.getText().trim());
            double novaTaxa = Double.parseDouble(taxaJurosField.getText().trim());
            TituloDivida titulo = mediator.buscar(identificador);

            if (titulo != null) {
                titulo.setTaxaJuros(novaTaxa);
                outputArea.setText("Taxa de juros atualizada para: " + novaTaxa + "%");
            } else {
                outputArea.setText("Título de Dívida não encontrado.");
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Erro: Verifique os valores inseridos.");
        }
    }


    private void handleConsultarTaxa(ActionEvent e) {
        try {
            int identificador = Integer.parseInt(identificadorField.getText().trim());
            TituloDivida titulo = mediator.buscar(identificador);

            if (titulo != null) {
                outputArea.setText("Taxa de juros atual: " + titulo.getTaxaJuros() + "%");
            } else {
                outputArea.setText("Título de Dívida não encontrado.");
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Erro: Identificador inválido.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProgramaTituloDividaSwing::new);
    }
}
