/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class GameController extends Controller implements  Initializable {

    @FXML
    private MFXButton btnPista;
    @FXML
    private MFXButton btnRendirce;
    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton btnTerminarMasTarde;
    @FXML
    private Label lbPuntuacion;
    @FXML
    private Label lbTime;
    @FXML
    private Pane pnBaraja;
    @FXML
    private Pane pnPila1;
    @FXML
    private Pane pnPila2;
    @FXML
    private Pane pnPila3;
    @FXML
    private Pane pnPila4;
    @FXML
    private Pane pnPila5;
    @FXML
    private Pane pnPila6;
    @FXML
    private Pane pnPila7;
    @FXML
    private Pane pnPila8;
    @FXML
    private Pane pnFila1;
    @FXML
    private Pane pnFila2;
    @FXML
    private Pane pnFila3;
    @FXML
    private Pane pnFila4;
    @FXML
    private Pane pnFila5;
    @FXML
    private Pane pnFila6;
    @FXML
    private Pane pnFila7;
    @FXML
    private Pane pnFila8;
    @FXML
    private Pane pnFila9;
    @FXML
    private Pane pnFila10;
    
    
    private List<Pane> filasPanes;
    private List<Pane> pilasPanes;
//    private List<List<Card>> cartasEnJuego;
//    private List<Card> cartasEnMazo;
//    private List<List<Card>> cartasEnPila;
//    private Map<Integer, Card> imageCache = new HashMap<>();
//    private Map<String, Integer> valoresCarta = new HashMap<>();
      private Integer valorCartaActual;
      private int indexColumnaActual; //columna seleccionada para mover con click
      private int indexCartaActual; //indice seleccionado para mover con click
      private final double OFFSET_Y = 30;
      private int hintSourceColumnIndex;
      private int hintCardIndex;
      private int hintTargetColumnIndex;
      private int lastHintTargetColumnIndex;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    }


    @FXML
    private void onActionBtnPista(ActionEvent event) {
    }

    @FXML
    private void onActionBtnRendirce(ActionEvent event) {
    }

    @FXML
    private void onActionTerminarMasTarde(ActionEvent event) {
    }

    
}
