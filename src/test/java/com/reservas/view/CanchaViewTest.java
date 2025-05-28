package com.reservas.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.assertions.api.Assertions.assertThat;

public class CanchaViewTest extends ApplicationTest {

    private Button boton;

    @Override
    public void start(Stage stage) {
        boton = new Button("Reservar");
        boton.setId("botonReservar");
        boton.setVisible(true); // se asegura que esté visible
        Scene scene = new Scene(boton, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void botonDeberiaEstarVisible() {
        // Verifica que el botón esté visible en la escena
        assertThat(lookup("#botonReservar").queryAs(Button.class)).isVisible();
    }
}
