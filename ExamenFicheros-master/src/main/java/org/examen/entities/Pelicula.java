package org.examen.entities;
public class Pelicula {
    private String titulo;
    private int anyo;

    public Pelicula(String titulo, int anyo) {
        this.titulo = titulo;
        this.anyo = anyo;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnyo() {
        return anyo;
    }
}
