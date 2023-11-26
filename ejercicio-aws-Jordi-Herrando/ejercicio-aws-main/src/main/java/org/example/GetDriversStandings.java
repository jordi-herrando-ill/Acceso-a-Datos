package org.example;

import java.sql.*;

public class GetDriversStandings {
    public static void main(String[] args) throws SQLException {
        String urlConexion = "jdbc:postgresql://formula1-2006.czyhfgywxafz.us-east-1.rds.amazonaws.com:5432/f12006";
        String usuario = "postgres";
        String password = "12345678";

        try (Connection conexion = DriverManager.getConnection(urlConexion, usuario, password)) {
            String llamada = "{ ? = call get_drivers_standings() }";
            try (CallableStatement callableStatement = conexion.prepareCall(llamada)) {
                callableStatement.registerOutParameter(1, Types.OTHER);  // Tipo de datos de tabla
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                while (resultSet.next()) {
                    String driver = resultSet.getString("driver");
                    long points = resultSet.getLong("points");

                    System.out.printf("Driver: %s, Points: %d%n", driver, points);
                }
            }
        }
    }
}
