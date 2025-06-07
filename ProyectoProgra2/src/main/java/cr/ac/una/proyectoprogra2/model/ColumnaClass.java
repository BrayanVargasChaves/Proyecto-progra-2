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
public class ColumnaClass {
    private Integer indiceColumna;
    private ObservableList<Card> cartas = FXCollections.observableArrayList();

    public ColumnaClass(Integer indiceColumna) {
        this.indiceColumna = indiceColumna;
    }

    public Integer getIndiceColumna() {
        return indiceColumna;
    }

    public void setIndiceColumna(Integer indiceColumna) {
        this.indiceColumna = indiceColumna;
    }

}
