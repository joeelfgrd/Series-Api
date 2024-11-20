module edu.badpals.controlador {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;
    requires jdom2;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;
    requires java.sql;

    opens edu.badpals.controlador to javafx.fxml;
    exports edu.badpals.controlador;
    exports edu.badpals;
    opens edu.badpals to javafx.fxml;
    exports edu.badpals.modelo;
    opens edu.badpals.modelo to com.fasterxml.jackson.databind, javafx.fxml;
}