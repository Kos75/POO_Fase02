<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Usuarios - MultiWorks Group</title>

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
        .badge-status {
            font-size: 0.75rem;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<jsp:include page="header.jsp" />

<div class="container-fluid py-4">
    <div class="row">
        <div class="col-md-12 mb-4">
            <h2>Gestión de Usuarios</h2>
            <p class="text-muted">Registra, edita o elimina usuarios del sistema.</p>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="card shadow-sm">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Usuarios Registrados</h5>
                    <a href="?action=registrar" class="btn btn-primary btn-sm">
                        <i class="fas fa-plus"></i> Registrar Nuevo Usuario
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
                            <h5 class="mb-3">Registrar Nuevo Usuario</h5>
                            <form action="usuarios" method="post">
                                <input type="hidden" name="action" value="guardar">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="nombre" class="form-label">Nombre Completo</label>
                                        <input type="text" name="nombre" id="nombre" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="correo" class="form-label">Correo Electrónico</label>
                                        <input type="email" name="correo" id="correo" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="clave" class="form-label">Contraseña</label>
                                        <input type="password" name="clave" id="clave" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="rol" class="form-label">Rol del Usuario</label>
                                        <select name="rol" id="rol" class="form-select form-control-sm" required>
                                            <option value="admin">Administrador</option>
                                            <option value="empleado">Empleado</option>
                                            <option value="cliente">Cliente</option>
                                        </select>
                                    </div>
                                    <div class="col-md-12 mt-3">
                                        <button type="submit" class="btn btn-success btn-sm"><i class="fas fa-save"></i> Guardar Usuario</button>
                                        <a href="usuarios" class="btn btn-secondary btn-sm ms-2"><i class="fas fa-times"></i> Cancelar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <!-- Formulario de Edición -->
                    <c:if test="${param.action == 'editar'}">
                        <div class="mb-4 p-3 bg-light rounded">
                            <h5 class="mb-3">Editar Usuario</h5>
                            <form action="usuarios" method="post">
                                <input type="hidden" name="action" value="actualizar">
                                <input type="hidden" name="id" value="${usuario.idUsuario}">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="edicion_nombre" class="form-label">Nombre Completo</label>
                                        <input type="text" name="nombre" id="edicion_nombre" class="form-control form-control-sm" value="${usuario.nombre}" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_correo" class="form-label">Correo Electrónico</label>
                                        <input type="email" name="correo" id="edicion_correo" class="form-control form-control-sm" value="${usuario.correo}" required readonly>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_rol" class="form-label">Rol del Usuario</label>
                                        <select name="rol" id="edicion_rol" class="form-select form-control-sm" required>
                                            <option value="admin" ${usuario.rol == 'admin' ? 'selected' : ''}>Administrador</option>
                                            <option value="empleado" ${usuario.rol == 'empleado' ? 'selected' : ''}>Empleado</option>
                                            <option value="cliente" ${usuario.rol == 'cliente' ? 'selected' : ''}>Cliente</option>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="estado" class="form-label">Estado</label>
                                        <select name="estado" id="estado" class="form-select form-control-sm" required>
                                            <option value="activo" ${usuario.estado == 'activo' ? 'selected' : ''}>Activo</option>
                                            <option value="inactivo" ${usuario.estado == 'inactivo' ? 'selected' : ''}>Inactivo</option>
                                        </select>
                                    </div>
                                    <div class="col-md-12 mt-3">
                                        <button type="submit" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i> Actualizar Usuario</button>
                                        <a href="usuarios" class="btn btn-secondary btn-sm ms-2"><i class="fas fa-times"></i> Cancelar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <!-- Tabla de Usuarios -->
                    <c:if test="${empty param.action or param.action == 'listar'}">
                        <div class="table-responsive">
                            <table class="table table-hover table-bordered align-middle">
                                <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Correo</th>
                                    <th>Rol</th>
                                    <th>Estado</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${usuarios}" var="u">
                                    <tr>
                                        <td>${u.idUsuario}</td>
                                        <td>${u.nombre}</td>
                                        <td>${u.correo}</td>
                                        <td>${u.rol}</td>
                                        <td>
                                            <span class="badge ${u.estado == 'activo' ? 'bg-success' : 'bg-danger'} badge-status">
                                                    ${u.estado}
                                            </span>
                                        </td>
                                        <td class="text-center">
                                            <a href="?action=editar&id=${u.idUsuario}" class="btn btn-outline-primary btn-sm me-1" title="Editar">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="?action=eliminar&id=${u.idUsuario}" onclick="return confirm('¿Estás seguro de eliminar este usuario?')" class="btn btn-outline-danger btn-sm" title="Eliminar">
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