package com.reservas.model;

import javafx.beans.property.*;

public class Cancha {
    private final IntegerProperty num = new SimpleIntegerProperty();
    private final StringProperty deporte = new SimpleStringProperty();
    private final DoubleProperty precio = new SimpleDoubleProperty();
    private final BooleanProperty disponible = new SimpleBooleanProperty();

    // Constructor vacío
    public Cancha() {}

    // Constructor con parámetros
    public Cancha(int num, String deporte, double precio, boolean disponible) {
        this.num.set(num);
        this.deporte.set(deporte);
        this.precio.set(precio);
        this.disponible.set(disponible);
    }

    // Getters y setters con Property
    public int getNum() { return num.get(); }
    public void setNum(int value) { num.set(value); }
    public IntegerProperty numProperty() { return num; }

    public String getDeporte() { return deporte.get(); }
    public void setDeporte(String value) { deporte.set(value); }
    public StringProperty deporteProperty() { return deporte; }

    public double getPrecio() { return precio.get(); }
    public void setPrecio(double value) { precio.set(value); }
    public DoubleProperty precioProperty() { return precio; }

    public boolean isDisponible() { return disponible.get(); }
    public void setDisponible(boolean value) { disponible.set(value); }
    public BooleanProperty disponibleProperty() { return disponible; }
}
