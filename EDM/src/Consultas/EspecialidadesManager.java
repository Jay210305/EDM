package Consultas;

import java.util.ArrayList;
import java.util.List;

public class EspecialidadesManager {
    private static List<Especialidad> especialidades = new ArrayList<>();

    public static Especialidad getEspecialidad(String nombreEspecialidad) {
        for (Especialidad esp : especialidades) {
            if (esp.getEspecialidad().equals(nombreEspecialidad)) {
                return esp;
            }
        }
        Especialidad nuevaEspecialidad = new Especialidad(nombreEspecialidad);
        especialidades.add(nuevaEspecialidad);
        return nuevaEspecialidad;
    }
}
