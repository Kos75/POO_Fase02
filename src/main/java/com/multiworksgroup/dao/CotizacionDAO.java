package com.multiworksgroup.dao;

import com.multiworksgroup.model.Cotizacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CotizacionDAO extends DAO {

    // CREATE
    public boolean registrarCotizacion(Cotizacion cotizacion) {
        String sql = "INSERT INTO cotizacion(id_cliente, numero_cotizacion, horas_proyecto, fecha_inicio, fecha_fin, costos_asignados, costos_adicionales, total, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cotizacion.getIdCliente());
            stmt.setString(2, cotizacion.getNumeroCotizacion());
            stmt.setDouble(3, cotizacion.getHorasProyecto());
            stmt.setDate(4, new java.sql.Date(cotizacion.getFechaInicio().getTime()));
            stmt.setDate(5, new java.sql.Date(cotizacion.getFechaFin().getTime()));
            stmt.setDouble(6, cotizacion.getCostosAsignados());
            stmt.setDouble(7, cotizacion.getCostosAdicionales());
            stmt.setDouble(8, cotizacion.getTotal());
            stmt.setString(9, cotizacion.getEstado());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ ALL
    public List<Cotizacion> obtenerCotizaciones() {
        List<Cotizacion> cotizaciones = new ArrayList<>();
        String sql = "SELECT * FROM cotizacion WHERE estado != 'eliminado'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cotizacion cotizacion = new Cotizacion();
                cotizacion.setIdCotizacion(rs.getInt("id_cotizacion"));
                cotizacion.setIdCliente(rs.getInt("id_cliente"));
                cotizacion.setNumeroCotizacion(rs.getString("numero_cotizacion"));
                cotizacion.setHorasProyecto(rs.getDouble("horas_proyecto"));
                cotizacion.setFechaInicio(rs.getDate("fecha_inicio"));
                cotizacion.setFechaFin(rs.getDate("fecha_fin"));
                cotizacion.setCostosAsignados(rs.getDouble("costos_asignados"));
                cotizacion.setCostosAdicionales(rs.getDouble("costos_adicionales"));
                cotizacion.setTotal(rs.getDouble("total"));
                cotizacion.setEstado(rs.getString("estado"));

                cotizaciones.add(cotizacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cotizaciones;
    }

    // READ BY ID
    public Cotizacion obtenerCotizacionPorId(int id) {
        String sql = "SELECT * FROM cotizacion WHERE id_cotizacion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cotizacion cotizacion = new Cotizacion();
                    cotizacion.setIdCotizacion(rs.getInt("id_cotizacion"));
                    cotizacion.setIdCliente(rs.getInt("id_cliente"));
                    cotizacion.setNumeroCotizacion(rs.getString("numero_cotizacion"));
                    cotizacion.setHorasProyecto(rs.getDouble("horas_proyecto"));
                    cotizacion.setFechaInicio(rs.getDate("fecha_inicio"));
                    cotizacion.setFechaFin(rs.getDate("fecha_fin"));
                    cotizacion.setCostosAsignados(rs.getDouble("costos_asignados"));
                    cotizacion.setCostosAdicionales(rs.getDouble("costos_adicionales"));
                    cotizacion.setTotal(rs.getDouble("total"));
                    cotizacion.setEstado(rs.getString("estado"));

                    return cotizacion;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public boolean actualizarCotizacion(Cotizacion cotizacion) {
        String sql = "UPDATE cotizacion SET id_cliente=?, numero_cotizacion=?, horas_proyecto=?, fecha_inicio=?, fecha_fin=?, costos_asignados=?, costos_adicionales=?, total=?, estado=? WHERE id_cotizacion=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cotizacion.getIdCliente());
            stmt.setString(2, cotizacion.getNumeroCotizacion());
            stmt.setDouble(3, cotizacion.getHorasProyecto());
            stmt.setDate(4, new java.sql.Date(cotizacion.getFechaInicio().getTime()));
            stmt.setDate(5, new java.sql.Date(cotizacion.getFechaFin().getTime()));
            stmt.setDouble(6, cotizacion.getCostosAsignados());
            stmt.setDouble(7, cotizacion.getCostosAdicionales());
            stmt.setDouble(8, cotizacion.getTotal());
            stmt.setString(9, cotizacion.getEstado());
            stmt.setInt(10, cotizacion.getIdCotizacion());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE (soft delete)
    public boolean eliminarCotizacion(int id) {
        String sql = "UPDATE cotizacion SET estado='eliminado' WHERE id_cotizacion=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Calcular total de la cotización
    public double calcularTotal(double costosAsignados, double costosAdicionales) {
        return costosAsignados + costosAdicionales;
    }
}