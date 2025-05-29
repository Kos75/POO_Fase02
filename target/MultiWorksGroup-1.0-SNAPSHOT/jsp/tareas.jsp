<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Tareas - MultiWorks Group</title>

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
            <h2>Gestión de Asignaciones de Tareas</h2>
            <p class="text-muted">Registra, edita o elimina asignaciones de tareas a empleados.</p>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="card shadow-sm">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Tareas Asignadas</h5>
                    <a href="?action=registrar" class="btn btn-primary btn-sm">
                        <i class="fas fa-plus"></i> Registrar Nueva Tarea
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
                            <h5 class="mb-3">Registrar Nueva Tarea</h5>
                            <form action="tareas" method="post">
                                <input type="hidden" name="action" value="guardar">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="empleado" class="form-label">Empleado</label>
                                        <select name="empleado" id="empleado" class="form-select form-control-sm" required>
                                            <c:forEach items="${empleados}" var="empleado">
                                                <option value="${empleado.idPersona}">${empleado.nombreCompleto} - ${empleado.documentoIdentidad}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="actividad" class="form-label">Actividad</label>
                                        <select name="actividad" id="actividad" class="form-select form-control-sm" required>
                                            <c:forEach items="${actividades}" var="actividad">
                                                <option value="${actividad.idActividad}">${actividad.titulo}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="titulo" class="form-label">Título de la Tarea</label>
                                        <input type="text" name="titulo" id="titulo" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="descripcion" class="form-label">Descripción (opcional)</label>
                                        <input type="text" name="descripcion" id="descripcion" class="form-control form-control-sm">
                                    </div>
                                    <div class="col-md-12 mt-3">
                                        <button type="submit" class="btn btn-success btn-sm"><i class="fas fa-save"></i> Guardar Tarea</button>
                                        <a href="tareas" class="btn btn-secondary btn-sm ms-2"><i class="fas fa-times"></i> Cancelar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <!-- Formulario de Edición -->
                    <c:if test="${param.action == 'editar'}">
                        <div class="mb-4 p-3 bg-light rounded">
                            <h5 class="mb-3">Editar Asignación de Tarea</h5>
                            <form action="tareas" method="post">
                                <input type="hidden" name="action" value="actualizar">
                                <input type="hidden" name="id" value="${asignacion.idAsignacion}">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="edicion_empleado" class="form-label">Empleado</label>
                                        <select name="empleado" id="edicion_empleado" class="form-select form-control-sm" required>
                                            <c:forEach items="${empleados}" var="empleado">
                                                <option value="${empleado.idPersona}" <c:if test="${empleado.idPersona == asignacion.idEmpleado}">selected</c:if>>
                                                        ${empleado.nombreCompleto} - ${empleado.documentoIdentidad}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_actividad" class="form-label">Actividad</label>
                                        <select name="actividad" id="edicion_actividad" class="form-select form-control-sm" required>
                                            <c:forEach items="${actividades}" var="actividad">
                                                <option value="${actividad.idActividad}" <c:if test="${actividad.idActividad == asignacion.idActividad}">selected</c:if>>
                                                        ${actividad.titulo}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_titulo" class="form-label">Título de la Tarea</label>
                                        <input type="text" name="titulo" id="edicion_titulo" class="form-control form-control-sm" value="${asignacion.titulo}" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_descripcion" class="form-label">Descripción (opcional)</label>
                                        <input type="text" name="descripcion" id="edicion_descripcion" class="form-control form-control-sm" value="${asignacion.descripcion}">
                                    </div>
                                    <div class="col-md-12 mt-3">
                                        <button type="submit" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i> Actualizar Tarea</button>
                                        <a href="/jsp/tareas.jsp" class="btn btn-secondary btn-sm ms-2"><i class="fas fa-times"></i> Cancelar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <!-- Tabla de Tareas -->
                    <c:if test="${empty param.action or param.action == 'listar'}">
                        <div class="table-responsive">
                            <table class="table table-hover table-bordered align-middle">
                                <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Título</th>
                                    <th>Empleado</th>
                                    <th>Actividad</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${asignaciones}" var="tarea">
                                    <tr>
                                        <td>${tarea.idAsignacion}</td>
                                        <td>${tarea.titulo}</td>
                                        <td>
                                            <c:forEach items="${empleados}" var="empleado">
                                                <c:if test="${empleado.idPersona == tarea.idEmpleado}">
                                                    ${empleado.nombreCompleto} - ${empleado.documentoIdentidad}
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach items="${actividades}" var="actividad">
                                                <c:if test="${actividad.idActividad == tarea.idActividad}">
                                                    ${actividad.titulo}
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td class="text-center">
                                            <a href="?action=editar&id=${tarea.idAsignacion}" class="btn btn-outline-primary btn-sm me-1" title="Editar">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="?action=eliminar&id=${tarea.idAsignacion}" onclick="return confirm('¿Estás seguro de eliminar esta tarea?')" class="btn btn-outline-danger btn-sm" title="Eliminar">
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