module cr.ac.una.proyectoprogra2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.logging;
    requires jakarta.persistence;
    requires MaterialFX;
    requires javafx.media;

    opens cr.ac.una.proyectoprogra2 to javafx.fxml;
    opens cr.ac.una.proyectoprogra2.util to javafx.fxml;
    opens cr.ac.una.proyectoprogra2.view to javafx.fxml;
    opens cr.ac.una.proyectoprogra2.controller to javafx.fxml;
    opens cr.ac.una.proyectoprogra2.model to javafx.fxml;
    
    exports cr.ac.una.proyectoprogra2;
    exports cr.ac.una.proyectoprogra2.util;
    exports cr.ac.una.proyectoprogra2.controller;
    exports cr.ac.una.proyectoprogra2.model;
    exports cr.ac.una.proyectoprogra2.service;
    
}
