<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.multiworksgroup.model.Usuario" %>
<%
    String rol = "invitado";
    String nombre = "Invitado";

    if (session != null && session.getAttribute("usuario") != null) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        rol = usuario.getRol();
        nombre = usuario.getNombre();
    }

    request.setAttribute("rolUsuario", rol);
    request.setAttribute("nombreUsuario", nombre);
%>

<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm mb-4 fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="index.jsp">MultiWorks Group</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fas fa-home"></i> Inicio</a>
                </li>

                <% if ("admin".equals(rol)) { %>
                <li class="nav-item">
                    <a class="nav-link" href="usuarios.jsp"><i class="fas fa-users-cog"></i> Usuarios</a>
                </li>
                <% } %>

                <li class="nav-item">
                    <a class="nav-link" href="clientes.jsp"><i class="fas fa-user-tie"></i> Clientes</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="empleados.jsp"><i class="fas fa-user-shield"></i> Empleados</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="cotizaciones.jsp"><i class="fas fa-file-invoice"></i> Cotizaciones</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="tareas.jsp"><i class="fas fa-tasks"></i> Asignaciones</a>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fas fa-user-circle"></i> <%= nombre %>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="perfil.jsp"><i class="fas fa-user"></i> Perfil</a></li>
                        <li><a class="dropdown-item" href="logout"><i class="fas fa-sign-out-alt"></i> Cerrar Sesión</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>