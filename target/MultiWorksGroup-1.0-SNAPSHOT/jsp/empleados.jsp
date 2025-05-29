<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Empleados - MultiWorks Group</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

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
            <h2>Gestión de Empleados</h2>
            <p class="text-muted">Registra, edita o elimina empleados del sistema.</p>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="card shadow-sm">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Empleados Registrados</h5>
                    <a href="?action=registrar" class="btn btn-primary btn-sm">
                        <i class="fas fa-plus"></i> Registrar Nuevo Empleado
                    </a>
                </div>
                <div class="card-body">

                    <!-- Mensaje de error si existe -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <!-- Formulario de Registro -->
                    <c:if test="${param.action == 'registrar'}">
                        <div class="mb-4 p-3 bg-light rounded">
                            <h5 class="mb-3">Registrar Nuevo Empleado</h5>
                            <form action="empleados" method="post">
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
                                        <label for="tipoContratacion" class="form-label">Tipo de Contratación</label>
                                        <select name="tipoContratacion" id="tipoContratacion" class="form-select form-select-sm" required>
                                            <option value="permanente">Permanente</option>
                                            <option value="por_proyecto">Por Proyecto</option>
                                        </select>
                                    </div>
                                    <div class="col-md-12 mt-3">
                                        <button type="submit" class="btn btn-success btn-sm"><i class="fas fa-save"></i> Guardar Empleado</button>
                                        <a href="empleados.jsp" class="btn btn-secondary btn-sm ms-2"><i class="fas fa-times"></i> Cancelar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <!-- Formulario de Edición -->
                    <c:if test="${param.action == 'editar'}">
                        <div class="mb-4 p-3 bg-light rounded">
                            <h5 class="mb-3">Editar Empleado</h5>
                            <form action="empleados" method="post">
                                <input type="hidden" name="action" value="actualizar">
                                <input type="hidden" name="id" value="${empleado.idPersona}">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="edicion_nombre" class="form-label">Nombre Completo</label>
                                        <input type="text" name="nombre" id="edicion_nombre" class="form-control form-control-sm" value="${empleado.nombreCompleto}" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_documento" class="form-label">Documento de Identidad</label>
                                        <input type="text" name="documento" id="edicion_documento" class="form-control form-control-sm" value="${empleado.documentoIdentidad}" required readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_telefono" class="form-label">Teléfono</label>
                                        <input type="text" name="telefono" id="edicion_telefono" class="form-control form-control-sm" value="${empleado.telefono}">
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_correo" class="form-label">Correo Electrónico</label>
                                        <input type="email" name="correo" id="edicion_correo" class="form-control form-control-sm" value="${empleado.correoElectronico}" required>
                                    </div>
                                    <div class="col-md-12">
                                        <label for="edicion_direccion" class="form-label">Dirección</label>
                                        <input type="text" name="direccion" id="edicion_direccion" class="form-control form-control-sm" value="${empleado.direccion}">
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_tipoContratacion" class="form-label">Tipo de Contratación</label>
                                        <select name="tipoContratacion" id="edicion_tipoContratacion" class="form-select form-select-sm">
                                            <option value="permanente" ${empleado.tipoContratacion == 'permanente' ? 'selected' : ''}>Permanente</option>
                                            <option value="por_proyecto" ${empleado.tipoContratacion == 'por_proyecto' ? 'selected' : ''}>Por Proyecto</option>
                                        </select>
                                    </div>
                                    <div class="col-md-12 mt-3">
                                        <button type="submit" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i> Actualizar Empleado</button>
                                        <a href="/jsp/empleados.jsps" class="btn btn-secondary btn-sm ms-2"><i class="fas fa-times"></i> Cancelar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <!-- Tabla de Empleados -->
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
                                    <th>Tipo de Contratación</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${empleados}" var="empleado">
                                    <tr>
                                        <td>${empleado.idPersona}</td>
                                        <td>${empleado.nombreCompleto}</td>
                                        <td>${empleado.documentoIdentidad}</td>
                                        <td>${empleado.telefono}</td>
                                        <td>${empleado.correoElectronico}</td>
                                        <td>${empleado.tipoContratacion}</td>
                                        <td class="text-center">
                                            <a href="?action=editar&id=${empleado.idPersona}" class="btn btn-outline-primary btn-sm me-1" title="Editar">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="?action=eliminar&id=${empleado.idPersona}" onclick="return confirm('¿Estás seguro de eliminar este empleado?')" class="btn btn-outline-danger btn-sm" title="Eliminar">
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