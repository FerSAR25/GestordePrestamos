package gui;

import control.Controlador;
import java.io.IOException;
import java.util.Scanner;

public class Interfaz {
    Controlador controlador;
    Scanner scanner;

    public void vista() {
        try{
            this.controlador = new Controlador();
            this.scanner = new Scanner(System.in);
        }
        catch(IOException e){
            System.out.println("Error al usar el sistema");
        }
        while (true) {
            System.out.println("\n*** Sistema de Gestión de Préstamos ***");
            System.out.println("1. Registrar alquiler");
            System.out.println("2. Ver lista de alquileres");
            System.out.println("3. Eliminar alquileres cancelados");
            System.out.println("4. Verificar multas");
            System.out.println("5. Marcar préstamo como pagado");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    try {
                        registrarAlquiler();
                    }
                    catch(IOException e){
                        System.out.println("Error al registrar alquiler");
                    }
                    break;
                case "2":
                    System.out.println(controlador.obtenerAlquileres());
                    break;
                case "3":
                    try {
                        controlador.eliminarCancelados();
                        System.out.println("Alquileres cancelados eliminados.");
                    }
                    catch(IOException e){
                        System.out.println("Error al eliminar alquileres");
                    }
                    break;
                case "4":
                    System.out.println(controlador.verificarMultas());
                    break;
                case "5":
                    marcarComoPagado();
                    break;
                case "6":
                    System.out.println("Saliendo del sistema...");
                    return;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    private void registrarAlquiler() throws IOException {
        System.out.print("Nombre del responsable: ");
        String responsableNombre = scanner.nextLine().trim();
        System.out.print("Dirección del responsable: ");
        String direccion = scanner.nextLine().trim();
        System.out.print("Celular del responsable: ");
        String celular = scanner.nextLine().trim();
        System.out.print("Cédula del responsable: ");
        String cedula = scanner.nextLine().trim();

        System.out.print("Nombre del estudiante: ");
        String estudianteNombre = scanner.nextLine().trim();
        System.out.print("Colegio del estudiante: ");
        String colegio = scanner.nextLine().trim();
        System.out.print("Curso del estudiante: ");
        String grado = scanner.nextLine().trim();
        System.out.print("Talla del estudiante: ");
        String talla = scanner.nextLine().trim();

        System.out.print("Clase de traje: ");
        String trajeClase = scanner.nextLine().trim();
        System.out.print("Color de traje: ");
        String color = scanner.nextLine().trim();
        System.out.print("Lleva sombrero?(S, N): ");
        String sombrero = scanner.nextLine().trim();
        System.out.print("Cantidad de trajes: ");
        String cantidad = scanner.nextLine().trim();
        System.out.print("Depósito: ");
        String deposito = scanner.nextLine().trim();
        System.out.print("Año de retiro (YYYY): ");
        String añoRetiro = scanner.nextLine().trim();
        System.out.print("Mes de retiro (MM): ");
        String mesRetiro = scanner.nextLine().trim();
        System.out.print("Día de retiro (DD): ");
        String diaRetiro = scanner.nextLine().trim();
        System.out.print("Hora de retiro (HH): ");
        String horaRetiro = scanner.nextLine().trim();
        System.out.print("Minutos de retiro (MM): ");
        String minRetiro = scanner.nextLine().trim();

        System.out.print("Año de entrega (YYYY): ");
        String añoEntrega = scanner.nextLine().trim();
        System.out.print("Mes de entrega (MM): ");
        String mesEntrega = scanner.nextLine().trim();
        System.out.print("Día de entrega (DD): ");
        String diaEntrega = scanner.nextLine().trim();
        System.out.print("Hora de entrega (HH): ");
        String horaEntrega = scanner.nextLine().trim();
        System.out.print("Minutos de entrega (MM): ");
        String minEntrega = scanner.nextLine().trim();

        if(sombrero.equalsIgnoreCase("s")){
            sombrero = "true";
        }
        else{
            sombrero = "false";
        }

        if (añoRetiro.isEmpty() || mesRetiro.isEmpty() || diaRetiro.isEmpty() ||
                horaRetiro.isEmpty() || minRetiro.isEmpty() ||
                añoEntrega.isEmpty() || mesEntrega.isEmpty() || diaEntrega.isEmpty() ||
                horaEntrega.isEmpty() || minEntrega.isEmpty()) {
            System.out.println("Error: Algún valor de la fecha está vacío.");
            return;
        }

        mesRetiro = mesRetiro.length() == 1 ? "0" + mesRetiro : mesRetiro;
        diaRetiro = diaRetiro.length() == 1 ? "0" + diaRetiro : diaRetiro;
        horaRetiro = horaRetiro.length() == 1 ? "0" + horaRetiro : horaRetiro;
        minRetiro = minRetiro.length() == 1 ? "0" + minRetiro : minRetiro;

        mesEntrega = mesEntrega.length() == 1 ? "0" + mesEntrega : mesEntrega;
        diaEntrega = diaEntrega.length() == 1 ? "0" + diaEntrega : diaEntrega;
        horaEntrega = horaEntrega.length() == 1 ? "0" + horaEntrega : horaEntrega;
        minEntrega = minEntrega.length() == 1 ? "0" + minEntrega : minEntrega;

        controlador.registrarAlquiler(responsableNombre, direccion, celular, cedula,
                estudianteNombre, grado, colegio, talla, cantidad, deposito, trajeClase, color, sombrero,
                añoRetiro, mesRetiro, diaRetiro, horaRetiro, minRetiro,
                añoEntrega, mesEntrega, diaEntrega, horaEntrega, minEntrega);
        System.out.println("Alquiler registrado exitosamente.");
    }

    private void marcarComoPagado() {
        System.out.print("Ingrese la cédula del representante: ");
        String cedulaRepresentante = scanner.nextLine();
        System.out.print("Año de retiro (YYYY): ");
        String añoRetiro = scanner.nextLine().trim();
        System.out.print("Mes de retiro (MM): ");
        String mesRetiro = scanner.nextLine().trim();
        System.out.print("Día de retiro (DD): ");
        String diaRetiro = scanner.nextLine().trim();
        System.out.print("Hora de retiro (HH): ");
        String horaRetiro = scanner.nextLine().trim();
        System.out.print("Minutos de retiro (MM): ");
        String minRetiro = scanner.nextLine().trim();

        mesRetiro = mesRetiro.length() == 1 ? "0" + mesRetiro : mesRetiro;
        diaRetiro = diaRetiro.length() == 1 ? "0" + diaRetiro : diaRetiro;
        horaRetiro = horaRetiro.length() == 1 ? "0" + horaRetiro : horaRetiro;
        minRetiro = minRetiro.length() == 1 ? "0" + minRetiro : minRetiro;

        try {
            if (controlador.marcarComoPagado(cedulaRepresentante, añoRetiro, mesRetiro, diaRetiro, horaRetiro, minRetiro)) {
                System.out.println("El alquiler ha sido marcado como pagado.");
            } else {
                System.out.println("No se encontró el alquiler.");
            }
        } catch (IOException e) {
            System.out.println("Error al marcar el alquiler como pagado.");
        }
    }
}
