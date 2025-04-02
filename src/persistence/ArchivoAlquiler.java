package persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoAlquiler {
    private static final String FILE_NAME = "alquileres.csv";

    // Lee el archivo CSV, lo guarda y lo devuelve como una lista de arrays de String
    public List<String[]> cargarAlquileres() throws IOException {
        List<String[]> alquileres = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                alquileres.add(linea.split(","));
            }
        }
        // Devuelve la lista creada
        return alquileres;
    }

    // Recibe un String de la logica, y lo guarda en el archivo csv
    public void guardarAlquiler(String alquilerCSV) throws IOException {
        // Selecciona el archivo de la constante FILE_NAME, y no sobreescribe la información dentro del CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(alquilerCSV);
            bw.newLine();
        }
        // Cuando sale correctamente no devuelve nada, si existe algún error al escribir se lanza la excepcion
    }

    // Actualiza la información del csv, pasándole una nueva lista de alquileres actualizada
    public void actualizarAlquileres(List<String[]> alquileres) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String[] alquiler : alquileres) {
                bw.write(String.join(",", alquiler));  // Convierte la lista en una línea de texto CSV
                bw.newLine();
            }
        }
    }
}
