
package com.reservas.view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import javafx.scene.control.Button;

public class CanchaViewTest {

    @Test
    public void botonDeberiaEstarVisible() {
        Button boton = new Button();
        boton.setVisible(true);
        assertTrue(boton.isVisible());
    }
}