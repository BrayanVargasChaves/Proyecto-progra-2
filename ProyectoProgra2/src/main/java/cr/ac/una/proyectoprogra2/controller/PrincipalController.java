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
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author monge
 */
public class PrincipalController implements Initializable {

    @FXML
    private MFXButton btnJugar;
    @FXML
    private MFXButton btnAcercaDe;
    @FXML
    private MFXButton btnConfiguraciones;
    @FXML
    private MFXButton btnEstadisticas;
    @FXML
    private MFXButton btnSalir;
    @FXML
    private ImageView imvUsario;
    @FXML
    private ImageView ImvFondo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionBtnJugar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnAcercaDe(ActionEvent event) {
    }

    @FXML
    private void onActionBtnConfiguraciones(ActionEvent event) {
    }

    @FXML
    private void onActionBtnEstadisticas(ActionEvent event) {
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
    }
    
}
