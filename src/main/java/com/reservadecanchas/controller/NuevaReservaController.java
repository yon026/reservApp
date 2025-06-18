package com.reservadecanchas.controller;

import com.reservadecanchas.util.CargadorVistas;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class NuevaReservaController {

    @FXML
    private void switchToReservas(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CargadorVistas.cambiarVista(stage, "/com/reservadecanchas/app/reservas.fxml", "Reservas");
    }

    @FXML
    private void salirNuevaReserva(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
