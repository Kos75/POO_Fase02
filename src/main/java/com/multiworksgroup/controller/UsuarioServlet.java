package com.multiworksgroup.controller;

import com.multiworksgroup.dao.UsuarioDAO;
import com.multiworksgroup.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import com.multiworksgroup.util.Validaciones;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "listar";
        }

        switch (action) {
            case "registrar":
                mostrarFormularioRegistro(request, response);
                break;
            case "editar":
                mostrarFormularioEdicion(request, response);
                break;
            case "eliminar":
                eliminarUsuario(request, response);
                break;
            default:
                listarUsuarios(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "guardar";
        }

        switch (action) {
            case "guardar":
                registrarUsuario(request, response);
                break;
            case "actualizar":
                actualizarUsuario(request, response);
                break;
            default:
                listarUsuarios(request, response);
                break;
        }
    }

    // Mostrar formulario de registro
    private void mostrarFormularioRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/usuarios.jsp?action=registrar").forward(request, response);
    }

    // Mostrar formulario de edición
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException | NullPointerException ex) {
            request.setAttribute("error", "ID de usuario inválido para editar.");
            listarUsuarios(request, response);
            return;
        }
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(id);
        if (usuario == null) {
            request.setAttribute("error", "Usuario no encontrado.");
            listarUsuarios(request, response);
            return;
        }
        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("/jsp/usuarios.jsp?action=editar&id=" + id).forward(request, response);
    }

    // Registrar nuevo usuario
    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");
        String rol = request.getParameter("rol");

        // Validaciones
        if (Validaciones.isCampoVacio(nombre) ||
                Validaciones.isCampoVacio(correo) ||
                Validaciones.isCampoVacio(clave) ||
                !Validaciones.isValidEmail(correo) ||
                !Validaciones.esContrasenaValida(clave)) {

            request.setAttribute("error", "Todos los campos son obligatorios. El correo debe ser válido y la contraseña debe tener al menos 6 caracteres.");
            mostrarFormularioRegistro(request, response);
            return;
        }

        // Encriptar contraseña (puedes mejorar con BCrypt o SHA-256)
        String claveEncriptada = encriptarClave(clave);

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setClave(claveEncriptada);
        nuevoUsuario.setRol(rol);
        nuevoUsuario.setEstado("activo");

        boolean registrado = usuarioDAO.registrarUsuario(nuevoUsuario);

        if (registrado) {
            response.sendRedirect("usuarios");
        } else {
            request.setAttribute("error", "Error al registrar el usuario");
            mostrarFormularioRegistro(request, response);
        }
    }

    // Actualizar usuario existente
    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParam = request.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException | NullPointerException ex) {
            request.setAttribute("error", "ID de usuario inválido para actualizar.");
            listarUsuarios(request, response);
            return;
        }
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String rol = request.getParameter("rol");
        String estado = request.getParameter("estado");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setRol(rol);
        usuario.setEstado(estado);

        boolean actualizado = usuarioDAO.actualizarUsuario(usuario);

        if (actualizado) {
            response.sendRedirect("usuarios");
        } else {
            request.setAttribute("error", "Error al actualizar el usuario");
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("/jsp/usuarios.jsp?action=editar&id=" + id).forward(request, response);
        }
    }

    // Eliminar usuario (soft delete)
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParam = request.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException | NullPointerException ex) {
            request.setAttribute("error", "ID de usuario inválido para eliminar.");
            listarUsuarios(request, response);
            return;
        }
        boolean eliminado = usuarioDAO.cambiarEstadoUsuario(id, "inactivo");

        if (eliminado) {
            response.sendRedirect("usuarios");
        } else {
            request.setAttribute("error", "Error al eliminar el usuario.");
            listarUsuarios(request, response);
        }
    }

    // Listar todos los usuarios
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> listaUsuarios = usuarioDAO.obtenerUsuarios();
        request.setAttribute("usuarios", listaUsuarios);
        request.getRequestDispatcher("/jsp/usuarios.jsp").forward(request, response);
    }

    // Método básico de encriptación (mejorar con BCrypt o SHA-256 en producción)
    private String encriptarClave(String clave) {
        // Aquí puedes implementar una encriptación segura
        return clave; // Temporal: sin encriptación
    }
}