package edu.badpals.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Conexion_App_bbdd {

    private String urldb = "jdbc:mysql://localhost:3306/app_series";

    public Connection crearConexion() {
        try {
            Properties propiedadesConexion = new Properties();
            propiedadesConexion.setProperty("user", "root");
            propiedadesConexion.setProperty("password", "root");
            return DriverManager.getConnection(urldb, propiedadesConexion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Serie> getSeries(Connection c) {
        List<Serie> series = new ArrayList<>();
        try {
            String stringSQL = "SELECT * FROM SERIES";
            PreparedStatement ps = c.prepareStatement(stringSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Serie serie = new Serie();
                serie.setId(rs.getInt("ID"));
                serie.setNombre(rs.getString("NOMBRE"));
                serie.setEstreno(rs.getDate("FECHA_ESTRENO"));
                serie.setTematica(rs.getString("TEMATICA"));
                serie.setDirector(rs.getString("DIRECTOR"));
                serie.setCalificacion(rs.getInt("RATING"));
                serie.setIdioma(rs.getString("IDIOMA"));
                serie.setEstado(Estado.valueOf(rs.getString("ESTADO").replace(" ", "_")));
                serie.setCadena(rs.getString("CADENA"));
                series.add(serie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return series;
    }

    public static List<Episodio> getEpisodios(Connection c, Serie serie) {
        List<Episodio> episodios = new ArrayList<>();
        try {
            String stringSQL = "SELECT * FROM EPISODIOS WHERE SERIE = ?";
            PreparedStatement ps = c.prepareStatement(stringSQL);
            ps.setInt(1, serie.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Episodio episodio = new Episodio();
                episodio.setId(rs.getInt("ID"));
                episodio.setNombre(rs.getString("NOMBRE"));
                episodio.setNumero(rs.getInt("NUMERO"));
                episodio.setTemporada(rs.getInt("TEMPORADA"));
                episodio.setSerie(serie); // Assuming `serie` is already set
                episodio.setFechaDeSalida(rs.getDate("FECHA_SALIDA"));
                episodio.setDuracion(rs.getTime("DURACION"));
                episodios.add(episodio);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodios;
    }

    public void cerrarConexion(Connection c) {
        try {
            c.close();
            System.out.println("Conexión cerrada en series");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}