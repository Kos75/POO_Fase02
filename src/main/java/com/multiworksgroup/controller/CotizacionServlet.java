package com.multiworksgroup.controller;

import com.multiworksgroup.dao.CotizacionDAO;
import com.multiworksgroup.model.Cotizacion;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.multiworksgroup.dao.ClienteDAO;
import com.multiworksgroup.util.Validaciones;

@WebServlet("/cotizaciones")
public class CotizacionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CotizacionDAO cotizacionDAO;

    public void init() {
        cotizacionDAO = new CotizacionDAO();
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
                eliminarCotizacion(request, response);
                break;
            default:
                listarCotizaciones(request, response);
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
                registrarCotizacion(request, response);
                break;
            case "actualizar":
                actualizarCotizacion(request, response);
                break;
            default:
                listarCotizaciones(request, response);
                break;
        }
    }

    // Mostrar formulario de registro
    private void mostrarFormularioRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("clientes", new ClienteDAO().obtenerClientes());
        request.getRequestDispatcher("/jsp/cotizaciones.jsp?action=registrar").forward(request, response);
    }

    // Mostrar formulario de edición
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "ID de cotización inválido para editar.");
            listarCotizaciones(request, response);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "ID de cotización inválido para editar.");
            listarCotizaciones(request, response);
            return;
        }
        Cotizacion cotizacion = cotizacionDAO.obtenerCotizacionPorId(id);
        if (cotizacion == null) {
            request.setAttribute("error", "Cotización no encontrada.");
            listarCotizaciones(request, response);
            return;
        }
        request.setAttribute("cotizacion", cotizacion);
        request.setAttribute("clientes", new ClienteDAO().obtenerClientes());
        request.getRequestDispatcher("/jsp/cotizaciones.jsp?action=editar&id=" + id).forward(request, response);
    }

    // Registrar nueva cotización
    private void registrarCotizacion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String horas = request.getParameter("horas");
            if (!Validaciones.isNumeroDecimal(horas)) {
                request.setAttribute("error", "Horas del proyecto debe ser un número positivo");
                mostrarFormularioRegistro(request, response);
                return;
            }

            int idCliente = Integer.parseInt(request.getParameter("cliente"));
            String numeroCotizacion = request.getParameter("numero");
            double horasProyecto = Double.parseDouble(horas);
            Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha_inicio"));
            Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha_fin"));
            double costosAsignados = Double.parseDouble(request.getParameter("costos_asignados"));
            double costosAdicionales = Double.parseDouble(request.getParameter("costos_adicionales"));
            double total = costosAsignados + costosAdicionales;
            String estado = request.getParameter("estado");

            Cotizacion nuevaCotizacion = new Cotizacion();
            nuevaCotizacion.setIdCliente(idCliente);
            nuevaCotizacion.setNumeroCotizacion(numeroCotizacion);
            nuevaCotizacion.setHorasProyecto(horasProyecto);
            nuevaCotizacion.setFechaInicio(fechaInicio);
            nuevaCotizacion.setFechaFin(fechaFin);
            nuevaCotizacion.setCostosAsignados(costosAsignados);
            nuevaCotizacion.setCostosAdicionales(costosAdicionales);
            nuevaCotizacion.setTotal(total);
            nuevaCotizacion.setEstado(estado);

            boolean registrado = cotizacionDAO.registrarCotizacion(nuevaCotizacion);

            if (registrado) {
                response.sendRedirect("cotizaciones");
            } else {
                request.setAttribute("error", "Error al registrar la cotización");
                mostrarFormularioRegistro(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Datos inválidos o incompletos");
            mostrarFormularioRegistro(request, response);
        }
    }

    // Actualizar cotización existente
    private void actualizarCotizacion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int idCliente = Integer.parseInt(request.getParameter("cliente"));
            String numeroCotizacion = request.getParameter("numero");
            double horasProyecto = Double.parseDouble(request.getParameter("horas"));
            Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha_inicio"));
            Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha_fin"));
            double costosAsignados = Double.parseDouble(request.getParameter("costos_asignados"));
            double costosAdicionales = Double.parseDouble(request.getParameter("costos_adicionales"));
            double total = costosAsignados + costosAdicionales;
            String estado = request.getParameter("estado");

            Cotizacion cotizacion = new Cotizacion();
            cotizacion.setIdCotizacion(id);
            cotizacion.setIdCliente(idCliente);
            cotizacion.setNumeroCotizacion(numeroCotizacion);
            cotizacion.setHorasProyecto(horasProyecto);
            cotizacion.setFechaInicio(fechaInicio);
            cotizacion.setFechaFin(fechaFin);
            cotizacion.setCostosAsignados(costosAsignados);
            cotizacion.setCostosAdicionales(costosAdicionales);
            cotizacion.setTotal(total);
            cotizacion.setEstado(estado);

            boolean actualizado = cotizacionDAO.actualizarCotizacion(cotizacion);

            if (actualizado) {
                response.sendRedirect("cotizaciones");
            } else {
                request.setAttribute("error", "Error al actualizar la cotización");
                mostrarFormularioEdicion(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Datos inválidos o incompletos");
            mostrarFormularioEdicion(request, response);
        }
    }

    // Eliminar cotización (soft delete)
    private void eliminarCotizacion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "ID de cotización inválido para eliminar.");
            listarCotizaciones(request, response);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "ID de cotización inválido para eliminar.");
            listarCotizaciones(request, response);
            return;
        }
        boolean eliminado = cotizacionDAO.eliminarCotizacion(id);

        if (eliminado) {
            response.sendRedirect("cotizaciones");
        } else {
            request.setAttribute("error", "Error al eliminar la cotización.");
            listarCotizaciones(request, response);
        }
    }

    // Listar todas las cotizaciones
    private void listarCotizaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cotizacion> listaCotizaciones = cotizacionDAO.obtenerCotizaciones();
        request.setAttribute("cotizaciones", listaCotizaciones);
        request.setAttribute("clientes", new ClienteDAO().obtenerClientes());
        request.getRequestDispatcher("/jsp/cotizaciones.jsp").forward(request, response);
    }
}