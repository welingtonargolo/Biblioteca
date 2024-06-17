package controller;

import model.DAO.LivroDAO;
import view.LivroView;
import model.Livro;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.swing.JOptionPane;

public class LivroController {
    private LivroDAO livroDAO;
    private LivroView livroView;

    public LivroController(LivroDAO livroDAO, LivroView livroView) throws SQLException {
        this.livroDAO = livroDAO;
        this.livroView = livroView;
        this.livroView.setController(this);
        listarLivros();
    }
    
    public void excluirLivro(int idLivro) {
        try {
            livroDAO.excluirLivro(idLivro);
            // Atualize a visualização após a exclusão
            listarLivros();
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "Não é possível excluir o livro, pois ele está associado a empréstimos existentes.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir livro.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Livro> listarLivros() throws SQLException {
        List<Livro> livros = livroDAO.listarLivros();
        livroView.mostrarLivros(livros);
		return livros;
    }

    public void inserirLivro(String titulo, String isbn, int autorId) {
        try {
            livroDAO.inserirLivro(titulo, isbn, autorId);
            listarLivros();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livro> obterLivros() throws SQLException {
        return livroDAO.listarLivros();
    }
    
 

    
}
