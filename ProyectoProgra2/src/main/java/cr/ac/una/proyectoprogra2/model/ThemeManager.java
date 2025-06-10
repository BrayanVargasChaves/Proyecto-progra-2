/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author emena
 */
public class ThemeManager {
    public static void setTheme(Stage stage, boolean isDarkMode) {
        Scene scene = stage.getScene();
        if (scene != null) {
            if (isDarkMode) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(ThemeManager.class.getResource("/cr/ac/una/proyectoprogra2/view/TemaOscuro.css").toExternalForm());
            } else {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(ThemeManager.class.getResource("/cr/ac/una/proyectoprogra2/view/TemaClaro.css").toExternalForm());
            }
        }
    }

    // Método para aplicar el tema a cualquier raíz de escena (como AnchorPane)
    public static void setTheme(AnchorPane root, boolean isDarkMode) {
        if (isDarkMode) {
            root.getStylesheets().clear();
            root.getStylesheets().add(ThemeManager.class.getResource("/cr/ac/una/proyectoprogra2/view/TemaOscuro.css").toExternalForm());
        } else {
            root.getStylesheets().clear();
            root.getStylesheets().add(ThemeManager.class.getResource("/cr/ac/una/proyectoprogra2/view/TemaClaro.css").toExternalForm());
        }
    }
}

