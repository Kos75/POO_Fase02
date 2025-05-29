<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - MultiWorks Group</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- FontAwesome Icons (opcional) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        body {
            background-color: #f8f9fa;
        }
        .card-counter{
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            transition: all 0.3s ease-in-out;
        }
        .card-counter:hover {
            transform: translateY(-5px);
        }
        .counter-icon {
            font-size: 2em;
            color: #ffffff;
        }
        .bg-blue { background-color: #3b7ddd; }
        .bg-green { background-color: #28a745; }
        .bg-orange { background-color: #fd7e14; }
        .bg-purple { background-color: #6f42c1; }

        .recent-item {
            padding: 10px 0;
            border-bottom: 1px solid #dee2e6;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<jsp:include page="header.jsp" />

<div class="container-fluid py-4">
    <div class="row">
        <div class="col-md-12 mb-4">
            <h2>Bienvenido al Sistema MultiWorks Group</h2>
            <p class="text-muted">Panel principal de gestión de empleados, clientes, cotizaciones y tareas asignadas.</p>
        </div>
    </div>

    <!-- Tarjetas de resumen -->
    <div class="row g-4">
        <div class="col-md-3">
            <div class="card text-white bg-blue card-counter shadow-sm">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <i class="fas fa-users counter-icon"></i>
                        </div>
                        <div>
                            <h5 class="card-title mb-0">Clientes</h5>
                            <h2 class="card-text">${contadorClientes}</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-green card-counter shadow-sm">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <i class="fas fa-briefcase counter-icon"></i>
                        </div>
                        <div>
                            <h5 class="card-title mb-0">Empleados</h5>
                            <h2 class="card-text">${contadorEmpleados}</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-orange card-counter shadow-sm">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <i class="fas fa-file-invoice-dollar counter-icon"></i>
                        </div>
                        <div>
                            <h5 class="card-title mb-0">Cotizaciones</h5>
                            <h2 class="card-text">${contadorCotizaciones}</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-white bg-purple card-counter shadow-sm">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <i class="fas fa-tasks counter-icon"></i>
                        </div>
                        <div>
                            <h5 class="card-title mb-0">Tareas Activas</h5>
                            <h2 class="card-text">${contadorTareas}</h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <hr class="my-4">

    <!-- Tablero rápido -->
    <div class="row">
        <div class="col-md-8">
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-white d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Actividades Recientes</h5>
                    <a href="cotizaciones" class="btn btn-sm btn-outline-primary"><i class="fas fa-eye"></i> Ver todas</a>
                </div>
                <div class="card-body">
                    <ul class="list-group list-group-flush">
                        <c:forEach items="${actividadesRecientes}" var="act">
                            <li class="list-group-item recent-item">
                                <strong>${act.titulo}</strong><br>
                                <small class="text-muted">Proyecto: ${act.areaAsignada} | Fecha: ${act.fechaInicio}</small>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-white d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Perfil del Usuario</h5>
                    <span class="badge bg-success">Activo</span>
                </div>
                <div class="card-body text-center">
                    <i class="fas fa-user-circle fa-5x text-secondary mb-3"></i>
                    <h4 class="mb-1">${nombreUsuario}</h4>
                    <p class="text-muted">${rolUsuario}</p>
                    <a href="#" class="btn btn-outline-primary btn-sm me-2"><i class="fas fa-cog"></i> Ajustes</a>
                    <a href="logout" class="btn btn-danger btn-sm"><i class="fas fa-sign-out-alt"></i> Cerrar Sesión</a>
                </div>
            </div>

            <!-- Acciones rápidas -->
            <div class="card shadow-sm">
                <div class="card-header bg-white">
                    <h5 class="mb-0">Acciones Rápidas</h5>
                </div>
                <div class="card-body">
                    <div class="list-group">
                        <a href="clientes.jsp" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                            Registrar Cliente
                            <i class="fas fa-chevron-right"></i>
                        </a>
                        <a href="empleados.jsp" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center mt-2">
                            Registrar Empleado
                            <i class="fas fa-chevron-right"></i>
                        </a>
                        <a href="cotizaciones.jsp" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center mt-2">
                            Crear Cotización
                            <i class="fas fa-chevron-right"></i>
                        </a>
                        <a href="tareas.jsp" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center mt-2">
                            Asignar Tarea
                            <i class="fas fa-chevron-right"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Fin del contenido -->
</div>

<!-- Bootstrap JS + FontAwesome -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>