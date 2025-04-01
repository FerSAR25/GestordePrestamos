package control;

import logic.Almacen;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controlador {
    private final Almacen almacen;

    public Controlador() throws IOException {
        this.almacen = new Almacen();
    }

    public void registrarAlquiler(String responsableNombre, String direccion, String celular, String cedula,
                                  String estudianteNombre, String colegio, String talla,
                                  String cantidad, String deposito, String grado,
                                  String trajeClase, String color, String sombrero,
                                  String añoRetiro, String mesRetiro, String diaRetiro, String horaRetiro,
                                  String minRetiro, String añoEntrega, String mesEntrega, String diaEntrega,
                                  String horaEntrega, String minEntrega) throws IOException {
        String retiro = añoRetiro + "-" + mesRetiro + "-" + diaRetiro + "T" + horaRetiro + ":" + minRetiro;
        String entrega = añoEntrega + "-" + mesEntrega + "-" + diaEntrega + "T" + horaEntrega + ":" + minEntrega;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaRetiro = LocalDateTime.parse(retiro, formatter);
        LocalDateTime fechaEntrega = LocalDateTime.parse(entrega, formatter);

        double depositoDouble = Double.parseDouble(deposito);

        almacen.registrarAlquiler(responsableNombre, direccion, Integer.parseInt(celular),
                Integer.parseInt(cedula), estudianteNombre, grado, colegio, talla,
                Integer.parseInt(cantidad), fechaRetiro, fechaEntrega, depositoDouble,
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

    // Método para marcar como pagado
    public boolean marcarComoPagado(String cedulaRepresentante, String añoRetiro, String mesRetiro, String diaRetiro, String horaRetiro, String minRetiro) throws IOException {
        String retiro = añoRetiro + "-" + mesRetiro + "-" + diaRetiro + "T" + horaRetiro + ":" + minRetiro;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaRetiro = LocalDateTime.parse(retiro, formatter);
        return almacen.marcarComoPagado(Integer.parseInt(cedulaRepresentante), fechaRetiro);
    }

    // Obtener alquileres pagados
    public String obtenerAlquileresPagados() {
        return almacen.obtenerAlquileresPagados();
    }
}
