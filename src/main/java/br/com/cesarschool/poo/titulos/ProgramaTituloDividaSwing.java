package br.com.cesarschool.poo.titulos;

import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import br.com.cesarschool.poo.titulos.mediators.MediatorTituloDivida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        frame.setSize(500, 500);
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

        calcularPrecoButton = new JButton("Calcular Preço");
        calcularPrecoButton.addActionListener(this::handleCalcularPreco);
        panel.add(calcularPrecoButton, gbc);

        ajustarTaxaButton = new JButton("Ajustar Taxa");
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
        // Parse fields and call mediator.incluir(new TituloDivida(...))
        // Display result in outputArea
    }

    private void handleAlterar(ActionEvent e) {
        // Similar to handleIncluir, but calls mediator.alterar
    }

    private void handleExcluir(ActionEvent e) {
        // Call mediator.excluir with ID from identificadorField
    }

    private void handleBuscar(ActionEvent e) {
        // Call mediator.buscar and display the title or "not found"
    }

    private void handleCalcularPreco(ActionEvent e) {
        // Use the calcularPrecoTransacao method from a TituloDivida instance
    }

    private void handleAjustarTaxa(ActionEvent e) {
        // Fetch the title, adjust the interest rate, and display success/failure
    }

    private void handleConsultarTaxa(ActionEvent e) {
        // Fetch the title and display its current interest rate
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProgramaTituloDividaSwing::new);
    }
}
