package persistence;

import java.io.*;
import java.util.*;

public class ArchivoAlquiler {
    private static final String FILE_PATH = "alquileres.csv";

    public void guardarAlquiler(String datosAlquiler) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(datosAlquiler);
            bw.newLine();
        }
    }

    public List<String> cargarAlquileres() throws IOException {
        List<String> lineas = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return lineas;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        }
        return lineas;
    }

    public void sobrescribirAlquileres(List<String> datosAlquileres) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String datos : datosAlquileres) {
                bw.write(datos);
                bw.newLine();
            }
        }
    }
}

