import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ConsultasFunkos {

    private static final Locale SPANISH_LOCALE = new Locale("es", "ES");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", SPANISH_LOCALE);
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(SPANISH_LOCALE);

    private static final String BACKUP_FILE_PATH = "funko_backup.dat";

    public static void main(String[] args) {
        String csvFile = "C:\\Users\\jorill\\OneDrive\\Escritorio\\MEMORIA\\funkos.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Lee la primera línea (encabezados) y descártala
            br.readLine();

            List<Funko> funkoList = br.lines()
                    .map(line -> line.split(","))
                    .filter(data -> data.length == 5)
                    .map(ConsultasFunkos::mapToModel)
                    .collect(Collectors.toList());

            // Imprimir resultados
            encontrarFunkoMasCaro(funkoList);
            calcularMediaPrecios(funkoList);
            imprimirFunkosPorModelo(funkoList);
            imprimirContadorFunkosPorModelo(funkoList);
            imprimirFunkosLanzadosEn2023(funkoList);

            // Backup de los objetos Funko
            backupFunkos(funkoList);

            // Restaurar objetos Funko desde el archivo
            List<Funko> restoredFunkos = restoreFunkos();
            imprimirRestoredFunkos(restoredFunkos);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Funko mapToModel(String[] data) {
        String nombre = data[1];
        String modelo = data[2];
        double precio;
        try {
            precio = Double.parseDouble(data[3]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error al convertir el precio a un número: " + Arrays.toString(data));
        }
        Date fechaLanzamiento;
        try {
            fechaLanzamiento = DATE_FORMAT.parse(data[4]);
        } catch (ParseException e) {
            throw new RuntimeException("Error al parsear la fecha: " + data[4]);
        }
        return new Funko(nombre, modelo, precio, fechaLanzamiento);
    }

    public static void encontrarFunkoMasCaro(List<Funko> funkoList) {
        Funko funkoMasCaro = funkoList.stream()
                .max(Comparator.comparing(Funko::getPrecio))
                .orElse(null);

        if (funkoMasCaro != null) {
            System.out.println("El Funko más caro es: " + funkoMasCaro.getNombre() +
                    " con un precio de " + CURRENCY_FORMAT.format(funkoMasCaro.getPrecio()));
        }
    }

    public static void calcularMediaPrecios(List<Funko> funkoList) {
        if (!funkoList.isEmpty()) {
            double mediaPrecios = funkoList.stream()
                    .mapToDouble(Funko::getPrecio)
                    .average()
                    .orElse(0.0);
            System.out.println("La media de precios de los Funkos es: " + CURRENCY_FORMAT.format(mediaPrecios));
        } else {
            System.out.println("No se encontraron Funkos en el archivo.");
        }
    }

    public static void imprimirFunkosPorModelo(List<Funko> funkoList) {
        Map<String, List<Funko>> funkosPorModelo = funkoList.stream()
                .collect(Collectors.groupingBy(Funko::getModelo));

        System.out.println("Funkos agrupados por modelos:");
        funkosPorModelo.forEach((modelo, funkos) ->
                System.out.println(modelo + ": " +
                        funkos.stream().map(Funko::getNombre).collect(Collectors.toList()))
        );
    }

    private static void imprimirContadorFunkosPorModelo(List<Funko> funkoList) {
        Map<String, Long> contadorFunkosPorModelo = funkoList.stream()
                .collect(Collectors.groupingBy(Funko::getModelo, Collectors.counting()));

        System.out.println("Número de Funkos por modelo:");
        contadorFunkosPorModelo.forEach((modelo, contador) ->
                System.out.println(modelo + ": " + contador)
        );
    }

    static void imprimirFunkosLanzadosEn2023(List<Funko> funkoList) {
        System.out.println("Funkos lanzados en 2023:");
        funkoList.stream()
                .filter(funko -> funko.getFechaLanzamiento().getYear() == 123)
                .forEach(funko -> System.out.println(funko.getNombre()));
    }

    private static void backupFunkos(List<Funko> funkoList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE_PATH))) {
            oos.writeObject(funkoList);
            System.out.println("Backup de Funkos completado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Funko> restoreFunkos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BACKUP_FILE_PATH))) {
            return (List<Funko>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void imprimirRestoredFunkos(List<Funko> restoredFunkos) {
        if (restoredFunkos != null) {
            System.out.println("Objetos Funko restaurados:");
            restoredFunkos.forEach(System.out::println);
        } else {
            System.out.println("No se pudieron restaurar los objetos Funko.");
        }
    }

    public static class Funko implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String nombre;
        private final String modelo;
        private final double precio;
        private final Date fechaLanzamiento;

        public Funko(String nombre, String modelo, double precio, Date fechaLanzamiento) {
            this.nombre = nombre;
            this.modelo = modelo;
            this.precio = precio;
            this.fechaLanzamiento = fechaLanzamiento;
        }

        public String getNombre() {
            return nombre;
        }

        public String getModelo() {
            return modelo;
        }

        public double getPrecio() {
            return precio;
        }

        public Date getFechaLanzamiento() {
            return fechaLanzamiento;
        }

        @Override
        public String toString() {
            return "Funko{" +
                    "nombre='" + nombre + '\'' +
                    ", modelo='" + modelo + '\'' +
                    ", precio=" + CURRENCY_FORMAT.format(precio) +
                    ", fechaLanzamiento=" + DATE_FORMAT.format(fechaLanzamiento) +
                    '}';
        }
    }
}
