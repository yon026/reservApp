package com.reservadecanchas.util;

import javafx.scene.control.Alert;

public class ShowAlert {

    public static void show(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
