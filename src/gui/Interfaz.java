package gui;

import control.Controlador;
import model.Alquiler;

import java.io.IOException;
import java.util.List;
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
            System.exit(1);
        }
        while (true) {
            System.out.println("\n*** Sistema de Gestión de Préstamos ***");
            System.out.println("1. Registrar alquiler");
            System.out.println("2. Ver lista de alquileres activos");
            System.out.println("3. Marcar préstamo como pagado");
            System.out.println("4. Mostrar multas");
            System.out.println("5. Buscar alquiler");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                // Caso para un nuevo registrar alquiler
                case "1":
                    try {
                        registrarAlquiler();
                    }
                    // Si ocurre un error se muestra que no pudo ser registrado el alquiler
                    catch(IOException e){
                        System.out.println("Error al registrar alquiler");
                    }
                    break;
                // Caso para mostrar los alquileres que siguen activos, y que
                // están guardados en el sistema o archivo (.CSV)
                case "2":
                    try{
                        List<Alquiler> alquileresActivos = controlador.obtenerAlquileres();
                        for(Alquiler alquiler: alquileresActivos){
                            System.out.println("-----------------------------------");
                        }
                    }
                    catch (IOException e){
                        System.out.println("Error al abrir archivo de alquileres activos");
                    }
                    break;
                // Permite marcar un prestamo o alquiler como pagado
                case "3":
                    marcarComoPagado();
                    break;
                // Llama al controlador para devolver todos los préstamos que tengan alguna multa
                case "4":
                    try{
                        // Usa exclusivamente los alquileres que aún no han sido devueltos
                        List<Alquiler> alquileres = controlador.obtenerAlquileres();
                        for(Alquiler alquiler: alquileres){
                            Alquiler multados = controlador.actualizarMultas(alquiler);
                            // Solo si se pasa del plazo se muestra la multa correspondiente
                            System.out.println("-----------------------------------");
                        }
                    }
                    catch(IOException e){
                        System.out.println("Error al mostrar las multas actuales");
                    }
                    break;
                // Se pide la fecha en que se realizó un alquiler y la cédula del representante
                case "5":
//                    try{
//                        buscarAlquiler();
//                    }
//                    catch (IOException e){
//                        System.out.println("Error al buscar alquiler.");
//                    }
                    break;
                case "6":
                    System.out.println("Saliendo del sistema...");
                    return;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    // Funcion que pide datos para permitir registrar los alquileres
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

        System.out.print("Año de entrega (YYYY): ");
        String aEntrega = scanner.nextLine().trim();
        System.out.print("Mes de entrega (MM): ");
        String mesEntrega = scanner.nextLine().trim();
        System.out.print("Día de entrega (DD): ");
        String diaEntrega = scanner.nextLine().trim();
        System.out.print("Hora de entrega (HH): ");
        String horaEntrega = scanner.nextLine().trim();

        // Dependiendo del valor, se pone verdadero o falso
        if(sombrero.equalsIgnoreCase("s")){
            sombrero = "true";
        }
        else{
            sombrero = "false";
        }

        // Le manda al controlador todos los datos ya comprobados
        controlador.registrarAlquiler(responsableNombre, direccion, celular, cedula,
                estudianteNombre, grado, colegio, talla, cantidad, deposito, trajeClase, color, sombrero,
                aEntrega, mesEntrega, diaEntrega, horaEntrega);

        // Si no hay ningún error (IOException), es decir se guardó correctamente, se muestra el siguiente mensaje en pantalla
        System.out.println("Alquiler registrado exitosamente.");
    }

    // Muestra uno por uno cada dato dentro de la lista de arrays de String, es decir la información de cada alquiler
    public void mostrarAlquileres(String[] alquiler) {
        System.out.println("Alquileres aún no devueltos:\n");
        System.out.println("Responsable: " + alquiler[0]);
        System.out.println("Dirección: " + alquiler[1]);
        System.out.println("Celular: " + alquiler[2]);
        System.out.println("Cédula: " + alquiler[3]);
        System.out.println("Estudiante: " + alquiler[4]);
        System.out.println("Grado: " + alquiler[5]);
        System.out.println("Colegio: " + alquiler[6]);
        System.out.println("Talla: " + alquiler[7]);
        System.out.println("Traje: " + alquiler[8]);
        System.out.println("Color: " + alquiler[9]);
        System.out.println("Sombrero: " + alquiler[10]);
        System.out.println("Cantidad: " + alquiler[11]);
        System.out.println("Fecha Retiro: " + alquiler[12]);
        System.out.println("Fecha Entrega: " + alquiler[13]);
        System.out.println("Depósito: " + alquiler[14]);
        System.out.println("Cancelado: " + alquiler[15]);
    }

//    // Pide los datos de cedula y fecha de retiro
//    private void buscarAlquiler() throws IOException {
//        System.out.print("Ingrese la cédula del representante: ");
//        String cedulaRepresentante = scanner.nextLine().trim();
//
//        List<String[]> alquiler = controlador.buscarAlquiler(cedulaRepresentante);
//
//        mostrarAlquileres(alquiler);
//        String[] multados = controlador.verificarMultas(alquiler);
//        mostrarAlquileres(multados);
//        System.out.println("Multa: " + multados[16]);
//        System.out.print("Pago: " + alquiler[16]);
//        System.out.println("-----------------------------------");
//    }

    // Recibe los datos de cedula y fecha de retiro para pasarlas al controlador
    private void marcarComoPagado() {
        System.out.print("Ingrese la cédula del representante: ");
        String cedulaRepresentante = scanner.nextLine().trim();
        System.out.print("Año de retiro (YYYY): ");
        String aRetiro = scanner.nextLine().trim();
        System.out.print("Mes de retiro (MM): ");
        String mesRetiro = scanner.nextLine().trim();
        System.out.print("Día de retiro (DD): ");
        String diaRetiro = scanner.nextLine().trim();
        System.out.print("Hora de retiro (HH): ");
        String horaRetiro = scanner.nextLine().trim();

        // Se llama al controlador, y si se recibe un valor true salió correctamente en cambio
        // si es false no se encontró el alquiler, o una excepcion no se pudo abrir el archivo
        try {
            if (controlador.entregarAlquiler(cedulaRepresentante, aRetiro)) {
                System.out.println("El alquiler ha sido marcado como pagado.");
            } else {
                System.out.println("No se encontró el alquiler.");
            }
        } catch (IOException e) {
            System.out.println("Error al marcar el alquiler como pagado.");
        }
    }


}
