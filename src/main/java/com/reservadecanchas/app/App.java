package com.reservadecanchas.app;

//  //Probando conexion a BD
//import com.reservadecanchas.persistence.DatabaseConnection;
//import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GestorEscenas.setStage(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/reservadecanchas/app/bienvenida.fxml"));
        Scene scene = new Scene(loader.load(), 640, 480);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Sistema de reservas de canchas");
        primaryStage.show();
    }

    public static void main(String[] args) {
//        //Probando conexion a BD, quitar comentario lineas 3-5
//        Connection conn = null;
//        conn = DatabaseConnection.getConnection();

        launch(args);

    }
}
