/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import cr.ac.una.proyectoprogra2.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class ConfiguracionesController extends Controller implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton btnAnadirUsuario;
    @FXML
    private MFXButton btnPersonalizarUsuario;
    @FXML
    private MFXTextField txfNombreUsuario;
    @FXML
    private MFXButton btnIniciarSesion;
    @FXML
    private MFXButton btnVolver;
    @FXML
    private MFXFilterComboBox<?> fcbUsuario;
    @FXML
    private MFXToggleButton tgbTema;
    @FXML
    private ImageView imgFondo;
    @FXML
    private MFXButton btnEliminarUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgFondo.fitHeightProperty().bind(root.heightProperty());
        imgFondo.fitWidthProperty().bind(root.widthProperty()); 
        root.setMaxHeight(640);
        root.setMaxWidth(400);    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnAnadirUsuario(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("UsuarioView");
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnPersinalizarUsuario(ActionEvent event) {
        
    }

    @FXML
    private void onActionBtnIniciarSesion(ActionEvent event) {
        
    }

    @FXML
    private void onActionBtnVolver(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("PrincipalView");
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onToggleClicked(MouseEvent event) {
    }

    @FXML
    private void onKeyPressedNombreUsuario(KeyEvent event) {
    }

    @FXML
    private void onActionBtnEliminarUsuario(ActionEvent event) {
    }

}
