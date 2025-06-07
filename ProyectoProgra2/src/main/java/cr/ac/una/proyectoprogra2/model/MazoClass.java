/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author emena
 */
public class MazoClass {
    private Integer estado;

    private ObservableList<Card> cartas = FXCollections.observableArrayList();

    public MazoClass(Integer estado) {
        this.estado = estado;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
   
}
