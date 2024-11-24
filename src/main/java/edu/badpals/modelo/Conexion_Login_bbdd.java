package edu.badpals.modelo;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Conexion_Login_bbdd {
    private final String URLDB = "jdbc:mysql://localhost:3306/login";

    public Connection crearConexion() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties propiedadesConexion = new Properties();
        propiedadesConexion.setProperty("user", "root");
        propiedadesConexion.setProperty("password", "root");
        return DriverManager.getConnection(URLDB, propiedadesConexion);
    }

    public static void crearUser(String user, String pswd) {
        String query = "INSERT INTO logins (USUARIO, PASSWD) VALUES (?, ?)";
        try (Connection conn = new Conexion_Login_bbdd().crearConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String encodedUser = LZ78.encode(user);
            String encodedPswd = LZ78.encode(pswd);

            stmt.setString(1, encodedUser);
            stmt.setString(2, encodedPswd);
            stmt.executeUpdate();  // Ejecutar la inserción
            stmt.close();
            conn.close();


            System.out.println("Usuario registrado correctamente");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    public static Map<String, String> leerUsers() {
        Map<String, String> map = new HashMap<>();
        String query = "SELECT * FROM logins";
        try (Connection conn = new Conexion_Login_bbdd().crearConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String encodedUser = rs.getString("USUARIO");
                String encodedPswd = rs.getString("PASSWD");

                String user = LZ78.decode(encodedUser);
                String pswd = LZ78.decode(encodedPswd);

                map.put(user, pswd);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error al leer los usuarios: " + e.getMessage());
        }
        return map;
    }
}