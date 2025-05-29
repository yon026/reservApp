package com.reservas.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Pago {

    private final LongProperty id = new SimpleLongProperty();
    private final ObjectProperty<Reserva> reserva = new SimpleObjectProperty<>();
    private final DoubleProperty monto = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<>();
    private final StringProperty tipo = new SimpleStringProperty("efectivo"); // por ahora fijo

    // Constructor vacío
    public Pago() {}

    // Constructor con parámetros
    public Pago(long id, Reserva reserva, double monto, LocalDate fecha, String tipo) {
        this.id.set(id);
        this.reserva.set(reserva);
        this.monto.set(monto);
        this.fecha.set(fecha);
        this.tipo.set(tipo);
    }

    // id
    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    // reserva
    public Reserva getReserva() {
        return reserva.get();
    }

    public void setReserva(Reserva reserva) {
        this.reserva.set(reserva);
    }

    public ObjectProperty<Reserva> reservaProperty() {
        return reserva;
    }

    // monto
    public double getMonto() {
        return monto.get();
    }

    public void setMonto(double monto) {
        this.monto.set(monto);
    }

    public DoubleProperty montoProperty() {
        return monto;
    }

    // fecha
    public LocalDate getFecha() {
        return fecha.get();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }

    public ObjectProperty<LocalDate> fechaProperty() {
        return fecha;
    }

    // tipo
    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public StringProperty tipoProperty() {
        return tipo;
    }
}
