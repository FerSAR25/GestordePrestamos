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
                    try {
                        marcarComoPagado();
                    }
                    catch(IOException e){
                        System.out.println("Error al actualizar prestamo");
                    }
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
        String responsableNombre = scanner.nextLine();
        System.out.print("Dirección del responsable: ");
        String direccion = scanner.nextLine();
        System.out.print("Celular del responsable: ");
        String celular = scanner.nextLine();
        System.out.print("Cédula del responsable: ");
        String cedula = scanner.nextLine();

        System.out.print("Nombre del estudiante: ");
        String estudianteNombre = scanner.nextLine();
        System.out.print("Colegio del estudiante: ");
        String colegio = scanner.nextLine();
        System.out.print("Curso del estudiante: ");
        String grado = scanner.nextLine();
        System.out.print("Talla del estudiante: ");
        String talla = scanner.nextLine();

        System.out.print("Clase de traje: ");
        String trajeClase = scanner.nextLine();
        System.out.print("Color de traje: ");
        String color = scanner.nextLine();
        System.out.print("Lleva sombrero?(S, N): ");
        String sombrero = scanner.nextLine();
        System.out.print("Cantidad de trajes: ");
        String cantidad = scanner.nextLine();
        System.out.print("Fecha de retiro (YYYY-MM-DDTHH:MM): ");
        String retiro = scanner.nextLine();
        System.out.print("Fecha de entrega (YYYY-MM-DDTHH:MM): ");
        String entrega = scanner.nextLine();
        System.out.print("Depósito: ");
        String deposito = scanner.nextLine();

        if(sombrero.equalsIgnoreCase("s")){
            sombrero = "true";
        }
        else{
            sombrero = "false";
        }

        controlador.registrarAlquiler(responsableNombre, direccion, celular, cedula,
                estudianteNombre, grado, colegio, talla, cantidad, retiro, entrega, deposito, trajeClase, color, sombrero);
        System.out.println("Alquiler registrado exitosamente.");
    }

    private void marcarComoPagado() throws IOException {
        System.out.print("Ingrese el índice del préstamo a marcar como pagado: ");
        String indice = scanner.nextLine();
        controlador.marcarComoPagado(indice);
        System.out.println("Préstamo marcado como pagado.");
    }
}
