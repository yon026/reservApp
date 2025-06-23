package com.reservadecanchas.controller;

import com.reservadecanchas.model.ReservaFx;
import com.reservadecanchas.persistence.ReferenciaDAO;
import com.reservadecanchas.persistence.ReservaDAO;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import com.reservadecanchas.util.ShowAlert;
import javafx.fxml.FXML;

public class NuevaReservaController implements Initializable {

    @FXML
    private TextField txtNuevaNombres;
    @FXML
    private TextField txtNuevaApellidos;
    @FXML
    private ComboBox<String> cbNuevaCancha;
    @FXML
    private ComboBox<String> cbNuevaHora;
    @FXML
    private DatePicker dpFechaNuevaReserva;

    private ReservaDAO reservaDAO;
    private ReferenciaDAO referenciaDAO;
    private LocalDate fechaInicial; // Para guardar la fecha que vino de la vista principal
    private LocalDate fechaReservaRealizada;

    // Constructor: Inicializa ambos DAO
    public NuevaReservaController() {
        this.reservaDAO = new ReservaDAO();
        this.referenciaDAO = new ReferenciaDAO();
    }

    // Método para recibir la fecha seleccionada desde la vista principal
    public void setFechaSeleccionada(LocalDate fecha) {
        this.fechaInicial = fecha;
        dpFechaNuevaReserva.setValue(fecha); // Establece la fecha en el DatePicker de la nueva ventana
    }

    public LocalDate getFechaReservaRealizada() {
        return fechaReservaRealizada;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Poblar ComboBoxes (igual que te expliqué antes)

        ObservableList<String> canchas = FXCollections.observableArrayList(referenciaDAO.getDescripcionesReferencia("Canchas", "nombreCancha"));
        cbNuevaCancha.setItems(canchas);
        if (!canchas.isEmpty()) {
            cbNuevaCancha.getSelectionModel().selectFirst();
        }

        ObservableList<String> horarios = FXCollections.observableArrayList(referenciaDAO.getDescripcionesReferencia("Horarios", "hora"));
        cbNuevaHora.setItems(horarios);
        if (!horarios.isEmpty()) {
            cbNuevaHora.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void saveReserva(ActionEvent event) {
        // 1. Validar la entrada de datos 
        if (txtNuevaNombres.getText().isEmpty() || txtNuevaApellidos.getText().isEmpty()
                || cbNuevaCancha.getValue() == null || cbNuevaHora.getValue() == null
                || dpFechaNuevaReserva.getValue() == null) {

            ShowAlert.show(Alert.AlertType.WARNING, "Campos Incompletos", "Por favor, complete todos los campos.");
            return;
        }

        // 2. Crear el objeto ReservaFx con los datos del formulario
        ReservaFx nuevaReserva = new ReservaFx(
                txtNuevaNombres.getText(),
                txtNuevaApellidos.getText(),
                dpFechaNuevaReserva.getValue(),
                cbNuevaCancha.getValue(),
                cbNuevaHora.getValue()
        );

        // 3. Llamar al DAO para insertar la reserva
        boolean insertado = reservaDAO.insertReserva(nuevaReserva);

        if (insertado) {
            ShowAlert.show(Alert.AlertType.INFORMATION, "Reserva Exitosa", "La reserva ha sido guardada correctamente.");
            this.fechaReservaRealizada = dpFechaNuevaReserva.getValue();// ¡Guardamos la fecha de la reserva!
            // 4. Cerrar la ventana de Nueva Reserva
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            ShowAlert.show(Alert.AlertType.ERROR, "Error al Reservar", "No se pudo guardar la reserva. Verifique si la cancha y el horario ya están ocupados para esa fecha.");
        }
    }

    @FXML
    private void cancelarNuevaReserva(ActionEvent event) {
        this.fechaReservaRealizada = null;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

}
