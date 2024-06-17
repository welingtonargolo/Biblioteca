package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Emprestimo {
    private int id;
    private Livro livro;
    private Usuario usuario;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private Date dataVencimento;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Emprestimo(int id, Livro livro, Usuario usuario, Date dataEmprestimo, Date dataDevolucao, Date dataVencimento) {
        this.id = id;
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.dataVencimento = dataVencimento;
    }

    public int getId() {
        return id;
    }

    public Livro getLivro() {
        return livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    @Override
    public String toString() {
        String devolucao = (dataDevolucao == null) ? "não devolvido" : sdf.format(dataDevolucao);
        return String.format("%-10s %-30s %-30s %-20s %-20s",
                             id,
                             livro.getTitulo(),
                             usuario.getNome(),
                             sdf.format(dataEmprestimo),
                             devolucao);
    }

    public static String getTableHeader() {
        return String.format("%-10s %-30s %-30s %-20s %-20s",
                             "ID",
                             "Livro",
                             "Usuario",
                             "Data de Emprestimo",
                             "Data de Devolução");
    }

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
}
