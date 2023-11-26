package org.example;

import java.sql.*;

public class equipos {
    public static void main(String[] args) throws SQLException {
        String urlConexion = "jdbc:postgresql://formula1-2006.czyhfgywxafz.us-east-1.rds.amazonaws.com:5432/f12006";
        String usuario = "postgres";
        String password = "12345678";

        try (Connection conexion = DriverManager.getConnection(urlConexion, usuario, password)) {
            try {
                // Consulta para obtener todos los equipos
                String consultaEquipos = "SELECT * FROM constructors";
                try (PreparedStatement preparedStatement = conexion.prepareStatement(consultaEquipos);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Mostrar información de cada equipo
                    while (resultSet.next()) {
                        int equipoId = resultSet.getInt("constructorid");
                        String constructorRef = resultSet.getString("constructorref");
                        String nombre = resultSet.getString("name");
                        String nacionalidad = resultSet.getString("nationality");
                        String url = resultSet.getString("url");

                        System.out.println("Equipo ID: " + equipoId);
                        System.out.println("Constructor Ref: " + constructorRef);
                        System.out.println("Nombre: " + nombre);
                        System.out.println("Nacionalidad: " + nacionalidad);
                        System.out.println("URL: " + url);
                        System.out.println("----------");
                    }
                }

            } catch (SQLException ex1) {
                System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());
            }
        }
    }
}
