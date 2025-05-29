package com.multiworksgroup.dao;

import com.multiworksgroup.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends DAO {

    // CREATE
    public boolean registrarCliente(Cliente cliente) {
        String sql = "INSERT INTO persona(nombre_completo, documento_identidad, tipo_persona, telefono, correo_electronico, direccion) VALUES (?, ?, 'cliente', ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getNombreCompleto());
            stmt.setString(2, cliente.getDocumentoIdentidad());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getCorreoElectronico());
            stmt.setString(5, cliente.getDireccion());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idPersona = generatedKeys.getInt(1);

                        // Insert into cliente table
                        String sqlCliente = "INSERT INTO cliente(id_cliente, tipo_cliente) VALUES (?, ?)";
                        try (PreparedStatement stmtCliente = connection.prepareStatement(sqlCliente)) {
                            stmtCliente.setInt(1, idPersona);
                            stmtCliente.setString(2, cliente.getTipoCliente());
                            stmtCliente.executeUpdate();
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
    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT p.id_persona, p.nombre_completo, p.documento_identidad, p.telefono, p.correo_electronico, p.direccion, c.tipo_cliente FROM persona p INNER JOIN cliente c ON p.id_persona = c.id_cliente WHERE p.estado = 'activo'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdPersona(rs.getInt("id_persona"));
                cliente.setNombreCompleto(rs.getString("nombre_completo"));
                cliente.setDocumentoIdentidad(rs.getString("documento_identidad"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setCorreoElectronico(rs.getString("correo_electronico"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTipoCliente(rs.getString("tipo_cliente"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    // READ BY ID
    public Cliente obtenerClientePorId(int id) {
        String sql = "SELECT p.id_persona, p.nombre_completo, p.documento_identidad, p.telefono, p.correo_electronico, p.direccion, c.tipo_cliente FROM persona p INNER JOIN cliente c ON p.id_persona = c.id_cliente WHERE p.id_persona = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setIdPersona(rs.getInt("id_persona"));
                    cliente.setNombreCompleto(rs.getString("nombre_completo"));
                    cliente.setDocumentoIdentidad(rs.getString("documento_identidad"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setCorreoElectronico(rs.getString("correo_electronico"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setTipoCliente(rs.getString("tipo_cliente"));
                    return cliente;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public boolean actualizarCliente(Cliente cliente) {
        // Documento de identidad no se actualiza (readonly en el formulario)
        String sqlPersona = "UPDATE persona SET nombre_completo=?, telefono=?, correo_electronico=?, direccion=? WHERE id_persona=?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlPersona)) {
            stmt.setString(1, cliente.getNombreCompleto());
            stmt.setString(2, cliente.getTelefono());
            stmt.setString(3, cliente.getCorreoElectronico());
            stmt.setString(4, cliente.getDireccion());
            stmt.setInt(5, cliente.getIdPersona());

            int rowsPersona = stmt.executeUpdate();

            String sqlCliente = "UPDATE cliente SET tipo_cliente=? WHERE id_cliente=?";
            try (PreparedStatement stmtCliente = connection.prepareStatement(sqlCliente)) {
                stmtCliente.setString(1, cliente.getTipoCliente());
                stmtCliente.setInt(2, cliente.getIdPersona());
                int rowsCliente = stmtCliente.executeUpdate();

                return rowsPersona > 0 && rowsCliente > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE (soft delete)
    public boolean eliminarCliente(int id) {
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