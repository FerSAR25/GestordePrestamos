package control;

import logic.Almacen;
import logic.Responsable;
import logic.Estudiante;
import logic.Traje;
import java.io.IOException;
import java.time.LocalDateTime;

public class Controlador {
    private final Almacen almacen;

    public Controlador() throws IOException {
        this.almacen = new Almacen();
    }

    public void registrarAlquiler(String responsableNombre, String direccion, String celular, String cedula,
                                  String estudianteNombre, String colegio, String talla,
                                  String cantidad, String retiro, String entrega, String deposito, String grado,
                                  String trajeClase, String color, String sombrero) throws IOException {

        LocalDateTime fechaRetiro = LocalDateTime.parse(retiro);
        LocalDateTime fechaEntrega = LocalDateTime.parse(entrega);
        double depositoDouble = Double.parseDouble(deposito);

        almacen.registrarAlquiler(responsableNombre, direccion, Integer.parseInt(celular), Integer.parseInt(cedula),
        estudianteNombre, grado, colegio, talla, Integer.parseInt(cantidad),
        fechaRetiro, fechaEntrega, depositoDouble,
                trajeClase, color, Boolean.parseBoolean(sombrero));
    }

    public String obtenerAlquileres() {
        return almacen.obtenerAlquileres();
    }

    public void eliminarCancelados() throws IOException {
        almacen.eliminarCancelados();
    }

    public String verificarMultas() {
        return almacen.verificarMultas();
    }

    public void marcarComoPagado(String index) throws IOException {
        int indexInt = Integer.parseInt(index);
        almacen.marcarComoPagado(indexInt);
    }
}
