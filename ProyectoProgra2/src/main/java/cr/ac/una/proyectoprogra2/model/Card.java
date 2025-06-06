/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model;

/**
 *
 * @author emena
 */
public class Card {

    private Integer indicePosCarta;
    private Integer numeroCarta;
    private String paloCarta;
    private Integer bocaArriba;

    public Card(Integer indicePosCarta, Integer numeroCarta, String paloCarta, Integer bocaArriba) {
        this.indicePosCarta = indicePosCarta;
        this.numeroCarta = numeroCarta;
        this.paloCarta = paloCarta;
        this.bocaArriba = bocaArriba;
    }

    public Card() {
    }

    public Integer getIndicePosCarta() {
        return indicePosCarta;
    }

    public Integer getNumeroCarta() {
        return numeroCarta;
    }

    public String getPaloCarta() {
        return paloCarta;
    }

    public Integer getBocaArriba() {
        return bocaArriba;
    }

    public void setIndicePosCarta(Integer indicePosCarta) {
        this.indicePosCarta = indicePosCarta;
    }

    public void setNumeroCarta(Integer numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public void setPaloCarta(String paloCarta) {
        this.paloCarta = paloCarta;
    }

    public void setBocaArriba(Integer bocaArriba) {
        this.bocaArriba = bocaArriba;
    }

}
