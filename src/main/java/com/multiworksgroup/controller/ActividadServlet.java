package com.multiworksgroup.controller;

import com.multiworksgroup.dao.ActividadDAO;
import com.multiworksgroup.model.Actividad;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/actividades")
public class ActividadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ActividadDAO actividadDAO;

    public void init() {
        actividadDAO = new ActividadDAO();
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
                eliminarActividad(request, response);
                break;
            default:
                listarActividades(request, response);
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
                registrarActividad(request, response);
                break;
            case "actualizar":
                actualizarActividad(request, response);
                break;
            default:
                listarActividades(request, response);
                break;
        }
    }

    // Mostrar formulario de registro
    private void mostrarFormularioRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/actividades.jsp?action=registrar").forward(request, response);
    }

    // Mostrar formulario de edición
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Actividad actividad = actividadDAO.obtenerActividadPorId(id);

        request.setAttribute("actividad", actividad);
        request.getRequestDispatcher("/jsp/actividades.jsp?action=editar&id=" + id).forward(request, response);
    }

    // Registrar nueva actividad
    private void registrarActividad(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String titulo = request.getParameter("titulo");
            String areaAsignada = request.getParameter("area");
            double costoPorHora = Double.parseDouble(request.getParameter("costo_hora"));
            Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(request.getParameter("fecha_inicio"));
            Date fechaFin = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(request.getParameter("fecha_fin"));
            double cantidadHoras = Double.parseDouble(request.getParameter("horas"));
            double costoBase = Double.parseDouble(request.getParameter("costo_base"));
            double incrementoExtra = Double.parseDouble(request.getParameter("incremento_extra"));
            double total = costoBase + incrementoExtra;

            Actividad nuevaActividad = new Actividad();
            nuevaActividad.setTitulo(titulo);
            nuevaActividad.setAreaAsignada(areaAsignada);
            nuevaActividad.setCostoPorHora(costoPorHora);
            nuevaActividad.setFechaInicio(fechaInicio);
            nuevaActividad.setFechaFin(fechaFin);
            nuevaActividad.setCantidadHoras(cantidadHoras);
            nuevaActividad.setCostoBase(costoBase);
            nuevaActividad.setIncrementoExtra(incrementoExtra);
            nuevaActividad.setTotal(total);

            boolean registrado = actividadDAO.registrarActividad(nuevaActividad);

            if (registrado) {
                response.sendRedirect("actividades");
            } else {
                request.setAttribute("error", "Error al registrar la actividad");
                request.getRequestDispatcher("/jsp/actividades.jsp?action=registrar").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Datos inválidos o incompletos");
            request.getRequestDispatcher("/jsp/actividades.jsp?action=registrar").forward(request, response);
        }
    }

    // Actualizar actividad existente
    private void actualizarActividad(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String titulo = request.getParameter("titulo");
            String areaAsignada = request.getParameter("area");
            double costoPorHora = Double.parseDouble(request.getParameter("costo_hora"));
            Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(request.getParameter("fecha_inicio"));
            Date fechaFin = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(request.getParameter("fecha_fin"));
            double cantidadHoras = Double.parseDouble(request.getParameter("horas"));
            double costoBase = Double.parseDouble(request.getParameter("costo_base"));
            double incrementoExtra = Double.parseDouble(request.getParameter("incremento_extra"));
            double total = costoBase + incrementoExtra;

            Actividad actividad = new Actividad();
            actividad.setIdActividad(id);
            actividad.setTitulo(titulo);
            actividad.setAreaAsignada(areaAsignada);
            actividad.setCostoPorHora(costoPorHora);
            actividad.setFechaInicio(fechaInicio);
            actividad.setFechaFin(fechaFin);
            actividad.setCantidadHoras(cantidadHoras);
            actividad.setCostoBase(costoBase);
            actividad.setIncrementoExtra(incrementoExtra);
            actividad.setTotal(total);

            boolean actualizado = actividadDAO.actualizarActividad(actividad);

            if (actualizado) {
                response.sendRedirect("actividades");
            } else {
                request.setAttribute("error", "Error al actualizar la actividad");
                request.getRequestDispatcher("/jsp/actividades.jsp?action=editar&id=" + id).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Datos inválidos o incompletos");
            request.getRequestDispatcher("/jsp/actividades.jsp?action=editar&id=" + request.getParameter("id")).forward(request, response);
        }
    }

    // Eliminar actividad
    private void eliminarActividad(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean eliminado = actividadDAO.eliminarActividad(id);

        if (eliminado) {
            response.sendRedirect("actividades");
        } else {
            response.sendRedirect("actividades?error=eliminar");
        }
    }

    // Listar todas las actividades
    private void listarActividades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Actividad> listaActividades = actividadDAO.obtenerActividades();
        request.setAttribute("actividades", listaActividades);
        request.getRequestDispatcher("/jsp/actividades.jsp").forward(request, response);
    }
}