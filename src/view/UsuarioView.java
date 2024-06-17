package view;

import controller.UsuarioController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UsuarioView extends JPanel {
    private UsuarioController controller;
    private JTextField nomeField;
    private JTextField cpfField; 
    private JTextField emailField; // Campo Email
    private JButton salvarButton;
    private JButton excluirButton; // Botão Excluir
    private JList<Usuario> usuarioList;

    public UsuarioView() {
        setLayout(new BorderLayout());

        nomeField = new JTextField(20);
        cpfField = new JTextField(20);
        emailField = new JTextField(20); // Campo Email
        salvarButton = new JButton("Salvar");
        excluirButton = new JButton("Excluir"); // Botão Excluir
        usuarioList = new JList<>();

        // Configurando o painel do formulário com GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Adicionando componentes ao formPanel com GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("CPF:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(salvarButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4; // Nova linha para o botão Excluir
        formPanel.add(excluirButton, gbc); // Adicionando o botão Excluir

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(usuarioList), BorderLayout.CENTER);

        salvarButton.addActionListener(e -> {
            String nome = nomeField.getText();
            String cpf = cpfField.getText();
            String email = emailField.getText(); // Campo Email
            controller.inserirUsuario(nome, cpf, email); // Alterado para incluir Email
        });
        
        excluirButton.addActionListener(e -> {
            Usuario usuarioSelecionado = usuarioList.getSelectedValue();
            if (usuarioSelecionado != null) {
                controller.excluirUsuario(usuarioSelecionado.getId());
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um usuário para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void setController(UsuarioController controller) {
        this.controller = controller;
    }

    public void mostrarUsuarios(List<Usuario> usuarios) {
        usuarioList.setListData(usuarios.toArray(new Usuario[0]));
    }
}
