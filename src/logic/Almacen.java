package logic;

import model.Alquiler;
import model.Estudiante;
import model.Responsable;
import model.Traje;
import persistence.ArchivoAlquiler;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Almacen {
    private final ArchivoAlquiler archivo;
    private final double MULTA_POR_DIA = 2500;

    public Almacen() {
        this.archivo = new ArchivoAlquiler();
    }

    // Recibe los datos ya parseados del controlador, y ahora realiza el proceso para registrar el nuevo alquiler
    public void registrarAlquiler(String responsableNombre, String direccion, long celular, long cedula,
                                  String estudianteNombre, String grado, String colegio, String talla, int cantidad,
                                  LocalDateTime retiro, LocalDateTime entrega, Double deposito,
                                  String trajeClase, String color, boolean sombrero) throws IOException {

        List<Alquiler> alquileres = archivo.cargarAlquileres();

        // Crea al Responsable ,Estudiante y Traje con los datos necesarios, y luego crea el nuevo alquiler pasandole todos los datos restantes
        Responsable responsable = new Responsable(responsableNombre, direccion, celular, cedula);
        Estudiante estudiante = new Estudiante(estudianteNombre, grado, colegio, talla);
        Traje traje = new Traje();
        traje.setClase(trajeClase);
        traje.setColor(color);
        traje.setSombrero(sombrero);
        Alquiler alquiler = new Alquiler(responsable, estudiante, traje, cantidad, retiro, entrega, deposito);

        if(alquileres == null){
            alquileres = new ArrayList<>();
        }
        alquileres.add(alquiler);

        // Se llama a la persistencia para guardar el nuevo traje, no sin antes convertirlo a formato csv
        archivo.actualizarAlquileres(alquileres);
    }

    // Obtiene la lista de alquileres llamando a la persistencia
    public List<Alquiler> obtenerAlquileres() throws IOException {
        // Recibe todos los alquileres guardados en el archivo csv
        return archivo.cargarAlquileres();
    }

    // Recibe los datos del controlador, y de la persistencia para marcar como entregado un alquiler
    public boolean entregarAlquiler(Long cedulaResponsable, LocalDateTime fechaRetiro) throws IOException {
        List<Alquiler> alquileres = archivo.cargarAlquileres();
        boolean encontrado = false;

        for (Alquiler alquiler : alquileres) {
            // Verifica si la cédula del responsable y la fecha de entrega coinciden
            if (alquiler.getResponsable().getCedula() == cedulaResponsable && alquiler.getFechaRetiro().equals(fechaRetiro)) {
                alquiler.entregar(); // Cambia el estado de entregado a "true"
                encontrado = true;
                break;  // Sale del bucle al encontrar el alquiler
            }
        }

        if (encontrado) {
            archivo.actualizarAlquileres(alquileres);  // Guarda los cambios en el archivo
            return true;
        } else {
            return false;
        }
    }

    public void actualizarAlquileres(List<Alquiler> alquileres) throws IOException {
        archivo.actualizarAlquileres(alquileres);
    }

    public Alquiler actualizarMultas(Alquiler alquiler) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Obtiene la fecha actual para ser comparada con la fecha de entrega, verificando si se pasó o no
        LocalDateTime fecha = LocalDateTime.now().withSecond(0).withNano(0);
        String actual = fecha.format(formatter);

        // Convierte las fechas String en formato de fecha a LocalDateTime
        LocalDateTime fechaActual = LocalDateTime.parse(actual, formatter);

        LocalDateTime fechaRetiro = alquiler.getFechaRetiro();
        LocalDateTime fechaEntrega = alquiler.getFechaEntrega();

        // Calcular los días entre fecha de retiro y entrega
        long diasEntrega = ChronoUnit.DAYS.between(fechaRetiro, fechaEntrega);

        // Calcular los días entre fecha de retiro y la fecha actual
        long diasHoy = ChronoUnit.DAYS.between(fechaRetiro, fechaActual);

        double multaTotal = 0;

        if (diasHoy > diasEntrega) {
            multaTotal = (diasHoy - diasEntrega) * MULTA_POR_DIA;
        }
        alquiler.setMulta(multaTotal);

        // La deuda es la parte de la multa que no cubre el depósito
        double deudaCalculada = multaTotal - alquiler.getDeposito();
        if (deudaCalculada < 0) {
            deudaCalculada = 0;
        }
        alquiler.setDeuda(deudaCalculada);

        if(alquiler.isEntregado()){
            alquiler.setMulta(0);
            alquiler.setDeuda(0);
            alquiler.setDeposito(0);
        }
        return alquiler;
    }

    public List<Alquiler> buscarAlquiler(Long cedulaResponsable) throws IOException {
        List<Alquiler> alquileres = archivo.cargarAlquileres();
        List<Alquiler> encontrados = new ArrayList<>();

        for (Alquiler alquiler : alquileres) {
            if (alquiler.getResponsable().getCedula() == cedulaResponsable) {
                encontrados.add(alquiler);
            }
        }
        if (encontrados.isEmpty()) {
            return null;
        }
        return encontrados;
    }

//    // Convierte true y false a texto como si y no
//    public List<Alquiler> reescribirAlquileres(List<Alquiler> alquileres){
//        List<Alquiler> nuevosAlquileres = new ArrayList<>();
//
//        for(Alquiler alquiler: alquileres){
//            nuevosAlquileres.add(reescribir(alquiler));
//        }
//        return nuevosAlquileres;
//    }
}

