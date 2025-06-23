package com.reservadecanchas.app;

import com.reservadecanchas.persistence.DatabaseConnection;
import com.reservadecanchas.util.DatabaseUtils;
import com.reservadecanchas.util.ShowAlert;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // --- Paso 1: Asegurar que la base de datos y tablas estén inicializadas ---
        try (java.sql.Connection conn = DatabaseConnection.getConnection()) { // Obtén una conexión
            // Verifica si la tabla 'Reservas' existe
            if (!DatabaseUtils.tableExists(conn, "Reservas")) {
                System.out.println("La tabla 'Reservas' no existe. Creando tablas y datos iniciales...");
                DatabaseUtils.executeSqlScript(conn, "/sql/ReservaBD_Creacion.sql"); // Creacion de tablas
                DatabaseUtils.executeSqlScript(conn, "/sql/ReservaBD_Carga.sql"); // Datos de prueba

            } else {
                System.out.println("La tabla 'Reservas' ya existe. No se recreará.");
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error fatal al inicializar la base de datos: " + e.getMessage());
            ShowAlert.show(Alert.AlertType.ERROR, "Error de Inicio", "No se pudo conectar o inicializar la base de datos. Verifique la configuración.");
            System.exit(1);
        }

        // --- Paso 2: Cargar y mostrar la interfaz de usuario ---
        GestorEscenas.setStage(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/reservadecanchas/app/bienvenida.fxml"));
        Scene scene = new Scene(loader.load(), 640, 480);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Sistema de reservas de canchas");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
