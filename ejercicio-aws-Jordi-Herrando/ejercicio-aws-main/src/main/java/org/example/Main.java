package org.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String urlConexion = "jdbc:postgresql://formula1-2006.czyhfgywxafz.us-east-1.rds.amazonaws.com:5432/f12006";
        String usuario = "postgres";
        String password = "12345678";

        try (Connection conexion = DriverManager.getConnection(urlConexion, usuario, password)) {
            try {
                // Inicio de la transacción
                conexion.setAutoCommit(false);

                // Inserción o actualización del equipo Seat F1 (UPSERT)
                String insertEquipoSQL = "INSERT INTO constructors (constructorref, name, nationality) " +
                        "VALUES ('seat', 'Seat F1', 'Spanish') RETURNING constructorid;";


                int equipoId;
                try (PreparedStatement equipoStatement = conexion.prepareStatement(insertEquipoSQL)) {
                    ResultSet resultSet = equipoStatement.executeQuery();
                    if (resultSet.next()) {
                        equipoId = resultSet.getInt("constructorid");

                        // Inserción del piloto Carlos Sainz asociado al equipo Seat F1
                        String insertPilotoSQL = "INSERT INTO drivers (code, forename, surname, dob, nationality, constructorid) " +
                                "VALUES ('CSA', 'Carlos', 'Sainz', '1994-09-01', 'Spanish', ?)";
                        try (PreparedStatement pilotoStatement = conexion.prepareStatement(insertPilotoSQL)) {
                            pilotoStatement.setInt(1, equipoId);
                            pilotoStatement.executeUpdate();
                        }

                        // Inserción del piloto Manuel Alomá asociado al equipo Seat F1
                        String insertAlomaSQL = "INSERT INTO drivers (code, forename, surname, dob, nationality, constructorid) " +
                                "VALUES ('ALM', 'Manuel', 'Alomá', '1990-01-01', 'Spanish', ?)";
                        try (PreparedStatement alomaStatement = conexion.prepareStatement(insertAlomaSQL)) {
                            alomaStatement.setInt(1, equipoId);
                            alomaStatement.executeUpdate();
                        }
                    }
                }

                // Confirmación de la transacción
                conexion.commit();
                System.out.println("Transacción completada con éxito.");

            } catch (SQLException ex1) {
                System.err.println(ex1.getClass().getName() + ": " + ex1.getMessage());
                try {
                    // Deshacemos las operaciones realizadas en la base de datos
                    conexion.rollback();
                    System.err.println("ROLLBACK ejecutado");
                } catch (SQLException ex2) {
                    System.err.println("Error haciendo ROLLBACK");
                }
            }
        }
    }
}
