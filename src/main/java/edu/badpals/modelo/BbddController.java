package edu.badpals.modelo;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BbddController {

    // Método para registrar un nuevo usuario en la base de datos (codificando con LZ78)
    public static void crearUser(String user, String pswd) {
        String query = "INSERT INTO logins (usuario, contraseña) VALUES (?, ?)";
        try (Connection conn = new Conexion_Login_bbdd().crearConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Codificar el usuario y la contraseña antes de guardarlos en la base de datos
            String encodedUser = LZ78.encode(user);
            String encodedPswd = LZ78.encode(pswd);

            stmt.setString(1, encodedUser);
            stmt.setString(2, encodedPswd);
            stmt.executeUpdate();  // Ejecutar la inserción

            System.out.println("Usuario registrado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    // Método para leer los usuarios desde la base de datos y devolverlos en un mapa (decodificando con LZ78)
    public static Map<String, String> leerUsers() {
        Map<String, String> map = new HashMap<>();
        String query = "SELECT * FROM logins";
        try (Connection conn = new Conexion_Login_bbdd().crearConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Leer y decodificar los usuarios y contraseñas
                String encodedUser = rs.getString("usuario");
                String encodedPswd = rs.getString("contraseña");

                // Decodificar los valores utilizando LZ78
                String user = LZ78.decode(encodedUser);
                String pswd = LZ78.decode(encodedPswd);

                map.put(user, pswd);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer los usuarios: " + e.getMessage());
        }
        return map;
    }
}
