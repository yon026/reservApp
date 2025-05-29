package com.reservas.model;

import javafx.beans.property.*;

public class Cliente {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty telefono = new SimpleStringProperty();

    // Constructor vacío
    public Cliente() {}

    // Constructor con parámetros
    public Cliente(int id, String nombre, String telefono, String email) {
        this.id.set(id);
        this.nombre.set(nombre);
        this.telefono.set(telefono);
    }

    // Getters y setters con Property
    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }
    public IntegerProperty idProperty() { return id; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty nombreProperty() { return nombre; }

    public String getTelefono() { return telefono.get(); }
    public void setTelefono(String value) { telefono.set(value); }
    public StringProperty telefonoProperty() { return telefono; }

}
