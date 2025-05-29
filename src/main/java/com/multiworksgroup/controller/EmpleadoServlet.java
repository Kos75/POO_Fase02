package com.multiworksgroup.controller;

import com.multiworksgroup.dao.EmpleadoDAO;
import com.multiworksgroup.model.Empleado;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/empleados")
public class EmpleadoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmpleadoDAO empleadoDAO;

    public void init() {
        empleadoDAO = new EmpleadoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
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
                eliminarEmpleado(request, response);
                break;
            default:
                listarEmpleados(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            action = "guardar";
        }

        switch (action) {
            case "guardar":
                registrarEmpleado(request, response);
                break;
            case "actualizar":
                actualizarEmpleado(request, response);
                break;
            default:
                listarEmpleados(request, response);
                break;
        }
    }

    private void mostrarFormularioRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/empleados.jsp?action=registrar").forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "ID de empleado inválido para editar.");
            listarEmpleados(request, response);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "ID de empleado inválido para editar.");
            listarEmpleados(request, response);
            return;
        }
        Empleado empleado = empleadoDAO.obtenerEmpleadoPorId(id);
        if (empleado == null) {
            request.setAttribute("error", "Empleado no encontrado.");
            listarEmpleados(request, response);
            return;
        }
        request.setAttribute("empleado", empleado);
        request.getRequestDispatcher("/jsp/empleados.jsp?action=editar&id=" + id).forward(request, response);
    }

    private void registrarEmpleado(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nombreCompleto = request.getParameter("nombre");
        String documentoIdentidad = request.getParameter("documento");
        String telefono = request.getParameter("telefono");
        String correoElectronico = request.getParameter("correo");
        String direccion = request.getParameter("direccion");
        String tipoContratacion = request.getParameter("tipoContratacion");

        // Validación simple
        if (nombreCompleto == null || documentoIdentidad == null || correoElectronico == null ||
                nombreCompleto.trim().isEmpty() || documentoIdentidad.trim().isEmpty() || correoElectronico.trim().isEmpty()) {
            request.setAttribute("error", "Los campos Nombre, Documento y Correo son obligatorios");
            mostrarFormularioRegistro(request, response);
            return;
        }

        Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setNombreCompleto(nombreCompleto);
        nuevoEmpleado.setDocumentoIdentidad(documentoIdentidad);
        nuevoEmpleado.setTelefono(telefono);
        nuevoEmpleado.setCorreoElectronico(correoElectronico);
        nuevoEmpleado.setDireccion(direccion);
        nuevoEmpleado.setTipoContratacion(tipoContratacion);

        boolean registrado = empleadoDAO.registrarEmpleado(nuevoEmpleado);

        if (registrado) {
            response.sendRedirect("empleados");
        } else {
            request.setAttribute("error", "Error al registrar el empleado");
            mostrarFormularioRegistro(request, response);
        }
    }

    private void actualizarEmpleado(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "ID de empleado inválido para actualizar.");
            listarEmpleados(request, response);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "ID de empleado inválido para actualizar.");
            listarEmpleados(request, response);
            return;
        }
        String nombreCompleto = request.getParameter("nombre");
        String documentoIdentidad = request.getParameter("documento");
        String telefono = request.getParameter("telefono");
        String correoElectronico = request.getParameter("correo");
        String direccion = request.getParameter("direccion");
        String tipoContratacion = request.getParameter("tipoContratacion");

        Empleado empleado = new Empleado();
        empleado.setIdPersona(id);
        empleado.setNombreCompleto(nombreCompleto);
        empleado.setDocumentoIdentidad(documentoIdentidad);
        empleado.setTelefono(telefono);
        empleado.setCorreoElectronico(correoElectronico);
        empleado.setDireccion(direccion);
        empleado.setTipoContratacion(tipoContratacion);

        boolean actualizado = empleadoDAO.actualizarEmpleado(empleado);

        if (actualizado) {
            response.sendRedirect("empleados");
        } else {
            request.setAttribute("error", "Error al actualizar el empleado");
            request.setAttribute("empleado", empleado);
            request.getRequestDispatcher("/jsp/empleados.jsp?action=editar&id=" + id).forward(request, response);
        }
    }

    private void eliminarEmpleado(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "ID de empleado inválido para eliminar.");
            listarEmpleados(request, response);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "ID de empleado inválido para eliminar.");
            listarEmpleados(request, response);
            return;
        }
        boolean eliminado = empleadoDAO.eliminarEmpleado(id);

        if (eliminado) {
            response.sendRedirect("empleados");
        } else {
            request.setAttribute("error", "Error al eliminar el empleado.");
            listarEmpleados(request, response);
        }
    }

    private void listarEmpleados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Empleado> listaEmpleados = empleadoDAO.obtenerEmpleados();
        request.setAttribute("empleados", listaEmpleados);

        String errorParam = request.getParameter("error");
        if (errorParam != null) {
            if ("eliminar".equals(errorParam)) {
                request.setAttribute("error", "No se pudo eliminar el empleado.");
            }
        }

        request.getRequestDispatcher("/jsp/empleados.jsp").forward(request, response);
    }
}