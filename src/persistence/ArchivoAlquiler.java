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

    public void sobrescribirAlquileres(List<String> datos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String linea : datos) {
                bw.write(linea);
                bw.newLine();
            }
        }
    }
}
