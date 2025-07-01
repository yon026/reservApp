package com.reservadecanchas.controller;

import com.reservadecanchas.app.GestorEscenas;
import com.reservadecanchas.persistence.DatabaseConnection;
import com.reservadecanchas.util.ShowAlert;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class BienvenidaController {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContrasena;

    @FXML
    private void switchToReservas() throws IOException {

        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        if (DatabaseConnection.validarUsuario(usuario, contrasena)) {
            GestorEscenas.cambiarEscena("/com/reservadecanchas/app/reservas.fxml");
        } else {
            ShowAlert.show(Alert.AlertType.ERROR, "Acceso denegado", "Usuario o contraseña incorrectos.");
        }
    }

    @FXML
    private void salirAplicacion(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
