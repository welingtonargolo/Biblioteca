package view;

import controller.AutorController;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import model.Autor;

public class AutorView extends JPanel {
    private AutorController controller;
    private JTextField nomeField;
    private JButton cadastrarButton;
    private JList<Autor> autorList;
    private JComboBox<Autor> autorComboBox;

    public AutorView() {
        setLayout(new BorderLayout());

        nomeField = new JTextField(10);
        cadastrarButton = new JButton("Cadastrar");
        autorList = new JList<>();
        autorComboBox = new JComboBox<>();

        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(cadastrarButton);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(autorList), BorderLayout.CENTER);
        add(autorComboBox, BorderLayout.SOUTH);

        cadastrarButton.addActionListener(e -> {
            String nome = nomeField.getText();

				controller.inserirAutor(nome);

        });
        JButton excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(e -> {
            // Obtenha o autor selecionado na tabela ou lista de autores
            Autor autorSelecionado = autorList.getSelectedValue();
            if (autorSelecionado != null) {
                // Chame o método de exclusão do autor no controlador
                controller.excluirAutor(autorSelecionado.getId());
                
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um autor para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        formPanel.add(excluirButton);
    }

    public void setController(AutorController controller) {
        this.controller = controller;
    }

    public void mostrarAutores(List<Autor> autores) {
        autorList.setListData(autores.toArray(new Autor[0]));
        autorComboBox.removeAllItems();
        for (Autor autor : autores) {
            autorComboBox.addItem(autor);
        }
    }
    
}
