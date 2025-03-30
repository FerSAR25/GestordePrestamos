package gui;

import control.Controlador;
import java.io.IOException;
import java.util.Scanner;

public class Interfaz {
    private Controlador controlador;
    private Scanner scanner;

    public void vista() {
        try{
            this.controlador = new Controlador();
            this.scanner = new Scanner(System.in);
        }
        catch(IOException e){
            System.out.println("Error: El sistema no pudo ser cargado");
            return;
        }
        while (true) {
            System.out.println("Menú:");
            System.out.println("1. Registrar Alquiler");
            System.out.println("2. Ver Alquileres");
            System.out.println("3. Eliminar Cancelados");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarAlquiler();
                    break;
                case 2:
                    System.out.println(controlador.obtenerAlquileres());
                    break;
                case 3:
                    try {
                        controlador.eliminarCancelados();
                        System.out.println("Cancelados eliminados.");
                    } catch (IOException e) {
                        System.out.println("Error al eliminar cancelados.");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void registrarAlquiler() {
        try {
            System.out.print("Nombre del Responsable: ");
            String responsableNombre = scanner.nextLine();

            System.out.print("Cedula del Responsable: ");
            String cedula = scanner.nextLine();

            System.out.print("Celular del Responsable: ");
            String celular = scanner.nextLine();

            System.out.print("Dirección del Responsable: ");
            String direccion = scanner.nextLine();

            System.out.print("Nombre del Estudiante: ");
            String estudianteNombre = scanner.nextLine();

            System.out.print("Talla del Estudiante: ");
            String talla = scanner.nextLine();

            System.out.print("Colegio del Estudiante: ");
            String colegio = scanner.nextLine();

            System.out.print("Curso del Estudiante: ");
            String curso = scanner.nextLine();

            System.out.print("Clase del Traje: ");
            String trajeClase = scanner.nextLine();

            System.out.print("Cantidad: ");
            String cantidad = scanner.nextLine();

            System.out.print("Fecha de Retiro (YYYY-MM-DDTHH:MM:SS): ");
            String retiro = scanner.nextLine();

            System.out.print("Fecha de Entrega (YYYY-MM-DDTHH:MM:SS): ");
            String entrega = scanner.nextLine();

            System.out.print("Depósito: ");
            String deposito = scanner.nextLine();

            controlador.registrarAlquiler(responsableNombre, cedula, celular, direccion, estudianteNombre, talla,
                    colegio, curso, trajeClase, cantidad, retiro, entrega, deposito);
            System.out.println("Alquiler registrado correctamente.");
        } catch (Exception e) {
            System.out.println("Error en el registro: " + e.getMessage());
        }
    }
}
