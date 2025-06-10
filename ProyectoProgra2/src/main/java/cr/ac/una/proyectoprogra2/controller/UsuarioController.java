/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra2.controller;

import cr.ac.una.proyectoprogra2.model.dtos.UsuarioDto;
import cr.ac.una.proyectoprogra2.service.UsuarioService;
import cr.ac.una.proyectoprogra2.util.BindingUtils;
import cr.ac.una.proyectoprogra2.util.FlowController;
import cr.ac.una.proyectoprogra2.util.Formato;
import cr.ac.una.proyectoprogra2.util.Mensaje;
import cr.ac.una.proyectoprogra2.util.Respuesta;
import cr.ac.una.proyectoprogra2.model.Sonidos;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author monge
 */
public class UsuarioController extends Controller implements Initializable {

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
    private Label lblPersonalizada;
    @FXML
    private ImageView imvFondo;
    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txfNombreUsuario;
    @FXML
    private MFXToggleButton tgbTema;

    private File imagenSeleccionada;

    private UsuarioDto usuarioDto;
    private ObjectProperty<UsuarioDto> usuarioProperty = new SimpleObjectProperty<>();
    private List<Node> requeridos = new ArrayList();
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
   
    @FXML
    private void onActionBtnVolver(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("PrincipalView");
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onActionRdbCara1(ActionEvent event) {
        // Cuando se selecciona rdbCara1, seleccionamos rdbEscudo1 automáticamente
        if (rdbCara1.isSelected()) {
            rdbEscudo1.setSelected(true);
        }
    }

    @FXML
    private void onActionRdbCara2(ActionEvent event) {
        // Cuando se selecciona rdbCara2, seleccionamos rdbEscudo2 automáticamente
        if (rdbCara2.isSelected()) {
            rdbEscudo2.setSelected(true);
        }
    }

    @FXML
    private void onActionRdbEscudo1(ActionEvent event) {
        // Cuando se selecciona rdbEscudo1, seleccionamos rdbCara1 automáticamente
        if (rdbEscudo1.isSelected()) {
            rdbCara1.setSelected(true);
        }
    }

    @FXML
    private void onActionRdbEscudo2(ActionEvent event) {
        // Cuando se selecciona rdbEscudo2, seleccionamos rdbCara2 automáticamente
        if (rdbEscudo2.isSelected()) {
            rdbCara2.setSelected(true);
        }
    }

    @FXML
    private void onToggleClicked(MouseEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdbCara1.setUserData("");
        rdbCara2.setUserData("");
        rdbEscudo1.setUserData("");
        rdbEscudo2.setUserData("");

        rdbCara1.setOnAction(this::onActionRdbCara1);
        rdbCara2.setOnAction(this::onActionRdbCara2);
        rdbEscudo1.setOnAction(this::onActionRdbEscudo1);
        rdbEscudo2.setOnAction(this::onActionRdbEscudo2);

        txfNombreUsuario.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(40));
        usuarioDto = new UsuarioDto();
        bindUsuario();
        cargarValoresDefecto();
        indicarRequeridos();

        imvPersonalizada.setOnMouseClicked(event -> {
            Sonidos.reproducir("click.wav");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar Imagen");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            File file = fileChooser.showOpenDialog(root.getScene().getWindow());
            if (file != null) {
                imagenSeleccionada = file;
                Image image = new Image(file.toURI().toString());
                imvPersonalizada.setImage(image);
            }
        });

        imvPersonalizada.setOnDragOver(event -> {
            if (event.getGestureSource() != imvPersonalizada && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        imvPersonalizada.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                File file = db.getFiles().get(0);
                imagenSeleccionada = file;
                Image image = new Image(file.toURI().toString());
                imvPersonalizada.setImage(image);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
        Sonidos.asignarSonido(btnGuardar);
        Sonidos.asignarSonido(btnVolver);
    }

    @Override
    public void initialize() {

    }

    private void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txfNombreUsuario));
    }

    private void cargarValoresDefecto() {
        usuarioDto = new UsuarioDto();
        usuarioDto.setNombreUsuario("Invitado");
        usuarioProperty.setValue(usuarioDto);
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            }
            if (validos) {
                return "";
            } else {
                return "Campos requeridos o con problemas de formato [" + invalidos + "].";
            }
        }
        return "";
    }

    private void bindUsuario() {
        try {
            // Listener para actualizar los campos cuando el objeto cambie
            usuarioProperty.addListener((obs, oldVal, newVal) -> {
                if (oldVal != null) {
                    // Desvincula los campos previos
                    txfNombreUsuario.textProperty().unbindBidirectional(oldVal.getNombreUsuarioProperty());
                    // Unbind los RadioButton
                    BindingUtils.unbindToggleGroupToProperty(imagenDetras, oldVal.getImagenCartaProperty());
                    BindingUtils.unbindToggleGroupToProperty(imagenDelante, oldVal.getCaraCartaImgProperty());
                }

                if (newVal != null) {
                    // Vincula los campos actuales
                    txfNombreUsuario.textProperty().bindBidirectional(newVal.getNombreUsuarioProperty());

                    // Aquí vinculamos los RadioButton a las propiedades del DTO
                    BindingUtils.bindToggleGroupToProperty(imagenDetras, newVal.getImagenCartaProperty());
                    BindingUtils.bindToggleGroupToProperty(imagenDelante, newVal.getCaraCartaImgProperty());

                    // No es necesario vincular tgbTema, ya que puede ser nulo.
                }
            });

        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar el bindeo", getStage(), "Ocurrió un error al realizar el bindeo.");
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (this.usuarioDto.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Usuario", getStage(), "Favor consultar usuario a eliminar.");
            } else {
                UsuarioService usuarioService = new UsuarioService();
                Respuesta respuesta = usuarioService.eliminarUsuario(this.usuarioDto.getId());
                if (respuesta.getEstado()) {
                    cargarValoresDefecto();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar Usuario", getStage(), "El usuario se elimino correctamente.");

                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar usuario", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, "Error guardando el usuario.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar usuario", getStage(), "Ocurrió un error eliminando el usuario.");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", getStage(), invalidos);
            } else {
                UsuarioService usuarioService = new UsuarioService();
                Respuesta respuesta = usuarioService.guardarUsuario(this.usuarioDto);
                if (respuesta.getEstado()) {
                    this.usuarioDto = (UsuarioDto) respuesta.getResultado("Usuario");
                    this.usuarioProperty.set(this.usuarioDto);
                    validarRequeridos();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", getStage(), respuesta.getMensaje());
                }
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar usuario", getStage(), "El usuario se guardó correctamente.");
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, "Error guardando el usuario.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", getStage(), "Ocurrió un error guardando el usuario.");
        }
    }

    private void cargarEmpleado(Long id) {

    }
}
