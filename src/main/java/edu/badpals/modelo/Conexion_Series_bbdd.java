package edu.badpals.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion_Series_bbdd {

    private String urldb = "jdbc:mysql://localhost:3306/app_series";

    public Connection crearConexion() throws SQLException {
        Properties propiedadesConexion = new Properties();
        propiedadesConexion.setProperty("user", "root");
        propiedadesConexion.setProperty("password", "root");
        return DriverManager.getConnection(urldb, propiedadesConexion);
    }

    public void cerrarConexion(Connection conn) throws SQLException {
        conn.close();
        System.out.println("Conexión cerrada en series");
    }
}