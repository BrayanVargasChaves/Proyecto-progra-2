module cr.ac.una.proyectoprogra2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.logging;
    requires jakarta.persistence;
    requires MaterialFX;

    opens cr.ac.una.proyectoprogra2 to javafx.fxml;
    opens cr.ac.una.proyectoprogra2.util to javafx.fxml;
    exports cr.ac.una.proyectoprogra2;
    exports cr.ac.una.proyectoprogra2.util;
    exports cr.ac.una.proyectoprogra2.controller;
    exports cr.ac.una.proyectoprogra2.view;
}
