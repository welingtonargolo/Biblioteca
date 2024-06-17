import controller.AutorController;
import controller.EmprestimoController;
import controller.LivroController;
import controller.UsuarioController;
import model.DAO.AutorDAO;
import model.DAO.EmprestimoDAO;
import model.DAO.LivroDAO;
import model.DAO.UsuarioDAO;
import view.MainView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BibliotecaApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Conexão com o banco de dados
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admin");

                // Criação dos DAOs
                AutorDAO autorDAO = new AutorDAO(connection);
                LivroDAO livroDAO = new LivroDAO(connection);
                UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
                EmprestimoDAO emprestimoDAO = new EmprestimoDAO(connection);

                // Criação da vista principal
                MainView mainView = new MainView();

                // Criação dos controladores
                AutorController autorController = new AutorController(autorDAO, mainView.getAutorView());
                LivroController livroController = new LivroController(livroDAO, mainView.getLivroView());
                UsuarioController usuarioController = new UsuarioController(usuarioDAO, mainView.getUsuarioView());
                EmprestimoController emprestimoController = new EmprestimoController(emprestimoDAO, mainView.getEmprestimoView());

                // Configuração dos controladores na vista principal
                mainView.setControllers(autorController, livroController, usuarioController, emprestimoController);

                // Exibição da vista principal
                mainView.setVisible(true);
                mainView.setLocationRelativeTo(null);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
