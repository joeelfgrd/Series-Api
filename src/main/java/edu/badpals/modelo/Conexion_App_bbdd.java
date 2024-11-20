package edu.badpals.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Conexion_App_bbdd {

    private String urldb = "jdbc:mysql://localhost:3306/app_series";

    public static List<Serie> getSeries(Connection c) {
        String stringSQL = "SELECT * FROM SERIES";
        return selectSeries(c, stringSQL);
    }

    public static List<Serie> getSeriesEstreno(Connection c, String orden) {
        if(!Objects.equals(orden, "ASC") && !Objects.equals(orden, "DESC")){
            return null;
        }
        String stringSQL = "SELECT * FROM SERIES ORDER BY FECHA_ESTRENO " + orden;
        return selectSeries(c, stringSQL);
    }

    public static List<Serie> getSeriesCal(Connection c, String orden) {
        if(!Objects.equals(orden, "ASC") && !Objects.equals(orden, "DESC")){
            return null;
        }
        String stringSQL = "SELECT * FROM SERIES ORDER BY RATING " + orden;
        return selectSeries(c, stringSQL);
    }

    private static List<Serie> selectSeries(Connection c, String stringSQL) {
        List<Serie> series = new ArrayList<>();
        try {
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

    /*
    private static List<Serie> selectSeries(Connection c, String stringSQL, String orden) {
        List<Serie> series = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement(stringSQL);
            ps.setString(1,orden);
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
    */

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
        String stringSQL = "SELECT * FROM EPISODIOS WHERE SERIE = ?";
        return selectEpisodios(c, serie, stringSQL);
    }

    private static List<Episodio> selectEpisodios(Connection c, Serie serie, String stringSQL) {
        List<Episodio> episodios = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement(stringSQL);
            ps.setInt(1, serie.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Episodio episodio = getEpisodioRS(serie, rs);
                episodios.add(episodio);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodios;
    }

    private static List<Episodio> selectEpisodiosOrden(Connection c, Serie serie, String stringSQL, String orden) {
        List<Episodio> episodios = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement(stringSQL);
            ps.setInt(1, serie.getId());
            ps.setString(2, orden);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Episodio episodio = getEpisodioRS(serie, rs);
                episodios.add(episodio);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodios;
    }

    private static Episodio getEpisodioRS(Serie serie, ResultSet rs) throws SQLException {
        Episodio episodio = new Episodio();
        episodio.setId(rs.getInt("ID"));
        episodio.setNombre(rs.getString("NOMBRE"));
        episodio.setNumero(rs.getInt("NUMERO"));
        episodio.setTemporada(rs.getInt("TEMPORADA"));
        episodio.setSerie(serie); // Assuming `serie` is already set
        episodio.setFechaDeSalida(rs.getDate("FECHA_SALIDA"));
        episodio.setDuracion(rs.getTime("DURACION"));
        return episodio;
    }

    public static boolean eliminarEpisodio(Connection c, Episodio episodio) {
        boolean ejecutado = false;
        try {

            String stringSQL = "DELETE FROM EPISODIOS WHERE ID = ?";
            PreparedStatement ps = c.prepareStatement(stringSQL);
            ps.setInt(1, episodio.getId());
            ejecutado = ps.executeUpdate() > 0;
            ps.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ejecutado;
    }

    public static int modificarEpisodio(Connection c, Episodio episodio) {
        int ejecutado = 0;
        try {
            if(checkEpisodio(c,episodio,episodio.getId())){
                return 2;
            }

            String stringSQL = "UPDATE EPISODIOS SET numero = ?, temporada = ?, nombre = ?, serie = ?, FECHA_SALIDA = ?, duracion = ? WHERE ID = ?";
            PreparedStatement ps = c.prepareStatement(stringSQL);
            ps.setInt(1, episodio.getNumero());
            ps.setInt(2, episodio.getTemporada());
            ps.setString(3, episodio.getNombre());
            ps.setInt(4, episodio.getSerie().getId());
            ps.setDate(5, episodio.getFechaDeSalida());
            ps.setTime(6, episodio.getDuracion());
            ps.setInt(7, episodio.getId());
            ejecutado = ps.executeUpdate();
            ps.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ejecutado;
    }

    public static boolean crearEpisodio(Connection c, Episodio episodio) {
        boolean ejecutado = false;
        try {

            if (!checkEpisodio(c, episodio)) {
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
        boolean hasNext = true;
        try {
            String stringCheckNumeroTemp = "SELECT * FROM EPISODIOS WHERE serie = ? AND temporada = ? AND numero = ?";
            PreparedStatement ps = c.prepareStatement(stringCheckNumeroTemp);
            ps.setInt(1, episodio.getSerie().getId());
            ps.setInt(2, episodio.getTemporada());
            ps.setInt(3, episodio.getNumero());
            ResultSet rs = ps.executeQuery();
            hasNext = rs.next();
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasNext;
    }

    private static boolean checkEpisodio(Connection c, Episodio episodio, int id) {
        boolean hasNext = true;
        try {
            String stringCheckNumeroTemp = "SELECT * FROM EPISODIOS WHERE serie = ? AND temporada = ? AND numero = ?";
            PreparedStatement ps = c.prepareStatement(stringCheckNumeroTemp);
            ps.setInt(1, episodio.getSerie().getId());
            ps.setInt(2, episodio.getTemporada());
            ps.setInt(3, episodio.getNumero());
            ResultSet rs = ps.executeQuery();
            hasNext = rs.next();
            if(hasNext){
                hasNext = rs.getInt(1) != id;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasNext;
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