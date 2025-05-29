package com.reservas.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Reserva {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final ObjectProperty<Cliente> cliente = new SimpleObjectProperty<>();
    private final ObjectProperty<Cancha> cancha = new SimpleObjectProperty<>();
    private final StringProperty horario = new SimpleStringProperty(); // si es tipo texto tipo "10:00 - 11:00"
    private final ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<>();

    public Reserva() {
    }

    public Reserva(int id, Cliente cliente, Cancha cancha, String horario, LocalDate fecha) {
        this.id.set(id);
        this.cliente.set(cliente);
        this.cancha.set(cancha);
        this.horario.set(horario);
        this.fecha.set(fecha);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public Cliente getCliente() {
        return cliente.get();
    }

    public void setCliente(Cliente cliente) {
        this.cliente.set(cliente);
    }

    public ObjectProperty<Cliente> clienteProperty() {
        return cliente;
    }

    public Cancha getCancha() {
        return cancha.get();
    }

    public void setCancha(Cancha cancha) {
        this.cancha.set(cancha);
    }

    public ObjectProperty<Cancha> canchaProperty() {
        return cancha;
    }

    public String getHorario() {
        return horario.get();
    }

    public void setHorario(String horario) {
        this.horario.set(horario);
    }

    public StringProperty horarioProperty() {
        return horario;
    }

    public LocalDate getFecha() {
        return fecha.get();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }

    public ObjectProperty<LocalDate> fechaProperty() {
        return fecha;
    }
}
