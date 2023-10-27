import java.util.List;

public class atletaFemenina {
    private String nombre;
    private List<String> prueba;
    private int edad;
    private String pais;




    // Constructor
    public atletaFemenina() {

    }

    // Constructor con parámetros
    public atletaFemenina(String nombre, List<String> prueba, int edad, String pais) {
        this.nombre = nombre;
        this.prueba = prueba;
        this.edad = edad;
        this.pais = pais;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public List<String> getPrueba() {
        return prueba;
    }

    public int getEdad() {
        return edad;
    }

    public String getPais() {
        return pais;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrueba(List<String> prueba) {
        this.prueba = prueba;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "atletaFemenina [nombre=" + nombre + ", prueba=" + prueba + ", edad=" + edad + ", pais=" + pais + "]";
    }
}

