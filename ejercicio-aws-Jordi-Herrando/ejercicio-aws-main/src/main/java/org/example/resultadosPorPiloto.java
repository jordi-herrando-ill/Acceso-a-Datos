package org.example;

import java.sql.*;

public class resultadosPorPiloto {
    public static void main(String[] args) throws SQLException {
        String urlConexion = "jdbc:postgresql://formula1-2006.czyhfgywxafz.us-east-1.rds.amazonaws.com:5432/f12006";
        String usuario = "postgres";
        String password = "12345678";
        String codigoPiloto = "CSA";  // Reemplaza con el código de piloto deseado

        try (Connection conexion = DriverManager.getConnection(urlConexion, usuario, password)) {
            String llamada = "{ ? = call get_results_by_driver(?) }";
            try (CallableStatement callableStatement = conexion.prepareCall(llamada)) {
                callableStatement.registerOutParameter(1, Types.OTHER);  // Tipo de datos de tabla
                callableStatement.setString(2, codigoPiloto);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                while (resultSet.next()) {
                    int round = resultSet.getInt("round");
                    String circuit = resultSet.getString("circuit");
                    int result = resultSet.getInt("result");
                    int points = resultSet.getInt("points");
                    Date date = resultSet.getDate("date");

                    System.out.printf("Round: %d, Circuit: %s, Result: %d, Points: %d, Date: %s%n",
                            round, circuit, result, points, date.toString());
                }
            }
        }
    }
}
