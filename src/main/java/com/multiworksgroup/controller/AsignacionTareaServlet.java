package com.multiworksgroup.controller;

import com.multiworksgroup.dao.AsignacionTareaDAO;
import com.multiworksgroup.model.AsignacionTarea;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import com.multiworksgroup.dao.EmpleadoDAO;
import com.multiworksgroup.dao.ActividadDAO;

@WebServlet("/tareas")
public class AsignacionTareaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AsignacionTareaDAO asignacionTareaDAO;


    public void init() {
        asignacionTareaDAO = new AsignacionTareaDAO();
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
                eliminarAsignacion(request, response);
                break;
            default:
                listarAsignaciones(request, response);
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
                registrarAsignacion(request, response);
                break;
            case "actualizar":
                actualizarAsignacion(request, response);
                break;
            default:
                listarAsignaciones(request, response);
                break;
        }
    }

    // Mostrar formulario de registro
    private void mostrarFormularioRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("empleados", new EmpleadoDAO().obtenerEmpleados());
        request.setAttribute("actividades", new ActividadDAO().obtenerActividades());
        request.getRequestDispatcher("/jsp/tareas.jsp?action=registrar").forward(request, response);
    }

    // Mostrar formulario de edición
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException | NullPointerException ex) {
            request.setAttribute("error", "ID de asignación inválido para editar.");
            listarAsignaciones(request, response);
            return;
        }
        AsignacionTarea asignacion = asignacionTareaDAO.obtenerAsignacionPorId(id);
        if (asignacion == null) {
            request.setAttribute("error", "Asignación no encontrada.");
            listarAsignaciones(request, response);
            return;
        }
        request.setAttribute("asignacion", asignacion);
        request.setAttribute("empleados", new EmpleadoDAO().obtenerEmpleados());
        request.setAttribute("actividades", new ActividadDAO().obtenerActividades());
        request.getRequestDispatcher("/jsp/tareas.jsp?action=editar&id=" + id).forward(request, response);
    }

    // Registrar nueva asignación
    private void registrarAsignacion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int idEmpleado = Integer.parseInt(request.getParameter("empleado"));
            int idActividad = Integer.parseInt(request.getParameter("actividad"));
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");

            AsignacionTarea nuevaAsignacion = new AsignacionTarea();
            nuevaAsignacion.setIdEmpleado(idEmpleado);
            nuevaAsignacion.setIdActividad(idActividad);
            nuevaAsignacion.setTitulo(titulo);
            nuevaAsignacion.setDescripcion(descripcion);

            boolean registrado = asignacionTareaDAO.registrarAsignacion(nuevaAsignacion);

            if (registrado) {
                response.sendRedirect("tareas");
            } else {
                setListasEmpleadosActividades(request);
                request.setAttribute("error", "Error al registrar la asignación");
                request.getRequestDispatcher("/jsp/tareas.jsp?action=registrar").forward(request, response);
            }
        } catch (Exception e) {
            setListasEmpleadosActividades(request);
            e.printStackTrace();
            request.setAttribute("error", "Datos inválidos o incompletos");
            request.getRequestDispatcher("/jsp/tareas.jsp?action=registrar").forward(request, response);
        }
    }

    // Actualizar asignación existente
    private void actualizarAsignacion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int idEmpleado = Integer.parseInt(request.getParameter("empleado"));
            int idActividad = Integer.parseInt(request.getParameter("actividad"));
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");

            AsignacionTarea asignacion = new AsignacionTarea();
            asignacion.setIdAsignacion(id);
            asignacion.setIdEmpleado(idEmpleado);
            asignacion.setIdActividad(idActividad);
            asignacion.setTitulo(titulo);
            asignacion.setDescripcion(descripcion);

            boolean actualizado = asignacionTareaDAO.actualizarAsignacion(asignacion);

            if (actualizado) {
                response.sendRedirect("tareas");
            } else {
                setListasEmpleadosActividades(request);
                request.setAttribute("error", "Error al actualizar la asignación");
                request.setAttribute("asignacion", asignacion);
                request.getRequestDispatcher("/jsp/tareas.jsp?action=editar&id=" + id).forward(request, response);
            }
        } catch (Exception e) {
            setListasEmpleadosActividades(request);
            e.printStackTrace();
            request.setAttribute("error", "Datos inválidos o incompletos");
            request.getRequestDispatcher("/jsp/tareas.jsp?action=editar&id=" + request.getParameter("id")).forward(request, response);
        }
    }

    // Eliminar asignación
    private void eliminarAsignacion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParam = request.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException | NullPointerException ex) {
            request.setAttribute("error", "ID de asignación inválido para eliminar.");
            listarAsignaciones(request, response);
            return;
        }
        boolean eliminado = asignacionTareaDAO.eliminarAsignacion(id);

        if (eliminado) {
            response.sendRedirect("tareas");
        } else {
            request.setAttribute("error", "Error al eliminar la asignación.");
            listarAsignaciones(request, response);
        }
    }

    // Listar todas las asignaciones
    private void listarAsignaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<AsignacionTarea> listaAsignaciones = asignacionTareaDAO.obtenerAsignaciones();
        request.setAttribute("asignaciones", listaAsignaciones);
        setListasEmpleadosActividades(request);
        request.getRequestDispatcher("/jsp/tareas.jsp").forward(request, response);
    }

    // Helper para cargar empleados y actividades en los request attributes
    private void setListasEmpleadosActividades(HttpServletRequest request) {
        request.setAttribute("empleados", new EmpleadoDAO().obtenerEmpleados());
        request.setAttribute("actividades", new ActividadDAO().obtenerActividades());
    }
}