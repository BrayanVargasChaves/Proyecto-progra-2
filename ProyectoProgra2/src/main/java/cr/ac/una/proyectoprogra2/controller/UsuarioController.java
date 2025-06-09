/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author monge
 */
public class UsuarioController implements Initializable {

    @FXML
    private MFXButton btnVolver;
    @FXML
    private MFXRadioButton rdbCara1;
    @FXML
    private ToggleGroup imagenDelante;
    @FXML
    private MFXRadioButton rdbEscudo1;
    @FXML
    private ToggleGroup imagenDetras;
    @FXML
    private MFXRadioButton rdbCara2;
    @FXML
    private MFXRadioButton rdbEscudo2;
    @FXML
    private ImageView imvPersonalizada;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Label lblPersonalizada;
    @FXML
    private ImageView imvFondo;
    @FXML
    private ImageView imvDelantera;
    @FXML
    private ImageView imgDetras;
    @FXML
    private ImageView ImvDelantera2;
    @FXML
    private ImageView imvDetras2;
    @FXML
    private ImageView imvGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionBtnVolver(ActionEvent event) {
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
    }
    
}
