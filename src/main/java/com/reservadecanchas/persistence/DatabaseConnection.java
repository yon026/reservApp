package com.reservadecanchas.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String USER = "TestUserApp";
    private static final String PASSWORD = "Contrasenia!";
    private static final String DATABASE_NAME = "ReservaBD";
    private static final String SERVER_IP = "localhost";
    private static final String PORT = "1433";

    private static Connection connection = null; // Usamos 'static' para una única instancia de conexión (Singleton)

    private static final String CONNECTION_STRING = "jdbc:sqlserver://" + SERVER_IP + ":" + PORT + ";"
            + "databaseName=" + DATABASE_NAME
            + ";encrypt=true;trustServerCertificate=true"; // Parámetros de seguridad

    // Constructor privado para implementar el patrón Singleton
    private DatabaseConnection() {
    }

    // Método para obtener la única instancia de la conexión
    public static Connection getConnection() {
        try {
            // Si la conexión es nula o está cerrada, la establecemos
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
                System.out.println("Conexión a la BD establecida correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error de SQL al conectar a la BD: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver de SQL Server no encontrado. Asegúrate de tener la dependencia en Maven.");
            System.err.println(e.getMessage());
        }
        return connection;
    }

    // Método para cerrar la conexión
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión a la BD cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión a la BD: " + e.getMessage());
        } finally {
            connection = null; // Permitir una nueva conexión si se necesita
        }
    }
}
