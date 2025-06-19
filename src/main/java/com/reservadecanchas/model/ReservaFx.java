package com.reservadecanchas.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class ReservaFx {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombres = new SimpleStringProperty();
    private final StringProperty apellidos = new SimpleStringProperty();
    private final StringProperty sexo = new SimpleStringProperty();
    private final IntegerProperty edad = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<>();
    private final StringProperty cancha = new SimpleStringProperty();
    private final StringProperty horario = new SimpleStringProperty();

    // Constructor completo
    public ReservaFx(int id, String nombres, String apellidos, String sexo, int edad,
            LocalDate fecha, String cancha, String horario) {
        this.id.set(id);
        this.nombres.set(nombres);
        this.apellidos.set(apellidos);
        this.sexo.set(sexo);
        this.edad.set(edad);
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

    public StringProperty sexoProperty() {
        return sexo;
    }

    public IntegerProperty edadProperty() {
        return edad;
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

    public String getSexo() {
        return sexo.get();
    }

    public int getEdad() {
        return edad.get();
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

    public void setSexo(String sexo) {
        this.sexo.set(sexo);
    }

    public void setEdad(int edad) {
        this.edad.set(edad);
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
