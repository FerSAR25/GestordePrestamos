package control;

import logic.Almacen;
import logic.Responsable;
import logic.Estudiante;
import logic.Traje;
import java.io.IOException;
import java.time.LocalDateTime;

public class Controlador {
    private Almacen almacen;

    public Controlador() throws IOException {
        this.almacen = new Almacen();
    }

    public void registrarAlquiler(String responsableNombre, String cedula, String celular, String direccion,
                                  String estudianteNombre, String talla, String colegio, String curso,
                                  String trajeClase, String cantidad, String retiro, String entrega, String deposito) throws IOException {
        // Convertir los datos antes de pasarlos a Almacen
        int cedulaInt = Integer.parseInt(cedula);
        int celularInt = Integer.parseInt(celular);
        int cantidadInt = Integer.parseInt(cantidad);
        double depositoDouble = Double.parseDouble(deposito);
        LocalDateTime fechaRetiro = LocalDateTime.parse(retiro);
        LocalDateTime fechaEntrega = LocalDateTime.parse(entrega);

        Responsable responsable = new Responsable(responsableNombre, direccion, celularInt, cedulaInt);
        Estudiante estudiante = new Estudiante(estudianteNombre, curso, colegio, talla);
        Traje traje = new Traje();
        traje.setClase(trajeClase);

        almacen.registrarAlquiler(responsable, estudiante, traje, cantidadInt, fechaRetiro, fechaEntrega, depositoDouble);
    }

    public String obtenerAlquileres() {
        return almacen.obtenerAlquileres();
    }

    public void eliminarCancelados() throws IOException {
        almacen.eliminarCancelados();
    }
}
