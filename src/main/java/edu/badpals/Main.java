package edu.badpals;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/edu/badpals/vista/login.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 500, 600);

        stage.setTitle("Series");

        stage.setScene(scene);
        stage.show();
    }
}

    /*public static void main(String[] args) {

        Conexion_Login_bbdd conexion = new Conexion_Login_bbdd();
        Conexion_Series_bbdd conexion2 = new Conexion_Series_bbdd();
        try {
            Connection conn = conexion.crearConexion();
            Connection conn2 = conexion2.crearConexion();
            if (conn != null && conn2 != null) {
                System.out.println("Conexión exitosa a login");
                System.out.println("Conexión exitosa a series");

            } else {
                System.out.println("Fallo en la conexión a login");
                System.out.println("Fallo en la conexión a series");
            }
            conexion.cerrarConexion(conn);
            conexion2.cerrarConexion(conn2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/