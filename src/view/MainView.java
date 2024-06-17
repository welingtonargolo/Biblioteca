package view;

import controller.AutorController;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import controller.EmprestimoController;
import controller.LivroController;
import controller.UsuarioController;
import model.Autor;
import model.DAO.AutorDAO;
import model.DAO.EmprestimoDAO;
import model.DAO.LivroDAO;
import model.DAO.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainView extends JFrame {
    private AutorView autorView;
    private LivroView livroView;
    private UsuarioView usuarioView;
    private EmprestimoView emprestimoView;

    public MainView() {
        setTitle("Sistema de Gerenciamento de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        // Setando comboxs toda vez que uma janela for alternada
        tabbedPane.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
                // Conexão com o banco de dados
                Connection connection = null;
				try {
					connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "admin");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

                // Criação dos DAOs
                AutorDAO autorDAO = new AutorDAO(connection);
                LivroDAO livroDAO = new LivroDAO(connection);
                UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
                EmprestimoDAO emprestimoDAO = new EmprestimoDAO(connection);

        		
                // Criação dos controladores
                AutorController autorController = new AutorController(autorDAO, getAutorView());
                LivroController livroController = null;
				try {
					livroController = new LivroController(livroDAO, getLivroView());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					livroController = new LivroController(livroDAO, getLivroView());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                UsuarioController usuarioController = null;
				try {
					usuarioController = new UsuarioController(usuarioDAO, getUsuarioView());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                EmprestimoController emprestimoController = null;
				try {
					emprestimoController = new EmprestimoController(emprestimoDAO, getEmprestimoView());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

                // Configuração dos controladores na vista principal
                try {
					setControllers(autorController, livroController, usuarioController, emprestimoController);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

        	}
        });

        autorView = new AutorView();
        livroView = new LivroView();
        usuarioView = new UsuarioView();
        emprestimoView = new EmprestimoView();
        
        tabbedPane.addTab("Autor", autorView);
        tabbedPane.addTab("Livros", livroView);
        tabbedPane.addTab("Usuários", usuarioView);
        tabbedPane.addTab("Empréstimos", emprestimoView);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public AutorView getAutorView() {
        return autorView;
    }

    public LivroView getLivroView() {
        return livroView;
    }

    public UsuarioView getUsuarioView() {
        return usuarioView;
    }

    public EmprestimoView getEmprestimoView() {
        return emprestimoView;
    }

    public void setControllers(AutorController autorController, LivroController livroController, UsuarioController usuarioController, EmprestimoController emprestimoController) throws SQLException {
        autorView.setController(autorController);
        livroView.setController(livroController);
        usuarioView.setController(usuarioController);
        emprestimoView.setController(emprestimoController);

        autorController.listarAutores();
		livroController.listarLivros();
		usuarioController.listarUsuarios();
		emprestimoController.listarEmprestimos();


        List<Autor> autores = autorController.obterAutores();
        livroView.setAutores(autores);
		// Preencher comboboxes de livros e usuários nas views de empréstimos
		emprestimoView.setLivros(livroController.listarLivros());
		emprestimoView.setUsuarios(usuarioController.listarUsuarios());
    }
}
