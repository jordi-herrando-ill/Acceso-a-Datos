// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<atletaFemenina> atletas = new ArrayList<>();

        // Crea algunos objetos AtletaFemenina
        atletaFemenina atleta1 = new atletaFemenina("Atleta 1", List.of("Prueba 1"), 25, "País 1");
        atletaFemenina atleta2 = new atletaFemenina("Atleta 2", List.of("Prueba 2"), 30, "País 2");

        atletas.add(atleta1);
        atletas.add(atleta2);

        // Serializa la lista de objetos a un archivo XML
        ObjectMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(new File("atletas_femeninas.xml"), atletas);
        } catch (JsonGenerationException | JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<atletaFemenina> atletasLeidos = deserializarDesdeXML("atletas_femeninas.xml");
        for (atletaFemenina atleta : atletasLeidos) {
            System.out.println(atleta);
        }
    }

    private static List<atletaFemenina> deserializarDesdeXML(String nombreArchivo) {
        ObjectMapper xmlMapper = new XmlMapper();
        try {
            List<atletaFemenina> atletas = xmlMapper.readValue(new File(nombreArchivo), new TypeReference<List<atletaFemenina>>() {});
            return atletas;
        } catch (JsonGenerationException | JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}