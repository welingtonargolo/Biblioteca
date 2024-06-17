package model.DAO;

import model.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    private Connection connection;

    public LivroDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void excluirLivro(int idLivro) throws SQLException {
        String sql = "DELETE FROM livros WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idLivro);
            stmt.executeUpdate();
        }
    }

    public List<Livro> listarLivros() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT id, titulo, isbn, autor_id FROM livros";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String titulo = resultSet.getString("titulo");
            String isbn = resultSet.getString("isbn");
            int autorId = resultSet.getInt("autor_id");
            Livro livro = new Livro(id, titulo, isbn, autorId);
            livros.add(livro);
        }

        resultSet.close();
        statement.close();
        return livros;
    }

    public void inserirLivro(String titulo, String isbn, int autorId) throws SQLException {
        String sql = "INSERT INTO livros (titulo, isbn, autor_id) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, titulo);
        statement.setString(2, isbn);
        statement.setInt(3, autorId);
        statement.executeUpdate();
        statement.close();
    }
}
