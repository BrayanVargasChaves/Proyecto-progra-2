/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author emena
 */

public class Sonidos {

    private static MediaPlayer reproductor;

    public static void reproducir(String nombreArchivo) {
        try {
            URL url = Sonidos.class.getResource("/cr/ac/una/proyectoprogra2/resources/sonidos/" + nombreArchivo);
            if (url == null) {
                System.out.println("No se encontró el sonido: " + nombreArchivo);
                return;
            }

            Media sonido = new Media(url.toExternalForm());
            MediaPlayer mp = new MediaPlayer(sonido);
            mp.setVolume(0.5);
            mp.play();
        } catch (Exception e) {
            System.out.println("No se pudo reproducir el sonido: " + nombreArchivo);
            e.printStackTrace();
        }
    }

    public static void reproducirLoop(String nombreArchivo) {
        try {
            URL url = Sonidos.class.getResource("/cr/ac/una/proyectoprogra2/resources/sonidos/" + nombreArchivo);
            if (url == null) {
                System.out.println("No se encontró el sonido para loop: " + nombreArchivo);
                return;
            }

            Media sonido = new Media(url.toExternalForm());
            reproductor = new MediaPlayer(sonido);
            reproductor.setCycleCount(MediaPlayer.INDEFINITE); // Repetir infinitamente
            reproductor.setVolume(0.5);
            reproductor.play();

        } catch (Exception e) {
            System.out.println("Error al reproducir en loop: " + nombreArchivo);
            e.printStackTrace();
        }
    }

    public static void detener() {
        if (reproductor != null) {
            reproductor.stop();
        }
    }

    public static void asignarSonido(Button btn) {
        btn.setOnMouseEntered(e -> reproducir("hover.wav"));
        btn.addEventHandler(ActionEvent.ACTION, e -> reproducir("click.wav"));
    }

    public static void asignarSonido(MFXButton btn) {
        btn.setOnMouseEntered(e -> reproducir("hover.wav"));
        btn.addEventHandler(ActionEvent.ACTION, e -> reproducir("click.wav"));
    }

    public static void asignarSonidoEmpezar(MFXButton btn) {
        btn.setOnMouseEntered(e -> reproducir("hover.wav"));
        btn.addEventHandler(ActionEvent.ACTION, e -> reproducir("clickEmpezar.mp3"));
    }
    
    public static void asignarSonidoDeck(MFXButton btn) {
        btn.setOnMouseEntered(e -> reproducir("hover.wav"));
        btn.addEventHandler(ActionEvent.ACTION, e -> reproducir("deck.mp3"));
    }
    
    public static void asignarSonidoPista(MFXButton btn) {
        btn.setOnMouseEntered(e -> reproducir("notification.mp3"));
        btn.addEventHandler(ActionEvent.ACTION, e -> reproducir("click.wav"));
    }
}
