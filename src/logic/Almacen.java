package logic;

import persistence.ArchivoAlquiler;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Almacen {
	private ArchivoAlquiler archivo;
	private static final double MULTA_POR_DIA = 2500;

	public Almacen() throws IOException {
		this.archivo = new ArchivoAlquiler();
	}

    // Recibe los datos ya parseados del controlador, y ahora realiza el proceso para registrar el nuevo alquiler
	public void registrarAlquiler(String responsableNombre, String direccion, int celular, int cedula,
								  String estudianteNombre, String grado, String colegio, String talla, int cantidad,
								  LocalDateTime retiro, LocalDateTime entrega, Double deposito,
								  String trajeClase, String color, boolean sombrero) throws IOException {

        // Crea al Responsable ,Estudiante y Traje con los datos necesarios, y luego crea el nuevo alquiler pasandole todos los datos restantes
		Responsable responsable = new Responsable(responsableNombre, direccion, celular, cedula);
		Estudiante estudiante = new Estudiante(estudianteNombre, grado, colegio, talla);
		Traje traje = new Traje();
		traje.setClase(trajeClase);
		traje.setColor(color);
		traje.setSombrero(sombrero);
		Alquiler alquiler = new Alquiler(responsable, estudiante, traje, cantidad, retiro, entrega, deposito);

        // Se llama a la persistencia para guardar el nuevo traje, no sin antes convertirlo a formato csv
        archivo.guardarAlquiler(convertirACSV(alquiler));
    }

    // Obtiene la lista de alquileres activos llamando a la persistencia
	public List<String[]> obtenerAlquileresActivos() throws IOException {
        // Recibe todos los alquileres guardados en el archivo csv
        List<String[]> alquileres = archivo.cargarAlquileres();
        List<String[]> alquileresActivos = new ArrayList<>();

        // Filtra entre todos los alquileres aquellos que no han sido
        // devueltos y los guarda en una nueva lista de arrays
        for (String[] alquiler : alquileres) {
            // Verifica si la última posición es "true"
            if (alquiler[16].equalsIgnoreCase("false")) {
                alquileresActivos.add(alquiler);
            }
        }
        return alquileresActivos;
	}

    // Recibe los datos del controlador, y de la persistencia para marcar como pago un prestamo
    public boolean marcarComoPagado(String cedulaResponsable, String fechaRetiro) throws IOException {
        List<String[]> alquileres = archivo.cargarAlquileres();
        boolean encontrado = false;

        for (String[] alquiler : alquileres) {
            // Verifica si la cédula del responsable y la fecha de entrega coinciden
            if (alquiler[3].equals(cedulaResponsable) && alquiler[12].equals(fechaRetiro)) {
                alquiler[15] = "true"; // Cambia el estado de pago a "true"
                encontrado = true;
                break;  // Sale del bucle al encontrar el alquiler
            }
        }

        if (encontrado) {
            archivo.actualizarAlquileres(alquileres);  // Guarda los cambios en el archivo CSV
            return true;
        } else {
            return false;
        }
    }

    public double verificarMultas(LocalDateTime fechaActual, LocalDateTime fechaRetiro, LocalDateTime fechaEntrega) throws IOException {

        // Calcular los días entre fecha de retiro y entrega
        long diasEntrega = ChronoUnit.DAYS.between(fechaRetiro, fechaEntrega);

        // Calcular los días entre fecha de retiro y la fecha actual
        long diasHoy = ChronoUnit.DAYS.between(fechaRetiro, fechaActual);

        long diferencia = diasEntrega - diasHoy;

        if(diferencia >= 0){
            return 0;
        }
        else{
            return (diasHoy - diasEntrega) * MULTA_POR_DIA;
        }
    }

    // Recibe los datos del controlador, y de la persistencia para devolver un prestamo
        public String[] buscarAlquiler(String cedulaResponsable, String fechaRetiro) throws IOException {
        List<String[]> alquileres = archivo.cargarAlquileres();

        for (String[] alquiler : alquileres) {
            // Verifica si la cédula del responsable y la fecha de entrega coinciden
            if (alquiler[3].equals(cedulaResponsable) && alquiler[12].equals(fechaRetiro)) {
                return alquiler;
            }
        }
        return null;
    }

    // Convierte en formato CSV un Alquiler
    private String convertirACSV(Alquiler alquiler) {
        // Se usa cada dato del alquiler y se separa mediante coma y se devuelve como String
        return alquiler.getResponsable().getNombre() + "," +
                alquiler.getResponsable().getDireccion() + "," +
                alquiler.getResponsable().getCelular() + "," +
                alquiler.getResponsable().getCedula() + "," +
                alquiler.getEstudiante().getNombre() + "," +
                alquiler.getEstudiante().getGrado() + "," +
                alquiler.getEstudiante().getColegio() + "," +
                alquiler.getEstudiante().getTalla() + "," +
                alquiler.getTraje().getClase() + "," +
                alquiler.getTraje().getColor() + "," +
                alquiler.getTraje().isSombrero() + "," +
                alquiler.getCantidad() + "," +
                alquiler.getFechaRetiro() + "," +
                alquiler.getFechaEntrega() + "," +
                alquiler.getDeposito() + "," +
                alquiler.isCancelado() + "," + false;
    }
//
//	public void eliminarCancelados() throws IOException {
//		alquileres.removeIf(Alquiler::isCancelado);
//		guardarAlquileres();
//	}
//
//    public String obtenerAlquileresPagados() {
//        StringBuilder sb = new StringBuilder();
//        for (Alquiler alquiler : alquileres) {
//            if (alquiler.isCancelado()) {
//                sb.append(alquiler.getEstudiante().getNombre() + "," +
//                        alquiler.getFechaRetiro() + "," +
//                        alquiler.getFechaEntrega() + "," +
//                        alquiler.getDeposito() + "," +
//                        (alquiler.isCancelado() ? "S" : "N")).append("\n");
//            }
//        }
//        return !sb.isEmpty() ? sb.toString() : "No hay alquileres pagados.";
//    }
//
//	private double calcularMulta(Alquiler alquiler) {
//		long diasRetraso = ChronoUnit.DAYS.between(alquiler.getFechaEntrega(), LocalDateTime.now());
//		return diasRetraso > 0 ? diasRetraso * MULTA_POR_DIA : 0;
//	}
//
//	private void cargarAlquileres() throws IOException {
//		for (String linea : archivo.cargarAlquileres()) {
//			this.alquileres.add(parseCSV(linea));
//		}
//	}
//
//	private void guardarAlquileres() throws IOException {
//		List<String> datos = new ArrayList<>();
//		for (Alquiler alquiler : alquileres) {
//			datos.add(convertirACSV(alquiler));
//		}
//		archivo.sobrescribirAlquileres(datos);
//	}
}

