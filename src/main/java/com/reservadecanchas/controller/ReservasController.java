package com.reservadecanchas.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import com.reservadecanchas.util.CargadorVistas;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class ReservasController {

    @FXML
    private void switchToEditarReserva(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CargadorVistas.cambiarVista(stage, "/com/reservadecanchas/app/editarReserva.fxml", "Editar Reserva");
    }

    @FXML
    private void switchToNuevaReserva(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CargadorVistas.cambiarVista(stage, "/com/reservadecanchas/app/nuevaReserva.fxml", "Nueva Reserva");

    }
}
