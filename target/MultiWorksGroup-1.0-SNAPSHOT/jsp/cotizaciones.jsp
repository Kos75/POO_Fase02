<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Cotizaciones - MultiWorks Group</title>

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
            <h2>Gestión de Cotizaciones</h2>
            <p class="text-muted">Registra, edita o elimina cotizaciones del sistema.</p>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="card shadow-sm">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Cotizaciones Registradas</h5>
                    <a href="?action=registrar" class="btn btn-primary btn-sm">
                        <i class="fas fa-plus"></i> Crear Nueva Cotización
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
                            <h5 class="mb-3">Registrar Nueva Cotización</h5>
                            <form action="cotizaciones" method="post">
                                <input type="hidden" name="action" value="guardar">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="cliente" class="form-label">Cliente</label>
                                        <select name="cliente" id="cliente" class="form-select form-control-sm" required>
                                            <c:forEach items="${clientes}" var="cliente">
                                                <option value="${cliente.idPersona}">${cliente.nombreCompleto} - ${cliente.documentoIdentidad}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="numero" class="form-label">Número de Cotización</label>
                                        <input type="text" name="numero" id="numero" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="horas" class="form-label">Horas del Proyecto</label>
                                        <input type="number" step="0.01" name="horas" id="horas" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="fecha_inicio" class="form-label">Fecha Inicio</label>
                                        <input type="date" name="fecha_inicio" id="fecha_inicio" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="fecha_fin" class="form-label">Fecha Fin</label>
                                        <input type="date" name="fecha_fin" id="fecha_fin" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="costos_asignados" class="form-label">Costos Asignados ($)</label>
                                        <input type="number" step="0.01" name="costos_asignados" id="costos_asignados" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="costos_adicionales" class="form-label">Costos Adicionales ($)</label>
                                        <input type="number" step="0.01" name="costos_adicionales" id="costos_adicionales" class="form-control form-control-sm" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="estado" class="form-label">Estado</label>
                                        <select name="estado" id="estado" class="form-select form-control-sm" required>
                                            <option value="en proceso">En Proceso</option>
                                            <option value="finalizada">Finalizada</option>
                                            <option value="cancelada">Cancelada</option>
                                        </select>
                                    </div>
                                    <div class="col-md-12 mt-3">
                                        <button type="submit" class="btn btn-success btn-sm"><i class="fas fa-save"></i> Guardar Cotización</button>
                                        <a href="cotizaciones.jsp" class="btn btn-secondary btn-sm ms-2"><i class="fas fa-times"></i> Cancelar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <!-- Formulario de Edición -->
                    <c:if test="${param.action == 'editar'}">
                        <div class="mb-4 p-3 bg-light rounded">
                            <h5 class="mb-3">Editar Cotización</h5>
                            <form action="cotizaciones" method="post">
                                <input type="hidden" name="action" value="actualizar">
                                <input type="hidden" name="id" value="${cotizacion.idCotizacion}">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="edicion_cliente" class="form-label">Cliente</label>
                                        <select name="cliente" id="edicion_cliente" class="form-select form-control-sm" required>
                                            <c:forEach items="${clientes}" var="cli">
                                                <option value="${cli.idPersona}" <c:if test="${cli.idPersona == cotizacion.idCliente}">selected</c:if>>
                                                        ${cli.nombreCompleto} - ${cli.documentoIdentidad}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_numero" class="form-label">Número de Cotización</label>
                                        <input type="text" name="numero" id="edicion_numero" class="form-control form-control-sm" value="${cotizacion.numeroCotizacion}" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_horas" class="form-label">Horas del Proyecto</label>
                                        <input type="number" step="0.01" name="horas" id="edicion_horas" class="form-control form-control-sm" value="${cotizacion.horasProyecto}" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_fecha_inicio" class="form-label">Fecha Inicio</label>
                                        <input type="date" name="fecha_inicio" id="edicion_fecha_inicio" class="form-control form-control-sm"
                                               value="<fmt:formatDate value='${cotizacion.fechaInicio}' pattern='yyyy-MM-dd' />" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_fecha_fin" class="form-label">Fecha Fin</label>
                                        <input type="date" name="fecha_fin" id="edicion_fecha_fin" class="form-control form-control-sm"
                                               value="<fmt:formatDate value='${cotizacion.fechaFin}' pattern='yyyy-MM-dd' />" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_costos_asignados" class="form-label">Costos Asignados ($)</label>
                                        <input type="number" step="0.01" name="costos_asignados" id="edicion_costos_asignados" class="form-control form-control-sm" value="${cotizacion.costosAsignados}" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_costos_adicionales" class="form-label">Costos Adicionales ($)</label>
                                        <input type="number" step="0.01" name="costos_adicionales" id="edicion_costos_adicionales" class="form-control form-control-sm" value="${cotizacion.costosAdicionales}" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="edicion_estado" class="form-label">Estado</label>
                                        <select name="estado" id="edicion_estado" class="form-select form-control-sm" required>
                                            <option value="en proceso" <c:if test="${cotizacion.estado == 'en proceso'}">selected</c:if>>En Proceso</option>
                                            <option value="finalizada" <c:if test="${cotizacion.estado == 'finalizada'}">selected</c:if>>Finalizada</option>
                                            <option value="cancelada" <c:if test="${cotizacion.estado == 'cancelada'}">selected</c:if>>Cancelada</option>
                                        </select>
                                    </div>
                                    <div class="col-md-12 mt-3">
                                        <button type="submit" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i> Actualizar Cotización</button>
                                        <a href="cotizaciones.jsp" class="btn btn-secondary btn-sm ms-2"><i class="fas fa-times"></i> Cancelar</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>

                    <!-- Tabla de Cotizaciones -->
                    <c:if test="${empty param.action or param.action == 'listar'}">
                        <div class="table-responsive">
                            <table class="table table-hover table-bordered align-middle">
                                <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Número</th>
                                    <th>Cliente</th>
                                    <th>Horas</th>
                                    <th>Fecha Inicio</th>
                                    <th>Fecha Fin</th>
                                    <th>Total ($)</th>
                                    <th>Estado</th>
                                    <th>Acciones</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${cotizaciones}" var="cotizacion">
                                    <tr>
                                        <td>${cotizacion.idCotizacion}</td>
                                        <td>${cotizacion.numeroCotizacion}</td>
                                        <td>
                                            <c:forEach items="${clientes}" var="cli">
                                                <c:if test="${cli.idPersona == cotizacion.idCliente}">
                                                    ${cli.nombreCompleto} - ${cli.documentoIdentidad}
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>${cotizacion.horasProyecto}</td>
                                        <td><fmt:formatDate value="${cotizacion.fechaInicio}" pattern="yyyy-MM-dd" /></td>
                                        <td><fmt:formatDate value="${cotizacion.fechaFin}" pattern="yyyy-MM-dd" /></td>
                                        <td>$${cotizacion.total}</td>
                                        <td>
                                            <span class="badge
                                                <c:choose>
                                                    <c:when test="${cotizacion.estado == 'en proceso'}">bg-info</c:when>
                                                    <c:when test="${cotizacion.estado == 'finalizada'}">bg-success</c:when>
                                                    <c:otherwise>bg-danger</c:otherwise>
                                                </c:choose>
                                                badge-status">
                                                    ${cotizacion.estado}
                                            </span>
                                        </td>
                                        <td class="text-center">
                                            <a href="?action=editar&id=${cotizacion.idCotizacion}" class="btn btn-outline-primary btn-sm me-1" title="Editar">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="?action=eliminar&id=${cotizacion.idCotizacion}" onclick="return confirm('¿Estás seguro de eliminar esta cotización?')" class="btn btn-outline-danger btn-sm" title="Eliminar">
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