package com.reservadecanchas.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReferenciaDAO {

    /**
     * Obtiene una lista de descripciones de una tabla de referencia.
     *
     * @param tableName El nombre de la tabla de referencia (ej. "Sexos",
     * "Canchas", "Horarios").
     * @param columnName La columna que contiene la descripción (ej.
     * "descripcion", "nombreCancha", "hora").
     * @return Una lista de Strings con las descripciones.
     */
    public List<String> getDescripcionesReferencia(String tableName, String columnName) {
        List<String> descripciones = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT " + columnName + " FROM " + tableName + " ORDER BY " + columnName;

        try {
            conn = DatabaseConnection.getConnection();
            if (conn != null) {
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    // Para Horarios, si la columna es TIME, la formateamos a String HH:mm
                    if (tableName.equalsIgnoreCase("Horarios") && columnName.equalsIgnoreCase("hora")) {
                        LocalTime horarioTime = rs.getTime(columnName).toLocalTime();
                        descripciones.add(horarioTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
                    } else {
                        descripciones.add(rs.getString(columnName));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener descripciones de " + tableName + ": " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en getDescripcionesReferencia: " + e.getMessage());
            }
        }
        return descripciones;
    }
}
