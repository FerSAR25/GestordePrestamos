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
                                  String aEntrega, String mesEntrega, String diaEntrega,
                                  String horaEntrega) throws IOException {
        // Si por ejemplo la fecha está "1", le pone el formato adecuado "01"
        mesEntrega = mesEntrega.length() == 1 ? "0" + mesEntrega : mesEntrega;
        diaEntrega = diaEntrega.length() == 1 ? "0" + diaEntrega : diaEntrega;
        horaEntrega = horaEntrega.length() == 1 ? "0" + horaEntrega : horaEntrega;

        // Genera el formato necesario de fecha, agrupando todos los datos obtenidos de cada fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime fechaActual = LocalDateTime.now();
        String retiro = fechaActual.format(formatter);
        String entrega = aEntrega + "-" + mesEntrega + "-" + diaEntrega + "T" + horaEntrega + ":00";

        // Convierte las fechas String en formato de fecha a LocalDateTime
        LocalDateTime fechaRetiro = LocalDateTime.parse(retiro, formatter);
        LocalDateTime fechaEntrega = LocalDateTime.parse(entrega, formatter);

        // Convierte el String a Double
        double depositoDouble = Double.parseDouble(deposito);

        // Le pasa la información ya en el tipo deseado a la logica del Almacen, parseandole algunos datos
        almacen.registrarAlquiler(responsableNombre, direccion, Long.parseLong(celular),
                Long.parseLong(cedula), estudianteNombre, grado, colegio, talla,
                Integer.parseInt(cantidad), fechaRetiro, fechaEntrega, depositoDouble,
                trajeClase, color, Boolean.parseBoolean(sombrero));
    }

    // Recibe la lista de alquileres desde la logica y se la regresa a la interfaz
    public List<String[]> obtenerAlquileresActivos() throws IOException {
        return almacen.obtenerAlquileresActivos();
    }

    // Metodo para marcar como pagado, pasandole los datos necesarios a la logica
    public boolean marcarComoPagado(String cedulaRepresentante, String aRetiro, String mesRetiro, String diaRetiro, String horaRetiro) throws IOException {
        mesRetiro = mesRetiro.length() == 1 ? "0" + mesRetiro : mesRetiro;
        diaRetiro = diaRetiro.length() == 1 ? "0" + diaRetiro : diaRetiro;
        horaRetiro = horaRetiro.length() == 1 ? "0" + horaRetiro : horaRetiro;

        String retiro = aRetiro + "-" + mesRetiro + "-" + diaRetiro + "T" + horaRetiro + ":00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaRetiro = LocalDateTime.parse(retiro, formatter);

        return almacen.marcarComoPagado(cedulaRepresentante, String.valueOf(fechaRetiro));
    }

    // Llama al almacen para devolver una lista de arrays con los alquileres
    public double verificarMultas(String[] alquiler) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Obtiene la fecha actual para ser comparada con la fecha de entrega, verificando si se pasó o no
        LocalDateTime fecha = LocalDateTime.now();
        String actual = fecha.format(formatter);

        String retiro = alquiler[12];
        String entrega = alquiler[13];

        // Convierte las fechas String en formato de fecha a LocalDateTime
        LocalDateTime fechaActual = LocalDateTime.parse(actual, formatter);
        LocalDateTime fechaRetiro = LocalDateTime.parse(retiro, formatter);
        LocalDateTime fechaEntrega = LocalDateTime.parse(entrega, formatter);

        return almacen.verificarMultas(fechaActual, fechaRetiro, fechaEntrega, Double.parseDouble(alquiler[14]));
    }

    // Metodo para buscar un alquiler en específico, pasandole los datos necesarios a la logica
    public String[] buscarAlquiler(String cedulaRepresentante, String aRetiro, String mesRetiro, String diaRetiro, String horaRetiro) throws IOException {
        mesRetiro = mesRetiro.length() == 1 ? "0" + mesRetiro : mesRetiro;
        diaRetiro = diaRetiro.length() == 1 ? "0" + diaRetiro : diaRetiro;
        horaRetiro = horaRetiro.length() == 1 ? "0" + horaRetiro : horaRetiro;

        String retiro = aRetiro + "-" + mesRetiro + "-" + diaRetiro + "T" + horaRetiro + ":00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaRetiro = LocalDateTime.parse(retiro, formatter);

        return almacen.buscarAlquiler(cedulaRepresentante, String.valueOf(fechaRetiro));
    }
}
