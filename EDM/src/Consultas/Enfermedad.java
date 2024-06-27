package Consultas;

public class Enfermedad {
    private String nombre;
    private boolean cronico;

    public Enfermedad(String nombre, boolean cronico) {
        this.nombre = nombre;
        this.cronico = cronico;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isCronico() {
        return cronico;
    }

    @Override
    public String toString() {
        if (cronico) {
            return nombre + '\'' + "crónico";
        }
        return nombre;
    }
}
