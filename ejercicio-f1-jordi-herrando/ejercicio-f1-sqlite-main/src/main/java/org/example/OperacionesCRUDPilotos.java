package org.example;

import java.sql.*;
import java.util.*;

public class OperacionesCRUDPilotos {

    // Método para crear un piloto
    public static void CrearPiloto(Connection conn, Piloto piloto) {
        Piloto.escribirPiloto(conn, piloto);
    }

    // Método para leer un piloto
    public static Piloto LeerPiloto(Connection conn, int driverId) {
        return Piloto.leerPiloto(conn, driverId);
    }

    // Método para leer todos los pilotos
    public static List<Piloto> LeerPilotos(Connection conn) {
        List<Piloto> pilotos = new ArrayList<>();
        try {
            String query = "SELECT * FROM drivers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                pilotos.add(new Piloto(
                        rs.getInt("driverId"),
                        rs.getString("code"),
                        rs.getString("forename"),
                        rs.getString("surname"),
                        rs.getString("dob"),
                        rs.getString("nationality")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pilotos;
    }

    // Método para actualizar un piloto
    public static void ActualizarPiloto(Connection conn, Piloto piloto) {
        try {
            String query = "UPDATE drivers SET code = ?, forename = ?, surname = ?, dob = ?, nationality = ? WHERE driverId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, piloto.getCode());
            pstmt.setString(2, piloto.getForename());
            pstmt.setString(3, piloto.getSurname());
            pstmt.setString(4, piloto.getDob());
            pstmt.setString(5, piloto.getNationality());
            pstmt.setInt(6, piloto.getDriverId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para borrar un piloto
    public static void BorrarPiloto(Connection conn, Piloto piloto) {
        try {
            String query = "DELETE FROM drivers WHERE driverId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, piloto.getDriverId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para mostrar la clasificación de los pilotos
    public static void MostrarClasificacionPiloto(Connection conn) {
        try {
            String query = "SELECT drivers.driverId, drivers.forename, drivers.surname, SUM(results.points) as total_points " +
                    "FROM drivers JOIN results ON drivers.driverId = results.driverId " +
                    "GROUP BY drivers.driverId " +
                    "ORDER BY total_points DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println(rs.getString("forename") + " " + rs.getString("surname") + ": " + rs.getInt("total_points"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para mostrar la clasificación de los constructores
    public static void MostrarClasificacionConstructores(Connection conn) {
        try {
            String query = "SELECT constructors.constructorId, constructors.name, SUM(results.points) as total_points " +
                    "FROM constructors JOIN drivers ON constructors.constructorId = drivers.constructorId " +
                    "JOIN results ON drivers.driverId = results.driverId " +
                    "GROUP BY constructors.constructorId " +
                    "ORDER BY total_points DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println(rs.getString("name") + ": " + rs.getInt("total_points"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
