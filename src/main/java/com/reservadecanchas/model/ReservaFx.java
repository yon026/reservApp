package com.reservadecanchas.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class ReservaFx {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombres = new SimpleStringProperty();
    private final StringProperty apellidos = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<>();
    private final StringProperty cancha = new SimpleStringProperty();
    private final StringProperty horario = new SimpleStringProperty();

    // Constructor sin ID (útil para nuevas reservas antes de insertarlas en la BD)
    public ReservaFx(String nombres, String apellidos, LocalDate fecha, String cancha, String horario) {
        this(0, nombres, apellidos, fecha, cancha, horario); // El ID será asignado por la BD
    }

    // Constructor completo
    public ReservaFx(int id, String nombres, String apellidos,
            LocalDate fecha, String cancha, String horario) {
        this.id.set(id);
        this.nombres.set(nombres);
        this.apellidos.set(apellidos);
        this.fecha.set(fecha);
        this.cancha.set(cancha);
        this.horario.set(horario);
    }

    // Métodos Property (Esenciales para TableView)
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nombresProperty() {
        return nombres;
    }

    public StringProperty apellidosProperty() {
        return apellidos;
    }

    public ObjectProperty<LocalDate> fechaProperty() {
        return fecha;
    }

    public StringProperty canchaProperty() {
        return cancha;
    }

    public StringProperty horarioProperty() {
        return horario;
    }

    // Métodos Getters
    public int getId() {
        return id.get();
    }

    public String getNombres() {
        return nombres.get();
    }

    public String getApellidos() {
        return apellidos.get();
    }

    public LocalDate getFecha() {
        return fecha.get();
    }

    public String getCancha() {
        return cancha.get();
    }

    public String getHorario() {
        return horario.get();
    }

    // Métodos Setters 
    public void setId(int id) {
        this.id.set(id);
    }

    public void setNombres(String nombres) {
        this.nombres.set(nombres);
    }

    public void setApellidos(String apellidos) {
        this.apellidos.set(apellidos);
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }

    public void setCancha(String cancha) {
        this.cancha.set(cancha);
    }

    public void setHorario(String horario) {
        this.horario.set(horario);
    }

}
