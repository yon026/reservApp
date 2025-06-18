package com.reservadecanchas.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class GestorEscenas {

    private static Stage stage;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void cambiarEscena(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(GestorEscenas.class.getResource(fxmlPath));
        Parent root = loader.load();
        stage.getScene().setRoot(root);

        stage.sizeToScene();
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
}
