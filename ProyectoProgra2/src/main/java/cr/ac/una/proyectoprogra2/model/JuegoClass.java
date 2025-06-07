/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model;

import java.time.LocalDate;

/**
 *
 * @author emena
 */
public class JuegoClass {

    private String dificultad;
    private String estado;
    private LocalDate fechaHora;
    private Integer puntaje;
    private Integer tiempoTranscurrido;

    public JuegoClass(String dificultad, String estado, LocalDate fechaHora, Integer puntaje, Integer tiempoTranscurrido) {
        this.dificultad = dificultad;
        this.estado = estado;
        this.fechaHora = fechaHora;
        this.puntaje = puntaje;
        this.tiempoTranscurrido = tiempoTranscurrido;
    }

    public JuegoClass() {
    }

    public String getDificultad() {
        return dificultad;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public Integer getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public void setTiempoTranscurrido(Integer tiempoTranscurrido) {
        this.tiempoTranscurrido = tiempoTranscurrido;
    }

}
