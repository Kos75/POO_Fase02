package com.multiworksgroup.dao;

import com.multiworksgroup.model.AsignacionTarea;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignacionTareaDAO extends DAO {

    // CREATE
    public boolean registrarAsignacion(AsignacionTarea asignacion) {
        String sql = "INSERT INTO asignacion_tarea(id_empleado, id_actividad, titulo, descripcion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, asignacion.getIdEmpleado());
            stmt.setInt(2, asignacion.getIdActividad());
            stmt.setString(3, asignacion.getTitulo());
            stmt.setString(4, asignacion.getDescripcion());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ ALL
    public List<AsignacionTarea> obtenerAsignaciones() {
        List<AsignacionTarea> asignaciones = new ArrayList<>();
        String sql = "SELECT * FROM asignacion_tarea";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AsignacionTarea asignacion = new AsignacionTarea();
                asignacion.setIdAsignacion(rs.getInt("id_asignacion"));
                asignacion.setIdEmpleado(rs.getInt("id_empleado"));
                asignacion.setIdActividad(rs.getInt("id_actividad"));
                asignacion.setTitulo(rs.getString("titulo"));
                asignacion.setDescripcion(rs.getString("descripcion"));

                asignaciones.add(asignacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignaciones;
    }

    // READ BY ID
    public AsignacionTarea obtenerAsignacionPorId(int id) {
        String sql = "SELECT * FROM asignacion_tarea WHERE id_asignacion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AsignacionTarea asignacion = new AsignacionTarea();
                    asignacion.setIdAsignacion(rs.getInt("id_asignacion"));
                    asignacion.setIdEmpleado(rs.getInt("id_empleado"));
                    asignacion.setIdActividad(rs.getInt("id_actividad"));
                    asignacion.setTitulo(rs.getString("titulo"));
                    asignacion.setDescripcion(rs.getString("descripcion"));
                    return asignacion;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public boolean actualizarAsignacion(AsignacionTarea asignacion) {
        String sql = "UPDATE asignacion_tarea SET id_empleado=?, id_actividad=?, titulo=?, descripcion=? WHERE id_asignacion=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, asignacion.getIdEmpleado());
            stmt.setInt(2, asignacion.getIdActividad());
            stmt.setString(3, asignacion.getTitulo());
            stmt.setString(4, asignacion.getDescripcion());
            stmt.setInt(5, asignacion.getIdAsignacion());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean eliminarAsignacion(int id) {
        String sql = "DELETE FROM asignacion_tarea WHERE id_asignacion=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}