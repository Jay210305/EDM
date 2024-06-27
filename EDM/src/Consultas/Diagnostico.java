package Consultas;

import java.util.ArrayList;
import java.util.Date;

public class Diagnostico {
    private Date fechaConsulta;
    private ArrayList<Enfermedad> enfermedades;
    private ArrayList<String> medicamentos;
    private Doctor doctor;

    public Diagnostico(Date fechaConsulta, Doctor doctor) {
        this.fechaConsulta = fechaConsulta;
        this.enfermedades = new ArrayList<>();
        this.medicamentos = new ArrayList<>();
        this.doctor = doctor;
    }

    public void agregarEnfermedad(Enfermedad enfermedad) {
        enfermedades.add(enfermedad);
    }

    public void agregarMedicamento(String medicamento) {
        medicamentos.add(medicamento);
    }

    @Override
    public String toString() {
        return "\nEncargado de la Consulta: " + doctor + "\nDiagnostico:" + "\n"+ fechaConsulta + "\n" + enfermedades + "\nReceta Médica: "
                + medicamentos+"\n";
    }
}
