package edu.badpals.modelo;

import java.util.Date;

public class Serie {
    private int id;
    private String nombre;
    private Date estreno;
    private String tematica;
    private String director;
    private int calificacion;
    private String idioma;
    private Estado estado;
    private String cadena;

    // Constructor vacío
    public Serie() {
    }

    // Constructor con parámetros
    public Serie(int id, String nombre, Date estreno, String tematica, String director, int calificacion, String idioma, Estado estado, String cadena) {
        this.id = id;
        this.nombre = nombre;
        this.estreno = estreno;
        this.tematica = tematica;
        this.director = director;
        this.calificacion = calificacion;
        this.idioma = idioma;
        this.estado = estado;
        this.cadena = cadena;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getEstreno() {
        return estreno;
    }

    public void setEstreno(Date estreno) {
        this.estreno = estreno;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estreno=" + estreno +
                ", tematica='" + tematica + '\'' +
                ", director='" + director + '\'' +
                ", calificacion=" + calificacion +
                ", idioma='" + idioma + '\'' +
                ", estado=" + estado +
                ", cadena='" + cadena + '\'' +
                '}';
    }
}