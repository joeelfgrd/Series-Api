package edu.badpals.Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion_Login_bbdd {
    private String urldb = "jdbc:mysql://localhost:3306/login";
    public Connection crearConexion() throws SQLException {
        Properties propiedadesConexion = new Properties();
        propiedadesConexion.setProperty("user", "root");
        propiedadesConexion.setProperty("password", "renaido");
        return DriverManager.getConnection(urldb, propiedadesConexion);
    }
    public void cerrarConexion(Connection conn) throws SQLException {
        conn.close();
        System.out.println("Conexión cerrada en login");
    }
}

