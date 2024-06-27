package main;

import Pacientes.HashA;
import Pacientes.Paciente;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String args[]) {
        HashA<Paciente> pacientes = new HashA<Paciente>(101);
        for (int i = 0; i < 10; i++) { // Crear 10 pacientes
            String nombre = generateRandomName();
            int DNI = generateRandomDNI();
            Date fechaNacimiento = generateRandomDate();

            Paciente paciente = new Paciente(nombre, String.valueOf(DNI), fechaNacimiento);
            pacientes.insert(DNI, paciente);
        }

        System.out.println(pacientes.toString());
    }

    // Método para generar nombres aleatorios
    private static String generateRandomName() {
        String[] nombres = {"Juan", "María", "Carlos", "Ana", "Pedro", "Lucía", "Miguel", "Laura", "Jorge", "Elena"};
        Random random = new Random();
        return nombres[random.nextInt(nombres.length)];
    }

    // Método para generar DNI aleatorios
    private static int generateRandomDNI() {
        Random random = new Random();
        int numero = random.nextInt(90000000) + 10000000; // Genera un número entre 10000000 y 99999999
        return numero;
    }

    // Método para generar fechas de nacimiento aleatorias
    private static Date generateRandomDate() {
        long minDay = Date.UTC(50, 0, 1, 0, 0, 0); // 1 de enero de 1950
        long maxDay = Date.UTC(120, 0, 1, 0, 0, 0); // 1 de enero de 2020
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return new Date(randomDay);
    }

}
