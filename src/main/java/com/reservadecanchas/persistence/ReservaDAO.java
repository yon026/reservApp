/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.reservadecanchas.persistence;

import com.reservadecanchas.model.ReservaFx;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cafe
 */
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
                + "    S.descripcion AS Sexo, "
                + "    R.edad, "
                + "    R.fecha, "
                + "    C.nombreCancha AS Cancha, "
                + "    H.hora AS Horario "
                + "FROM "
                + "    Reservas R "
                + "JOIN "
                + "    Sexos S ON R.fksexo = S.id "
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
                            rs.getString("Sexo"),
                            rs.getInt("edad"),
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
                + "    S.descripcion AS Sexo, "
                + "    R.edad, "
                + "    R.fecha, "
                + "    C.nombreCancha AS Cancha, "
                + "    H.hora AS Horario "
                + "FROM "
                + "    Reservas R "
                + "JOIN "
                + "    Sexos S ON R.fksexo = S.id "
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
                            rs.getString("Sexo"),
                            rs.getInt("edad"),
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

}
