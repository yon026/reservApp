package com.reservadecanchas.controller;

import com.reservadecanchas.model.ReservaFx;
import com.reservadecanchas.persistence.ReservaDAO;

import javafx.stage.Stage;
import com.reservadecanchas.util.CargadorVistas;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory; // Importante para las propiedades
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;

public class ReservasController {

    @FXML
    private void switchToEditarReserva(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CargadorVistas.cambiarVista(stage, "/com/reservadecanchas/app/editarReserva.fxml", "Editar Reserva");
    }

    @FXML
    private void switchToNuevaReserva(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        CargadorVistas.cambiarVista(stage, "/com/reservadecanchas/app/nuevaReserva.fxml", "Nueva Reserva");

    }

    @FXML
    private DatePicker dpFechaReserva;
    @FXML
    private TableView<ReservaFx> tvReservas; // El TableView ahora es de tipo Reserva
    @FXML
    private TableColumn<ReservaFx, String> colNombres; // Tipo de dato de la columna en la tabla y tipo de dato del valor que mostrará
    @FXML
    private TableColumn<ReservaFx, String> colApellidos;
    @FXML
    private TableColumn<ReservaFx, String> colSexo;
    @FXML
    private TableColumn<ReservaFx, Integer> colEdad; // Integer para edad
    @FXML
    private TableColumn<ReservaFx, LocalDate> colFecha; // LocalDate para fecha
    @FXML
    private TableColumn<ReservaFx, String> colCancha;
    @FXML
    private TableColumn<ReservaFx, String> colHorario;

    private ReservaDAO reservaDAO;

    @FXML
    public void initialize() {
        reservaDAO = new ReservaDAO();

        // **Configura las celdas de la tabla para usar PropertyValueFactory**
        // Esto le dice a cada columna qué método "Property()" de la clase Reserva
        // debe llamar para obtener el valor.
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colFecha.setCellFactory(column -> new TableCell<ReservaFx, LocalDate>() {
            private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate fecha, boolean empty) {
                super.updateItem(fecha, empty);
                setText(empty || fecha == null
                        ? ""
                        : fmt.format(fecha));
            }
        });

        colCancha.setCellValueFactory(new PropertyValueFactory<>("cancha"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

        // Establece la fecha actual por defecto y carga las reservas
        dpFechaReserva.setValue(LocalDate.now());
        loadReservasForDate(LocalDate.now());

        // Listener para cuando Rex cambie la fecha
        dpFechaReserva.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                loadReservasForDate(newDate);
            }
        });

        // 3) Listener para filtrar por fecha
        dpFechaReserva.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                List<ReservaFx> filtradas = reservaDAO.getReservasByFecha(newDate);
                tvReservas.setItems(FXCollections.observableArrayList(filtradas));
            }
        });
    }

    private void loadReservasForDate(LocalDate date) {
        List<ReservaFx> reservas = reservaDAO.getReservasByFecha(date);
        tvReservas.getItems().clear();
        tvReservas.getItems().addAll(reservas);

    }

    @FXML
    private void onVerTodasReservas(ActionEvent event) {
        // 1) Limpiar el DatePicker
        dpFechaReserva.setValue(null);

        // Obtiene todas las reservas y refresca la tabla
        List<ReservaFx> todas = reservaDAO.getAllReservas();
        ObservableList<ReservaFx> datos = FXCollections.observableArrayList(todas);
        tvReservas.setItems(datos);
    }
}
