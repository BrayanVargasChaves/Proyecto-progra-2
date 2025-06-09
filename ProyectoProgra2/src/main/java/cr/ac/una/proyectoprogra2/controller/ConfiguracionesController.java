/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import cr.ac.una.proyectoprogra2.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class ConfiguracionesController extends Controller implements Initializable {

    private MFXToggleButton toggleBtnTemaOscuro;
    private Label estadoTemaOscuro;
    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton btnAnadirUsuario;
    @FXML
    private MFXButton btnPersonalizarUsuario;
    @FXML
    private MFXComboBox<?> CbxElejirUsuario;
    @FXML
    private MFXTextField txfNombreUsuario;
    @FXML
    private MFXButton btnIniciarSesion;
    @FXML
    private MFXButton btnVolver;

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
    private void onActionBtnAnadirUsuario(ActionEvent event) {
    }

    @FXML
    private void onActionBtnPersinalizarUsuario(ActionEvent event) {
    }

    @FXML
    private void onActionCbxElejirUsuario(ActionEvent event) {
    }

    @FXML
    private void onActionTxfNombreUsuario(ActionEvent event) {
    }

    @FXML
    private void onActionBtnIniciarSesion(ActionEvent event) {
    }

    @FXML
    private void onActionBtnVolver(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("PrincipalView");
        ((Stage) root.getScene().getWindow()).close();
    }

}
