package control;

import logic.Almacen;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        try{
            // Si por ejemplo la fecha está "1", le pone el formato adecuado "01"
            mesEntrega = mesEntrega.length() == 1 ? "0" + mesEntrega : mesEntrega;
            diaEntrega = diaEntrega.length() == 1 ? "0" + diaEntrega : diaEntrega;
            horaEntrega = horaEntrega.length() == 1 ? "0" + horaEntrega : horaEntrega;

            // Genera el formato necesario de fecha, agrupando todos los datos obtenidos de cada fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            LocalDateTime fechaActual = LocalDateTime.now().withSecond(0).withNano(0);
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
        catch (Exception e){
            throw new IOException("Error de entrada de datos.");
        }
    }

    // Recibe la lista de alquileres desde la logica y se la regresa a la interfaz
    public List<String[]> obtenerAlquileres() throws IOException {
        List<String[]> nuevosAlquileres = new ArrayList<>();
        for(String[] alquiler: almacen.obtenerAlquileres()){
            nuevosAlquileres.add(obtenerMultas(alquiler));
        }
        return nuevosAlquileres;
    }

    // Metodo para marcar como pagado, pasandole los datos necesarios a la logica
    public boolean marcarComoPagado(String cedulaRepresentante, String retiro) throws IOException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime fechaRetiro = LocalDateTime.parse(retiro, formatter);

            return almacen.marcarComoPagado(cedulaRepresentante, String.valueOf(fechaRetiro));
        }
        catch (Exception e){
            throw new IOException("Error de entrada de datos.");
        }
    }

    // Llama al almacen para devolver una lista de arrays con los alquileres
    public String[] obtenerMultas(String[] alquiler) throws IOException {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            // Obtiene la fecha actual para ser comparada con la fecha de entrega, verificando si se pasó o no
            LocalDateTime fecha = LocalDateTime.now().withSecond(0).withNano(0);
            String actual = fecha.format(formatter);
            String retiro = alquiler[12];
            String entrega = alquiler[13];

            // Convierte las fechas String en formato de fecha a LocalDateTime
            LocalDateTime fechaActual = LocalDateTime.parse(actual, formatter);
            LocalDateTime fechaRetiro = LocalDateTime.parse(retiro, formatter);
            LocalDateTime fechaEntrega = LocalDateTime.parse(entrega, formatter);

            double multa = almacen.verificarMultas(fechaActual, fechaRetiro, fechaEntrega, Double.parseDouble(alquiler[14]));

            return almacen.reescribir(almacen.obtenerMultado(alquiler, String.valueOf(multa), multa));
        }
        catch (Exception e){
            throw new IOException("Error de entrada de datos.");
        }
    }

    // Metodo para buscar un alquiler en específico, pasandole los datos necesarios a la logica
    public List<String[]> buscarAlquiler(String cedulaRepresentante) throws IOException {
        try{
            return almacen.reescribirAlquileres(almacen.buscarAlquiler(cedulaRepresentante));
        }
        catch (Exception e){
            throw new IOException("Error de entrada de datos.");
        }
    }
}
