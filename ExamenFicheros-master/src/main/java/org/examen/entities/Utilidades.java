package org.examen.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Utilidades {
    public static List<PeliculaOscarizada> leerPeliculasOscarizadasCsv(String filePath, String sexo) {
        List<PeliculaOscarizada> peliculasOscarizadas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String nombre = parts[0].trim();
                int anyo = Integer.parseInt(parts[1].trim());
                String pelicula = parts[2].trim();
                int edad = Integer.parseInt(parts[3].trim());
                String actorSexo = parts[4].trim();
                if (sexo.equals(actorSexo)) {
                    peliculasOscarizadas.add(new PeliculaOscarizada(pelicula, anyo, nombre, edad, actorSexo));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return peliculasOscarizadas;
    }

    public static List<Actor> convertirPeliculasOscarizadasEnActores(List<PeliculaOscarizada> peliculasOscarizadas) {
        Map<String, Actor> actorMap = new HashMap<>();
        for (PeliculaOscarizada pelicula : peliculasOscarizadas) {
            String actorNombre = pelicula.getActor();
            if (!actorMap.containsKey(actorNombre)) {
                Actor actor = new Actor(actorNombre, pelicula.getSexo(), 0);
                actorMap.put(actorNombre, actor);
            }
            actorMap.get(actorNombre).agregarPelicula(new Pelicula(pelicula.getPelicula(), pelicula.getAnyo()));
        }
        return new ArrayList<>(actorMap.values());
    }

    public static void escribirActoresEnJson(List<Actor> actores, String filePath) {
        List<Map<String, Object>> actorList = actores.stream().map(actor -> {
            Map<String, Object> actorMap = new HashMap<>();
            actorMap.put("name", actor.getNombre());
            actorMap.put("sex", actor.getSexo());
            actorMap.put("yearOfBirth", actor.getAnyoNacimiento());
            List<Map<String, Object>> peliculas = actor.getPeliculas().stream().map(p -> {
                Map<String, Object> peliculaMap = new HashMap<>();
                peliculaMap.put("title", p.getTitulo());
                peliculaMap.put("year", p.getAnyo());
                return peliculaMap;
            }).collect(Collectors.toList());
            actorMap.put("movies", peliculas);
            return actorMap;
        }).collect(Collectors.toList());

        try (FileWriter file = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(actorList);
            file.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> actoresConMasDeUnOscar(List<Actor> actores) {
        Map<String, Integer> actorCountMap = new HashMap<>();
        for (Actor actor : actores) {
            String actorNombre = actor.getNombre();
            actorCountMap.put(actorNombre, actorCountMap.getOrDefault(actorNombre, 0) + actor.getPeliculas().size());
        }

        return actorCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static List<String> actoresMasJovenesEnGanarUnOscar(List<PeliculaOscarizada> peliculasOscarizadas) {
        Map<String, Integer> actorEdadMap = new HashMap<>();
        for (PeliculaOscarizada pelicula : peliculasOscarizadas) {
            String actorNombre = pelicula.getActor();
            int edad = pelicula.getEdad();
            if (!actorEdadMap.containsKey(actorNombre) || edad < actorEdadMap.get(actorNombre)) {
                actorEdadMap.put(actorNombre, edad);
            }
        }

        return actorEdadMap.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
