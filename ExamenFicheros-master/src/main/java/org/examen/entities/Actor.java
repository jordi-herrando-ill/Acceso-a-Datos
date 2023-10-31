package org.examen.entities;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    private String nombre;
    private String sexo;
    private int anyoNacimiento;
    private List<Pelicula> peliculas;

    public Actor(String nombre, String sexo, int anyoNacimiento) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.anyoNacimiento = anyoNacimiento;
        this.peliculas = new ArrayList<>();
    }

    public void agregarPelicula(Pelicula pelicula) {
        peliculas.add(pelicula);
    }

    public String getNombre() {
        return nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public int getAnyoNacimiento() {
        return anyoNacimiento;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }
}


