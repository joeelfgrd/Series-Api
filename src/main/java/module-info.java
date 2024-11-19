module edu.badpals.controlador {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;


    opens edu.badpals.controlador to javafx.fxml;
    exports edu.badpals.controlador;
    exports edu.badpals to javafx.graphics;
    exports edu.badpals.modelo;
    opens edu.badpals.modelo to javafx.fxml;
}