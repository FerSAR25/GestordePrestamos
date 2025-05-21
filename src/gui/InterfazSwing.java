package gui;

import control.Controlador;
import model.Alquiler;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InterfazSwing extends JFrame {
    Controlador controlador;
    JPanel principalPanel, menuPanel, registroPanel, mostrarPanel;
    JButton btnRegistrar, btnVer, btnSalir, registrar, btnBack;
    JTextField nombreResponsable, direccion, celular, cedula, nombreEstudiante, colegio, curso, talla,
            clase, color, cantidad, sombrero, deposito, year, month, day, hour;
    JTable mostrarTable;
    DefaultTableModel mostrarTableModel;
    Font btnFont;
    Dimension btnSize;
    JLabel labelImagen;
    Border borde;
    Border padding;
    ImageIcon logo;

    public InterfazSwing(){
        // Configuración de la ventana
        setTitle("Gestor de Préstamos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1080, 720));       // Tamaño mínimo
        setLocationRelativeTo(null);                    // Centrar en pantalla

        // Establecer Look and Feel moderno
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ignored) {}

        btnBack = new JButton("Volver");
        btnFont = new Font("Segoe UI", Font.PLAIN, 18);
        btnSize = new Dimension(250, 70);

        btnBack.setPreferredSize(btnSize);
        btnBack.setFont(btnFont);

        borde = BorderFactory.createLineBorder(Color.BLACK);
        padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);

        logo = new ImageIcon("imagenes/trajes.jpg");

        this.setIconImage(logo.getImage());
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
        principalPanel = new JPanel();
        principalPanel.setLayout(new BorderLayout());
        principalPanel.setBackground(Color.LIGHT_GRAY);
        principalPanel.setBorder(padding);

        // Menú Principal
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(8, 1, 5, 5)); // Botones uno debajo del otro
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createCompoundBorder(borde, padding));

        ImageIcon icon = new ImageIcon("imagenes/trajes.jpg");
        labelImagen = new JLabel(icon);

        // Título encabezado
        JLabel title = new JLabel("Gestor de Préstamos");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        principalPanel.add(title, BorderLayout.NORTH);

        // Título menu
        JLabel subtitle = new JLabel("Menu");
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        menuPanel.add(title, BorderLayout.NORTH);

        // Botones del menu principal
        btnRegistrar = new JButton("Registrar Alquiler");
        btnVer = new JButton("Alquileres Activos");
        btnSalir = new JButton("Salir");

        // Estilo de los botones
        btnRegistrar.setFont(btnFont);
        btnVer.setFont(btnFont);
        btnSalir.setFont(btnFont);

        // Ajustar tamaño de los botones
        btnRegistrar.setPreferredSize(btnSize);
        btnVer.setPreferredSize(btnSize);
        btnSalir.setPreferredSize(btnSize);

        // Alinear botones al centro
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregar las acciones a los botones
        btnRegistrar.addActionListener(e -> registrarAlquiler());
        btnVer.addActionListener(e -> mostrarAlquileres());
        btnSalir.addActionListener(e -> System.exit(0));

        // Espaciado entre los botones
        menuPanel.add(Box.createVerticalStrut(20));  // Espacio antes del primer botón
        menuPanel.add(btnRegistrar);
        menuPanel.add(Box.createVerticalStrut(15));  // Espacio entre botones
        menuPanel.add(btnVer);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(btnSalir);

        principalPanel.add(menuPanel, BorderLayout.WEST);
        principalPanel.add(labelImagen);
        setContentPane(principalPanel);
        setVisible(true);
    }

    // Funcion que pide datos para permitir registrar los alquileres
    private void registrarAlquiler() {
        if(mostrarPanel != null && mostrarPanel.isVisible()){
            resetMenu(mostrarPanel);
        }
        if(registroPanel != null && registroPanel.isVisible()){
            resetMenu(registroPanel);
            return;
        }
        if(labelImagen.isVisible()){
            labelImagen.setVisible(false);
        }
        // Crear el panel principal de registro
        registroPanel = new JPanel();
        registroPanel.setLayout(new BoxLayout(registroPanel, BoxLayout.Y_AXIS));
        registroPanel.setBackground(Color.WHITE);
        registroPanel.setBorder(BorderFactory.createCompoundBorder(borde, padding));

        // Si ya tiene información se elimina
        if (registroPanel.getComponentCount() != 0) {
            registroPanel.removeAll();
        }

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

        year = new JTextField("Año de Entrega");
        year.setFont(btnFont);
        year.setPreferredSize(btnSize);
        month = new JTextField("Mes de Entrega");
        month.setFont(btnFont);
        month.setPreferredSize(btnSize);
        day = new JTextField("Día de Entrega");
        day.setFont(btnFont);
        day.setPreferredSize(btnSize);
        hour = new JTextField("Hora de Entrega");
        hour.setFont(btnFont);
        hour.setPreferredSize(btnSize);

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
        contenidoPanel.add(year);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(month);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(day);
        contenidoPanel.add(Box.createVerticalStrut(15));
        contenidoPanel.add(hour);
        contenidoPanel.add(Box.createVerticalStrut(15));

        // Botón registrar
        registrar.setPreferredSize(btnSize);
        registrar.setFont(btnFont);
        registrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        registrar.addActionListener(e -> {
            registro(
                    nombreResponsable.getText().trim(), direccion.getText().trim(),
                    celular.getText().trim(), cedula.getText().trim(),
                    nombreEstudiante.getText().trim(), curso.getText().trim(),
                    colegio.getText().trim(), talla.getText().trim(),
                    cantidad.getText().trim(), deposito.getText().trim(),
                    clase.getText().trim(), color.getText().trim(), sombrero.getText().trim(),
                    year.getText().trim(), month.getText().trim(), day.getText().trim(), hour.getText().trim()
            );
        });
        // Ahora contenidoPanel lo metemos en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(contenidoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Finalmente el scrollPane se agrega dentro de registroPanel
        registroPanel.add(scrollPane, BorderLayout.CENTER);
        registroPanel.add(registrar);

        // Botón volver
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> resetMenu(registroPanel));
        registroPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        registroPanel.add(btnBack);

        // Mostramos
        registroPanel.setVisible(true);
        add(registroPanel);
        principalPanel.add(registroPanel);
        revalidate();
        repaint();
    }

    public void registro(String nombreResponsable, String direccion, String celular, String cedula,
                         String nombreEstudiante, String grado, String colegio, String talla,
                         String cantidad, String deposito,
                         String trajeClase, String color, String sombrero,
                         String year, String month, String day, String hour) {
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

            validarCampo(year, "Año de Entrega");
            validarCampo(month, "Mes de Entrega");
            validarCampo(day, "Día de Entrega");
            validarCampo(hour, "Hora de Entrega");

            // Registro
            controlador.registrarAlquiler(nombreResponsable, direccion, celular,
                    cedula, nombreEstudiante, grado, colegio, talla,
                    cantidad, deposito, trajeClase, color, sombrero,
                    year, month, day, hour
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
        if (registroPanel != null && registroPanel.isVisible()) {
            resetMenu(registroPanel);
        }
        if(mostrarPanel != null && mostrarPanel.isVisible()){
            resetMenu(mostrarPanel);
            return;
        }
        if (labelImagen.isVisible()) {
            labelImagen.setVisible(false);
        }

        mostrarPanel = new JPanel();
        mostrarPanel.setLayout(new BoxLayout(mostrarPanel, BoxLayout.Y_AXIS));
        mostrarPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mostrarPanel.setBackground(Color.WHITE);
        mostrarPanel.setBorder(BorderFactory.createCompoundBorder(borde, padding));

        JLabel title = new JLabel("Mostrar Alquileres");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mostrarPanel.add(title);

        JCheckBox chkEntregados = new JCheckBox("Mostrar no entregados");
        chkEntregados.setFont(new Font("Arial", Font.PLAIN, 14));
        chkEntregados.setBackground(Color.WHITE);
        chkEntregados.setFocusable(false);

        JComboBox<String> comboFiltro = new JComboBox<>(new String[]{
                "Cédula", "Responsable", "Estudiante", "Fecha de Retiro", "Fecha de Entrega"
        });
        comboFiltro.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextField campoBusqueda = new JTextField(20);
        campoBusqueda.setFont(new Font("Arial", Font.PLAIN, 14));

        if (mostrarTableModel == null) {
            String[] cols = {"Responsable", "Dirección", "Celular", "Cédula", "Estudiante", "Colegio", "Grado", "Talla",
                    "Traje", "Color", "Sombrero", "Cantidad", "Retiro", "Entrega", "Depósito", "Multa", "Deuda", "Entregado", "Entregar"};
            mostrarTableModel = new DefaultTableModel(cols, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == getColumnCount() - 1;
                }
            };
            mostrarTable = new JTable(mostrarTableModel);
            mostrarTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            mostrarTable.setFont(new Font("Arial", Font.PLAIN, 13));

            mostrarTable.getColumn("Entregar").setCellRenderer(new ButtonRenderer());
            mostrarTable.getColumn("Entregar").setCellEditor(new ButtonEditor(new JCheckBox(), mostrarTable));
        } else {
            mostrarTableModel.setRowCount(0);
        }

        JScrollPane scrollTabla = new JScrollPane(mostrarTable);

        List<Alquiler> alquileres;
        try {
            alquileres = controlador.obtenerAlquileres();
            if(alquileres == null){
                throw new IOException("No hay alquileres registrados.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            resetMenu(mostrarPanel);
            return;
        }
        final List<Alquiler> finalAlquileres = alquileres;

        campoBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltros(finalAlquileres, campoBusqueda, comboFiltro, chkEntregados, mostrarTable);
            }

            public void removeUpdate(DocumentEvent e) {
                aplicarFiltros(finalAlquileres, campoBusqueda, comboFiltro, chkEntregados, mostrarTable);
            }

            public void changedUpdate(DocumentEvent e) {
                aplicarFiltros(finalAlquileres, campoBusqueda, comboFiltro, chkEntregados, mostrarTable);
            }
        });

        comboFiltro.addActionListener(e ->
                aplicarFiltros(finalAlquileres, campoBusqueda, comboFiltro, chkEntregados, mostrarTable)
        );

        chkEntregados.addActionListener(e ->
                aplicarFiltros(finalAlquileres, campoBusqueda, comboFiltro, chkEntregados, mostrarTable)
        );

        JPanel filtrosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtrosPanel.setBackground(Color.WHITE);
        filtrosPanel.add(new JLabel("Buscar por:"));
        filtrosPanel.add(comboFiltro);
        filtrosPanel.add(campoBusqueda);
        filtrosPanel.add(chkEntregados);

        mostrarPanel.add(filtrosPanel);
        mostrarPanel.add(scrollTabla);

        // Botón volver
        btnBack.addActionListener(e -> resetMenu(mostrarPanel));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        mostrarPanel.add(Box.createVerticalStrut(15));
        mostrarPanel.add(btnBack);

        principalPanel.add(mostrarPanel);


        // Cargar datos al iniciar
        aplicarFiltros(finalAlquileres, campoBusqueda, comboFiltro, chkEntregados, mostrarTable);

        revalidate();
        repaint();
    }

    private void aplicarFiltros(List<Alquiler> alquileres, JTextField campoBusqueda, JComboBox<String> comboFiltro,
                                JCheckBox chkNoEntregados, JTable mostrarTable) {

        String texto = campoBusqueda.getText().trim().toLowerCase();
        int indice = comboFiltro.getSelectedIndex();

        List<Alquiler> filtrados = new ArrayList<>();

        for (Alquiler alq : alquileres) {
            boolean coincide = false;

            if (texto.isEmpty()) {
                coincide = true;
            } else {
                String campo = switch (indice) {
                    case 0 -> String.valueOf(alq.getResponsable().getCedula()); // Cédula
                    case 1 -> alq.getResponsable().getNombre(); // Responsable
                    case 2 -> alq.getEstudiante().getNombre(); // Estudiante
                    case 3 -> alq.getFechaRetiro().toString(); // Retiro
                    case 4 -> alq.getFechaEntrega().toString(); // Entrega
                    default -> "";
                };

                if (campo != null && campo.toLowerCase().contains(texto)) {
                    coincide = true;
                }
            }
            if (coincide) {
                filtrados.add(alq);
            }
        }

        try {
            actualizarTabla(filtrados, chkNoEntregados.isSelected());
            ajustarAnchoColumnas(mostrarTable);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar los alquileres.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void entregar(Object cedula, Object fecha){
        try {
            // Registro
            if(controlador.entregarAlquiler(cedula, fecha)){
                JOptionPane.showMessageDialog(this, "Alquiler fue pagado y entregado exitosamente.");
                resetMenu(mostrarPanel);
            }
            else{
                throw new IOException("No se encontró el alquiler");
            }
        } catch (Exception ex) {
            // Si falta algo, solo mostrar error
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarTabla(List<Alquiler> alquileresFiltrados, boolean soloNoEntregados) throws IOException {
        mostrarTableModel.setRowCount(0); // Limpiar la tabla
//        botonesPanel.removeAll(); // Limpiar botones anteriores

        for (Alquiler alquiler : alquileresFiltrados) {
            boolean yaEntregado = alquiler.isEntregado();
            if (soloNoEntregados && yaEntregado) continue;
            Object[] fila = {alquiler.getResponsable().getNombre(), alquiler.getResponsable().getDireccion(),
                    alquiler.getResponsable().getCelular(), alquiler.getResponsable().getCedula(),
                    alquiler.getEstudiante().getNombre(), alquiler.getEstudiante().getColegio(),
                    alquiler.getEstudiante().getGrado(), alquiler.getEstudiante().getTalla(),
                    alquiler.getTraje().getClase(), alquiler.getTraje().getColor(),
                    (alquiler.getTraje().isSombrero() ? "Si": "No"), alquiler.getCantidad(),
                    alquiler.getFechaRetiro(), alquiler.getFechaEntrega(), alquiler.getDeposito(),
                    alquiler.getMulta(), alquiler.getDeuda(), (alquiler.isEntregado() ? "Si": "No")};
            mostrarTableModel.addRow(fila);
        }
//            JButton btnEntregar = new JButton("Entregar");
//            btnEntregar.setFont(new Font("Arial", Font.PLAIN, 12));
//            btnEntregar.setAlignmentX(Component.CENTER_ALIGNMENT);

//            final int rowIndex = mostrarTableModel.getRowCount() - 1;
//
//            if (yaEntregado){ btnEntregar.setEnabled(false);}
//
//            btnEntregar.addActionListener(e -> {
//                String entregado = ((String) mostrarTable.getValueAt(rowIndex, 16)).trim();
//                if (entregado.equalsIgnoreCase("Si")) {
//                    JOptionPane.showMessageDialog(null, "Este alquiler ya fue entregado.", "Aviso", JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//
//                String[] filaDatos = new String[mostrarTable.getColumnCount()];
//                for (int col = 0; col < filaDatos.length; col++) {
//                    filaDatos[col] = (String) mostrarTable.getValueAt(rowIndex, col);
//                }
//
//                marcarPago(filaDatos[3], filaDatos[12]); // Cédula y fecha de retiro
//                mostrarAlquileres(); // Volver a recargar
//            });
//
//            botonesPanel.add(Box.createVerticalStrut(5));
//            botonesPanel.add(btnEntregar);
//        }

//        botonesPanel.revalidate();
//        botonesPanel.repaint();
    }

//    private void buscarAlquiler() {
//        buscarPanel = new JPanel(new BorderLayout());
//        buscarPanel.setBackground(Color.WHITE);
//        buscarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        if (buscarPanel.getComponentCount() != 0) {
//            buscarPanel.removeAll();
//        }
//
//        JLabel title = new JLabel("Buscar Alquiler(es)");
//        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
//        title.setHorizontalAlignment(SwingConstants.CENTER);
//        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
//        buscarPanel.add(title, BorderLayout.NORTH);
//
//        menuPanel.setVisible(false);
//
//        JPanel contentPanel = new JPanel();
//        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
//        contentPanel.setBackground(Color.WHITE);
//
//        // Campo de texto
//        cedula = new JTextField("Cédula del Responsable");
//        cedula.setFont(btnFont);
//        cedula.setMaximumSize(new Dimension(400, 40));
//        cedula.setAlignmentX(Component.CENTER_ALIGNMENT);
//        contentPanel.add(cedula);
//        contentPanel.add(Box.createVerticalStrut(15));
//
//        // Botón Buscar
//        buscar = new JButton("Buscar");
//        buscar.setFont(btnFont);
//        buscar.setMaximumSize(new Dimension(400, 50));
//        buscar.setAlignmentX(Component.CENTER_ALIGNMENT);
//        contentPanel.add(buscar);
//        contentPanel.add(Box.createVerticalStrut(15));
//
//        // Botón Volver
//        btnBack.setFont(btnFont);
//        btnBack.setMaximumSize(new Dimension(400, 50));
//        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
//        btnBack.addActionListener(e -> resetMenu(buscarPanel));
//        contentPanel.add(btnBack);
//
//        buscarPanel.add(contentPanel, BorderLayout.CENTER);
//
//        buscar.addActionListener(e -> resultadoBusqueda(cedula.getText()));
//
//        add(buscarPanel);
//
//        // Recargar el modelo de la interfaz
//        revalidate();
//        repaint();
//    }
//
//    public void resultadoBusqueda(String cedula) {
//        try {
//            validarCampo(cedula, "Cédula del Responsable");
//            buscarPanel.setVisible(false);
//
//            // Crear el panel de mostrar alquileres
//            resultadoPanel = new JPanel();
//            resultadoPanel.setLayout(new BoxLayout(resultadoPanel, BoxLayout.Y_AXIS)); // Botones uno debajo del otro
//            resultadoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//            resultadoPanel.setBackground(Color.WHITE);
//            resultadoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//            // Título encabezado
//            JLabel title = new JLabel("Alquiler(es) Encontrado(s)");
//            title.setFont(new Font("Segoe UI", Font.BOLD, 32));
//            title.setHorizontalAlignment(SwingConstants.CENTER);
//            title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
//            resultadoPanel.add(title);
//
//            if (resultadoTableModel == null) {
//                String[] cols = {"Responsable", "Dirección", "Celular", "Cédula", "Estudiante", "Grado", "Colegio", "Talla",
//                        "Traje", "Color", "Sombrero", "Cantidad", "Retiro", "Entrega", "Depósito", "Cancelado", "Devuelto", "Multa"};
//                resultadoTableModel = new DefaultTableModel(cols, 0) {
//                    @Override
//                    public boolean isCellEditable(int row, int column) {
//                        return false;
//                    }
//                };
//                resultadoTable = new JTable(resultadoTableModel);
//                resultadoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//                resultadoTable.setFont(btnFont);
//            } else {
//                resultadoTableModel.setRowCount(0);
//            }
//
//            // Colocar información dentro de la tabla
//            try {
//                // Recibe la lista de los alquileres desde el controlador
//                List<String[]> alquileres = controlador.buscarAlquiler(cedula);
//                if (alquileres.isEmpty()) {
//                    JOptionPane.showMessageDialog(this, "No hay alquileres registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
//                }
//                for (String[] a : alquileres) {
//                    if(a[16].equalsIgnoreCase("Si")){
//                        String[] nuevoAlquiler = Arrays.copyOf(a, a.length + 1);
//                        nuevoAlquiler[a.length] = "0";
//                        resultadoTableModel.addRow(nuevoAlquiler);
//                    }
//                    else{
//                        resultadoTableModel.addRow(controlador.verificarMultas(a));
//                    }
//                }
//            } catch (IOException ex) {
//                JOptionPane.showMessageDialog(this, "Error al cargar los alquileres.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//
//            ajustarAnchoColumnas(resultadoTable);
//
//            // Agrega la tabla al nuevo panel
//            JScrollPane scroll = new JScrollPane(resultadoTable);
//            resultadoPanel.add(scroll);
//
//            // Botón de volver
//            btnBack.addActionListener(e -> resetMenu(resultadoPanel));
//            btnBack.setFont(btnFont);
//            btnBack.setPreferredSize(btnSize);
//            resultadoPanel.add(btnBack);
//
//            title.setAlignmentX(Component.CENTER_ALIGNMENT);
//            scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
//            btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//            add(resultadoPanel);
//
//            // Recargar el modelo de la interfaz
//            revalidate();
//            repaint();
//        }
//        catch (IOException ex) {
//            // Si falta algo, solo mostrar error
//            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }

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
        tabla.setRowHeight(50);
    }

    private void resetMenu(JPanel panel){
        // Elimina el panel actual antes de mostrar el menú
        panel.setVisible(false);
        labelImagen.setVisible(true);
        revalidate();  // Actualiza el diseño.
        repaint();     // Redibuja el frame.
    }

    // Verifica que el usuario haya ingresado un dato, y no esté el default
    private void validarCampo(String valor, String textoGuia) throws IOException {
        if (valor.isEmpty() || valor.equals(textoGuia)) {
            throw new IOException("Debe ingresar un dato para: " + textoGuia);
        }
    }

    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Entregar" : value.toString());
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox, JTable tableRef) {
            super(checkBox);
            this.table = tableRef;
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {

            label = (value == null) ? "Entregar" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int rowIndex = table.getSelectedRow();

                String entregado = (String)table.getValueAt(rowIndex, 17);
                if (entregado.equalsIgnoreCase("Si")) {
                    JOptionPane.showMessageDialog(null, "Este alquiler ya fue entregado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    entregar(table.getValueAt(rowIndex, 3), table.getValueAt(rowIndex, 12));
                    mostrarAlquileres();
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

}
