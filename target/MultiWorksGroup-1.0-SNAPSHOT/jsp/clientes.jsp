<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Clientes - MultiWorks Group</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css ">

    <style>
        body {
            background-color: #f8f9fa;
        }
        .table-hover tbody tr:hover {
            background-color: #f1f1f1;
        }
        .card-header {
            background-color: #ffffff;
            border-bottom: 1px solid #dee2e6;
        }
        .form-control-sm {
            font-size: 0.875rem;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<jsp:include page="header.jsp" />

<div class="container-fluid py-4">
    <div class="row">
        <div class="col-md-12 mb-4">
            <h2>Gestión de Clientes</h2>
            <p class="text-muted">Registra, edita o elimina clientes del sistema.</p>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="card shadow-sm">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Clientes Registrados</h5>
                    <a href="?action=registrar" class="btn btn-primary btn-sm">
                        <i class="fas fa-plus"></i> Registrar Nuevo Cliente
                    </a>
                </div>
                <div class="card-body">

                    <!-- Mostrar mensaje de error si hay -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <!-- Formulario de Registro -->
                    <c:if test="${param.action == 'registrar'}">
                        <div class="mb-4 p-3 bg-light rounded">
                            <h5 class="mb-3">Registrar Nuevo Cliente</h5>
                            <form action="clientes" method="post">
                                <input type="hidden" name="action" value="guardar">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="nombre" class="form-label">Nombre Completo</label>
                                        <input type="text" name="nombre" id="nombre" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="documento" class="form-label">Documento de Identidad</label>
                                        <input type="text" name="documento" id="documento" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="telefono" class="form-label">Teléfono</label>
                                        <input type="text" name="telefono" id="telefono" class="form-control form-control-sm">
                                    </div>
                                    <div class="col-md-6">
                                        <label for="correo" class="form-label">Correo Electrónico</label>
                                        <input type="email" name="correo" id="correo" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-12">
                                        <label for="direccion" class="form-label">Dirección</label>
                                        <input type="text" name="direccion" id="direccion" class="form-control form-control-sm">
                                    </div>
                                    <div class="col-md-6">
                                        <label for="tipoCliente" class="form-label">Tipo de Cliente</label>
                                        <select name="tipoCliente" id="tipoCliente" class="form-select form-select-sm" required>
                                            <option value="empresa">Empresa</option>
                                            <option value="individual">Individual</option>
                                        </select>
                                    </div>
                                    <div class="col-md-12 mt-3">
                                        <button type="submit" class="btn btn-success btn-sm"><i class="fas fa-save"></i> Guardar Cliente</button>
                                        <a href="clientes.jsp" class="btn btn-secondary btn-sm ms-2"><i class="fas fa-times"></i> Cancelar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <!-- Formulario de Edición -->
                    <c:if test="${param.action == 'editar'}">
                        <div class="mb-4 p-3 bg-light rounded">
                            <h5 class="mb-3">Editar Cliente</h5>
                            <form action="clientes" method="post">
                                <input type="hidden" name="action" value="actualizar">
                                <input type="hidden" name="id" value="${cliente.idPersona}">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="edicion_nombre" class="form-label">Nombre Completo</label>
                                        <input type="text" name="nombre" id="edicion_nombre" class="form-control form-control-sm" value="${cliente.nombreCompleto}" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_documento" class="form-label">Documento de Identidad</label>
                                        <input type="text" name="documento" id="edicion_documento" class="form-control form-control-sm" value="${cliente.documentoIdentidad}" required readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_telefono" class="form-label">Teléfono</label>
                                        <input type="text" name="telefono" id="edicion_telefono" class="form-control form-control-sm" value="${cliente.telefono}">
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_correo" class="form-label">Correo Electrónico</label>
                                        <input type="email" name="correo" id="edicion_correo" class="form-control form-control-sm" value="${cliente.correoElectronico}" required>
                                    </div>
                                    <div class="col-md-12">
                                        <label for="edicion_direccion" class="form-label">Dirección</label>
                                        <input type="text" name="direccion" id="edicion_direccion" class="form-control form-control-sm" value="${cliente.direccion}">
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_tipoCliente" class="form-label">Tipo de Cliente</label>
                                        <select name="tipoCliente" id="edicion_tipoCliente" class="form-select form-select-sm">
                                            <option value="empresa" ${cliente.tipoCliente == 'empresa' ? 'selected' : ''}>Empresa</option>
                                            <option value="individual" ${cliente.tipoCliente == 'individual' ? 'selected' : ''}>Individual</option>
                                        </select>
                                    </div>
                                    <div class="col-md-12 mt-3">
                                        <button type="submit" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i> Actualizar Cliente</button>
                                        <a href="clientes.jsp" class="btn btn-secondary btn-sm ms-2"><i class="fas fa-times"></i> Cancelar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <!-- Tabla de Clientes -->
                    <c:if test="${empty param.action or param.action == 'listar'}">
                        <div class="table-responsive">
                            <table class="table table-hover table-bordered align-middle">
                                <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Documento</th>
                                    <th>Teléfono</th>
                                    <th>Correo</th>
                                    <th>Tipo</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${clientes}" var="cliente">
                                    <tr>
                                        <td>${cliente.idPersona}</td>
                                        <td>${cliente.nombreCompleto}</td>
                                        <td>${cliente.documentoIdentidad}</td>
                                        <td>${cliente.telefono}</td>
                                        <td>${cliente.correoElectronico}</td>
                                        <td>${cliente.tipoCliente}</td>
                                        <td class="text-center">
                                            <a href="?action=editar&id=${cliente.idPersona}" class="btn btn-outline-primary btn-sm me-1" title="Editar">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="?action=eliminar&id=${cliente.idPersona}" onclick="return confirm('¿Estás seguro de eliminar este cliente?')" class="btn btn-outline-danger btn-sm" title="Eliminar">
                                                <i class="fas fa-trash-alt"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <div class="d-flex justify-content-between mt-4">
        <small class="text-muted">© 2025 MultiWorks Group</small>
        <a href="index.jsp" class="btn btn-sm btn-outline-secondary"><i class="fas fa-home"></i> Volver al Inicio</a>
    </div>
</div>

<!-- Bootstrap JS + FontAwesome -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>