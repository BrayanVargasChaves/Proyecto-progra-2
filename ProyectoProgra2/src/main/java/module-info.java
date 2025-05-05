module cr.ac.una.proyectoprogra2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens cr.ac.una.proyectoprogra2 to javafx.fxml;
    exports cr.ac.una.proyectoprogra2;
}
