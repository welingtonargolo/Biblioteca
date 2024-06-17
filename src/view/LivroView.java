package view;

import controller.LivroController;
import model.Autor;
import model.Livro;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LivroView extends JPanel {
    private LivroController controller;
    private JTextField tituloField;
    private JTextField isbnField;
    private JComboBox<Autor> autorComboBox;
    private JButton salvarButton;
    private JList<Livro> livroList;

    public LivroView() {
        setLayout(new BorderLayout());

        tituloField = new JTextField(20);
        isbnField = new JTextField(20);
        autorComboBox = new JComboBox<>();
        salvarButton = new JButton("Salvar");
        livroList = new JList<>();

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Título:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(tituloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("ISBN:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(isbnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Autor:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(autorComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(salvarButton, gbc);

        JButton excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(e -> {
            Livro livroSelecionado = livroList.getSelectedValue();
            if (livroSelecionado != null) {
                controller.excluirLivro(livroSelecionado.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um livro para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(excluirButton, gbc);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(livroList), BorderLayout.CENTER);

        salvarButton.addActionListener(e -> {
            String titulo = tituloField.getText();
            String isbn = isbnField.getText();
            Autor autorSelecionado = (Autor) autorComboBox.getSelectedItem();
            if (autorSelecionado != null) {
                controller.inserirLivro(titulo, isbn, autorSelecionado.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um autor.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void setController(LivroController controller) {
        this.controller = controller;
    }

    public void mostrarLivros(List<Livro> livros) {
        livroList.setListData(livros.toArray(new Livro[0]));
    }

    public void setAutores(List<Autor> autores) {
        autorComboBox.removeAllItems();
        for (Autor autor : autores) {
            autorComboBox.addItem(autor);
        }
    }
}
