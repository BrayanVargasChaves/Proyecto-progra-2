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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private MFXButton btnVolver;
    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton btnVolver1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (btnVolver != null) {
            btnVolver.setOnMouseEntered(event -> {
                Sonidos.asignarSonido(btnVolver);
            });
        } else {
            System.out.println("El botón no ha sido inicializado correctamente");
        }
        imvFondo.fitHeightProperty().bind(root.heightProperty());
        imvFondo.fitWidthProperty().bind(root.widthProperty());
        root.setMaxHeight(640);
        root.setMaxWidth(400);
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
