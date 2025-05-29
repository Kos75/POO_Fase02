package com.multiworksgroup.dao;

import com.multiworksgroup.model.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO extends DAO {

    // CREATE
    public boolean registrarEmpleado(Empleado empleado) {
        String sqlPersona = "INSERT INTO persona(nombre_completo, documento_identidad, tipo_persona, telefono, correo_electronico, direccion) VALUES (?, ?, 'empleado', ?, ?, ?)";
        try (PreparedStatement stmtPersona = connection.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
            stmtPersona.setString(1, empleado.getNombreCompleto());
            stmtPersona.setString(2, empleado.getDocumentoIdentidad());
            stmtPersona.setString(3, empleado.getTelefono());
            stmtPersona.setString(4, empleado.getCorreoElectronico());
            stmtPersona.setString(5, empleado.getDireccion());

            int rowsAffected = stmtPersona.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmtPersona.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idPersona = generatedKeys.getInt(1);

                        // Insert into empleado table
                        String sqlEmpleado = "INSERT INTO empleado(id_empleado, tipo_contratacion) VALUES (?, ?)";
                        try (PreparedStatement stmtEmpleado = connection.prepareStatement(sqlEmpleado)) {
                            stmtEmpleado.setInt(1, idPersona);
                            stmtEmpleado.setString(2, empleado.getTipoContratacion());
                            stmtEmpleado.executeUpdate();
                        }
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ ALL
    public List<Empleado> obtenerEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT p.id_persona, p.nombre_completo, p.documento_identidad, p.telefono, p.correo_electronico, p.direccion, e.tipo_contratacion FROM persona p INNER JOIN empleado e ON p.id_persona = e.id_empleado WHERE p.estado = 'activo'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdPersona(rs.getInt("id_persona"));
                empleado.setNombreCompleto(rs.getString("nombre_completo"));
                empleado.setDocumentoIdentidad(rs.getString("documento_identidad"));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setCorreoElectronico(rs.getString("correo_electronico"));
                empleado.setDireccion(rs.getString("direccion"));
                empleado.setTipoContratacion(rs.getString("tipo_contratacion"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    // READ BY ID
    public Empleado obtenerEmpleadoPorId(int id) {
        String sql = "SELECT p.id_persona, p.nombre_completo, p.documento_identidad, p.telefono, p.correo_electronico, p.direccion, e.tipo_contratacion FROM persona p INNER JOIN empleado e ON p.id_persona = e.id_empleado WHERE p.id_persona = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Empleado empleado = new Empleado();
                    empleado.setIdPersona(rs.getInt("id_persona"));
                    empleado.setNombreCompleto(rs.getString("nombre_completo"));
                    empleado.setDocumentoIdentidad(rs.getString("documento_identidad"));
                    empleado.setTelefono(rs.getString("telefono"));
                    empleado.setCorreoElectronico(rs.getString("correo_electronico"));
                    empleado.setDireccion(rs.getString("direccion"));
                    empleado.setTipoContratacion(rs.getString("tipo_contratacion"));
                    return empleado;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public boolean actualizarEmpleado(Empleado empleado) {
        // Documento de identidad no se actualiza (readonly en el formulario)
        String sqlPersona = "UPDATE persona SET nombre_completo=?, telefono=?, correo_electronico=?, direccion=? WHERE id_persona=?";
        try (PreparedStatement stmtPersona = connection.prepareStatement(sqlPersona)) {
            stmtPersona.setString(1, empleado.getNombreCompleto());
            stmtPersona.setString(2, empleado.getTelefono());
            stmtPersona.setString(3, empleado.getCorreoElectronico());
            stmtPersona.setString(4, empleado.getDireccion());
            stmtPersona.setInt(5, empleado.getIdPersona());

            int rowsPersona = stmtPersona.executeUpdate();

            String sqlEmpleado = "UPDATE empleado SET tipo_contratacion=? WHERE id_empleado=?";
            try (PreparedStatement stmtEmpleado = connection.prepareStatement(sqlEmpleado)) {
                stmtEmpleado.setString(1, empleado.getTipoContratacion());
                stmtEmpleado.setInt(2, empleado.getIdPersona());
                int rowsEmpleado = stmtEmpleado.executeUpdate();

                return rowsPersona > 0 && rowsEmpleado > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE (soft delete)
    public boolean eliminarEmpleado(int id) {
        String sql = "UPDATE persona SET estado='inactivo' WHERE id_persona=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}