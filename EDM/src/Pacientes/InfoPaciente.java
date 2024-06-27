package Pacientes;

import Consultas.Diagnostico;
import Consultas.Enfermedad;

import java.util.ArrayList;

public class InfoPaciente {
    private ArrayList<Enfermedad> enfermedadesPreexistentes;
    private ArrayList<String> medicacion;
    private String operaciones;
    private String antecedentesFamiliares;
    private String alergias;

    public InfoPaciente() {
        this.enfermedadesPreexistentes = new ArrayList<>();
        this.medicacion = new ArrayList<>();
    }

    public void agregarEnfermedadPreexistente(Enfermedad enfermedad) {
        if (enfermedad.isCronico()) {
            enfermedadesPreexistentes.add(enfermedad);
        }
    }

    public void agregarMedicacion(String medicamento) {
        medicacion.add(medicamento);
    }

    public void setOperaciones(String operaciones) {
        this.operaciones = operaciones;
    }

    public void setAntecedentesFamiliares(String antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    @Override
    public String toString() {
        return "\nHistorialMedico\n" + "\nEnfermedadesPreexistentes=" + enfermedadesPreexistentes + "\nOperaciones='" + operaciones + '\'' + "\nAntecedentesFamiliares='"
                + antecedentesFamiliares + '\'' + "\nAlergias='" + alergias + '\'';
    }
}
