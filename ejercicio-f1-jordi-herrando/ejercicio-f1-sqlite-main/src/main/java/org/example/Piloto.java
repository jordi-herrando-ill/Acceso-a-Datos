package org.example;

import java.sql.*;
import java.nio.file.*;

public class Piloto {
    private int driverId;
    private String code;
    private String forename;
    private String surname;

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    private String dob;
    private String nationality;

    // Constructor
    public Piloto(int driverId, String code, String forename, String surname, String dob, String nationality) {
        this.driverId = driverId;
        this.code = code;
        this.forename = forename;
        this.surname = surname;
        this.dob = dob;
        this.nationality = nationality;
    }

    // Getters and setters
    // ...

    // Método para leer un piloto de la base de datos
    public static Piloto leerPiloto(Connection conn, int driverId) {
        try {
            String query = "SELECT * FROM drivers WHERE driverId = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, driverId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Piloto(
                        rs.getInt("driverId"),
                        rs.getString("code"),
                        rs.getString("forename"),
                        rs.getString("surname"),
                        rs.getString("dob"),
                        rs.getString("nationality")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // Método para escribir un piloto en la base de datos
    public static void escribirPiloto(Connection conn, Piloto piloto) {
        try {
            String query = "INSERT INTO drivers (driverId, code, forename, surname, dob, nationality) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, piloto.driverId);
            pstmt.setString(2, piloto.code);
            pstmt.setString(3, piloto.forename);
            pstmt.setString(4, piloto.surname);
            pstmt.setString(5, piloto.dob);
            pstmt.setString(6, piloto.nationality);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para establecer una conexión con la base de datos
    public static Connection conectar() {
        Connection conn = null;
        try {
            // Carga el driver de SQLite
            Class.forName("org.sqlite.JDBC");

            // Establece la conexión con la base de datos
            String url = "jdbc:sqlite:" + Paths.get("src/main/resources/db/f12006sqlite.db").toAbsolutePath().toString();
            conn = DriverManager.getConnection(url);

            System.out.println("Conexión a SQLite establecida.");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    @Override
    public String toString() {
        return "Piloto: " +
                "Id del piloto: " + driverId +
                ", código: '" + code + '\'' +
                ", Primer Nombre: '" + forename + '\'' +
                ", Apellido: '" + surname + '\'' +
                ", Fecha de Nacimiento: '" + dob + '\'' +
                ", Nacionalidad: '" + nationality + '\'';
    }

}
