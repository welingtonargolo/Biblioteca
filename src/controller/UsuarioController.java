package controller;

import model.DAO.UsuarioDAO;
import model.Usuario;
import view.UsuarioView;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.swing.JOptionPane;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;
    private UsuarioView usuarioView;

    public UsuarioController(UsuarioDAO usuarioDAO, UsuarioView usuarioView) throws SQLException {
        this.usuarioDAO = usuarioDAO;
        this.usuarioView = usuarioView;
        this.usuarioView.setController(this);
        listarUsuarios();
    }
    
    public void excluirUsuario(int idUsuario) {
        try {
            usuarioDAO.excluirUsuario(idUsuario);
            // Atualize a visualização após a exclusão
            listarUsuarios();
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir o usuário, pois ele está associado a empréstimos existentes.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        usuarioView.mostrarUsuarios(usuarios);
		return usuarios;
    }

    public void inserirUsuario(String nome, String cpf, String email) {
        try {
            usuarioDAO.inserirUsuario(nome, cpf, email);
            listarUsuarios();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> obterUsuarios() throws SQLException {
        return usuarioDAO.listarUsuarios();
    }
}
