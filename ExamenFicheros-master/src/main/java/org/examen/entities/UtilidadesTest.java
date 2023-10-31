package org.examen.entities;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;




public class UtilidadesTest {
    @Test
    public void testActoresConMasDeUnOscar() {
        // Crear una lista de PeliculasOscarizadas para simular datos
        List<PeliculaOscarizada> peliculas = new ArrayList<>();
        peliculas.add(new PeliculaOscarizada("Pelicula1", 2000, "Actor1", 30, "H"));
        peliculas.add(new PeliculaOscarizada("Pelicula2", 2005, "Actor1", 35, "H"));
        peliculas.add(new PeliculaOscarizada("Pelicula3", 2010, "Actor2", 28, "M"));
        peliculas.add(new PeliculaOscarizada("Pelicula4", 2015, "Actor3", 40, "H"));
        peliculas.add(new PeliculaOscarizada("Pelicula5", 2020, "Actor3", 45, "H"));

        // Convierte las PeliculasOscarizadas en una lista de Actores
        List<Actor> actores = Utilidades.convertirPeliculasOscarizadasEnActores(peliculas);

        // Llama al método que quieres probar
        List<String> actoresConMasDeUnOscar = Utilidades.actoresConMasDeUnOscar(actores);

        // Define el resultado esperado
        List<String> resultadoEsperado = new ArrayList<>();
        resultadoEsperado.add("Actor1");

        // Comprueba si el resultado obtenido coincide con el resultado esperado
        assertEquals(resultadoEsperado, actoresConMasDeUnOscar);
    }
}