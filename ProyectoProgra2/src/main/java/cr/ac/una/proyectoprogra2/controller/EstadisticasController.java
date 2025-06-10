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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author monge
 */
public class EstadisticasController extends Controller implements Initializable {

    @FXML
    private ImageView imvFondo;
    @FXML
    private TableView<?> tblEstadisticas;
    @FXML
    private TableColumn<?, ?> colPartidas;
    @FXML
    private TableColumn<?, ?> colGanados;
    @FXML
    private TableColumn<?, ?> colPerdidos;
    @FXML
    private TableColumn<?, ?> colPuntos;
    @FXML
    private MFXButton btnVolver;

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

    @Override
    public void initialize() {
    }
    
}
