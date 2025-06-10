/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import cr.ac.una.proyectoprogra2.model.Sonidos;
import cr.ac.una.proyectoprogra2.model.dtos.UsuarioDto;
import cr.ac.una.proyectoprogra2.util.AppContext;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author monge
 */
public class FinDelJuegoController implements Initializable {

    @FXML
    private VBox vbxParametros;
    @FXML
    private VBox vbxValores;
    @FXML
    private MFXButton btnContinuar;
    @FXML
    private ImageView imvFondo;
    @FXML
    private AnchorPane root;
    @FXML
    private Label lblResultado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Sonidos.reproducir("gol.mp3");
        Sonidos.asignarSonido(btnContinuar);
        cargarEstadisticas((UsuarioDto) AppContext.getInstance().get("usuario"), (String) AppContext.getInstance().get("duracion"));
        imvFondo.fitHeightProperty().bind(root.heightProperty());
        imvFondo.fitWidthProperty().bind(root.widthProperty());
        root.setMaxHeight(640);
        root.setMaxWidth(400);
    }

    public void cargarEstadisticas(UsuarioDto usuario, String duracion) {
        vbxParametros.getChildren().clear();
        vbxValores.getChildren().clear();
        vbxParametros.getChildren().addAll(new Label("Partidas Jugadas:"),
                new Label("Partidas Ganadas:"),
                new Label("Partidas Perdidas:"),
                new Label("Puntos:"),
                new Label("Duracion:"));

        vbxValores.getChildren().addAll(new Label(String.valueOf(usuario.getPartidasGanadas() + usuario.getPartidasPerdidas())),
                new Label(String.valueOf(usuario.getPartidasGanadas())),
                new Label(String.valueOf(usuario.getPartidasPerdidas())),
                new Label(String.valueOf(usuario.getPuntajeTotal())),
                new Label(duracion));

    }

    @FXML
    private void onActionBtnContinuar(ActionEvent event) {
    }

}
