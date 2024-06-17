package view;

import controller.EmprestimoController;
import model.Emprestimo;
import model.Livro;
import model.Usuario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmprestimoView extends JPanel {
    private EmprestimoController controller;
    private JComboBox<Livro> livroComboBox;
    private JComboBox<Usuario> usuarioComboBox;
    private JButton emprestarButton;
    private JButton relatorioAtivosButton;
    private JButton relatorioVencidosButton;
    private JTable emprestimoTable;
    private DefaultTableModel tableModel;
    private JDateChooser dataVencimento;


    public EmprestimoView() {
        setLayout(new BorderLayout());

        livroComboBox = new JComboBox<>();
        usuarioComboBox = new JComboBox<>();
        emprestarButton = new JButton("Emprestar");
        dataVencimento = new JDateChooser();
        relatorioAtivosButton = new JButton("Relatório de Empréstimos Ativos");
        relatorioVencidosButton = new JButton("Relatório de Empréstimos Vencidos");
        dataVencimento.setDateFormatString("dd-MM-yyyy");

        // Configurando o painel do formulário com GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Adicionando componentes ao formPanel com GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Livro:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(livroComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Usuário:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(usuarioComboBox, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Data de Vencimento:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(dataVencimento, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(emprestarButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(relatorioAtivosButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(relatorioVencidosButton, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Configurar a tabela de empréstimos
        tableModel = new DefaultTableModel(new String[]{"ID", "Livro", "Usuário", "Data de Empréstimo", "Data de Devolução","Data Vencimento"}, 0);
        emprestimoTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(emprestimoTable);
        add(scrollPane, BorderLayout.CENTER);

        emprestarButton.addActionListener(e -> {
            Livro livroSelecionado = (Livro) livroComboBox.getSelectedItem();
            Usuario usuarioSelecionado = (Usuario) usuarioComboBox.getSelectedItem();

            if (livroSelecionado != null && usuarioSelecionado != null) {
                Date dataEmprestimo = new Date();
                try {
                    controller.realizarEmprestimo(livroSelecionado.getId(), usuarioSelecionado.getId(), dataEmprestimo,dataVencimento);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um livro e um usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        relatorioAtivosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection = null;
                try {
					connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admin");
                    JasperPrint jasperPrint = JasperFillManager.fillReport("EmprestimosAtivos.jasper", null, connection);
                    JasperViewer.viewReport(jasperPrint, false);
                } catch (JRException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        relatorioVencidosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Conexão com o banco de dados
                Connection connection = null;
                try {
					connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admin");
                    JasperPrint jasperPrint = JasperFillManager.fillReport("EmprestimosVencidos.jasper", null, connection);
                    JasperViewer.viewReport(jasperPrint, false);
                } catch (JRException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        emprestimoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = emprestimoTable.rowAtPoint(e.getPoint());
                int col = emprestimoTable.columnAtPoint(e.getPoint());

                if (col == 4) {
                    int emprestimoId = (int) tableModel.getValueAt(row, 0);
                    String novaDataDevolucaoStr = JOptionPane.showInputDialog(null, "Digite a nova data de devolução (DD-MM-AAAA):");

                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        Date novaDataDevolucao = sdf.parse(novaDataDevolucaoStr);
                        java.sql.Date novaDataDevolucaoSql = new java.sql.Date(novaDataDevolucao.getTime());
                        controller.atualizarDataDevolucao(emprestimoId, novaDataDevolucaoSql);
                    } catch (IllegalArgumentException | ParseException ex) {
                        JOptionPane.showMessageDialog(null, "Data inválida. Use o formato DD-MM-AAAA.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void setController(EmprestimoController controller) {
        this.controller = controller;
    }

    public void mostrarEmprestimos(List<Emprestimo> emprestimos) {
        tableModel.setRowCount(0);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        for (Emprestimo emprestimo : emprestimos) {
            Object[] rowData = {
                emprestimo.getId(),
                emprestimo.getLivro().getTitulo(),
                emprestimo.getUsuario().getNome(),
                formatter.format(emprestimo.getDataEmprestimo()),
                emprestimo.getDataDevolucao() == null ? "Não devolvido" : formatter.format(emprestimo.getDataDevolucao()),
        		formatter.format(emprestimo.getDataVencimento())
            };
            tableModel.addRow(rowData);
        }
    }

    public void setLivros(List<Livro> livros) {
        livroComboBox.removeAllItems();
        for (Livro livro : livros) {
            livroComboBox.addItem(livro);
        }
    }

    public void setUsuarios(List<Usuario> usuarios) {
        usuarioComboBox.removeAllItems();
        for (Usuario usuario : usuarios) {
            usuarioComboBox.addItem(usuario);
        }
    }
}
