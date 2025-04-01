package persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoAlquiler {
    private static final String FILE_NAME = "alquileres.csv";

    public List<String> cargarAlquileres() throws IOException {
        List<String> lineas = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        }
        return lineas;
    }

    public void guardarAlquilerDirecto(String alquilerCSV) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(alquilerCSV);
            bw.newLine();
        }
    }

    public void sobrescribirAlquileres(List<String> datos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String linea : datos) {
                bw.write(linea);
                bw.newLine();
            }
        }
    }

    // Método para actualizar un alquiler en el archivo CSV
    public void actualizarAlquiler(int indice, String alquilerCSV) throws IOException {
        // Leer todas las líneas del archivo CSV
        List<String> lineas = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        }

        // Comprobar si el índice está dentro del rango
        if (indice >= 0 && indice < lineas.size()) {
            // Actualizar la línea correspondiente al índice con los nuevos datos
            lineas.set(indice, alquilerCSV);
        } else {
            return;
        }

        // Sobrescribir el archivo con las líneas actualizadas
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        }
    }
}
