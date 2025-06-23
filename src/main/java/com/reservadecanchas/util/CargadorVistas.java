package com.reservadecanchas.util;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class CargadorVistas {

    public static FXMLLoader getLoader(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(CargadorVistas.class.getResource(fxmlPath));
        loader.load(); // Es importante llamar a load() para que el Parent y el Controller estén disponibles
        return loader;
    }
}
