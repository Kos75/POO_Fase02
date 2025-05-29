package com.multiworksgroup.dao;

import com.multiworksgroup.model.Actividad;
import com.multiworksgroup.util.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAO extends DAO {

    // CREATE
    public boolean registrarActividad(Actividad actividad) {
        String sql = "INSERT INTO actividad(titulo, area_asignada, costo_por_hora, fecha_inicio, fecha_fin, cantidad_horas, costo_base, incremento_extra, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, actividad.getTitulo());
            stmt.setString(2, actividad.getAreaAsignada());
            stmt.setDouble(3, actividad.getCostoPorHora());
            stmt.setTimestamp(4, new java.sql.Timestamp(actividad.getFechaInicio().getTime()));
            stmt.setTimestamp(5, new java.sql.Timestamp(actividad.getFechaFin().getTime()));
            stmt.setDouble(6, actividad.getCantidadHoras());
            stmt.setDouble(7, actividad.getCostoBase());
            stmt.setDouble(8, actividad.getIncrementoExtra());
            stmt.setDouble(9, actividad.getTotal());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ ALL
    public List<Actividad> obtenerActividades() {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT * FROM actividad";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Actividad actividad = new Actividad();
                actividad.setIdActividad(rs.getInt("id_actividad"));
                actividad.setTitulo(rs.getString("titulo"));
                actividad.setAreaAsignada(rs.getString("area_asignada"));
                actividad.setCostoPorHora(rs.getDouble("costo_por_hora"));
                actividad.setFechaInicio(rs.getTimestamp("fecha_inicio"));
                actividad.setFechaFin(rs.getTimestamp("fecha_fin"));
                actividad.setCantidadHoras(rs.getDouble("cantidad_horas"));
                actividad.setCostoBase(rs.getDouble("costo_base"));
                actividad.setIncrementoExtra(rs.getDouble("incremento_extra"));
                actividad.setTotal(rs.getDouble("total"));

                actividades.add(actividad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actividades;
    }

    // READ BY ID
    public Actividad obtenerActividadPorId(int id) {
        String sql = "SELECT * FROM actividad WHERE id_actividad = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Actividad actividad = new Actividad();
                actividad.setIdActividad(rs.getInt("id_actividad"));
                actividad.setTitulo(rs.getString("titulo"));
                actividad.setAreaAsignada(rs.getString("area_asignada"));
                actividad.setCostoPorHora(rs.getDouble("costo_por_hora"));
                actividad.setFechaInicio(rs.getTimestamp("fecha_inicio"));
                actividad.setFechaFin(rs.getTimestamp("fecha_fin"));
                actividad.setCantidadHoras(rs.getDouble("cantidad_horas"));
                actividad.setCostoBase(rs.getDouble("costo_base"));
                actividad.setIncrementoExtra(rs.getDouble("incremento_extra"));
                actividad.setTotal(rs.getDouble("total"));

                return actividad;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public boolean actualizarActividad(Actividad actividad) {
        String sql = "UPDATE actividad SET titulo=?, area_asignada=?, costo_por_hora=?, fecha_inicio=?, fecha_fin=?, cantidad_horas=?, costo_base=?, incremento_extra=?, total=? WHERE id_actividad=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, actividad.getTitulo());
            stmt.setString(2, actividad.getAreaAsignada());
            stmt.setDouble(3, actividad.getCostoPorHora());
            stmt.setTimestamp(4, new java.sql.Timestamp(actividad.getFechaInicio().getTime()));
            stmt.setTimestamp(5, new java.sql.Timestamp(actividad.getFechaFin().getTime()));
            stmt.setDouble(6, actividad.getCantidadHoras());
            stmt.setDouble(7, actividad.getCostoBase());
            stmt.setDouble(8, actividad.getIncrementoExtra());
            stmt.setDouble(9, actividad.getTotal());
            stmt.setInt(10, actividad.getIdActividad());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE (soft delete o eliminar directamente)
    public boolean eliminarActividad(int id) {
        String sql = "DELETE FROM actividad WHERE id_actividad=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Calcular total de la actividad
    public double calcularTotal(double costoBase, double incrementoExtra) {
        return costoBase + incrementoExtra;
    }
}