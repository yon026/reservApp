package com.reservadecanchas.persistence;

import com.reservadecanchas.model.ReservaFx;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ReservaDAO {

    public List<ReservaFx> getReservasByFecha(LocalDate fecha) {
        List<ReservaFx> reservas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT "
                + "    R.id, "
                + // Ahora sí seleccionamos el ID para el objeto Reserva
                "    R.nombres, "
                + "    R.apellidos, "
                + "    R.fecha, "
                + "    C.nombreCancha AS Cancha, "
                + "    H.hora AS Horario "
                + "FROM "
                + "    Reservas R "
                + "JOIN "
                + "    Canchas C ON R.fkcancha = C.id "
                + "JOIN "
                + "    Horarios H ON R.fkhorario = H.id "
                + "WHERE "
                + "    R.fecha = ? "
                + "ORDER BY "
                + "    C.nombreCancha, H.hora";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setDate(1, java.sql.Date.valueOf(fecha)); // Convierte LocalDate a java.sql.Date
                rs = pstmt.executeQuery();

                while (rs.next()) {

                    // Creamos un objeto Reserva con los datos del ResultSet
                    ReservaFx reserva = new ReservaFx(
                            rs.getInt("id"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getDate("fecha").toLocalDate(), // Convierte java.sql.Date a LocalDate
                            rs.getString("Cancha"),
                            rs.getTime("Horario").toLocalTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) // Formatea a HH:MM
                    );
                    reservas.add(reserva);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener reservas: " + e.getMessage());
            // Manejar la excepción, quizás relanzar una AppException
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return reservas;
    }

    public List<ReservaFx> getAllReservas() {
        List<ReservaFx> reservas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT "
                + "    R.id, "
                + // Ahora sí seleccionamos el ID para el objeto Reserva
                "    R.nombres, "
                + "    R.apellidos, "
                + "    R.fecha, "
                + "    C.nombreCancha AS Cancha, "
                + "    H.hora AS Horario "
                + "FROM "
                + "    Reservas R "
                + "JOIN "
                + "    Canchas C ON R.fkcancha = C.id "
                + "JOIN "
                + "    Horarios H ON R.fkhorario = H.id "
                + "ORDER BY "
                + "    R.fecha, C.nombreCancha, H.hora ";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();

                while (rs.next()) {

                    // Creamos un objeto Reserva con los datos del ResultSet
                    ReservaFx reserva = new ReservaFx(
                            rs.getInt("id"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getDate("fecha").toLocalDate(), // Convierte java.sql.Date a LocalDate
                            rs.getString("Cancha"),
                            rs.getTime("Horario").toLocalTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) // Formatea a HH:MM
                    );
                    reservas.add(reserva);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener reservas: " + e.getMessage());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return reservas;
    }

    /**
     * Inserta una nueva reserva en la base de datos. Asigna el ID generado por
     * la base de datos al objeto ReservaFx después de la inserción.
     *
     * @param reserva El objeto ReservaFx a insertar (el ID será 0 o nulo antes
     * de la inserción).
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean insertReserva(ReservaFx reserva) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rsGeneratedKeys = null; // Para capturar el ID generado

        // Consulta SQL para insertar una nueva reserva.
        // NOTA: No incluimos 'id' en el INSERT porque es autoincremental.
        // Usamos Placeholders (?) para todos los valores.
        // RETURNING id o Statement.RETURN_GENERATED_KEYS son para obtener el ID generado.
        String sql = "INSERT INTO Reservas (nombres, apellidos, fecha , fkcancha, fkhorario) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn != null) {
                // Preparamos la declaración para que nos devuelva las claves generadas (el ID)
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                // Mapeamos los valores del objeto ReservaFx a los placeholders (?)
                // Necesitamos obtener los IDs de las tablas de referencia (Sexos, Canchas, Horarios)
                // 2. Obtener ID de Cancha desde la descripción (ej: "Futbol" -> 1)
                int fkCancha = getIdFromDescripcion("Canchas", "nombreCancha", reserva.getCancha());
                // 3. Obtener ID de Horario desde la descripción (ej: "16:00" -> 1)
                int fkHorario = getIdFromDescripcion("Horarios", "hora", reserva.getHorario());

                pstmt.setString(1, reserva.getNombres());
                pstmt.setString(2, reserva.getApellidos());
                pstmt.setDate(3, Date.valueOf(reserva.getFecha())); // Convierte LocalDate a java.sql.Date
                pstmt.setInt(4, fkCancha);      // ID de la cancha
                pstmt.setInt(5, fkHorario);     // ID del horario

                // Ejecutamos la inserción
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    // Si se insertó al menos una fila, intentamos obtener el ID generado
                    rsGeneratedKeys = pstmt.getGeneratedKeys();
                    if (rsGeneratedKeys.next()) {
                        int idGenerado = rsGeneratedKeys.getInt(1); // El primer campo es el ID generado
                        reserva.setId(idGenerado); // Asignamos el ID a nuestro objeto ReservaFx
                        System.out.println("Reserva insertada con ID: " + idGenerado);
                        return true; // Inserción exitosa
                    }
                }
            }
        } catch (SQLException e) {
            // Manejo de errores: por ejemplo, si la reserva ya existe (UNIQUE constraint violation)
            if (e.getErrorCode() == 2627) { // SQL Server error code for unique constraint violation
                System.err.println("Error: Ya existe una reserva para esa cancha, fecha y horario.");

            } else {
                System.err.println("Error SQL al insertar reserva: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Error general al insertar reserva: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rsGeneratedKeys != null) {
                    rsGeneratedKeys.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en insertReserva: " + e.getMessage());
            }
        }
        return false; // Si llegamos aquí, la inserción no fue exitosa
    }

    /**
     * Método auxiliar para obtener el ID de una tabla de referencia a partir de
     * su descripción. Este método es NECESARIO porque el objeto ReservaFx
     * guarda descripciones (ej. "Masculino"), pero la base de datos necesita
     * los IDs (ej. 1).
     *
     * @param tableName Nombre de la tabla de referencia (ej. "Sexos",
     * "Canchas", "Horarios").
     * @param columnName Nombre de la columna de descripción (ej. "descripcion",
     * "nombreCancha", "hora").
     * @param description La descripción a buscar (ej. "Masculino", "Futbol",
     * "16:00").
     * @return El ID correspondiente a la descripción, o -1 si no se encuentra.
     */
    private int getIdFromDescripcion(String tableName, String columnName, String description) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int id = -1; // Valor por defecto si no se encuentra

        String sql = "SELECT id FROM " + tableName + " WHERE " + columnName + " = ?";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, description);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    id = rs.getInt("id");
                }
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en getIdFromDescripcion: " + e.getMessage());
            }
        }
        return id;
    }

    /**
     * Actualiza una reserva existente en la base de datos.
     *
     * @param reserva El objeto ReservaFx con los datos actualizados (debe tener
     * un ID válido).
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean editarReserva(ReservaFx reserva) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        // La consulta SQL para actualizar la reserva.
        // Usamos SET para cada columna que puede ser actualizada, y WHERE id = ? para especificar cuál.
        String sql = "UPDATE Reservas SET nombres = ?, apellidos = ?,"
                + "fecha = ?, fkcancha = ?, fkhorario = ? WHERE id = ?";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(sql);

                // Obtenemos los IDs de las tablas de referencia, igual que en insertReserva
                int fkCancha = getIdFromDescripcion("Canchas", "nombreCancha", reserva.getCancha());
                int fkHorario = getIdFromDescripcion("Horarios", "hora", reserva.getHorario());

                // Mapeamos los valores del objeto ReservaFx a los placeholders (?)
                pstmt.setString(1, reserva.getNombres());
                pstmt.setString(2, reserva.getApellidos());
                pstmt.setDate(3, Date.valueOf(reserva.getFecha()));
                pstmt.setInt(4, fkCancha);
                pstmt.setInt(5, fkHorario);
                pstmt.setInt(6, reserva.getId()); // ¡Importante! El ID para la cláusula WHERE

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Reserva actualizada con ID: " + reserva.getId());
                    return true; // Actualización exitosa
                }
            }
        } catch (SQLException e) {
            // Manejo de errores similar a insertReserva
            if (e.getErrorCode() == 2627) { // SQL Server error code for unique constraint violation
                System.err.println("Error: Ya existe una reserva para esa cancha, fecha y horario.");
            } else {
                System.err.println("Error SQL al actualizar reserva: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Error general al actualizar reserva: " + e.getMessage());
            e.printStackTrace();
        } finally {

            try {
                if (pstmt != null) {
                    pstmt.close();
                }

            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en updateReserva: " + e.getMessage());
            }
        }
        return false; // Si llegamos aquí, la actualización no fue exitosa
    }

    /**
     * Elimina una reserva de la base de datos.
     *
     * @param idReserva El ID de la reserva a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean deleteReserva(int idReserva) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM Reservas WHERE id = ?";

        try {
            conn = DatabaseConnection.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, idReserva);

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Reserva eliminada con ID: " + idReserva);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al eliminar reserva: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general al eliminar reserva: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en deleteReserva: " + e.getMessage());
            }
        }
        return false; // Si llegamos aquí, la eliminación no fue exitosa
    }
}
