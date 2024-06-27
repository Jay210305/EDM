package Consultas;

import Pacientes.Paciente;

public class Doctor {
    private String nombre;
    private Especialidad especialidad;
    private QueueLink<Paciente> cola;

    public Doctor(String nombre, String nombreEspecialidad) {
        this.nombre = nombre;
        this.cola = new QueueLink<>();
        this.especialidad = EspecialidadesManager.getEspecialidad(nombreEspecialidad);
        this.especialidad.addDoctor(this);
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad.getEspecialidad();
    }

    public QueueLink<Paciente> getCola() {
        return cola;
    }

    public void setCola(QueueLink<Paciente> cola) {
        this.cola = cola;
    }

    @Override
    public String toString() {
        return "Doctor{" + "nombre='" + nombre + '\'' + ", especialidad='" + getEspecialidad() + '\'' + '}';
    }
}