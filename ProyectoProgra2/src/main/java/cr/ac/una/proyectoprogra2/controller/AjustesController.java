/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class AjustesController extends Controller implements Initializable {

    @FXML
    private MFXToggleButton toggleBtnTemaOscuro;
    @FXML
    private Label estadoTemaOscuro;
    @FXML
    private ScrollPane root;

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
    private void onActionToggleBtnTemaOscuro(MouseEvent event) {
       if(toggleBtnTemaOscuro.isSelected()){
           estadoTemaOscuro.setText("Activado");
       }
       else{
           estadoTemaOscuro.setText("Desactivado");
       }
    }
    
}
