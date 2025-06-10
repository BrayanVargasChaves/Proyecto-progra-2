/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import cr.ac.una.proyectoprogra2.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author monge
 */
public class PrincipalController extends Controller implements Initializable {

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
    @FXML
    private AnchorPane root;
    @FXML
    private Label lblUsuario;
    private static Scene scene;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImvFondo.fitHeightProperty().bind(root.heightProperty());
        ImvFondo.fitWidthProperty().bind(root.widthProperty());
        root.setMaxHeight(640);
        root.setMaxWidth(400);

    }

    @FXML
    private void onActionBtnJugar(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("GameView");
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnAcercaDe(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("AcercaDeView");
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnConfiguraciones(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("configuracionesView");
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnEstadisticas(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("EstadisticasView");
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).close();
    }

    @Override
    public void initialize() {
    }

}
