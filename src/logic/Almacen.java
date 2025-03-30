package logic;

import persistence.ArchivoAlquiler;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Almacen {
	private List<Alquiler> alquileres;
	private ArchivoAlquiler archivo;
	private static final double MULTA_POR_DIA = 5000;

	public Almacen() throws IOException {
		this.archivo = new ArchivoAlquiler();
		this.alquileres = new ArrayList<>();

		for (String linea : archivo.cargarAlquileres()) {
			this.alquileres.add(parseCSV(linea));
		}
	}

	public void registrarAlquiler(Responsable responsable, Estudiante estudiante, Traje traje,
								  int cantidad, LocalDateTime retiro, LocalDateTime entrega, double deposito) throws IOException {
		Alquiler alquiler = new Alquiler(responsable, estudiante, traje, cantidad, retiro, entrega, deposito);
		alquileres.add(alquiler);
		archivo.guardarAlquiler(toCSV(alquiler));
	}

	public String obtenerAlquileres() {
		StringBuilder sb = new StringBuilder();
		for (Alquiler alquiler : alquileres) {
			sb.append(toCSV(alquiler)).append("\n");
		}
		return sb.toString();
	}

	public void eliminarCancelados() throws IOException {
		alquileres.removeIf(Alquiler::isCancelado);
		List<String> datosFiltrados = new ArrayList<>();
		for (Alquiler alquiler : alquileres) {
			datosFiltrados.add(toCSV(alquiler));
		}
		archivo.sobrescribirAlquileres(datosFiltrados);
	}

	private String toCSV(Alquiler alquiler) {
		return alquiler.getResponsable().getNombre() + "," + alquiler.getResponsable().getDireccion() + "," +
				alquiler.getResponsable().getCelular() + "," + alquiler.getResponsable().getCedula() + "," +
				alquiler.getEstudiante().getNombre() + "," + alquiler.getEstudiante().getTalla() + "," +
				alquiler.getEstudiante().getColegio() + "," + alquiler.getEstudiante().getGrado() + "," +
				alquiler.getTraje().getClase() + "," + alquiler.getCantidad() + "," +
				alquiler.getFechaRetiro().toString() + "," + alquiler.getFechaEntrega().toString() + "," +
				alquiler.getDeposito() + "," + alquiler.isCancelado();
	}

	private Alquiler parseCSV(String linea) {
		String[] datos = linea.split(",");
		Responsable responsable = new Responsable(datos[0], datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]));
		Estudiante estudiante = new Estudiante(datos[4], datos[5], datos[6], datos[7]);
		Traje traje = new Traje();
		traje.setClase(datos[8]);
		int cantidad = Integer.parseInt(datos[9]);
		LocalDateTime fechaRetiro = LocalDateTime.parse(datos[10]);
		LocalDateTime fechaEntrega = LocalDateTime.parse(datos[11]);
		double deposito = Double.parseDouble(datos[12]);
		boolean cancelado = Boolean.parseBoolean(datos[13]);

		Alquiler alquiler = new Alquiler(responsable, estudiante, traje, cantidad, fechaRetiro, fechaEntrega, deposito);
		if (cancelado) alquiler.cancelarAlquiler();
		return alquiler;
	}
}
