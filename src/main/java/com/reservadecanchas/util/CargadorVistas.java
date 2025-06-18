package com.reservadecanchas.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.stage.Modality;

public class CargadorVistas {

    public static void cambiarVista(Stage stage, String rutaFXML, String windowTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(CargadorVistas.class.getResource(rutaFXML));

            Parent root = loader.load();
            Stage stageEdicion = new Stage();
            stageEdicion.setTitle(windowTitle);

            stageEdicion.setScene(new Scene(root));

            stageEdicion.sizeToScene();
            stageEdicion.centerOnScreen();
            stageEdicion.setResizable(false);
            stageEdicion.initModality(Modality.APPLICATION_MODAL);
            stageEdicion.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
