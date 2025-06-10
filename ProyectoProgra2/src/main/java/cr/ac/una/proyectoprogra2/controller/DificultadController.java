/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import cr.ac.una.proyectoprogra2.model.Sonidos;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author monge
 */
public class DificultadController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView ImvFondo;
    @FXML
    private MFXButton btnFacil;
    @FXML
    private MFXButton BtnNormal;
    @FXML
    private MFXButton btnDificil;
    @FXML
    private MFXButton btnVolver;
    @FXML
    private Label lblDificultad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Sonidos.asignarSonido(btnFacil);
        Sonidos.asignarSonido(BtnNormal);
        Sonidos.asignarSonido(btnDificil);
        Sonidos.asignarSonido(btnVolver);
    }    

    @FXML
    private void onActionBtnFacil(ActionEvent event) {
    }

    @FXML
    private void onActionBtnNormal(ActionEvent event) {
    }

    @FXML
    private void onActionBtnDificil(ActionEvent event) {
    }

    @FXML
    private void onActionBtnVolver(ActionEvent event) {
    }
    
}
