package com.reservas.model;

import javafx.beans.property.*;

public class Horario {

    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty horaInicio = new SimpleStringProperty();
    private final StringProperty horaFin = new SimpleStringProperty();
    private final StringProperty dia = new SimpleStringProperty();

    // Constructor vacío
    public Horario() {}

    // Constructor con parámetros
    public Horario(long id, String horaInicio, String horaFin, String dia) {
        this.id.set(id);
        this.horaInicio.set(horaInicio);
        this.horaFin.set(horaFin);
        this.dia.set(dia);
    }

    // ID
    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    // Hora inicio
    public String getHoraInicio() {
        return horaInicio.get();
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio.set(horaInicio);
    }

    public StringProperty horaInicioProperty() {
        return horaInicio;
    }

    // Hora fin
    public String getHoraFin() {
        return horaFin.get();
    }

    public void setHoraFin(String horaFin) {
        this.horaFin.set(horaFin);
    }

    public StringProperty horaFinProperty() {
        return horaFin;
    }

    // Día
    public String getDia() {
        return dia.get();
    }

    public void setDia(String dia) {
        this.dia.set(dia);
    }

    public StringProperty diaProperty() {
        return dia;
    }
}
