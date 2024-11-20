package edu.badpals.controlador;

import edu.badpals.modelo.Conexion_Login_bbdd;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class LoginController {
    @FXML
    TextField txtFieldUser;

    @FXML
    TextField txtFieldPwd;

    public void toSerie(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/badpals/vista/serie.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setTitle("Series");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registrarUser() {
        Conexion_Login_bbdd.crearUser(txtFieldUser.getText(), txtFieldPwd.getText());
    }


    public void acceder(ActionEvent actionEvent) {
        Map<String, String> usuarios = Conexion_Login_bbdd.leerUsers();
        String decodedUser = txtFieldUser.getText();
        String decodedPwd = txtFieldPwd.getText();
        if (usuarios.containsKey(decodedUser) && usuarios.get(decodedUser).equals(decodedPwd)) {
            toSerie(actionEvent);
        } else {
            System.out.println("Usuario o contraseña incorrectos");
        }
    }


}
