package model;

public class Livro {
    private int id;
    private String titulo;
    private String isbn; // Novo campo ISBN
    private int autorId;

    public Livro(int id, String titulo, String isbn, int autorId) {
    	this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.autorId = autorId;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAutorId() {
        return autorId;
    }

    @Override
    public String toString() {
        return titulo + " ISBN: " + isbn;
    }
}
