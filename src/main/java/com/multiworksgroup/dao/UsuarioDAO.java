package com.multiworksgroup.dao;

import com.multiworksgroup.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAO {

    // CREATE - Registrar nuevo usuario con contraseña encriptada
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario(nombre, correo, clave, rol, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getClave());
            stmt.setString(4, usuario.getRol());
            stmt.setString(5, usuario.getEstado());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ ALL - Obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setClave(rs.getString("clave")); // No mostrar en vistas
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getString("estado"));
                try {
                    usuario.setCreadoEn(rs.getTimestamp("creado_en"));
                } catch (SQLException e) {
                    // columna creado_en es opcional
                }
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // READ BY ID - Obtener un usuario por su ID
    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setClave(rs.getString("clave"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setEstado(rs.getString("estado"));
                    try {
                        usuario.setCreadoEn(rs.getTimestamp("creado_en"));
                    } catch (SQLException e) {
                        // columna creado_en es opcional
                    }
                    return usuario;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ BY EMAIL - Usado para login
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setClave(rs.getString("clave"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setEstado(rs.getString("estado"));
                    try {
                        usuario.setCreadoEn(rs.getTimestamp("creado_en"));
                    } catch (SQLException e) {
                        // columna creado_en es opcional
                    }
                    return usuario;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE - Actualizar datos del usuario (excepto contraseña)
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre=?, rol=?, estado=? WHERE id_usuario=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRol());
            stmt.setString(3, usuario.getEstado());
            stmt.setInt(4, usuario.getIdUsuario());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // LOGIN - Verificar credenciales del usuario
    public Usuario autenticarUsuario(String correo, String clave) {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String claveBD = rs.getString("clave");
                    if (claveBD.equals(clave)) { // Comparar contraseñas encriptadas
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(rs.getInt("id_usuario"));
                        usuario.setNombre(rs.getString("nombre"));
                        usuario.setCorreo(rs.getString("correo"));
                        usuario.setRol(rs.getString("rol"));
                        usuario.setEstado(rs.getString("estado"));
                        try {
                            usuario.setCreadoEn(rs.getTimestamp("creado_en"));
                        } catch (SQLException e) {}
                        return usuario;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ACTIVAR/DESACTIVAR USUARIO (soft delete)
    public boolean cambiarEstadoUsuario(int id, String nuevoEstado) {
        String sql = "UPDATE usuario SET estado=? WHERE id_usuario=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ELIMINAR USUARIO (eliminación real, solo si lo requieres)
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}