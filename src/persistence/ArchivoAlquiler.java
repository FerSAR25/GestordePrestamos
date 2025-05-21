package persistence;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import model.Alquiler;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;

public class ArchivoAlquiler {
    private final String FILE_NAME = "alquileres.json";
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).setPrettyPrinting().create();;

    // Lee el archivo CSV, lo guarda y lo devuelve como una lista de arrays de String
    public List<Alquiler> cargarAlquileres() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) {
            return null; // No hay datos aún
        }
        try(FileReader reader = new FileReader(FILE_NAME)){
            Alquiler[] array = gson.fromJson(reader, Alquiler[].class);
            return new ArrayList<>(Arrays.asList(array));
        }
        catch(JsonIOException e){
            throw new IOException("No hay alquileres registrados.");
        }
        catch(IOException e){
            return null;
        }
    }

    // Actualiza la información del csv, pasándole una nueva lista de alquileres actualizada
    public void actualizarAlquileres(List<Alquiler> alquileres) throws IOException {
        try(FileWriter writer = new FileWriter(FILE_NAME)){
            gson.toJson(alquileres != null ? alquileres : new ArrayList<>(), writer);
        }
    }
}
