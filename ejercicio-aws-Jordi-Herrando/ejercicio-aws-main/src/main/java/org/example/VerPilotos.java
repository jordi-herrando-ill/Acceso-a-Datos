package org.example;

import java.sql.*;

public class VerPilotos {

    public static void main(String[] args) {
        String urlConexion = "jdbc:postgresql://formula1-2006.czyhfgywxafz.us-east-1.rds.amazonaws.com:5432/f12006";
        String usuario = "postgres";
        String password = "12345678";

        try (Connection conexion = DriverManager.getConnection(urlConexion, usuario, password)) {
            try {
                // Consulta para obtener todos los pilotos
                String consultaPilotos = "SELECT * FROM drivers";
                try (PreparedStatement preparedStatement = conexion.prepareStatement(consultaPilotos);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Mostrar información de todos los pilotos
                    System.out.println("Lista de Pilotos:");
                    while (resultSet.next()) {
                        int id = resultSet.getInt("driverid");
                        String code = resultSet.getString("code");
                        String forename = resultSet.getString("forename");
                        String surname = resultSet.getString("surname");
                        Date dob = resultSet.getDate("dob");
                        String nationality = resultSet.getString("nationality");
                        int constructorId = resultSet.getInt("constructorid");

                        System.out.println("ID: " + id +
                                ", Código: " + code +
                                ", Nombre: " + forename +
                                ", Apellido: " + surname +
                                ", Fecha de Nacimiento: " + dob +
                                ", Nacionalidad: " + nationality +
                                ", ID del Equipo: " + constructorId);
                    }
                }

            } catch (SQLException ex1) {
                System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos: " + e.getMessage());
        }
    }
}
