package controller;

import model.DAO.AutorDAO;
import view.AutorView;
import model.Autor;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.swing.JOptionPane;

public class AutorController {
    private AutorDAO autorDAO;
    private AutorView autorView;

    public AutorController(AutorDAO autorDAO, AutorView autorView) {
        this.autorDAO = autorDAO;
        this.autorView = autorView;
        this.autorView.setController(this);
        listarAutores();
    }
    
    public void excluirAutor(int idAutor) {
        try {
            autorDAO.excluirAutor(idAutor);
            // Atualize a visualização após a exclusão
            listarAutores();
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir o autor, pois ele está associado a livros existentes.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir autor.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listarAutores() {
        List<Autor> autores = null;
		try {
			autores = autorDAO.listarAutores();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        autorView.mostrarAutores(autores);
    }

    public void inserirAutor(String nome) {
        autorDAO.inserirAutor(nome);
        listarAutores();
    }

    public List<Autor> obterAutores() throws SQLException {
        return autorDAO.listarAutores();
    }
}
