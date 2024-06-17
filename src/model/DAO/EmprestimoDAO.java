package model.DAO;

import model.Emprestimo;
import model.Livro;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {
    private Connection connection;

    public EmprestimoDAO(Connection connection) {
        this.connection = connection;
    }

    public void realizarEmprestimo(int livroId, int usuarioId, Date dataEmprestimo, Date dataVencimento) {
        String sql = "INSERT INTO emprestimos (livro_id, usuario_id, data_emprestimo, data_vencimento) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, livroId);
            stmt.setInt(2, usuarioId);
            stmt.setDate(3, dataEmprestimo);
            stmt.setDate(4, dataVencimento);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void realizarDevolucao(int emprestimoId, Date dataDevolucao) {
        String sql = "UPDATE emprestimos SET data_devolucao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, dataDevolucao);
            stmt.setInt(2, emprestimoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Emprestimo> listarEmprestimos() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT e.id, e.data_emprestimo, e.data_devolucao, e.data_vencimento, " +
                     "l.id as livroId, l.titulo as livroTitulo, " +
                     "u.id as usuarioId, u.nome as usuarioNome " +
                     "FROM emprestimos e " +
                     "JOIN livros l ON e.livro_id = l.id " +
                     "JOIN usuarios u ON e.usuario_id = u.id";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Date dataEmprestimo = resultSet.getDate("data_emprestimo");
            Date dataDevolucao = resultSet.getDate("data_devolucao");
            Date dataVencimento = resultSet.getDate("data_vencimento");

            
            int livroId = resultSet.getInt("livroId");
            String livroTitulo = resultSet.getString("livroTitulo");
            Livro livro = new Livro(livroId, livroTitulo, null, 0); // ajuste conforme necessário

            int usuarioId = resultSet.getInt("usuarioId");
            String usuarioNome = resultSet.getString("usuarioNome");
            Usuario usuario = new Usuario(usuarioId, usuarioNome, null, null); // ajuste conforme necessário

            Emprestimo emprestimo = new Emprestimo(id, livro, usuario, dataEmprestimo, dataDevolucao,dataVencimento);
            emprestimos.add(emprestimo);
        }

        resultSet.close();
        statement.close();
        return emprestimos;
    }
}
