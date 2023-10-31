package org.examen.entities;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class PeliculaOscarizada {
    private String pelicula;
    private int anyo;
    private String actor;
    private int edad;
    private String sexo;

    public PeliculaOscarizada(String pelicula, int anyo, String actor, int edad, String sexo) {
        this.pelicula = pelicula;
        this.anyo = anyo;
        this.actor = actor;
        this.edad = edad;
        this.sexo = sexo;
    }

    public String getPelicula() {
        return pelicula;
    }

    public int getAnyo() {
        return anyo;
    }

    public String getActor() {
        return actor;
    }

    public int getEdad() {
        return edad;
    }

    public String getSexo() {
        return sexo;
    }

    public static List<PeliculaOscarizada> leerDesdeArchivoCSV(String filePath) {
        List<PeliculaOscarizada> peliculasOscarizadas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String pelicula = parts[0].trim();
                int anyo = Integer.parseInt(parts[1].trim());
                String actor = parts[2].trim();
                int edad = Integer.parseInt(parts[3].trim());
                String sexo = parts[4].trim();
                peliculasOscarizadas.add(new PeliculaOscarizada(pelicula, anyo, actor, edad, sexo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return peliculasOscarizadas;
    }
}

