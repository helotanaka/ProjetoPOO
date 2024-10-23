package br.com.cesarschool.poo.titulos;

import br.com.cesarschool.poo.titulos.entidades.*;
import br.com.cesarschool.poo.titulos.mediators.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Programa {
    static MediatorAcao mediatorAcao = MediatorAcao.getInstance();
    static MediatorEntidadeOperadora mediatorEntidade = MediatorEntidadeOperadora.getInstance();
    static MediatorTituloDivida mediatorTituloDivida = MediatorTituloDivida.getInstance();
    static MediatorOperacao mediatorOperacao = MediatorOperacao.getInstance();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Programa::mostrarMenuPrincipal);
    }

    private static void mostrarMenuPrincipal() {
        JFrame frame = new JFrame("Atividade POO - Menu Principal");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnAcao = new JButton("Ação");
        JButton btnEntidade = new JButton("Entidade Operadora");
        JButton btnTitulo = new JButton("Título Dívida");
        JButton btnTransacao = new JButton("Transação");

        btnAcao.addActionListener(e -> mostrarMenuAcao());
        btnEntidade.addActionListener(e -> mostrarMenuEntidade());
        btnTitulo.addActionListener(e -> mostrarMenuTituloDivida());
        btnTransacao.addActionListener(e -> mostrarMenuTransacao());

        frame.add(btnAcao);
        frame.add(btnEntidade);
        frame.add(btnTitulo);
        frame.add(btnTransacao);

        frame.setVisible(true);
    }

    private static void mostrarMenuAcao() {
        JFrame frame = new JFrame("Menu Ação");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(5, 1));

        JButton btnIncluir = new JButton("Incluir Ação");
        JButton btnAlterar = new JButton("Alterar Ação");
        JButton btnExcluir = new JButton("Excluir Ação");
        JButton btnBuscar = new JButton("Buscar Ação");
        JButton btnPrecoTransacao = new JButton("Preço Transação");

        btnIncluir.addActionListener(e -> incluirAcao());
        btnAlterar.addActionListener(e -> alterarAcao());
        btnExcluir.addActionListener(e -> excluirAcao());
        btnBuscar.addActionListener(e -> buscarAcao());
        btnPrecoTransacao.addActionListener(e -> precoTransacaoAcao());

        frame.add(btnIncluir);
        frame.add(btnAlterar);
        frame.add(btnExcluir);
        frame.add(btnBuscar);
        frame.add(btnPrecoTransacao);

        frame.setVisible(true);
    }

    private static void incluirAcao() {
        JTextField txtId = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtData = new JTextField("yyyy-MM-dd");
        JTextField txtValor = new JTextField();

        Object[] inputs = {
                "Identificador:", txtId,
                "Nome:", txtNome,
                "Data de Validade:", txtData,
                "Valor Unitário:", txtValor
        };

        if (JOptionPane.showConfirmDialog(null, inputs, "Incluir Ação", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nome = txtNome.getText();
                LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                double valor = Double.parseDouble(txtValor.getText());

                Acao acao = new Acao(id, nome, data, valor);
                String mensagem = mediatorAcao.incluir(acao);
                JOptionPane.showMessageDialog(null, mensagem == null ? "Ação incluída com sucesso!" : "Erro: " + mensagem);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void alterarAcao() {
        JTextField txtId = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtData = new JTextField("yyyy-MM-dd");
        JTextField txtValor = new JTextField();

        Object[] inputs = {
                "Identificador da Ação:", txtId,
                "Novo Nome:", txtNome,
                "Nova Data de Validade:", txtData,
                "Novo Valor Unitário:", txtValor
        };

        if (JOptionPane.showConfirmDialog(null, inputs, "Alterar Ação", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nome = txtNome.getText();
                LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                double valor = Double.parseDouble(txtValor.getText());

                Acao acao = new Acao(id, nome, data, valor);
                String mensagem = mediatorAcao.alterar(acao);
                JOptionPane.showMessageDialog(null, mensagem == null ? "Ação alterada com sucesso!" : "Erro: " + mensagem);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void excluirAcao() {
        JTextField txtId = new JTextField();
        Object[] inputs = {"Identificador da Ação:", txtId};

        if (JOptionPane.showConfirmDialog(null, inputs, "Excluir Ação", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                String mensagem = mediatorAcao.excluir(id);
                JOptionPane.showMessageDialog(null, mensagem == null ? "Ação excluída com sucesso!" : "Erro: " + mensagem);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void buscarAcao() {
        JTextField txtId = new JTextField();
        Object[] inputs = {"Identificador da Ação:", txtId};

        if (JOptionPane.showConfirmDialog(null, inputs, "Buscar Ação", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                Acao acao = mediatorAcao.buscar(id);
                if (acao != null) {
                    JOptionPane.showMessageDialog(null, String.format(
                            "Ação Encontrada:\nID: %d\nNome: %s\nData: %s\nValor: %.2f",
                            acao.getIdentificador(), acao.getNome(), acao.getDataDeValidade(), acao.getValorUnitario()
                    ));
                } else {
                    JOptionPane.showMessageDialog(null, "Ação não encontrada.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void precoTransacaoAcao() {
        JTextField txtId = new JTextField();
        JTextField txtMontante = new JTextField();

        Object[] inputs = {
                "Identificador da Ação:", txtId,
                "Montante:", txtMontante
        };

        if (JOptionPane.showConfirmDialog(null, inputs, "Preço Transação", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                int montante = Integer.parseInt(txtMontante.getText());
                Acao acao = mediatorAcao.buscar(id);
                if (acao != null) {
                    double preco = acao.calcularPrecoTransacao(montante);
                    JOptionPane.showMessageDialog(null, "Preço da Transação: " + preco);
                } else {
                    JOptionPane.showMessageDialog(null, "Ação não encontrada.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    public static void mostrarMenuEntidade() {
        JFrame frame = new JFrame("Menu Entidade Operadora");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 1));

        JButton btnIncluir = new JButton("Incluir Entidade");
        JButton btnAlterar = new JButton("Alterar Entidade");
        JButton btnExcluir = new JButton("Excluir Entidade");
        JButton btnBuscar = new JButton("Buscar Entidade");
        JButton btnOperacaoSaldoAcao = new JButton("Operações Saldo Ação");
        JButton btnOperacaoTituloDivida = new JButton("Operações Título Dívida");

        btnIncluir.addActionListener(e -> incluirEntidade());
        btnAlterar.addActionListener(e -> alterarEntidade());
        btnExcluir.addActionListener(e -> excluirEntidade());
        btnBuscar.addActionListener(e -> buscarEntidade());
        btnOperacaoSaldoAcao.addActionListener(e -> operacaoSaldoAcao());
        btnOperacaoTituloDivida.addActionListener(e -> operacaoTituloDivida());

        frame.add(btnIncluir);
        frame.add(btnAlterar);
        frame.add(btnExcluir);
        frame.add(btnBuscar);
        frame.add(btnOperacaoSaldoAcao);
        frame.add(btnOperacaoTituloDivida);

        frame.setVisible(true);
    }

    private static void incluirEntidade() {
        JTextField txtId = new JTextField();
        JTextField txtNome = new JTextField();
        JCheckBox chkAutorizacao = new JCheckBox("Autorização de Ação");

        Object[] inputs = {
                "Identificador (100-1000):", txtId,
                "Nome (10-100 caracteres):", txtNome,
                chkAutorizacao
        };

        if (JOptionPane.showConfirmDialog(null, inputs, "Incluir Entidade", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                long id = Long.parseLong(txtId.getText());
                String nome = txtNome.getText();
                boolean autorizacao = chkAutorizacao.isSelected();

                EntidadeOperadora entidade = new EntidadeOperadora(id, nome, autorizacao);
                String mensagem = mediatorEntidade.incluir(entidade);

                JOptionPane.showMessageDialog(null, mensagem == null ? "Entidade incluída com sucesso!" : "Erro: " + mensagem);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void alterarEntidade() {
        JTextField txtId = new JTextField();
        JTextField txtNome = new JTextField();
        JCheckBox chkAutorizacao = new JCheckBox("Nova Autorização de Ação");

        Object[] inputs = {
                "Identificador da Entidade:", txtId,
                "Novo Nome:", txtNome,
                chkAutorizacao
        };

        if (JOptionPane.showConfirmDialog(null, inputs, "Alterar Entidade", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                long id = Long.parseLong(txtId.getText());
                String nome = txtNome.getText();
                boolean autorizacao = chkAutorizacao.isSelected();

                EntidadeOperadora entidade = new EntidadeOperadora(id, nome, autorizacao);
                String mensagem = mediatorEntidade.alterar(entidade);

                JOptionPane.showMessageDialog(null, mensagem == null ? "Entidade alterada com sucesso!" : "Erro: " + mensagem);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void excluirEntidade() {
        JTextField txtId = new JTextField();
        Object[] inputs = {"Identificador da Entidade:", txtId};

        if (JOptionPane.showConfirmDialog(null, inputs, "Excluir Entidade", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                long id = Long.parseLong(txtId.getText());
                String mensagem = mediatorEntidade.excluir((int) id);

                JOptionPane.showMessageDialog(null, mensagem == null ? "Entidade excluída com sucesso!" : "Erro: " + mensagem);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void buscarEntidade() {
        JTextField txtId = new JTextField();
        Object[] inputs = {"Identificador da Entidade:", txtId};

        if (JOptionPane.showConfirmDialog(null, inputs, "Buscar Entidade", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                long id = Long.parseLong(txtId.getText());
                EntidadeOperadora entidade = mediatorEntidade.buscar((int) id);

                if (entidade != null) {
                    JOptionPane.showMessageDialog(null, String.format(
                            "Entidade Encontrada:\nID: %d\nNome: %s\nAutorização: %b\nSaldo Ação: %.2f\nSaldo Título: %.2f",
                            entidade.getIdentificador(), entidade.getNome(), entidade.getAutorizacaoAcao(),
                            entidade.getSaldoAcao(), entidade.getSaldoTituloDivida()
                    ));
                } else {
                    JOptionPane.showMessageDialog(null, "Entidade não encontrada.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void operacaoSaldoAcao() {
        String[] opcoes = {"Creditar", "Debitar"};
        int opcao = JOptionPane.showOptionDialog(null, "Selecione a operação:", "Operação Saldo Ação",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        if (opcao != -1) {
            realizarOperacaoSaldo(opcao == 0, true);
        }
    }

    private static void operacaoTituloDivida() {
        String[] opcoes = {"Creditar", "Debitar"};
        int opcao = JOptionPane.showOptionDialog(null, "Selecione a operação:", "Operação Título Dívida",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        if (opcao != -1) {
            realizarOperacaoSaldo(opcao == 0, false);
        }
    }

    private static void realizarOperacaoSaldo(boolean isCredito, boolean isAcao) {
        JTextField txtId = new JTextField();
        JTextField txtValor = new JTextField();

        Object[] inputs = {
                "Identificador da Entidade:", txtId,
                "Valor:", txtValor
        };

        if (JOptionPane.showConfirmDialog(null, inputs, "Operação Saldo", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                long id = Long.parseLong(txtId.getText());
                double valor = Double.parseDouble(txtValor.getText());
                EntidadeOperadora entidade = mediatorEntidade.buscar((int) id);

                if (entidade != null) {
                    if (isAcao) {
                        if (isCredito) entidade.creditarSaldoAcao(valor);
                        else entidade.debitarSaldoAcao(valor);
                    } else {
                        if (isCredito) entidade.creditarSaldoTituloDivida(valor);
                        else entidade.debitarSaldoTituloDivida(valor);
                    }
                    mediatorEntidade.alterar(entidade);
                    JOptionPane.showMessageDialog(null, "Operação realizada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Entidade não encontrada.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    public static void mostrarMenuTituloDivida() {
        JFrame frame = new JFrame("Menu Título Dívida");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(7, 1));

        JButton btnIncluir = new JButton("Incluir Título");
        JButton btnAlterar = new JButton("Alterar Título");
        JButton btnExcluir = new JButton("Excluir Título");
        JButton btnBuscar = new JButton("Buscar Título");
        JButton btnPrecoTransacao = new JButton("Visualizar Preço da Transação");
        JButton btnAjustarTaxaJuros = new JButton("Ajustar Taxa de Juros");
        JButton btnConsultarTaxaJuros = new JButton("Consultar Taxa de Juros");

        btnIncluir.addActionListener(e -> incluirTitulo());
        btnAlterar.addActionListener(e -> alterarTitulo());
        btnExcluir.addActionListener(e -> excluirTitulo());
        btnBuscar.addActionListener(e -> buscarTitulo());
        btnPrecoTransacao.addActionListener(e -> precoTransacao());
        btnAjustarTaxaJuros.addActionListener(e -> ajustarTaxaJuros());
        btnConsultarTaxaJuros.addActionListener(e -> consultarTaxaJuros());

        frame.add(btnIncluir);
        frame.add(btnAlterar);
        frame.add(btnExcluir);
        frame.add(btnBuscar);
        frame.add(btnPrecoTransacao);
        frame.add(btnAjustarTaxaJuros);
        frame.add(btnConsultarTaxaJuros);

        frame.setVisible(true);
    }

    private static void incluirTitulo() {
        JTextField txtId = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtData = new JTextField("yyyy-MM-dd");
        JTextField txtTaxaJuros = new JTextField();

        Object[] inputs = {
                "Identificador (1-99999):", txtId,
                "Nome (10-100 caracteres):", txtNome,
                "Data de Validade:", txtData,
                "Taxa de Juros:", txtTaxaJuros
        };

        if (JOptionPane.showConfirmDialog(null, inputs, "Incluir Título", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nome = txtNome.getText();
                LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                double taxaJuros = Double.parseDouble(txtTaxaJuros.getText());

                TituloDivida titulo = new TituloDivida(id, nome, data, taxaJuros);
                String mensagem = mediatorTituloDivida.incluir(titulo);

                JOptionPane.showMessageDialog(null, mensagem == null ? "Título incluído com sucesso!" : mensagem);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void alterarTitulo() {
        JTextField txtId = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtData = new JTextField("yyyy-MM-dd");
        JTextField txtTaxaJuros = new JTextField();

        Object[] inputs = {
                "Identificador do Título:", txtId,
                "Novo Nome:", txtNome,
                "Nova Data de Validade:", txtData,
                "Nova Taxa de Juros:", txtTaxaJuros
        };

        if (JOptionPane.showConfirmDialog(null, inputs, "Alterar Título", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nome = txtNome.getText();
                LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                double taxaJuros = Double.parseDouble(txtTaxaJuros.getText());

                TituloDivida titulo = new TituloDivida(id, nome, data, taxaJuros);
                String mensagem = mediatorTituloDivida.alterar(titulo);

                JOptionPane.showMessageDialog(null, mensagem == null ? "Título alterado com sucesso!" : mensagem);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void excluirTitulo() {
        JTextField txtId = new JTextField();
        Object[] inputs = {"Identificador do Título:", txtId};

        if (JOptionPane.showConfirmDialog(null, inputs, "Excluir Título", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                String mensagem = mediatorTituloDivida.excluir(id);

                JOptionPane.showMessageDialog(null, mensagem == null ? "Título excluído com sucesso!" : mensagem);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void buscarTitulo() {
        JTextField txtId = new JTextField();
        Object[] inputs = {"Identificador do Título:", txtId};

        if (JOptionPane.showConfirmDialog(null, inputs, "Buscar Título", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                TituloDivida titulo = mediatorTituloDivida.buscar(id);

                if (titulo != null) {
                    JOptionPane.showMessageDialog(null, String.format(
                            "Título Encontrado:\nID: %d\nNome: %s\nData: %s\nTaxa de Juros: %.2f",
                            titulo.getIdentificador(), titulo.getNome(), titulo.getDataDeValidade(), titulo.getTaxaJuros()
                    ));
                } else {
                    JOptionPane.showMessageDialog(null, "Título não encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void precoTransacao() {
        JTextField txtId = new JTextField();
        JTextField txtMontante = new JTextField();

        Object[] inputs = {
                "Identificador do Título:", txtId,
                "Montante:", txtMontante
        };

        if (JOptionPane.showConfirmDialog(null, inputs, "Preço da Transação", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                double montante = Double.parseDouble(txtMontante.getText());

                TituloDivida titulo = mediatorTituloDivida.buscar(id);
                if (titulo != null) {
                    double preco = titulo.calcularPrecoTransacao(montante);
                    JOptionPane.showMessageDialog(null, "Preço da Transação: " + preco);
                } else {
                    JOptionPane.showMessageDialog(null, "Título não encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void ajustarTaxaJuros() {
        JTextField txtId = new JTextField();
        JTextField txtNovaTaxa = new JTextField();

        Object[] inputs = {
                "Identificador do Título:", txtId,
                "Nova Taxa de Juros (%):", txtNovaTaxa
        };

        if (JOptionPane.showConfirmDialog(null, inputs, "Ajustar Taxa de Juros", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                double novaTaxa = Double.parseDouble(txtNovaTaxa.getText());

                TituloDivida titulo = mediatorTituloDivida.buscar(id);
                if (titulo != null) {
                    titulo.setTaxaJuros(novaTaxa);
                    JOptionPane.showMessageDialog(null, "Taxa de juros ajustada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Título não encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void consultarTaxaJuros() {
        JTextField txtId = new JTextField();
        Object[] inputs = {"Identificador do Título:", txtId};

        if (JOptionPane.showConfirmDialog(null, inputs, "Consultar Taxa de Juros", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                TituloDivida titulo = mediatorTituloDivida.buscar(id);

                if (titulo != null) {
                    JOptionPane.showMessageDialog(null, "Taxa de Juros Atual: " + titulo.getTaxaJuros() + "%");
                } else {
                    JOptionPane.showMessageDialog(null, "Título não encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static final String CAMINHO_ARQUIVO = "src/main/java/br/com/cesarschool/poo/titulos/repositorios/Transacao.txt";

    public static void mostrarMenuTransacao() {
        JFrame frame = new JFrame("Menu Transação");
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(2, 1));

        JButton btnRealizarOperacao = new JButton("Realizar Operação");
        JButton btnGerarExtrato = new JButton("Gerar Extrato");

        btnRealizarOperacao.addActionListener(e -> realizarOperacao());
        btnGerarExtrato.addActionListener(e -> gerarExtrato());

        frame.add(btnGerarExtrato);


        frame.add(btnRealizarOperacao);
        frame.add(btnGerarExtrato);

        frame.setVisible(true);
    }

    private static void realizarOperacao() {
        JTextField txtIdCredito = new JTextField();
        JTextField txtIdDebito = new JTextField();
        JCheckBox chkEhAcao = new JCheckBox("É uma operação de Ação?");
        JTextField txtIdAcaoTitulo = new JTextField();
        JTextField txtValorOperacao = new JTextField();

        Object[] inputs = {
                "ID Entidade Crédito:", txtIdCredito,
                "ID Entidade Débito:", txtIdDebito,
                chkEhAcao,
                "ID Ação ou Título:", txtIdAcaoTitulo,
                "Valor da Operação:", txtValorOperacao
        };

        if (JOptionPane.showConfirmDialog(null, inputs, "Realizar Operação", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int idCredito = Integer.parseInt(txtIdCredito.getText());
                int idDebito = Integer.parseInt(txtIdDebito.getText());
                boolean ehAcao = chkEhAcao.isSelected();
                int idAcaoOuTitulo = Integer.parseInt(txtIdAcaoTitulo.getText());
                double valorOperacao = Double.parseDouble(txtValorOperacao.getText());

                if (valorOperacao <= 0) {
                    JOptionPane.showMessageDialog(null, "Valor inválido.");
                    return;
                }

                String mensagem = mediatorOperacao.realizarOperacao(ehAcao, idCredito, idDebito, idAcaoOuTitulo, valorOperacao);

                JOptionPane.showMessageDialog(null, mensagem == null ? "Operação realizada com sucesso!" : "Erro: " + mensagem);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados.");
            }
        }
    }

    private static void gerarExtrato() {
        JTextField txtIdEntidade = new JTextField();
        Object[] inputs = {"Identificador da Entidade:", txtIdEntidade};

        if (JOptionPane.showConfirmDialog(null, inputs, "Gerar Extrato", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                String input = txtIdEntidade.getText().trim();
                System.out.println("Valor digitado: '" + input + "'");

                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira um ID.");
                    return;
                }

                int idEntidade = Integer.parseInt(input);

                // Depuração: Imprimir o extrato retornado
                Transacao[] extrato = mediatorOperacao.gerarExtrato(idEntidade);
                System.out.println("Extrato retornado: " + (extrato != null ? extrato.length : "null"));

                if (extrato == null || extrato.length == 0) {
                    JOptionPane.showMessageDialog(null, "Nenhuma transação encontrada para essa entidade.");
                    return;
                }

                StringBuilder detalhesExtrato = new StringBuilder("Extrato de Transações:\n\n");
                for (Transacao transacao : extrato) {
                    detalhesExtrato.append(String.format(
                            "Data: %s\nValor: %.2f\nEntidade Crédito: %s\nEntidade Débito: %s\n\n",
                            transacao.getDataHoraOperacao(),
                            transacao.getValorOperacao(),
                            transacao.getEntidadeCredito().getNome(),
                            transacao.getEntidadeDebito().getNome()
                    ));
                }

                JTextArea textArea = new JTextArea(detalhesExtrato.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                JOptionPane.showMessageDialog(null, scrollPane, "Extrato", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID inválido. Por favor, insira um número válido.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar o extrato: " + ex.getMessage());
            }
        }
    }
}