/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author monge
 */
public class AcercaDeController extends Controller implements Initializable {

    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private VBox VbCreditos;
    @FXML
    private MFXButton btnCerrar;
    @FXML
    private ImageView imvFondo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCreditos();
        animarCreditos();

    }

    private void cargarCreditos() {
        VbCreditos.getChildren().addAll(
                new Label("Proyecto de programacion 2"),
                new Label(""),
                new Label("Desarrollado por: "),
                new Label(""),
                new Label("Andres Cortez Victor"),
                new Label(""),
                new Label("Brayan Vargas Chaves"),
                new Label(""),
                new Label("Esteban Mena Cascante"),
                new Label(""),
                new Label("Profesor: "),
                new Label(""),
                new Label("Carlos Carranza Blanco"),
                new Label(""),
                new Label("Universidad Nacional"),
                new Label(""),
                new Label("Ingenieria en sistemas"),
                new Label(""),
                new Label("2025"));
        VbCreditos.getChildren().forEach(n -> {
            if (n instanceof Label l) {
                l.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
            }
        });
    }

    private void animarCreditos() {
        TranslateTransition transicion = new TranslateTransition();
        transicion.setNode(VbCreditos);
        transicion.setDuration(javafx.util.Duration.seconds(20));
        transicion.setFromY(scrollPane.getHeight());
        transicion.setToY(-VbCreditos.getBoundsInParent().getHeight());
        transicion.setCycleCount(TranslateTransition.INDEFINITE);
        transicion.play();
    }

    @FXML
    private void onActionBtnCerrar(ActionEvent event) {
    }

    @Override
    public void initialize() {
    }

}
