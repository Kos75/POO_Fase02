package com.multiworksgroup.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización opcional
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        String action = request.getParameter("action");

        // Permitir rutas públicas
        if (isRutaPublica(path, action)) {
            chain.doFilter(request, response);
            return;
        }

        // Verificar si hay sesión activa
        if (session == null || session.getAttribute("usuario") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        // Verificar rol del usuario
        String rol = (String) session.getAttribute("rol");

        if (path.startsWith("/clientes") && !"admin".equals(rol)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/dashboard.jsp");
            return;
        }

        if (path.startsWith("/empleados") && !"admin".equals(rol)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/dashboard.jsp");
            return;
        }

        if (path.startsWith("/usuarios") && !"admin".equals(rol) && !"registrar".equals(action)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/dashboard.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Limpieza opcional
    }

    // Permitir acceso a ciertas rutas sin autenticación
    private boolean isRutaPublica(String path, String action) {
        return path.equals("/login.jsp") ||
                path.equals("/LoginServlet") ||
                path.equals("/") ||
                path.startsWith("/css") ||
                path.startsWith("/js") ||
                path.startsWith("/img") ||
                path.equals("/logout") ||
                (path.equals("/usuarios") && "registrar".equals(action));
    }
}
