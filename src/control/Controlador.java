package control;

import logic.Almacen;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controlador {
    private final Almacen almacen;

    public Controlador() throws IOException {
        this.almacen = new Almacen();
    }

    // Recibe los datos de la interfaz para permitir registrar un nuevo prestamo
    public void registrarAlquiler(String responsableNombre, String direccion, String celular, String cedula,
                                  String estudianteNombre, String grado, String colegio, String talla,
                                  String cantidad, String deposito,
                                  String trajeClase, String color, String sombrero,
                                  String añoRetiro, String mesRetiro, String diaRetiro, String horaRetiro,
                                  String minRetiro, String añoEntrega, String mesEntrega, String diaEntrega,
                                  String horaEntrega, String minEntrega) throws IOException {
        // Si la fecha está "1", le pone el formato adecuado "01"
        mesRetiro = mesRetiro.length() == 1 ? "0" + mesRetiro : mesRetiro;
        diaRetiro = diaRetiro.length() == 1 ? "0" + diaRetiro : diaRetiro;
        horaRetiro = horaRetiro.length() == 1 ? "0" + horaRetiro : horaRetiro;
        minRetiro = minRetiro.length() == 1 ? "0" + minRetiro : minRetiro;

        mesEntrega = mesEntrega.length() == 1 ? "0" + mesEntrega : mesEntrega;
        diaEntrega = diaEntrega.length() == 1 ? "0" + diaEntrega : diaEntrega;
        horaEntrega = horaEntrega.length() == 1 ? "0" + horaEntrega : horaEntrega;
        minEntrega = minEntrega.length() == 1 ? "0" + minEntrega : minEntrega;

        // Genera el formato necesario de fecha, agrupando todos los datos obtenidos de cada fecha
        String retiro = añoRetiro + "-" + mesRetiro + "-" + diaRetiro + "T" + horaRetiro + ":" + minRetiro;
        String entrega = añoEntrega + "-" + mesEntrega + "-" + diaEntrega + "T" + horaEntrega + ":" + minEntrega;

        // Convierte las fechas String en formato de fecha a LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaRetiro = LocalDateTime.parse(retiro, formatter);
        LocalDateTime fechaEntrega = LocalDateTime.parse(entrega, formatter);

        // Convierte el String a Double
        double depositoDouble = Double.parseDouble(deposito);

        // Le pasa la información ya en el tipo deseado a la logica del Almacen, parseandole algunos datos
        almacen.registrarAlquiler(responsableNombre, direccion, Integer.parseInt(celular),
                Integer.parseInt(cedula), estudianteNombre, grado, colegio, talla,
                Integer.parseInt(cantidad), fechaRetiro, fechaEntrega, depositoDouble,
                trajeClase, color, Boolean.parseBoolean(sombrero));
    }

    // Recibe la lista de alquileres desde la logica y se la regresa a la interfaz
    public List<String[]> obtenerAlquileresActivos() throws IOException {
        return almacen.obtenerAlquileresActivos();
    }

    // Metodo para marcar como pagado, pasandole los datos necesarios a la logica
    public boolean marcarComoPagado(String cedulaRepresentante, String añoRetiro, String mesRetiro, String diaRetiro, String horaRetiro, String minRetiro) throws IOException {
        mesRetiro = mesRetiro.length() == 1 ? "0" + mesRetiro : mesRetiro;
        diaRetiro = diaRetiro.length() == 1 ? "0" + diaRetiro : diaRetiro;
        horaRetiro = horaRetiro.length() == 1 ? "0" + horaRetiro : horaRetiro;
        minRetiro = minRetiro.length() == 1 ? "0" + minRetiro : minRetiro;

        String retiro = añoRetiro + "-" + mesRetiro + "-" + diaRetiro + "T" + horaRetiro + ":" + minRetiro;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaRetiro = LocalDateTime.parse(retiro, formatter);

        return almacen.marcarComoPagado(cedulaRepresentante, String.valueOf(fechaRetiro));
    }

    public void eliminarCancelados() throws IOException {
        almacen.eliminarCancelados();
    }

    public String verificarMultas() {
        return almacen.verificarMultas();
    }

    // Obtener alquileres pagados
    public String obtenerAlquileresPagados() {
        return almacen.obtenerAlquileresPagados();
    }
}
