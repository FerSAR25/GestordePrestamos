package gui;

import control.Controlador;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class InterfazSwing extends JFrame {
    Controlador controlador;
    JPanel menuPanel, registroPanel, mostrarPanel, pagoPanel, multasPanel, buscarPanel, resultadoPanel;
    JButton btnRegistrar, btnVer, btnMarcar, btnMultas, btnBuscar, btnSalir, registrar, btnBack, pagar, buscar;
    JTextField nombreResponsable, direccion, celular, cedula, nombreEstudiante, colegio, curso, talla,
            clase, color, cantidad, sombrero, deposito, año, mes, dia, hora;
    JTable mostrarTable, multasTable, resultadoTable;
    DefaultTableModel mostrarTableModel, multasTableModel, resultadoTableModel;
    Font btnFont;
    Dimension btnSize;
    boolean columnaDevueltoOculta1 = false;
    boolean columnaDevueltoOculta2 = false;

    public InterfazSwing(){
        // Configuración de la ventana
        setTitle("Gestor de Préstamos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 600));       // Tamaño mínimo
        setLocationRelativeTo(null);                    // Centrar en pantalla

        // Establecer Look and Feel moderno
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ignored) {}

        btnBack = new JButton("Volver");
        btnFont = new Font("Segoe UI", Font.PLAIN, 18);
        btnSize = new Dimension(250, 50);
    }

    public void vista(){
        try{
            this.controlador = new Controlador();
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error al iniciar el controlador.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        initGUI();
    }

    private void initGUI() {
        // Menú Principal con botones grandes y espaciados
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(8, 1, 5, 5)); // Botones uno debajo del otro
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título encabezado
        JLabel title = new JLabel("Gestor de Préstamos");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        menuPanel.add(title, BorderLayout.NORTH);

        // Botones del menu principal
        btnRegistrar = new JButton("Registrar Alquiler");
        btnVer = new JButton("Mostrar Alquileres");
        btnMarcar = new JButton("Pagar y Entregar");
        btnMultas = new JButton("Mostrar Multas");
        btnBuscar = new JButton("Buscar Alquileres");
        btnSalir = new JButton("Salir");

        // Estilo de los botones
        btnRegistrar.setFont(btnFont);
        btnVer.setFont(btnFont);
        btnMarcar.setFont(btnFont);
        btnMultas.setFont(btnFont);
        btnBuscar.setFont(btnFont);
        btnSalir.setFont(btnFont);

        // Ajustar tamaño de los botones
        btnRegistrar.setPreferredSize(btnSize);
        btnVer.setPreferredSize(btnSize);
        btnMarcar.setPreferredSize(btnSize);
        btnMultas.setPreferredSize(btnSize);
        btnBuscar.setPreferredSize(btnSize);
        btnSalir.setPreferredSize(btnSize);

        // Alinear botones al centro
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMarcar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMultas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBuscar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregar las acciones a los botones
        btnRegistrar.addActionListener(e -> registrarAlquiler());
        btnVer.addActionListener(e -> mostrarAlquileres());
        btnMarcar.addActionListener(e -> marcarComoPagado());
        btnMultas.addActionListener(e -> mostrarMultas());
        btnBuscar.addActionListener(e -> buscarAlquiler());
        btnSalir.addActionListener(e -> System.exit(0));

        // Espaciado entre los botones
        menuPanel.add(Box.createVerticalStrut(20));  // Espacio antes del primer botón
        menuPanel.add(btnRegistrar);
        menuPanel.add(Box.createVerticalStrut(15));  // Espacio entre botones
        menuPanel.add(btnVer);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(btnMarcar);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(btnMultas);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(btnBuscar);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(btnSalir);

        add(menuPanel);
        setVisible(true);
    }

    // Funcion que pide datos para permitir registrar los alquileres
    private void registrarAlquiler() {
        // Crear el panel principal de registro
        registroPanel = new JPanel();
        registroPanel.setLayout(new BorderLayout()); // Cambia a BorderLayout
        registroPanel.setBackground(Color.WHITE);
        registroPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Si ya tiene información se elimina
        if (registroPanel.getComponentCount() != 0) {
            registroPanel.removeAll();
        }

        // Se esconde el panel principal
        menuPanel.setVisible(false);

        // Panel que contendrá todos los campos
        JPanel contenidoPanel = new JPanel();
        contenidoPanel.setLayout(new BoxLayout(contenidoPanel, BoxLayout.Y_AXIS));
        contenidoPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Registrar Alquiler");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        registroPanel.add(title);

        // Campos
        nombreResponsable = new JTextField("Nombre del Responsable");
        nombreResponsable.setFont(btnFont);
        nombreResponsable.setPreferredSize(btnSize);
        direccion = new JTextField("Dirección del Responsable");
        direccion.setFont(btnFont);
        direccion.setPreferredSize(btnSize);
        celular = new JTextField("Celular del Responsable");
        celular.setFont(btnFont);
        celular.setPreferredSize(btnSize);
        cedula = new JTextField("Cédula del Responsable");
        cedula.setFont(btnFont);
        cedula.setPreferredSize(btnSize);

        nombreEstudiante = new JTextField("Nombre del Estudiante");
        nombreEstudiante.setFont(btnFont);
        nombreEstudiante.setPreferredSize(btnSize);
        colegio = new JTextField("Colegio del Estudiante");
        colegio.setFont(btnFont);
        colegio.setPreferredSize(btnSize);
        curso = new JTextField("Curso del Estudiante");
        curso.setFont(btnFont);
        curso.setPreferredSize(btnSize);
        talla = new JTextField("Talla del Estudiante");
        talla.setFont(btnFont);
        talla.setPreferredSize(btnSize);

        clase = new JTextField("Clase de Traje");
        clase.setFont(btnFont);
        clase.setPreferredSize(btnSize);
        color = new JTextField("Color del Traje");
        color.setFont(btnFont);
        color.setPreferredSize(btnSize);
        cantidad = new JTextField("Cantidad de Trajes");
        cantidad.setFont(btnFont);
        cantidad.setPreferredSize(btnSize);
        sombrero = new JTextField("Sombrero (S/N)");
        sombrero.setFont(btnFont);
        sombrero.setPreferredSize(btnSize);
        deposito = new JTextField("Depósito");
        deposito.setFont(btnFont);
        deposito.setPreferredSize(btnSize);

        año = new JTextField("Año de Entrega");
        año.setFont(btnFont);
        año.setPreferredSize(btnSize);
        mes = new JTextField("Mes de Entrega");
        mes.setFont(btnFont);
        mes.setPreferredSize(btnSize);
        dia = new JTextField("Día de Entrega");
        dia.setFont(btnFont);
        dia.setPreferredSize(btnSize);
        hora = new JTextField("Hora de Entrega");
        hora.setFont(btnFont);
        hora.setPreferredSize(btnSize);

        registrar = new JButton("Registrar");
        registrar.setFont(btnFont);
        registrar.setPreferredSize(btnSize);

        // Agregar componentes al contenidoPanel
        contenidoPanel.add(Box.createVerticalStrut(20));
        contenidoPanel.add(nombreResponsable);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(direccion);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(celular);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(cedula);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(nombreEstudiante);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(colegio);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(curso);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(talla);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(clase);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(color);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(cantidad);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(sombrero);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(deposito);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(año);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(mes);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(dia);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(hora);
        contenidoPanel.add(Box.createVerticalStrut(15));

        // Botón registrar
        registrar.setMaximumSize(new Dimension(400, 50));
        registrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        registrar.addActionListener(e -> {
            registro(
                    nombreResponsable.getText().trim(), direccion.getText().trim(),
                    celular.getText().trim(), cedula.getText().trim(),
                    nombreEstudiante.getText().trim(), curso.getText().trim(),
                    colegio.getText().trim(), talla.getText().trim(),
                    cantidad.getText().trim(), deposito.getText().trim(),
                    clase.getText().trim(), color.getText().trim(), sombrero.getText().trim(),
                    año.getText().trim(), mes.getText().trim(), dia.getText().trim(), hora.getText().trim()
            );
        });
        contenidoPanel.add(registrar);

        // Botón volver
        btnBack.setMaximumSize(new Dimension(400, 50));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> resetMenu(registroPanel));
        contenidoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contenidoPanel.add(btnBack);

        // Ahora contenidoPanel lo metemos en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(contenidoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Finalmente el scrollPane se agrega dentro de registroPanel
        registroPanel.add(scrollPane, BorderLayout.CENTER);

        // Mostramos
        registroPanel.setVisible(true);
        add(registroPanel);
        revalidate();
        repaint();
    }

    public void registro(String nombreResponsable, String direccion, String celular, String cedula,
                         String nombreEstudiante, String grado, String colegio, String talla,
                         String cantidad, String deposito,
                         String trajeClase, String color, String sombrero,
                         String año, String mes, String dia, String hora) {
        try {
            if (sombrero.trim().equalsIgnoreCase("s")) {
                sombrero = "true";
            } else {
                sombrero = "false";
            }

            // Validaciones
            validarCampo(nombreResponsable, "Nombre del Responsable");
            validarCampo(direccion, "Dirección del Responsable");
            validarCampo(celular, "Celular del Responsable");
            validarCampo(cedula, "Cédula del Responsable");

            validarCampo(nombreEstudiante, "Nombre del Estudiante");
            validarCampo(colegio, "Colegio del Estudiante");
            validarCampo(grado, "Curso del Estudiante");
            validarCampo(talla, "Talla del Estudiante");

            validarCampo(trajeClase, "Clase de Traje");
            validarCampo(color, "Color del Traje");
            validarCampo(cantidad, "Cantidad de Trajes");
            validarCampo(sombrero, "Sombrero (S/N)");
            validarCampo(deposito, "Depósito");

            validarCampo(año, "Año de Entrega");
            validarCampo(mes, "Mes de Entrega");
            validarCampo(dia, "Día de Entrega");
            validarCampo(hora, "Hora de Entrega");

            // Registro
            controlador.registrarAlquiler(nombreResponsable, direccion, celular,
                    cedula, nombreEstudiante, grado, colegio, talla,
                    cantidad, deposito, trajeClase, color, sombrero,
                    año, mes, dia, hora
            );
            JOptionPane.showMessageDialog(this, "Alquiler registrado exitosamente.");

            // Cuando todas las cosas esten correctas, se vuelve al menú principal
            resetMenu(registroPanel);

        } catch (IOException ex) {
            // Si falta algo, solo mostrar error
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarAlquileres() {
        menuPanel.setVisible(false);

        // Crear el panel de mostrar alquileres
        mostrarPanel = new JPanel();
        mostrarPanel.setLayout(new BoxLayout(mostrarPanel, BoxLayout.Y_AXIS)); // Botones uno debajo del otro
        mostrarPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mostrarPanel.setBackground(Color.WHITE);
        mostrarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título encabezado
        JLabel title = new JLabel("Mostrar Alquileres Activos");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mostrarPanel.add(title);

        if (mostrarTableModel == null) {
            String[] cols = {"Responsable", "Dirección", "Celular", "Cédula", "Estudiante", "Grado", "Colegio", "Talla",
                    "Traje", "Color", "Sombrero", "Cantidad", "Retiro", "Entrega", "Depósito", "Cancelado", "Devuelto"};
            mostrarTableModel = new DefaultTableModel(cols, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            mostrarTable = new JTable(mostrarTableModel);
            mostrarTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            mostrarTable.setFont(btnFont);
        } else {
            mostrarTableModel.setRowCount(0);
        }

        // Colocar información dentro de la tabla
        try {
            // Recibe la lista de los alquileres desde el controlador
            List<String[]> alquileres = controlador.obtenerAlquileresActivos();
            if (alquileres.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay alquileres registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            for (String[] a : alquileres) {
                mostrarTableModel.addRow(a);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los alquileres.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Ocultar columna "Devuelto" (índice 16)
        if (!columnaDevueltoOculta1) {
            TableColumnModel columnModel = mostrarTable.getColumnModel();
            TableColumn columnaOculta = columnModel.getColumn(16);
            columnModel.removeColumn(columnaOculta);
            columnaDevueltoOculta1 = true;
        }

        ajustarAnchoColumnas(mostrarTable);

        // Agrega la tabla al nuevo panel
        JScrollPane scroll = new JScrollPane(mostrarTable);
        mostrarPanel.add(scroll);

        // Botón de volver
        btnBack.addActionListener(e -> resetMenu(mostrarPanel));
        btnBack.setFont(btnFont);
        btnBack.setPreferredSize(btnSize);
        mostrarPanel.add(btnBack);

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(mostrarPanel);

        // Recargar el modelo de la interfaz
        revalidate();
        repaint();
    }

    // Recibe los datos de cedula y fecha de retiro para pasarlas al controlador
    private void marcarComoPagado() {
        // Crear el panel principal de pagar alquileres
        pagoPanel = new JPanel();
        pagoPanel.setLayout(new BorderLayout());

        // Si ya tiene información se elimina
        if (pagoPanel.getComponentCount() != 0) {
            pagoPanel.removeAll();
        }

        pagoPanel.setBackground(Color.WHITE);
        pagoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título encabezado
        JLabel title = new JLabel("Pagar Y Entregar Alquiler");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        pagoPanel.add(title, BorderLayout.NORTH); // Se agrega en la parte superior

        // Se esconde el panel principal
        menuPanel.setVisible(false);

        // Crear un panel interno con BoxLayout para apilar los componentes verticalmente
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Layout vertical
        contentPanel.setBackground(Color.WHITE);

        // Crea los campos de texto
        cedula = new JTextField("Cédula del Responsable");
        cedula.setFont(btnFont);
        cedula.setPreferredSize(btnSize);
        contentPanel.add(cedula);
        contentPanel.add(Box.createVerticalStrut(15));

        año = new JTextField("Año de Retiro");
        año.setFont(btnFont);
        año.setPreferredSize(btnSize);
        contentPanel.add(año);
        contentPanel.add(Box.createVerticalStrut(15));

        mes = new JTextField("Mes de Retiro");
        mes.setFont(btnFont);
        mes.setPreferredSize(btnSize);
        contentPanel.add(mes);
        contentPanel.add(Box.createVerticalStrut(15));

        dia = new JTextField("Día de Retiro");
        dia.setFont(btnFont);
        dia.setPreferredSize(btnSize);
        contentPanel.add(dia);
        contentPanel.add(Box.createVerticalStrut(15));

        hora = new JTextField("Hora de Retiro");
        hora.setFont(btnFont);
        hora.setPreferredSize(btnSize);
        contentPanel.add(hora);
        contentPanel.add(Box.createVerticalStrut(15));

        // Crear el JScrollPane para hacer scroll si el contenido es grande
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // Barra de desplazamiento siempre visible

        // Agregar el JScrollPane al centro del panel principal
        pagoPanel.add(scrollPane, BorderLayout.CENTER);

        // Crear el panel inferior para los botones
        JPanel botonPanel = new JPanel();
        botonPanel.setLayout(new BoxLayout(botonPanel, BoxLayout.Y_AXIS));
        botonPanel.setBackground(Color.WHITE);

        // Botón para pagar
        pagar = new JButton("Pagar y Entregar");
        pagar.setFont(btnFont);
        pagar.setPreferredSize(btnSize);
        pagar.setAlignmentX(Component.CENTER_ALIGNMENT);
        pagar.addActionListener(e -> {
            marcarPago(
                    cedula.getText().trim(), año.getText().trim(), mes.getText().trim(),
                    dia.getText().trim(), hora.getText().trim()
            );
        });
        botonPanel.add(Box.createVerticalStrut(5));
        botonPanel.add(pagar);
        botonPanel.add(Box.createVerticalStrut(15)); // Espacio

        // Botón de regresar
        btnBack.setFont(btnFont);
        btnBack.setPreferredSize(btnSize);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> resetMenu(pagoPanel));
        botonPanel.add(btnBack);

        // Agregar el panel de botones en la parte inferior del panel principal
        pagoPanel.add(botonPanel, BorderLayout.SOUTH);

        // Mostrar el panel
        pagoPanel.setVisible(true);
        add(pagoPanel);
        revalidate();
        repaint();
    }

    private void marcarPago(String cedula, String año, String mes, String dia, String hora){
        try {
            // Validaciones
            validarCampo(cedula, "Cédula del Responsable");

            validarCampo(año, "Año de Retiro");
            validarCampo(mes, "Mes de Retiro");
            validarCampo(dia, "Día de Retiro");
            validarCampo(hora, "Hora de Retiro");

            // Registro
            if(controlador.marcarComoPagado(cedula, año, mes, dia, hora)){
                JOptionPane.showMessageDialog(this, "Alquiler fue pagado y entregado exitosamente.");
            }
            else{
                throw new IOException("No se encontró el alquiler");
            }

            // Cuando todas las cosas esten correctas, se vuelve al menú principal
            resetMenu(pagoPanel);

        } catch (Exception ex) {
            // Si falta algo, solo mostrar error
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarMultas() {
        menuPanel.setVisible(false);

        // Crear el panel de mostrar alquileres
        multasPanel = new JPanel();
        multasPanel.setLayout(new BoxLayout(multasPanel, BoxLayout.Y_AXIS)); // Botones uno debajo del otro
        multasPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        multasPanel.setBackground(Color.WHITE);
        multasPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título encabezado
        JLabel title = new JLabel("Mostrar Multas");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        multasPanel.add(title);

        if (multasTableModel == null) {
            String[] cols = {"Responsable", "Dirección", "Celular", "Cédula", "Estudiante", "Grado", "Colegio", "Talla",
                    "Traje", "Color", "Sombrero", "Cantidad", "Retiro", "Entrega", "Depósito", "Cancelado", "Devuelto", "Multa"};
            multasTableModel = new DefaultTableModel(cols, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            multasTable = new JTable(multasTableModel);
            multasTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            multasTable.setFont(btnFont);
        } else {
            multasTableModel.setRowCount(0);
        }

        // Colocar información dentro de la tabla
        try {
            List<String[]> alquileres = controlador.obtenerAlquileresActivos();
            if (alquileres.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay alquileres registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

            String[] alquilerMultado;
            for(String[] alquiler: alquileres) {
                 alquilerMultado = controlador.verificarMultas(alquiler);
                 if(!alquilerMultado[alquiler.length].equals("0")){
                     multasTableModel.addRow(alquilerMultado);
                 }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los alquileres.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Ocultar columna "Devuelto" (índice 16)
        if (!columnaDevueltoOculta2) {
            TableColumnModel columnModel = multasTable.getColumnModel();
            TableColumn columnaOculta = columnModel.getColumn(16);
            columnModel.removeColumn(columnaOculta);
            columnaDevueltoOculta2 = true;
        }

        ajustarAnchoColumnas(multasTable);

        // Agrega la tabla al nuevo panel
        JScrollPane scroll = new JScrollPane(multasTable);
        multasPanel.add(scroll);

        // Botón de volver
        btnBack.addActionListener(e -> resetMenu(multasPanel));
        btnBack.setFont(btnFont);
        btnBack.setPreferredSize(btnSize);
        multasPanel.add(btnBack);

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(multasPanel);

        // Recargar el modelo de la interfaz
        revalidate();
        repaint();
    }

    private void buscarAlquiler() {
        buscarPanel = new JPanel(new BorderLayout());
        buscarPanel.setBackground(Color.WHITE);
        buscarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (buscarPanel.getComponentCount() != 0) {
            buscarPanel.removeAll();
        }

        JLabel title = new JLabel("Buscar Alquiler(es)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        buscarPanel.add(title, BorderLayout.NORTH);

        menuPanel.setVisible(false);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // Campo de texto
        cedula = new JTextField("Cédula del Responsable");
        cedula.setFont(btnFont);
        cedula.setMaximumSize(new Dimension(400, 40));
        cedula.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(cedula);
        contentPanel.add(Box.createVerticalStrut(15));

        // Botón Buscar
        buscar = new JButton("Buscar");
        buscar.setFont(btnFont);
        buscar.setMaximumSize(new Dimension(400, 50));
        buscar.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(buscar);
        contentPanel.add(Box.createVerticalStrut(15));

        // Botón Volver
        btnBack.setFont(btnFont);
        btnBack.setMaximumSize(new Dimension(400, 50));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> resetMenu(buscarPanel));
        contentPanel.add(btnBack);

        buscarPanel.add(contentPanel, BorderLayout.CENTER);

        buscar.addActionListener(e -> resultadoBusqueda(cedula.getText()));

        add(buscarPanel);

        // Recargar el modelo de la interfaz
        revalidate();
        repaint();
    }

    public void resultadoBusqueda(String cedula) {
        try {
            validarCampo(cedula, "Cédula del Responsable");
            buscarPanel.setVisible(false);

            // Crear el panel de mostrar alquileres
            resultadoPanel = new JPanel();
            resultadoPanel.setLayout(new BoxLayout(resultadoPanel, BoxLayout.Y_AXIS)); // Botones uno debajo del otro
            resultadoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            resultadoPanel.setBackground(Color.WHITE);
            resultadoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Título encabezado
            JLabel title = new JLabel("Alquiler(es) Encontrado(s)");
            title.setFont(new Font("Segoe UI", Font.BOLD, 32));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
            resultadoPanel.add(title);

            if (resultadoTableModel == null) {
                String[] cols = {"Responsable", "Dirección", "Celular", "Cédula", "Estudiante", "Grado", "Colegio", "Talla",
                        "Traje", "Color", "Sombrero", "Cantidad", "Retiro", "Entrega", "Depósito", "Cancelado", "Devuelto", "Multa"};
                resultadoTableModel = new DefaultTableModel(cols, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                resultadoTable = new JTable(resultadoTableModel);
                resultadoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                resultadoTable.setFont(btnFont);
            } else {
                resultadoTableModel.setRowCount(0);
            }

            // Colocar información dentro de la tabla
            try {
                // Recibe la lista de los alquileres desde el controlador
                List<String[]> alquileres = controlador.buscarAlquiler(cedula);
                if (alquileres.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay alquileres registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
                for (String[] a : alquileres) {
                    if(a[16].equalsIgnoreCase("Si")){
                        String[] nuevoAlquiler = Arrays.copyOf(a, a.length + 1);
                        nuevoAlquiler[a.length] = "0";
                        resultadoTableModel.addRow(nuevoAlquiler);
                    }
                    else{
                        resultadoTableModel.addRow(controlador.verificarMultas(a));
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar los alquileres.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            ajustarAnchoColumnas(resultadoTable);

            // Agrega la tabla al nuevo panel
            JScrollPane scroll = new JScrollPane(resultadoTable);
            resultadoPanel.add(scroll);

            // Botón de volver
            btnBack.addActionListener(e -> resetMenu(resultadoPanel));
            btnBack.setFont(btnFont);
            btnBack.setPreferredSize(btnSize);
            resultadoPanel.add(btnBack);

            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(resultadoPanel);

            // Recargar el modelo de la interfaz
            revalidate();
            repaint();
        }
        catch (IOException ex) {
            // Si falta algo, solo mostrar error
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ajustarAnchoColumnas(JTable tabla) {
        for (int columna = 0; columna < tabla.getColumnCount(); columna++) {
            TableColumn tableColumn = tabla.getColumnModel().getColumn(columna);
            int anchoMax = 75; // ancho mínimo

            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
                TableCellRenderer renderer = tabla.getCellRenderer(fila, columna);
                Component comp = tabla.prepareRenderer(renderer, fila, columna);
                anchoMax = Math.max(comp.getPreferredSize().width + 10, anchoMax);
            }

            tableColumn.setPreferredWidth(anchoMax);
        }
    }

    private void resetMenu(JPanel panel){
        // Elimina el panel actual antes de mostrar el menú
        panel.setVisible(false);
        remove(panel); // Remueve el panel actual del contenedor.
        menuPanel.setVisible(true);
        add(menuPanel); // Vuelve a agregar el menú.
        revalidate();  // Actualiza el diseño.
        repaint();     // Redibuja el frame.
    }

    // Verifica que el usuario haya ingresado un dato, y no esté el default
    private void validarCampo(String valor, String textoGuia) throws IOException {
        if (valor.isEmpty() || valor.equals(textoGuia)) {
            throw new IOException("Debe ingresar un dato para: " + textoGuia);
        }
    }
}
