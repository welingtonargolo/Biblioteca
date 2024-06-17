package controller;

import model.DAO.EmprestimoDAO;
import view.EmprestimoView;
import model.Emprestimo;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.toedter.calendar.JDateChooser;

public class EmprestimoController {
    private EmprestimoDAO emprestimoDAO;
    private EmprestimoView emprestimoView;

    public EmprestimoController(EmprestimoDAO emprestimoDAO, EmprestimoView emprestimoView) throws SQLException {
        this.emprestimoDAO = emprestimoDAO;
        this.emprestimoView = emprestimoView;
        this.emprestimoView.setController(this);
        listarEmprestimos();
    }

    public void listarEmprestimos() throws SQLException {
        List<Emprestimo> emprestimos = emprestimoDAO.listarEmprestimos();
        emprestimoView.mostrarEmprestimos(emprestimos);
    }

    public void realizarEmprestimo(int livroId, int usuarioId, java.util.Date dataEmprestimo, JDateChooser dataVencimento) throws SQLException {
        // Convertendo java.util.Date para java.sql.Date
        Date dataEmprestimoSql = new Date(dataEmprestimo.getTime());
        Date dataVencimentoSql = new Date(dataVencimento.getDate().getTime());
        emprestimoDAO.realizarEmprestimo(livroId, usuarioId, dataEmprestimoSql, dataVencimentoSql);
        listarEmprestimos();
    }

    public void realizarDevolucao(int emprestimoId, java.util.Date dataDevolucao) throws SQLException {
        // Convertendo java.util.Date para java.sql.Date
        Date dataDevolucaoSql = new Date(dataDevolucao.getTime());
        emprestimoDAO.realizarDevolucao(emprestimoId, dataDevolucaoSql);
        listarEmprestimos();
    }
    public void atualizarDataDevolucao(int emprestimoId, Date novaDataDevolucao) throws SQLException {
        // Chamando a função existente realizarDevolucao
        realizarDevolucao(emprestimoId, novaDataDevolucao);
    }
    
}
