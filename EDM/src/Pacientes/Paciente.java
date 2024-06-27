package Pacientes;

import Consultas.Diagnostico;
import HistorialMedico.BPlusTree;

import java.util.Calendar;
import java.util.Date;

public class Paciente implements Comparable<Paciente> {
    private String nombre;
    private String DNI;
    private Date fechaNacimiento;
    private InfoPaciente infoPersonal;
    private BPlusTree historialMedico;

    public Paciente(String nombre, String DNI, Date fechaNacimiento) {
        this.nombre = nombre;
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
        this.historialMedico = new BPlusTree<Diagnostico>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getEdad() {
        Calendar fechaNacimiento = Calendar.getInstance();
        fechaNacimiento.setTime(this.fechaNacimiento);
        Calendar fechaActual = Calendar.getInstance();
        int edad = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
        if (fechaActual.get(Calendar.DAY_OF_YEAR) < fechaNacimiento.get(Calendar.DAY_OF_YEAR)) {
            edad--;
        }
        return edad;
    }

    @Override
    public String toString() {
        return "PACIENTE\n" + nombre + "\nIdentificado con DNI n° " + DNI + "\n" + "Edad: " + getEdad()
                + historialMedico;
    }

    @Override
    public int compareTo(Paciente otroPaciente) {
        return this.DNI.compareTo(otroPaciente.getDNI());
    }

}
