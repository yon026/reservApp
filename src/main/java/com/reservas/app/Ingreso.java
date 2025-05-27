package com.reservas.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Ingreso extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label bienvenida = new Label("Sistema de reserva de canchas");
        Button ingresar = new Button("Ingresar");

        ingresar.setOnAction(e -> {
            System.out.println("Ingresando al sistema...");
            primaryStage.close();
        });

        VBox layout = new VBox(20, bienvenida, ingresar);
        layout.setStyle("-fx-padding: 30; -fx-alignment: center; -fx-font-size: 16;");

        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setTitle("Bienvenido/a");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
