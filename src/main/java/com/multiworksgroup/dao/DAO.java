package com.multiworksgroup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.multiworksgroup.util.ConexionDB;

public abstract class DAO {
    protected Connection connection;

    public DAO() {
        try {
            this.connection = ConexionDB.getConnection();
        } catch (SQLException e) {
            // Imprimir el error en consola
            e.printStackTrace();
            // Opcional: lanzar una excepción no verificada para cortar la ejecución si falla la conexión
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }

    protected void closeResources(PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
