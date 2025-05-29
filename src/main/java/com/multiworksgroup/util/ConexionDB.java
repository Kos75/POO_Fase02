package com.multiworksgroup.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    // Parámetros de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/multiworks";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver de MySQL (necesario solo en versiones antiguas de Java)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de MySQL: " + e.getMessage());
            throw new RuntimeException("No se encontró el driver JDBC", e);
        }

        // Retornar la conexión
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método para probar la conexión
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Conexión exitosa a la base de datos 'multiworks'");
            } else {
                System.out.println("No se pudo conectar a la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // Main para probar la conexión
    public static void main(String[] args) {
        testConnection();
    }
}
