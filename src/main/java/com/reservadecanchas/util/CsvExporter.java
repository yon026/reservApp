package com.reservadecanchas.util;

import com.reservadecanchas.model.ReservaFx;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CsvExporter {
    
    public static void exportarReservas(List<ReservaFx> reservas, String rutaArchivo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.append("Nombres,Apellidos,Fecha,Cancha,Horario\n");
            
            for (ReservaFx r : reservas) {
                writer.append(r.getFecha().format(formatter) + ",");
                writer.append(r.getCancha() + ",");
                writer.append(r.getHorario() + ",");
                writer.append(r.getNombres() + ",");
                writer.append(r.getApellidos() + "\n");
            }
            
            System.out.println("CSV exportado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al escribir archivo CSV: " + e.getMessage());
        }
    }
}
