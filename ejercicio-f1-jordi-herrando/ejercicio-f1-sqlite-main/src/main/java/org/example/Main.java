package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Establece la conexión con la base de datos
        Connection conn = Piloto.conectar();

        // Crea un nuevo piloto
        Piloto nuevoPiloto = new Piloto(1, "HAM", "Lewis", "Hamilton", "1985-01-07", "British");
        OperacionesCRUDPilotos.CrearPiloto(conn, nuevoPiloto);

        // Lee un piloto
        Piloto piloto = OperacionesCRUDPilotos.LeerPiloto(conn, 1);
        System.out.println(piloto.toString());

        // Lee todos los pilotos
        List<Piloto> pilotos = OperacionesCRUDPilotos.LeerPilotos(conn);
        for (Piloto p : pilotos) {
            System.out.println(p.toString());
        }

        // Actualiza un piloto
        nuevoPiloto.setForename("Lewis Carl");
        OperacionesCRUDPilotos.ActualizarPiloto(conn, nuevoPiloto);

        // Borra un piloto
        OperacionesCRUDPilotos.BorrarPiloto(conn, nuevoPiloto);

        // Muestra la clasificación de los pilotos
        OperacionesCRUDPilotos.MostrarClasificacionPiloto(conn);

        // Muestra la clasificación de los constructores
        OperacionesCRUDPilotos.MostrarClasificacionConstructores(conn);

        // Cierra la conexión con la base de datos
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
