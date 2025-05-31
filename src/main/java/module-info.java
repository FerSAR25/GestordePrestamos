module com.edu.uptcsoft.gestordeprestamos {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.github.lgooddatepicker;

    opens com.edu.uptcsoft.gestordeprestamos to javafx.fxml;
    opens com.edu.uptcsoft.gestordeprestamos.controller to javafx.fxml;
    opens com.edu.uptcsoft.gestordeprestamos.view to javafx.fxml;

    opens com.edu.uptcsoft.gestordeprestamos.model to com.google.gson;

    opens com.edu.uptcsoft.gestordeprestamos.persistence to com.google.gson;

    exports com.edu.uptcsoft.gestordeprestamos.controller;
    exports com.edu.uptcsoft.gestordeprestamos.view;
}
