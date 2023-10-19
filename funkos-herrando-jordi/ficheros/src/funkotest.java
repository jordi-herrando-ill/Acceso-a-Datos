import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class funkotest {

    @Test
    void testEncontrarFunkoMasCaro() {
        ConsultasFunkos.Funko funko1 = new ConsultasFunkos.Funko("Funko1", "MARVEL", 10.0, new Date());
        ConsultasFunkos.Funko funko2 = new ConsultasFunkos.Funko("Funko2", "MARVEL", 15.0, new Date());
        List<ConsultasFunkos.Funko> funkoList = Arrays.asList(funko1, funko2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ConsultasFunkos.encontrarFunkoMasCaro(funkoList);

        assertEquals("El Funko más caro es: Funko2 con un precio de €15.00\n", outContent.toString());
    }

    @Test
    void testCalcularMediaPrecios() {
        ConsultasFunkos.Funko funko1 = new ConsultasFunkos.Funko("Funko1", "MARVEL", 10.0, new Date());
        ConsultasFunkos.Funko funko2 = new ConsultasFunkos.Funko("Funko2", "MARVEL", 15.0, new Date());
        List<ConsultasFunkos.Funko> funkoList = Arrays.asList(funko1, funko2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ConsultasFunkos.calcularMediaPrecios(funkoList);

        assertEquals("La media de precios de los Funkos es: €12.50\n", outContent.toString());
    }

    @Test
    void testFunkosPorModelo() {
        ConsultasFunkos.Funko funko1 = new ConsultasFunkos.Funko("Funko1", "MARVEL", 10.0, new Date());
        ConsultasFunkos.Funko funko2 = new ConsultasFunkos.Funko("Funko2", "DISNEY", 15.0, new Date());
        List<ConsultasFunkos.Funko> funkoList = Arrays.asList(funko1, funko2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ConsultasFunkos.imprimirFunkosPorModelo(funkoList);

        assertEquals("Funkos agrupados por modelos:\n" +
                "MARVEL: [Funko1]\n" +
                "DISNEY: [Funko2]\n", outContent.toString());
    }

    @Test
    void testFunkosLanzadosEn2023() {
        ConsultasFunkos.Funko funko1 = new ConsultasFunkos.Funko("Funko1", "MARVEL", 10.0, parseDate("2023-01-01"));
        ConsultasFunkos.Funko funko2 = new ConsultasFunkos.Funko("Funko2", "DISNEY", 15.0, parseDate("2022-01-01"));
        List<ConsultasFunkos.Funko> funkoList = Arrays.asList(funko1, funko2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ConsultasFunkos.imprimirFunkosLanzadosEn2023(funkoList);

        assertEquals("Funkos lanzados en 2023:\n" +
                "Funko1\n", outContent.toString());
    }


    private Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Error al parsear la fecha: " + dateString, e);
        }
    }


    static class MockFunko extends ConsultasFunkos.Funko {
        public MockFunko() {
            super("MockFunko", "MOCK", 0.0, new Date());
        }
    }
}
