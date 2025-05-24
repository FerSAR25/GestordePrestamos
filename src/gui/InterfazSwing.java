package gui;

import com.github.lgooddatepicker.components.DateTimePicker;
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
    RoundedBorder btnBorder;

    public InterfazSwing(){
        // Configuración de la ventana
        setTitle("Gestor de Préstamos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1080, 720));       // Tamaño mínimo
        setLocationRelativeTo(null);                    // Centrar en pantalla


        btnBack = new JButton("Volver");
        btnFont = new Font("Segoe UI", Font.PLAIN, 18);
        btnSize = new Dimension(250, 70);
        btnBorder = new RoundedBorder(15);

        btnBack.setPreferredSize(btnSize);
        btnBack.setFont(btnFont);
        btnBack.setBorder(btnBorder);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(new Color(48, 48, 48));
        btnBack.setOpaque(true);

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
        principalPanel.setBackground(Color.WHITE);
        principalPanel.setBorder(padding);

        // Menú Principal
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(8, 1, 5, 5)); // Botones uno debajo del otro
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.setBackground(new Color(236, 236, 236));
        menuPanel.setBorder(BorderFactory.createCompoundBorder(borde, padding));

        ImageIcon icon = new ImageIcon("imagenes/trajes.jpg");
        labelImagen = new JLabel(icon);

        // Agregar layout al label para poner botones invisibles
        labelImagen.setLayout(new GridLayout(2, 4)); // 2 filas x 4 columnas

        // Agregar 8 botones invisibles sobre la imagen
        for (int i = 0; i < 8; i++) {
            JButton btnTraje = new JButton();
            btnTraje.setOpaque(false);
            btnTraje.setContentAreaFilled(false);
            btnTraje.setBorderPainted(false);
            btnTraje.setFocusPainted(false);

            int index = i; // Para usar dentro del lambda
            btnTraje.addActionListener(e -> registrarAlquiler(index));

            labelImagen.add(btnTraje);
        }

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
        subtitle.setBackground(new Color(227, 227, 227));
        subtitle.setOpaque(true);
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        menuPanel.add(subtitle);

        // Botones del menu principal
        btnRegistrar = new JButton("Registrar Alquiler");
        btnVer = new JButton("Alquileres Activos");
        btnSalir = new JButton("Salir");

        // Estilo de los botones
        btnRegistrar.setFont(btnFont);
        btnVer.setFont(btnFont);
        btnSalir.setFont(btnFont);

        // Ajustar tamaño de los botones
        btnRegistrar.setFocusable(false);
        btnVer.setFocusable(false);
        btnSalir.setFocusable(false);

        // Poner borde redondo
        btnRegistrar.setBorder(btnBorder);
        btnVer.setBorder(btnBorder);
        btnSalir.setBorder(btnBorder);

        // Que no se marquen seleccionados
        btnRegistrar.setPreferredSize(btnSize);
        btnVer.setPreferredSize(btnSize);
        btnSalir.setPreferredSize(btnSize);

        // Alinear botones al centro
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cambiar color de fondo
        btnRegistrar.setBackground(Color.WHITE);
        btnRegistrar.setOpaque(true);
        btnVer.setBackground(Color.WHITE);
        btnVer.setOpaque(true);
        btnSalir.setBackground(Color.WHITE);
        btnSalir.setOpaque(true);

        // Agregar las acciones a los botones
        btnRegistrar.addActionListener(e -> registrarAlquiler(-1));
        btnVer.addActionListener(e -> mostrarAlquileres());
        btnSalir.addActionListener(e -> System.exit(0));

        // Espaciado entre los botones
        menuPanel.add(Box.createVerticalStrut(20));  // Espacio antes del primer botón
        menuPanel.add(btnRegistrar);
        menuPanel.add(Box.createVerticalStrut(15));  // Espacio entre botones
        menuPanel.add(btnVer);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(btnSalir);

        menuPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        menuPanel.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        menuPanel.setPreferredSize(new Dimension(250, 0));


        principalPanel.add(menuPanel, BorderLayout.WEST);
        principalPanel.add(labelImagen); // Ya tiene layout y botones encima
        setContentPane(principalPanel);
        setVisible(true);
    }


    // Funcion que pide datos para permitir registrar los alquileres
    private void registrarAlquiler(int tipoTraje) {
        DateTimePicker dateTimePicker = new DateTimePicker();
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

        registroPanel.add(dateTimePicker);

        // Si ya tiene información se elimina
        if (registroPanel.getComponentCount() != 0) {
            registroPanel.removeAll();
        }

        // Panel que contendrá todos los campos
        JPanel contenidoPanel = new JPanel();
        contenidoPanel.setLayout(new GridLayout(8, 3, 10, 10));
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

        String claseTraje;
        switch(tipoTraje){
            case 0:
                claseTraje = "Brazileño";
                break;
            case 1:
                claseTraje = "Argentino";
                break;
            case 2:
                claseTraje = "Surinameño";
                break;
            case 3:
                claseTraje = "Paraguayo";
                break;
            case 4:
                claseTraje = "Boliviano";
                break;
            case 5:
                claseTraje = "Peruano";
                break;
            case 6:
                claseTraje = "Venezolano";
                break;
            case 7:
                claseTraje = "Colombiano";
                break;
            default:
                claseTraje = "Clase de Traje";
        }

        clase = new JTextField(claseTraje);
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

        registrar = new JButton("Registrar");
        registrar.setFont(btnFont);
        registrar.setPreferredSize(btnSize);

        // Agregar componentes al contenidoPanel
        contenidoPanel.add(nombreResponsable);
        contenidoPanel.add(direccion);
        contenidoPanel.add(celular);
        contenidoPanel.add(cedula);
        contenidoPanel.add(nombreEstudiante);
        contenidoPanel.add(colegio);
        contenidoPanel.add(curso);
        contenidoPanel.add(talla);
        contenidoPanel.add(clase);
        contenidoPanel.add(color);
        contenidoPanel.add(cantidad);
        contenidoPanel.add(sombrero);
        contenidoPanel.add(deposito);
        contenidoPanel.add(dateTimePicker);

        // Botón registrar
        registrar.setPreferredSize(btnSize);
        registrar.setFont(btnFont);
        registrar.setForeground(Color.WHITE);
        registrar.setBackground(new Color(45, 174, 223));
        registrar.setOpaque(true);
        registrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        registrar.setBorder(btnBorder);
        registrar.addActionListener(e -> {
            registro(
                    nombreResponsable.getText().trim(), direccion.getText().trim(),
                    celular.getText().trim(), cedula.getText().trim(),
                    nombreEstudiante.getText().trim(), curso.getText().trim(),
                    colegio.getText().trim(), talla.getText().trim(),
                    cantidad.getText().trim(), deposito.getText().trim(),
                    clase.getText().trim(), color.getText().trim(), sombrero.getText().trim(), dateTimePicker.getDateTimeStrict()
            );
        });
        // Ahora contenidoPanel lo metemos en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(contenidoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));

        // Finalmente el scrollPane se agrega dentro de registroPanel
        registroPanel.add(scrollPane, BorderLayout.CENTER);
        panelBotones.add(registrar);

        // Botón volver
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> resetMenu(registroPanel));
        registroPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        panelBotones.add(btnBack);

        registroPanel.add(panelBotones);

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
                         String trajeClase, String color, String sombrero, LocalDateTime fechaEntrega){
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

            if (fechaEntrega == null) {
                JOptionPane.showMessageDialog(null, "Por favor selecciona una fecha y hora válidas.");
                return;
            }

            // Registro
            controlador.registrarAlquiler(nombreResponsable, direccion, celular,
                    cedula, nombreEstudiante, grado, colegio, talla,
                    cantidad, deposito, trajeClase, color, sombrero, fechaEntrega
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
            setForeground(Color.BLACK);
            setBackground(new Color(226, 226, 226));
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
