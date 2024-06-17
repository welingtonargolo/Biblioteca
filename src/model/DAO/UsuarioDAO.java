package model.DAO;

import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void excluirUsuario(int idUsuario) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }
    
    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, email FROM usuarios";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nome = resultSet.getString("nome");
            String cpf = resultSet.getString("cpf");
            String email = resultSet.getString("email");
            Usuario usuario = new Usuario(id, nome, cpf, email);
            usuarios.add(usuario);
        }

        resultSet.close();
        statement.close();
        return usuarios;
    }

    public void inserirUsuario(String nome, String cpf, String email) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, cpf, email) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nome);
        statement.setString(2, cpf);
        statement.setString(3, email);
        statement.executeUpdate();
        statement.close();
    }
}
