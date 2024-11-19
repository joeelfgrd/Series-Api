module edu.badpals.controlador {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;


    opens edu.badpals.controlador to javafx.fxml;
    exports edu.badpals.controlador;
    exports edu.badpals to javafx.graphics;
    exports edu.badpals.modelo;
    opens edu.badpals.modelo to javafx.fxml;
}