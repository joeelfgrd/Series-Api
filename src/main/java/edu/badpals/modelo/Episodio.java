package edu.badpals.modelo;

import java.sql.Time;
import java.sql.Date;

public class Episodio {
    private int id;
    private String nombre;
    private int numero;
    private int temporada;
    private Serie serie;
    private Date fechaDeSalida;
    private Time duracion;

    // Constructor vacío
    public Episodio() {
    }

    // Constructor con parámetros
    public Episodio(int id, String nombre, int numero, int temporada, Serie serie, Date fechaDeSalida, Time duracion) {
        this.id = id;
        this.nombre = nombre;
        this.numero = numero;
        this.temporada = temporada;
        this.serie = serie;
        this.fechaDeSalida = fechaDeSalida;
        this.duracion = duracion;
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Date getFechaDeSalida() {
        return fechaDeSalida;
    }

    public void setFechaDeSalida(Date fechaDeSalida) {
        this.fechaDeSalida = fechaDeSalida;
    }

    public Time getDuracion() {
        return duracion;
    }

    public void setDuracion(Time duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Episodio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", numero=" + numero +
                ", temporada=" + temporada +
                ", serie=" + serie +
                ", fechaDeSalida=" + fechaDeSalida +
                ", duracion=" + duracion +
                '}';
    }
}