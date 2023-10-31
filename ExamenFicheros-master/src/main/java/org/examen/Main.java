package org.examen;

import org.examen.entities.Actor;
import org.examen.entities.PeliculaOscarizada;
import org.examen.entities.Utilidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Ejercicio 1
        List<PeliculaOscarizada> peliculasOscarizadasHombres = Utilidades.leerPeliculasOscarizadasCsv("oscar_age_male.csv", "H");
        List<PeliculaOscarizada> peliculasOscarizadasMujeres = Utilidades.leerPeliculasOscarizadasCsv("oscar_age_female.csv", "M");
        List<PeliculaOscarizada> todasLasPeliculas = new ArrayList<>();
        todasLasPeliculas.addAll(peliculasOscarizadasHombres);
        todasLasPeliculas.addAll(peliculasOscarizadasMujeres);

        // Ejercicio 2
        List<Actor> actores = Utilidades.convertirPeliculasOscarizadasEnActores(todasLasPeliculas);
        Utilidades.escribirActoresEnJson(actores, "resources/salida/actores.json");

        // Ejercicio 3
        List<String> actoresConMasDeUnOscar = Utilidades.actoresConMasDeUnOscar(actores);
        System.out.println("Actores con más de un Oscar: " + actoresConMasDeUnOscar);

        List<String> actoresMasJovenes = Utilidades.actoresMasJovenesEnGanarUnOscar(todasLasPeliculas);
        System.out.println("Actores más jóvenes en ganar un Oscar: " + actoresMasJovenes);
    }

}