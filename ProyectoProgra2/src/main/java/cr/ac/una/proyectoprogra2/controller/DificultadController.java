/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import cr.ac.una.proyectoprogra2.model.Sonidos;
import cr.ac.una.proyectoprogra2.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import cr.ac.una.proyectoprogra2.controller.PrincipalController;
import cr.ac.una.proyectoprogra2.util.AppContext;

/**
 * FXML Controller class
 *
 * @author monge
 */
public class DificultadController extends Controller implements Initializable {

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
    
    private Stage stage;
    
   

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
        String diff = "f";
        AppContext.getInstance().set("dificultad", diff);
        FlowController.getInstance().goViewInWindow("GameView");
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnNormal(ActionEvent event) {
        String diff = "m";
        AppContext.getInstance().set("dificultad", diff);
        FlowController.getInstance().goViewInWindow("GameView");
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnDificil(ActionEvent event) {       
        String diff = "d";
        AppContext.getInstance().set("dificultad", diff);
        FlowController.getInstance().goViewInWindow("GameView");
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnVolver(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("PrincipalView");
        ((Stage) root.getScene().getWindow()).close();
    }
    
    @Override
    public void initialize() {
    }
    
}
