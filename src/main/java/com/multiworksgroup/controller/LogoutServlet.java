package com.multiworksgroup.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión actual (si existe)
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Invalidar la sesión
            session.invalidate();
        }

        // Redirigir al login
        response.sendRedirect(request.getContextPath() + "/login");
    }
}