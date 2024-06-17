package model;

public class Usuario {
    private int id;
    private String nome;
    private String cpf; 
    private String email; // Mantendo email

    public Usuario(int id, String nome, String cpf, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return nome + " | CPF: " + cpf + " | " + "Email:" + " " + email;
    }
}
