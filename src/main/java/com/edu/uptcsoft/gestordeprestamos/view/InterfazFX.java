package com.edu.uptcsoft.gestordeprestamos.view;

import com.edu.uptcsoft.gestordeprestamos.controller.Controlador;

import com.edu.uptcsoft.gestordeprestamos.model.Alquiler;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InterfazFX extends Application {

    private Controlador controlador;
    private BorderPane principalPanel;
    private VBox menuVBox, registroVBox, mostrarVBox;
    private StackPane imagePane;
    private final Font btnFont = Font.font("Segoe UI", 18);
    private final double btnWidth = 500;
    private final double btnHeight = 140;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Gestor de Préstamos FX");
        stage.show();

        vista(stage);
    }

    public void vista(Stage stage) {
        try {
            this.controlador = new Controlador();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al iniciar el controlador.");
            alert.showAndWait();
            System.exit(1);
        }
        initGUI(stage);
    }

    private void initGUI(Stage stage) {
        principalPanel = new BorderPane();
        principalPanel.setStyle("-fx-background-color: #F9FAFB;");
        principalPanel.setPadding(new Insets(0));

        // Título principal
        Label title = new Label("Gestor de Préstamos");
        title.setFont(Font.font("Segoe UI", 32));
        title.setTextFill(Color.web("#2C3E50"));
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);
        title.setPadding(new Insets(30, 0, 20, 0));
        principalPanel.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        // Menú lateral estilizado
        menuVBox = new VBox(20);
        menuVBox.setPadding(new Insets(30));
        menuVBox.setAlignment(Pos.TOP_CENTER);
        menuVBox.setPrefWidth(260);
        menuVBox.setStyle("""
        -fx-background-color: linear-gradient(to bottom, #2C3E50, #34495E);
        -fx-border-width: 0 2px 0 0;
        -fx-border-color: #1A252F;
    """);

        Label subtitle = new Label("Menú");
        subtitle.setFont(Font.font("Segoe UI", 26));
        subtitle.setTextFill(Color.WHITE);
        subtitle.setAlignment(Pos.CENTER);
        subtitle.setMaxWidth(Double.MAX_VALUE);
        subtitle.setPadding(new Insets(10, 0, 20, 0));
        menuVBox.getChildren().add(subtitle);

        // Botones del menú
        Button btnRegistrar = crearBoton("📋 Registrar Alquiler");
        Button btnVer = crearBoton("📑 Ver Alquileres");
        Button btnSalir = crearBoton("⛔ Salir");

        List<Button> botones = List.of(btnRegistrar, btnVer, btnSalir);

        for (Button b : botones) {
            b.setCursor(Cursor.HAND);
            b.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-background-radius: 20;
            -fx-border-radius: 20;
            -fx-padding: 10 20;
        """);

            b.setOnMouseEntered(e -> b.setStyle("""
            -fx-background-color: #1ABC9C;
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-background-radius: 20;
            -fx-border-radius: 20;
            -fx-padding: 10 20;
        """));
            b.setOnMouseExited(e -> b.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 16px;
            -fx-background-radius: 20;
            -fx-border-radius: 20;
            -fx-padding: 10 20;
        """));
        }

        btnRegistrar.setOnAction(e -> registrarAlquiler(-1));
        btnVer.setOnAction(e -> mostrarAlquileres());
        btnSalir.setOnAction(e -> System.exit(0));

        menuVBox.getChildren().addAll(btnRegistrar, btnVer, btnSalir);
        principalPanel.setLeft(menuVBox);

        // Imagen de fondo con botones invisibles encima
        Image imagen = new Image("file:imagenes/trajes.jpg");
        ImageView backImage = new ImageView(imagen);

        GridPane overlay = new GridPane();
        overlay.setHgap(20);
        overlay.setVgap(20);
        overlay.setAlignment(Pos.CENTER);
        overlay.setPadding(new Insets(20));

        for (int col = 0; col < 4; col++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(25);
            overlay.getColumnConstraints().add(cc);
        }

        for (int row = 0; row < 2; row++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(50);
            overlay.getRowConstraints().add(rc);
        }

        for (int i = 0; i < 8; i++) {
            Button btn = new Button();
            btn.setOpacity(0); // invisible
            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            btn.setCursor(Cursor.HAND);

            GridPane.setHgrow(btn, Priority.ALWAYS);
            GridPane.setVgrow(btn, Priority.ALWAYS);
            int index = i;
            btn.setOnAction(e -> registrarAlquiler(index));
            overlay.add(btn, i % 4, i / 4);
        }

        imagePane = new StackPane(backImage, overlay);
        backImage.setPreserveRatio(true);
        backImage.setSmooth(true);
        backImage.setCache(true);

        // Ajuste dinámico de tamaño
        backImage.fitWidthProperty().bind(imagePane.widthProperty());
        backImage.fitHeightProperty().bind(imagePane.heightProperty());

        principalPanel.setCenter(imagePane);

        Scene scene = new Scene(principalPanel, 1080, 720);
        stage.setTitle("Gestor de Préstamos");
        stage.getIcons().add(imagen);
        stage.setScene(scene);
        stage.show();
    }


    private Button crearBoton(String texto) {
        Button btn = new Button(texto);
        btn.setFont(btnFont);
        btn.setPrefSize(btnWidth, btnHeight);
        btn.setStyle("""
        -fx-background-color: white;
        -fx-border-radius: 15;
        -fx-background-radius: 15;
    """);
        return btn;
    }

    public void registrarAlquiler(int tipoTraje) {
        if (mostrarVBox != null && mostrarVBox.isVisible()) resetMenu(mostrarVBox);
        if (registroVBox != null && registroVBox.isVisible()) {
            resetMenu(registroVBox);
            return;
        }
        if (imagePane.isVisible()) {
            imagePane.setVisible(false);
            principalPanel.setCenter(null);
        }

        registroVBox = new VBox(20);
        registroVBox.setPadding(new Insets(20));
        registroVBox.setStyle("""
        -fx-background-color: #f8f8f8;
        -fx-border-radius: 12;
        -fx-background-radius: 12;
    """);

        Label title = new Label("Registrar Alquiler");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setTextAlignment(TextAlignment.CENTER);

        // Campos
        TextField nombreResponsable = crearCampo("Nombre del Responsable");
        TextField direccion = crearCampo("Dirección del Responsable");
        TextField celular = crearCampo("Celular del Responsable");
        TextField cedula = crearCampo("Cédula del Responsable");

        TextField nombreEstudiante = crearCampo("Nombre del Estudiante");
        TextField colegio = crearCampo("Colegio del Estudiante");
        TextField curso = crearCampo("Curso del Estudiante");
        TextField talla = crearCampo("Talla del Estudiante");

        TextField color = crearCampo("Color del Traje");
        TextField cantidad = crearCampo("Cantidad de Trajes");
        TextField sombrero = crearCampo("Sombrero (S/N)");
        TextField deposito = crearCampo("Depósito");

        TextField clase = new TextField();
        clase.setPromptText("Clase de Traje");
        asignarClaseTraje(clase, tipoTraje);

        DatePicker datePicker = new DatePicker();
        Spinner<LocalTime> timeSpinner = getLocalTimeSpinner();
        timeSpinner.getEditor().setText(timeSpinner.getValue().format(DateTimeFormatter.ofPattern("HH:mm")));

        // Paneles organizados
        TitledPane responsablePane = crearSeccion("Datos del Responsable", new Control[][]{
                {new Label("Nombre:"), nombreResponsable},
                {new Label("Dirección:"), direccion},
                {new Label("Celular:"), celular},
                {new Label("Cédula:"), cedula}
        });

        TitledPane estudiantePane = crearSeccion("Datos del Estudiante", new Control[][]{
                {new Label("Nombre:"), nombreEstudiante},
                {new Label("Colegio:"), colegio},
                {new Label("Curso:"), curso},
                {new Label("Talla:"), talla}
        });

        TitledPane trajePane = crearSeccion("Datos del Traje y Entrega", new Control[][]{
                {new Label("Clase de Traje:"), clase},
                {new Label("Color:"), color},
                {new Label("Cantidad:"), cantidad},
                {new Label("Sombrero:"), sombrero},
                {new Label("Depósito:"), deposito},
                {new Label("Fecha de entrega:"), datePicker},
                {new Label("Hora de entrega:"), timeSpinner}
        });

        ScrollPane scrollPane = new ScrollPane(new VBox(15, responsablePane, estudiantePane, trajePane));
        scrollPane.setFitToWidth(true);

        // Botones
        Button registrar = crearBoton("Registrar");
        registrar.setStyle("-fx-background-color: #2DAEDF; -fx-text-fill: white; -fx-font-weight: bold;");
        registrar.setCursor(Cursor.HAND);

        Button btnBack = crearBoton("Volver");
        btnBack.setStyle("-fx-font-size: 14px; -fx-background-color: #CCCCCC; -fx-text-fill: white;");
        btnBack.setCursor(Cursor.HAND);

        registrar.setOnAction(e -> {
            LocalDate fecha = datePicker.getValue();
            LocalTime hora = timeSpinner.getValue();
            LocalDateTime fechaEntrega = (fecha != null && hora != null) ? LocalDateTime.of(fecha, hora) : null;

            registro(
                    nombreResponsable.getText().trim(), direccion.getText().trim(),
                    celular.getText().trim(), cedula.getText().trim(),
                    nombreEstudiante.getText().trim(), curso.getText().trim(),
                    colegio.getText().trim(), talla.getText().trim(),
                    cantidad.getText().trim(), deposito.getText().trim(),
                    clase.getText().trim(), color.getText().trim(), sombrero.getText().trim(),
                    fechaEntrega
            );
        });

        btnBack.setOnAction(e -> resetMenu(registroVBox));

        HBox botonesHBox = new HBox(40, registrar, btnBack);
        botonesHBox.setAlignment(Pos.CENTER);

        registroVBox.getChildren().addAll(title, scrollPane, botonesHBox);
        principalPanel.setCenter(registroVBox);
    }

    private TextField crearCampo(String prompt) {
        TextField campo = new TextField();
        campo.setPromptText(prompt);
        return campo;
    }

    private TitledPane crearSeccion(String titulo, Control[][] campos) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        for (int i = 0; i < campos.length; i++) {
            grid.addRow(i, campos[i]);
        }
        return new TitledPane(titulo, grid);
    }

    private void asignarClaseTraje(TextField clase, int tipoTraje) {
        String[] clases = {
                "Brazileño", "Argentino", "Surinameño", "Paraguayo",
                "Boliviano", "Peruano", "Venezolano", "Colombiano"
        };
        if (tipoTraje >= 0 && tipoTraje < clases.length) {
            clase.setText(clases[tipoTraje]);
        }
    }

    public void registro(String nombreResponsable, String direccion, String celular, String cedula,
                         String nombreEstudiante, String grado, String colegio, String talla,
                         String cantidad, String deposito,
                         String trajeClase, String color, String sombrero, LocalDateTime fechaEntrega) {
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
                mostrarAlerta(Alert.AlertType.WARNING, "Fecha inválida", "Por favor selecciona una fecha y hora válidas.");
                return;
            }

            // Registro
            controlador.registrarAlquiler(nombreResponsable, direccion, celular,
                    cedula, nombreEstudiante, grado, colegio, talla,
                    cantidad, deposito, trajeClase, color, sombrero, fechaEntrega
            );

            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "Alquiler registrado exitosamente.");

            resetMenu(registroVBox); // Vuelve al menú principal

        } catch (IOException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }
    }

    public void mostrarAlquileres() {
        if (registroVBox != null && registroVBox.isVisible()) {
            resetMenu(registroVBox);
        }
        if (mostrarVBox != null && mostrarVBox.isVisible()) {
            resetMenu(mostrarVBox);
            return;
        }

        if (imagePane.isVisible()) {
            imagePane.setVisible(false);
            principalPanel.setCenter(null);
        }

        List<Alquiler> alquileres = null;
        try {
            alquileres = controlador.obtenerAlquileres();
        } catch (IOException e) {
            showError(e.getMessage());
        }

        mostrarVBox = new VBox(10);
        mostrarVBox.setAlignment(Pos.CENTER);
        mostrarVBox.setPadding(new Insets(20));
        mostrarVBox.setStyle("-fx-background-color: white;");

        Label title = new Label("Mostrar Alquileres");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));

        CheckBox chkEntregados = new CheckBox("Mostrar no entregados");
        chkEntregados.setFont(Font.font("Arial", 14));

        ComboBox<String> comboFiltro = new ComboBox<>(FXCollections.observableArrayList(
                "Cédula", "Responsable", "Estudiante", "Fecha de Retiro", "Fecha de Entrega"
        ));
        comboFiltro.getSelectionModel().selectFirst();

        TextField campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar...");

        // Tabla
        TableView<Alquiler> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Columnas - Datos del Responsable
        TableColumn<Alquiler, String> colCedula = new TableColumn<>("Cédula Resp.");
        colCedula.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getResponsable().getCedula())));

        TableColumn<Alquiler, String> colNombreResp = new TableColumn<>("Nombre Resp.");
        colNombreResp.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getResponsable().getNombre()));

        TableColumn<Alquiler, String> colDireccion = new TableColumn<>("Dirección Resp.");
        colDireccion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getResponsable().getDireccion()));

        TableColumn<Alquiler, String> colCelular = new TableColumn<>("Celular Resp.");
        colCelular.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getResponsable().getCelular())));

        // Columnas - Datos del Estudiante
        TableColumn<Alquiler, String> colNombreEst = new TableColumn<>("Nombre Est.");
        colNombreEst.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstudiante().getNombre()));

        TableColumn<Alquiler, String> colGrado = new TableColumn<>("Grado");
        colGrado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstudiante().getGrado()));

        TableColumn<Alquiler, String> colColegio = new TableColumn<>("Colegio");
        colColegio.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstudiante().getColegio()));

        TableColumn<Alquiler, String> colTalla = new TableColumn<>("Talla");
        colTalla.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstudiante().getTalla()));

        // Columnas - Datos del Traje
        TableColumn<Alquiler, String> colClaseTraje = new TableColumn<>("Clase Traje");
        colClaseTraje.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTraje().getClase()));

        TableColumn<Alquiler, String> colColor = new TableColumn<>("Color");
        colColor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTraje().getColor()));

        TableColumn<Alquiler, String> colSombrero = new TableColumn<>("Sombrero");
        colSombrero.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTraje().isSombrero() ? "Sí" : "No"));

        TableColumn<Alquiler, String> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCantidad())));

        // Columnas - Fechas y estado
        TableColumn<Alquiler, String> colFechaRetiro = new TableColumn<>("Fecha Retiro");
        colFechaRetiro.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFechaRetiro().toString()));

        TableColumn<Alquiler, String> colFechaEntrega = new TableColumn<>("Fecha Entrega");
        colFechaEntrega.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFechaEntrega().toString()));

        TableColumn<Alquiler, String> colEntregado = new TableColumn<>("Entregado");
        colEntregado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isEntregado() ? "Sí" : "No"));

        // Columnas - Dinero
        TableColumn<Alquiler, String> colDeposito = new TableColumn<>("Depósito");
        colDeposito.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDeposito())));

        TableColumn<Alquiler, String> colMulta = new TableColumn<>("Multa");
        colMulta.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMulta())));

        TableColumn<Alquiler, String> colDeuda = new TableColumn<>("Deuda");
        colDeuda.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDeuda())));

        // Columna de Acción
        TableColumn<Alquiler, Void> colEntregar = new TableColumn<>("Acción");
        List<Alquiler> finalAlquileres = alquileres;
        colEntregar.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Entregar");

            {
                btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    Alquiler alquiler = getTableView().getItems().get(getIndex());

                    if (alquiler.isEntregado()) {
                        showError("Este alquiler ya fue entregado.");
                        return;
                    }

                    entregar(alquiler.getResponsable().getCedula(), alquiler.getFechaRetiro());
                    aplicarFiltros(finalAlquileres, campoBusqueda, comboFiltro, chkEntregados, tableView);
                    showInfo("Alquiler entregado exitosamente.");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    Alquiler alquiler = getTableView().getItems().get(getIndex());
                    btn.setText(alquiler.isEntregado() ? "Entregado" : "Entregar");
                    btn.setDisable(alquiler.isEntregado());
                    setGraphic(btn);
                }
            }
        });

        // Agregar columnas
        tableView.getColumns().addAll(
                colCedula, colNombreResp, colDireccion, colCelular,
                colNombreEst, colGrado, colColegio, colTalla,
                colClaseTraje, colColor, colSombrero, colCantidad,
                colFechaRetiro, colFechaEntrega, colDeposito,
                colMulta, colDeuda, colEntregado, colEntregar
        );

        // Filtros
        HBox filtrosBox = new HBox(10, new Label("Buscar por:"), comboFiltro, campoBusqueda, chkEntregados);
        filtrosBox.setAlignment(Pos.CENTER_LEFT);

        // Botón volver
        Button btnVolver = new Button("Volver");
        btnVolver.setOnAction(e -> resetMenu(mostrarVBox));
        btnVolver.setStyle("-fx-background-color: #666; -fx-text-fill: white;");
        btnVolver.setPrefWidth(100);

        VBox.setVgrow(tableView, Priority.ALWAYS);
        mostrarVBox.getChildren().addAll(title, filtrosBox, tableView, btnVolver);
        principalPanel.setCenter(mostrarVBox);

        // Listeners de filtros
        campoBusqueda.textProperty().addListener((obs, oldV, newV) ->
                aplicarFiltros(finalAlquileres, campoBusqueda, comboFiltro, chkEntregados, tableView)
        );
        comboFiltro.setOnAction(e ->
                aplicarFiltros(finalAlquileres, campoBusqueda, comboFiltro, chkEntregados, tableView)
        );
        chkEntregados.setOnAction(e ->
                aplicarFiltros(finalAlquileres, campoBusqueda, comboFiltro, chkEntregados, tableView)
        );

        // Filtro inicial
        aplicarFiltros(finalAlquileres, campoBusqueda, comboFiltro, chkEntregados, tableView);
    }


    private void entregar(Object cedula, Object fecha) {
        try {
            if (controlador.entregarAlquiler(cedula, fecha)) {
                showInfo("Alquiler fue pagado y entregado exitosamente.");
                resetMenu(mostrarVBox);
                mostrarAlquileres();
            } else {
                throw new IOException("No se encontró el alquiler");
            }
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }


    private void aplicarFiltros(List<Alquiler> alquileres, TextField campoBusqueda, ComboBox<String> comboFiltro,
                                CheckBox chkNoEntregados, TableView<Alquiler> tableView) {
        String texto = campoBusqueda.getText().trim().toLowerCase();
        int indice = comboFiltro.getSelectionModel().getSelectedIndex();

        List<Alquiler> filtrados = alquileres.stream().filter(alq -> {
            String campo = switch (indice) {
                case 0 -> String.valueOf(alq.getResponsable().getCedula());
                case 1 -> alq.getResponsable().getNombre();
                case 2 -> alq.getEstudiante().getNombre();
                case 3 -> alq.getFechaRetiro().toString();
                case 4 -> alq.getFechaEntrega().toString();
                default -> "";
            };
            return campo != null && campo.toLowerCase().contains(texto);
        }).toList();

        try {
            List<Alquiler> resultadoFinal = chkNoEntregados.isSelected()
                    ? filtrados.stream().filter(a -> !a.isEntregado()).toList()
                    : filtrados;

            tableView.setItems(FXCollections.observableArrayList(resultadoFinal));

            tableView.refresh();
        } catch (Exception e) {
            showError("Error al actualizar los alquileres.");
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Info");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void validarCampo(String valor, String nombreCampo) throws IOException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IOException("El campo \"" + nombreCampo + "\" no puede estar vacío.");
        }
    }

    private void resetMenu(VBox vbox) {
        // Limpia el contenido central
//        principalPanel.setCenter(null);
        vbox.setVisible(false);

        // Restaura la imagen de fondo si aplica
        imagePane.setVisible(true);
        principalPanel.setCenter(imagePane);
    }

    private static Spinner<LocalTime> getLocalTimeSpinner() {
        Spinner<LocalTime> timeSpinner = new Spinner<>();
        timeSpinner.setEditable(true);

        LocalTime min = LocalTime.of(8, 0);
        LocalTime max = LocalTime.of(22, 0);
        Duration step = Duration.ofMinutes(15);

        SpinnerValueFactory<LocalTime> customTimeFactory = new SpinnerValueFactory<>() {
            private LocalTime current = LocalTime.of(12, 0);
            {
                setValue(current);
            }

            @Override
            public void decrement(int steps) {
                current = current.minus(step.multipliedBy(steps));
                if (current.isBefore(min)) {
                    current = max;
                }
                setValue(current);
            }

            @Override
            public void increment(int steps) {
                current = current.plus(step.multipliedBy(steps));
                if (current.isAfter(max)) {
                    current = min;
                }
                setValue(current);
            }
        };

        timeSpinner.setValueFactory(customTimeFactory);
        return timeSpinner;
    }

}

