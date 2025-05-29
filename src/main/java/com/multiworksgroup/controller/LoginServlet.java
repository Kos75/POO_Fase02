package com.multiworksgroup.controller;

import com.multiworksgroup.dao.UsuarioDAO;
import com.multiworksgroup.model.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Muestra la pantalla de login con GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    // Procesa el login con POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");

        // Validaciones simples del lado del servidor
        if (correo == null || correo.trim().isEmpty()) {
            request.setAttribute("error", "El correo es requerido");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }

        if (clave == null || clave.trim().isEmpty()) {
            request.setAttribute("error", "La contraseña es requerida");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }

        // Autenticar usuario
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.autenticarUsuario(correo, clave);

        if (usuario != null && "activo".equals(usuario.getEstado())) {
            // Crear sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("rol", usuario.getRol());
            session.setAttribute("nombre", usuario.getNombre());

            // Redirigir según rol
            String context = request.getContextPath();
            switch (usuario.getRol()) {
                case "admin":
                    response.sendRedirect(context + "/index.jsp");
                    break;
                case "empleado":
                    response.sendRedirect(context + "/empleados");
                    break;
                case "cliente":
                    response.sendRedirect(context + "/clientes");
                    break;
                default:
                    request.setAttribute("error", "Rol no reconocido");
                    request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
                    break;
            }
        } else {
            // Si el usuario no existe o está inactivo
            request.setAttribute("error", "Correo o contraseña incorrectos, o el usuario está inactivo");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }
}