package Consultas;

import java.util.ArrayList;
import java.util.List;

public class Especialidad {
    private List<Doctor> doctores;
    private String especialidad;

    public Especialidad(String especialidad) {
        this.doctores = new ArrayList<>();
        this.especialidad = especialidad;
    }

    public List<Doctor> getDoctores() {
        return doctores;
    }

    public void addDoctor(Doctor doctor) {
        this.doctores.add(doctor);
    }

    public String getEspecialidad() {
        return especialidad;
    }
}
