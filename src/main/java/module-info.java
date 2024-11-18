module edu.badpals.Controlador {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;


    opens edu.badpals.Controlador to javafx.fxml;
    exports edu.badpals.Controlador;
}