package com.reservadecanchas.controller;

import com.reservadecanchas.app.GestorEscenas;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class BienvenidaController {

    @FXML
    private void switchToReservas() throws IOException {
        GestorEscenas.cambiarEscena("/com/reservadecanchas/app/reservas.fxml");
    }

    @FXML
    private void salirAplicacion(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
