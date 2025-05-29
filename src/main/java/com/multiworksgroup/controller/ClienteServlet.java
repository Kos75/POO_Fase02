package com.multiworksgroup.controller;

import com.multiworksgroup.dao.ClienteDAO;
import com.multiworksgroup.model.Cliente;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import com.multiworksgroup.util.Validaciones;

@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDAO;

    public void init() {
        clienteDAO = new ClienteDAO();
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
                eliminarCliente(request, response);
                break;
            default:
                listarClientes(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse  response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "guardar";
        }

        switch (action) {
            case "guardar":
                registrarCliente(request, response);
                break;
            case "actualizar":
                actualizarCliente(request, response);
                break;
            default:
                listarClientes(request, response);
                break;
        }
    }

    // Mostrar formulario de registro
    private void mostrarFormularioRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/clientes.jsp?action=registrar").forward(request, response);
    }

    // Mostrar formulario de edición
    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "ID de cliente inválido para editar.");
            listarClientes(request, response);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "ID de cliente inválido para editar.");
            listarClientes(request, response);
            return;
        }
        Cliente cliente = clienteDAO.obtenerClientePorId(id);
        if (cliente == null) {
            request.setAttribute("error", "Cliente no encontrado.");
            listarClientes(request, response);
            return;
        }
        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher("/jsp/clientes.jsp?action=editar&id=" + id).forward(request, response);
    }

    // Registrar nuevo cliente
    private void registrarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nombreCompleto = request.getParameter("nombre");
        String documentoIdentidad = request.getParameter("documento");
        String telefono = request.getParameter("telefono");
        String correoElectronico = request.getParameter("correo");
        String direccion = request.getParameter("direccion");
        String tipoCliente = request.getParameter("tipoCliente");

        // Validación simple
        if (nombreCompleto == null || documentoIdentidad == null || correoElectronico == null ||
                nombreCompleto.trim().isEmpty() || documentoIdentidad.trim().isEmpty() || correoElectronico.trim().isEmpty()) {
            request.setAttribute("error", "Los campos Nombre, Documento y Correo son obligatorios");
            request.getRequestDispatcher("/jsp/clientes.jsp?action=registrar").forward(request, response);
            return;
        }

        // Validación del documento
        if (!Validaciones.esDocumentoValido(documentoIdentidad)) {
            request.setAttribute("error", "El documento debe contener solo números y tener entre 8 y 14 dígitos");
            request.getRequestDispatcher("/jsp/clientes.jsp?action=registrar").forward(request, response);
            return;
        }

        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombreCompleto(nombreCompleto);
        nuevoCliente.setDocumentoIdentidad(documentoIdentidad);
        nuevoCliente.setTelefono(telefono);
        nuevoCliente.setCorreoElectronico(correoElectronico);
        nuevoCliente.setDireccion(direccion);
        nuevoCliente.setTipoCliente(tipoCliente);

        boolean registrado = clienteDAO.registrarCliente(nuevoCliente);

        if (registrado) {
            response.sendRedirect("clientes");
        } else {
            request.setAttribute("error", "Error al registrar el cliente");
            request.getRequestDispatcher("/jsp/clientes.jsp?action=registrar").forward(request, response);
        }
    }

    // Actualizar cliente existente
    private void actualizarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "ID de cliente inválido para actualizar.");
            listarClientes(request, response);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "ID de cliente inválido para actualizar.");
            listarClientes(request, response);
            return;
        }

        String nombreCompleto = request.getParameter("nombre");
        String documentoIdentidad = request.getParameter("documento");
        String telefono = request.getParameter("telefono");
        String correoElectronico = request.getParameter("correo");
        String direccion = request.getParameter("direccion");
        String tipoCliente = request.getParameter("tipoCliente");

        Cliente cliente = new Cliente();
        cliente.setIdPersona(id);
        cliente.setNombreCompleto(nombreCompleto);
        cliente.setDocumentoIdentidad(documentoIdentidad); // No se actualiza en SQL, pero se mantiene para mostrar en pantalla
        cliente.setTelefono(telefono);
        cliente.setCorreoElectronico(correoElectronico);
        cliente.setDireccion(direccion);
        cliente.setTipoCliente(tipoCliente);

        boolean actualizado = clienteDAO.actualizarCliente(cliente);

        if (actualizado) {
            response.sendRedirect("clientes");
        } else {
            request.setAttribute("error", "Error al actualizar el cliente");
            request.setAttribute("cliente", cliente);
            request.getRequestDispatcher("/jsp/clientes.jsp?action=editar&id=" + id).forward(request, response);
        }
    }

    // Eliminar cliente (soft delete)
    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            request.setAttribute("error", "ID de cliente inválido para eliminar.");
            listarClientes(request, response);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "ID de cliente inválido para eliminar.");
            listarClientes(request, response);
            return;
        }
        boolean eliminado = clienteDAO.eliminarCliente(id);

        if (eliminado) {
            response.sendRedirect("clientes");
        } else {
            request.setAttribute("error", "Error al eliminar el cliente.");
            listarClientes(request, response);
        }
    }

    // Listar todos los clientes
    private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> listaClientes = clienteDAO.obtenerClientes();
        request.setAttribute("clientes", listaClientes);

        // Mostrar cualquier mensaje de error almacenado en el parámetro
        String errorParam = request.getParameter("error");
        if (errorParam != null) {
            if ("eliminar".equals(errorParam)) {
                request.setAttribute("error", "No se pudo eliminar el cliente.");
            }
            // Puedes agregar más casos de error aquí si lo necesitas
        }

        request.getRequestDispatcher("/jsp/clientes.jsp").forward(request, response);
    }
}