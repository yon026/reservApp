package com.reservadecanchas.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {

    /**
     * Verifica si una tabla existe en la base de datos.
     *
     * @param conn La conexión a la base de datos.
     * @param tableName El nombre de la tabla a verificar.
     * @return true si la tabla existe, false en caso contrario.
     * @throws SQLException Si ocurre un error de SQL.
     */

    public static boolean tableExists(Connection conn, String tableName) throws SQLException {
        java.sql.DatabaseMetaData meta = conn.getMetaData();
        // rs.getString("TABLE_NAME") devolverá el nombre real.
        try (ResultSet rs = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
            return rs.next();
        }
    }

    public static void executeSqlScript(Connection conn, String scriptPath) throws SQLException, java.io.IOException {
        // Lee el script de un archivo (o un recurso dentro del JAR)
        // Ejemplo simplificado: asume que el script está en el classpath
        java.io.InputStream is = DatabaseUtils.class.getResourceAsStream(scriptPath);
        if (is == null) {
            throw new java.io.FileNotFoundException("Script SQL no encontrado: " + scriptPath);
        }
        String sqlScript = new String(is.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);

        String[] statements = sqlScript.split(";");

        try (Statement stmt = conn.createStatement()) {
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    stmt.execute(statement.trim());
                }
            }
        }
        System.out.println("Script SQL '" + scriptPath + "' ejecutado.");
    }
}
