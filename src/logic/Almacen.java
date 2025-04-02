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
		this.cargarAlquileres();
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
            if (alquiler[3].equals(cedulaResponsable) && alquiler[13].equals(fechaRetiro)) {
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

	public void eliminarCancelados() throws IOException {
		alquileres.removeIf(Alquiler::isCancelado);
		guardarAlquileres();
	}

	public String verificarMultas() {
		StringBuilder resultado = new StringBuilder();
		for (Alquiler alquiler : alquileres) {
			double multa = calcularMulta(alquiler);
			if (multa > 0) {
				resultado.append("Alquiler de ").append(alquiler.getEstudiante().getNombre())
						.append(" - Multa: $").append(multa).append("\n");
			}
		}
		return !resultado.isEmpty() ? resultado.toString() : "No hay multas pendientes.";
	}

    public String obtenerAlquileresPagados() {
        StringBuilder sb = new StringBuilder();
        for (Alquiler alquiler : alquileres) {
            if (alquiler.isCancelado()) {
                sb.append(alquiler.getEstudiante().getNombre() + "," +
                        alquiler.getFechaRetiro() + "," +
                        alquiler.getFechaEntrega() + "," +
                        alquiler.getDeposito() + "," +
                        (alquiler.isCancelado() ? "S" : "N")).append("\n");
            }
        }
        return !sb.isEmpty() ? sb.toString() : "No hay alquileres pagados.";
    }

	private double calcularMulta(Alquiler alquiler) {
		long diasRetraso = ChronoUnit.DAYS.between(alquiler.getFechaEntrega(), LocalDateTime.now());
		return diasRetraso > 0 ? diasRetraso * MULTA_POR_DIA : 0;
	}

	private void cargarAlquileres() throws IOException {
		for (String linea : archivo.cargarAlquileres()) {
			this.alquileres.add(parseCSV(linea));
		}
	}

	private void guardarAlquileres() throws IOException {
		List<String> datos = new ArrayList<>();
		for (Alquiler alquiler : alquileres) {
			datos.add(convertirACSV(alquiler));
		}
		archivo.sobrescribirAlquileres(datos);
	}

	private Alquiler parseCSV(String linea) {
		String[] datos = linea.split(",");

		// Validación para asegurar que se leen todos los datos correctamente
		if (datos.length < 14) {
			System.err.println("Error: Datos incompletos en línea CSV -> " + linea);
			return null; // Evita errores si la línea está malformada
		}

		Responsable responsable = new Responsable(datos[0], datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]));
		Estudiante estudiante = new Estudiante(datos[4], datos[5], datos[6], datos[7]);
		Traje traje = new Traje();
		traje.setClase(datos[8]);
		traje.setColor(datos[9]);
		traje.setSombrero(Boolean.parseBoolean(datos[10]));

		int cantidad = Integer.parseInt(datos[11]);
		LocalDateTime fechaRetiro = LocalDateTime.parse(datos[12]);
		LocalDateTime fechaEntrega = LocalDateTime.parse(datos[13]);
		double deposito = Double.parseDouble(datos[14]);
		boolean cancelado = Boolean.parseBoolean(datos[15]);

		Alquiler alquiler = new Alquiler(responsable, estudiante, traje, cantidad, fechaRetiro, fechaEntrega, deposito);
		if (cancelado) alquiler.cancelarAlquiler();
		return alquiler;
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
				alquiler.getFechaRetiro().toString() + "," +
				alquiler.getFechaEntrega().toString() + "," +
				alquiler.getDeposito() + "," +
				alquiler.isCancelado() + "," + false;
	}
}

