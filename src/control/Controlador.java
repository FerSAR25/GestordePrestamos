package control;

import logic.Almacen;
import model.Alquiler;

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
//        try{
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
//        }
//        catch (Exception e){
//            throw new IOException("Error de entrada de datos.");
//        }
    }

    // Recibe la lista de alquileres desde la logica y se la regresa a la interfaz
    public List<Alquiler> obtenerAlquileres() throws IOException {
        List<Alquiler> nuevosAlquileres = new ArrayList<>();
        List<Alquiler> alquileres = almacen.obtenerAlquileres();
        if(alquileres != null){
            for(Alquiler alquiler: alquileres){
                nuevosAlquileres.add(actualizarMultas(alquiler));
            }
            almacen.actualizarAlquileres(nuevosAlquileres);
            return nuevosAlquileres;
        }
        return null;

    }

    // Metodo para marcar como pagado, pasandole los datos necesarios a la logica
    public boolean entregarAlquiler(Object cedulaRepresentante, Object fechaRetiro) throws IOException {
        try {
            return almacen.entregarAlquiler((Long)cedulaRepresentante, (LocalDateTime)fechaRetiro);
        }
        catch (Exception e){
            throw new IOException("Error de entrada de datos.");
        }
    }

    // Llama al almacen para devolver una lista de arrays con los alquileres
    public Alquiler actualizarMultas(Alquiler alquiler) throws IOException {
        try{
            return almacen.actualizarMultas(alquiler);
        }
        catch (Exception e){
            throw new IOException("Error de entrada de datos.");
        }
    }
}
