package edu.badpals.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Conexion_App_bbdd {

    private String urldb = "jdbc:mysql://localhost:3306/app_series";

    public static List<Serie> getSeries(Connection c) {
        List<Serie> series = new ArrayList<>();
        try {
            String stringSQL = "SELECT * FROM SERIES";
            PreparedStatement ps = c.prepareStatement(stringSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Serie serie = getSerieFromRS(rs);
                series.add(serie);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return series;
    }

    public static Serie getSerie(Connection c, String nombre) {
        Serie serie = new Serie();
        try {
            String stringSQL = "SELECT * FROM SERIES WHERE NOMBRE = ?";
            PreparedStatement ps = c.prepareStatement(stringSQL);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                serie = getSerieFromRS(rs);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return serie;
    }

    private static Serie getSerieFromRS(ResultSet rs) throws SQLException {
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
        return serie;
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
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodios;
    }

    public static boolean crearEpisodio(Connection c, Episodio episodio) {
        boolean ejecutado = false;
        try {

            if (checkEpisodio(c,episodio)) {
                String stringSQL = "INSERT INTO Episodios (numero, temporada, nombre, serie, FECHA_SALIDA, duracion) VALUES" +
                        " (?,?,?,?,?,?)";
                PreparedStatement ps = c.prepareStatement(stringSQL);
                ps.setInt(1, episodio.getNumero());
                ps.setInt(2, episodio.getTemporada());
                ps.setString(3, episodio.getNombre());
                ps.setInt(4, episodio.getSerie().getId());
                ps.setDate(5, episodio.getFechaDeSalida());
                ps.setTime(6, episodio.getDuracion());
                ejecutado = ps.execute();
                ps.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ejecutado;
    }

    private static boolean checkEpisodio(Connection c, Episodio episodio) {
        try {
            String stringCheckNumeroTemp = "SELECT * FROM EPISODIOS WHERE serie = ? temporada = ? numero = ?";
            PreparedStatement ps = c.prepareStatement(stringCheckNumeroTemp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


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

    public void cerrarConexion(Connection c) {
        try {
            c.close();
            System.out.println("Conexión cerrada en series");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}