package com.reservadecanchas.controller;

import com.reservadecanchas.model.ReservaFx;
import com.reservadecanchas.persistence.ReservaDAO;
import javafx.stage.Stage;
import com.reservadecanchas.util.CargadorVistas;
import com.reservadecanchas.util.ShowAlert;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.stage.Modality;

public class ReservasController {

    @FXML
    private DatePicker dpFechaPrincipal;
    @FXML
    private TableView<ReservaFx> tvReservas; // El TableView es de tipo Reserva
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
        dpFechaPrincipal.setValue(LocalDate.now());
        loadReservasForDate(LocalDate.now());

        // Listener para cuando el usuario cambie la fecha
        dpFechaPrincipal.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                loadReservasForDate(newDate);
            }
        });

        // 3) Listener para filtrar por fecha
        dpFechaPrincipal.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                List<ReservaFx> filtradas = reservaDAO.getReservasByFecha(newDate);
                tvReservas.setItems(FXCollections.observableArrayList(filtradas));
            }
        });
    }

    // Método refrescar TableView
    private void cargarReservas(LocalDate fecha) {
        List<ReservaFx> reservas = reservaDAO.getReservasByFecha(fecha);
        tvReservas.getItems().setAll(reservas);
    }

    private void loadReservasForDate(LocalDate date) {
        List<ReservaFx> reservas = reservaDAO.getReservasByFecha(date);
        tvReservas.getItems().clear();
        tvReservas.getItems().addAll(reservas);

    }

    @FXML
    private void switchToNuevaReserva(ActionEvent event) {
        try {
            // 1. Usar el CargadorVistas para obtener el FXMLLoader
            FXMLLoader loader = CargadorVistas.getLoader("/com/reservadecanchas/app/nuevaReserva.fxml");

            // 2. Obtener el controlador de la nueva vista ANTES de mostrarla
            NuevaReservaController nuevaReservaController = loader.getController();

            // 3. Pasar la fecha seleccionada del DatePicker principal al controlador de la nueva vista
            nuevaReservaController.setFechaSeleccionada(dpFechaPrincipal.getValue());

            // 4. Configurar la nueva Stage (ventana) para que sea modal
            Stage newStage = new Stage();
            newStage.setTitle("Nueva Reserva");
            newStage.setScene(new Scene(loader.getRoot())); // loader.getRoot()  da el Parent
            newStage.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow()); // La Stage padre
            newStage.initModality(Modality.APPLICATION_MODAL); // Bloquea la aplicación
            newStage.setResizable(false); // No redimensionable

            // 5. Mostrar la nueva ventana y esperar a que se cierre
            newStage.showAndWait(); // ¡La ejecución se pausa aquí!

            // 6. Una vez que la ventana de nueva reserva se cierra, recuperamos la fecha
            //    de la reserva realizada desde el controlador de la ventana modal
            LocalDate fechaParaRefrescar = nuevaReservaController.getFechaReservaRealizada();

            // 7. Refrescar la tabla principal con la fecha correcta
            if (fechaParaRefrescar != null) { // Si una reserva fue guardada
                dpFechaPrincipal.setValue(fechaParaRefrescar); // Cambiar el DatePicker principal
                cargarReservas(fechaParaRefrescar); // Recargar la tabla para esa fecha
            } else {
                // Si la reserva fue cancelada o no se guardó, simplemente recargamos para
                // Obtiene todas las reservas y refresca la tabla
                List<ReservaFx> todas = reservaDAO.getAllReservas();
                ObservableList<ReservaFx> datos = FXCollections.observableArrayList(todas);
                tvReservas.setItems(datos);
            }

        } catch (IOException e) {
            System.err.println("Error al cargar la ventana de Nueva Reserva: " + e.getMessage());
            e.printStackTrace();
            ShowAlert.show(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de nueva reserva.");

        }
    }

    @FXML
    private void switchToEditarReserva(ActionEvent event) {

        ReservaFx reservaSeleccionada = tvReservas.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada == null) {
            ShowAlert.show(Alert.AlertType.WARNING, "Ninguna Reserva Seleccionada",
                    "Por favor, seleccione una reserva de la tabla para editar.");
            return;
        }

        try {
            // 1. Usar el CargadorVistas para obtener el FXMLLoader para la vista de edición
            FXMLLoader loader = CargadorVistas.getLoader("/com/reservadecanchas/app/editarReserva.fxml");

            // 2. Obtener el controlador de la vista de edición ANTES de mostrarla
            EditarReservaController editarReservaController = loader.getController();

            // 3. Pasar la reserva seleccionada al controlador de edición
            editarReservaController.setReservaAEditar(reservaSeleccionada);

            // 4. Configurar la nueva Stage (ventana) para que sea modal
            Stage newStage = new Stage();
            newStage.setTitle("Editar Reserva"); // Título específico para edición
            newStage.setScene(new Scene(loader.getRoot()));
            newStage.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setResizable(false);

            // 5. Mostrar la nueva ventana y esperar a que se cierre
            newStage.showAndWait(); // La ejecución se pausa aquí

            // 6. Una vez que la ventana de edición se cierra, recuperamos la fecha
            //    de la reserva actualizada (si hubo una actualización exitosa)
            LocalDate fechaParaRefrescar = editarReservaController.getFechaReservaActualizada();

            // 7. Refrescar la tabla principal con la fecha correcta
            if (fechaParaRefrescar != null) { // Si la reserva fue actualizada
                dpFechaPrincipal.setValue(fechaParaRefrescar); // Cambiar el DatePicker principal
                cargarReservas(fechaParaRefrescar); // Recargar la tabla para esa fecha
            } else {
                // Obtiene todas las reservas y refresca la tabla
                List<ReservaFx> todas = reservaDAO.getAllReservas();
                ObservableList<ReservaFx> datos = FXCollections.observableArrayList(todas);
                tvReservas.setItems(datos);

            }

        } catch (IOException e) {
            System.err.println("Error al cargar la ventana de Editar Reserva: " + e.getMessage());
            e.printStackTrace();
            ShowAlert.show(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de edición de reserva.");
        }
    }

    @FXML
    private void onVerTodas(ActionEvent event) {
        // 1) Limpiar el DatePicker
        dpFechaPrincipal.setValue(null);

        // Obtiene todas las reservas y refresca la tabla
        List<ReservaFx> todas = reservaDAO.getAllReservas();
        ObservableList<ReservaFx> datos = FXCollections.observableArrayList(todas);
        tvReservas.setItems(datos);
    }

    @FXML
    private void onEliminar() {
        ReservaFx reservaSeleccionada = tvReservas.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada == null) {
            // Muestra una alerta si no hay ninguna reserva seleccionada
            ShowAlert.show(Alert.AlertType.WARNING, "Ninguna Reserva Seleccionada",
                    "Por favor, seleccione una reserva de la tabla para eliminar.");
            return; // Sale del método
        }

        // --- Paso de Confirmación ---
        // Crea un diálogo de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminacion");
        alert.setHeaderText("Está a punto de eliminar una reserva.");
        alert.setContentText("¿Está seguro de que desea eliminar la reserva de "
                + reservaSeleccionada.getNombres() + " "
                + reservaSeleccionada.getApellidos() + " para el "
                + reservaSeleccionada.getFecha().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " en la cancha de " + reservaSeleccionada.getCancha() + "?");

        // Muestra el diálogo y espera la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();

        // Verifica si el usuario hizo clic en OK (Confirmar)
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // El usuario confirmó la eliminación, procede con la lógica
            boolean eliminado = reservaDAO.deleteReserva(reservaSeleccionada.getId());

            if (eliminado) {
                // Muestra un mensaje de éxito
                ShowAlert.show(Alert.AlertType.INFORMATION, "Eliminación Exitosa", "La reserva ha sido eliminada correctamente.");
                // Obtiene todas las reservas y refresca la tabla
                List<ReservaFx> todas = reservaDAO.getAllReservas();
                ObservableList<ReservaFx> datos = FXCollections.observableArrayList(todas);
                tvReservas.setItems(datos);
            } else {
                // Muestra un mensaje de error si la eliminación falló
                ShowAlert.show(Alert.AlertType.ERROR, "Error al Eliminar", "No se pudo eliminar la reserva. Intente de nuevo.");
            }
        } else {
            // El usuario canceló la eliminación
            System.out.println("Eliminación cancelada por el usuario.");
        }
    }

}
