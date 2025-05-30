/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class GameController extends Controller implements  Initializable {

    @FXML
    private MFXButton btnJuegoNuevo;
    @FXML
    private MFXButton btnSolucion;
    @FXML
    private MFXButton btnPista;
    @FXML
    private MFXButton btnDeshacerTodo;
    @FXML
    private MFXButton btnDeshacer;
    @FXML
    private AnchorPane root;

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
    private void onActionBtnJuegoNuevo(ActionEvent event) {
    }

    @FXML
    private void onActionBtnSolucion(ActionEvent event) {
    }

    @FXML
    private void onActionBtnPista(ActionEvent event) {
    }

    @FXML
    private void onActionBtnDeshacerTodo(ActionEvent event) {
    }

    @FXML
    private void onActionBtnDeshacer(ActionEvent event) {
    }
    
}
