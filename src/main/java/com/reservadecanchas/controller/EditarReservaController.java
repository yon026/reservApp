package com.reservadecanchas.controller;

import com.reservadecanchas.model.ReservaFx;
import com.reservadecanchas.persistence.ReferenciaDAO;
import com.reservadecanchas.persistence.ReservaDAO;
import com.reservadecanchas.util.ShowAlert;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EditarReservaController implements Initializable {

    @FXML
    private TextField txtEditarId; // Opcional, si lo tienes en el FXML
    @FXML
    private TextField txtEditarNombres;
    @FXML
    private TextField txtEditarApellidos;
    @FXML
    private ComboBox<String> cbEditarCancha;
    @FXML
    private ComboBox<String> cbEditarHora;
    @FXML
    private DatePicker dpFechaEditarReserva;

    private ReservaDAO reservaDAO;
    private ReferenciaDAO referenciaDAO;

    private ReservaFx reservaAEditar; // La reserva que se está editando
    private LocalDate fechaReservaActualizada; // Para devolver la fecha al controlador principal

    public EditarReservaController() {
        this.reservaDAO = new ReservaDAO();
        this.referenciaDAO = new ReferenciaDAO();
    }

    /**
     * Método para recibir la reserva que se va a editar desde la vista
     *
     * @param reserva La ReservaFx con los datos a cargar.
     */
    public void setReservaAEditar(ReservaFx reserva) {

        this.reservaAEditar = reserva;

        // Cargar los datos de la reserva en los campos del formulario
        if (txtEditarId != null) { // Solo si tienes txtEditarId en tu FXML
            txtEditarId.setText(String.valueOf(reserva.getId()));
        }
        txtEditarNombres.setText(reserva.getNombres());
        txtEditarApellidos.setText(reserva.getApellidos());
        dpFechaEditarReserva.setValue(reserva.getFecha());
        cbEditarCancha.getSelectionModel().select(reserva.getCancha());
        cbEditarHora.getSelectionModel().select(reserva.getHorario());
    }

    /**
     * Getter para que el controlador principal pueda obtener la fecha de la
     * reserva actualizada.
     */
    public LocalDate getFechaReservaActualizada() {
        return fechaReservaActualizada;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Poblar ComboBoxes (igual que en NuevaReservaController)
        ObservableList<String> canchas = FXCollections.observableArrayList(referenciaDAO.getDescripcionesReferencia("Canchas", "nombreCancha"));
        cbEditarCancha.setItems(canchas);

        ObservableList<String> horarios = FXCollections.observableArrayList(referenciaDAO.getDescripcionesReferencia("Horarios", "hora"));
        cbEditarHora.setItems(horarios);

    }

    @FXML
    private void actualizarReserva(ActionEvent event) {
        // 1. Validar la entrada de datos (¡Similar a NuevaReservaController!)
        if (txtEditarNombres.getText().isEmpty() || txtEditarApellidos.getText().isEmpty()
                || cbEditarCancha.getValue() == null || cbEditarHora.getValue() == null
                || dpFechaEditarReserva.getValue() == null) {

            ShowAlert.show(Alert.AlertType.WARNING, "Campos Incompletos", "Por favor, complete todos los campos.");
            return;
        }


        // Asegurarse de que hay una reserva para editar
        if (reservaAEditar == null) {
            ShowAlert.show(Alert.AlertType.ERROR, "Error de Edición", "No se ha cargado ninguna reserva para editar.");
            return;
        }

        // 2. Actualizar el objeto ReservaFx con los nuevos datos del formulario
        reservaAEditar.setNombres(txtEditarNombres.getText());
        reservaAEditar.setApellidos(txtEditarApellidos.getText());
        reservaAEditar.setFecha(dpFechaEditarReserva.getValue());
        reservaAEditar.setCancha(cbEditarCancha.getValue());
        reservaAEditar.setHorario(cbEditarHora.getValue());

        // 3. Llamar al DAO para actualizar la reserva
        boolean actualizado = reservaDAO.editarReserva(reservaAEditar);

        if (actualizado) {
            ShowAlert.show(Alert.AlertType.INFORMATION, "Edición Exitosa", "La reserva ha sido actualizada correctamente.");
            this.fechaReservaActualizada = reservaAEditar.getFecha(); // Guardamos la fecha de la reserva actualizada
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close(); // Cerrar la ventana
        } else {
            ShowAlert.show(Alert.AlertType.ERROR, "Error al Actualizar", "No se pudo actualizar la reserva. Verifique si la cancha y el horario ya están ocupados para esa fecha.");
            // No cerrar la ventana si hubo un error para que el usuario pueda corregir
        }
    }

    @FXML
    private void cancelarEditarReserva(ActionEvent event) {
        this.fechaReservaActualizada = null;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
}
