<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Perfil de Usuario - MultiWorks Group</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        body {
            background-color: #f8f9fa;
        }
        .profile-card {
            max-width: 500px;
            margin: auto;
        }
        .info-label {
            font-weight: bold;
            color: #555;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<jsp:include page="header.jsp" />

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">

            <!-- Tarjeta de perfil -->
            <div class="card shadow-sm profile-card">
                <div class="card-header bg-white text-center">
                    <h4><i class="fas fa-user-circle me-2"></i>Mi Perfil</h4>
                </div>
                <div class="card-body text-center">


                    <i class="fas fa-user-circle text-secondary mb-3" style="font-size: 5em;"></i>
                    <h5 class="mb-1">${nombreUsuario}</h5>
                    <p class="text-muted mb-4">${rolUsuario}</p>

                    <!-- Información del usuario -->
                    <ul class="list-group list-group-flush text-start">
                        <li class="list-group-item">
                            <span class="info-label">Correo:</span> ${correoUsuario}
                        </li>
                        <li class="list-group-item">
                            <span class="info-label">Rol:</span> ${rolUsuario}
                        </li>
                        <li class="list-group-item">
                            <span class="info-label">Estado:</span>
                            <c:choose>
                                <c:when test="${estadoUsuario == 'activo'}">
                                    <span class="badge bg-success">Activo</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger">Inactivo</span>
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li class="list-group-item">
                            <span class="info-label">Fecha de Registro:</span> ${creadoEn}
                        </li>
                    </ul>

                    <hr class="my-4">

                    <!-- Acciones -->
                    <div class="d-grid gap-2">
                        <a href="#" class="btn btn-outline-primary btn-sm"><i class="fas fa-edit"></i> Editar Perfil</a>
                        <a href="logout" class="btn btn-danger btn-sm"><i class="fas fa-sign-out-alt"></i> Cerrar Sesión</a>
                    </div>

                </div>
            </div>

            <!-- Volver al inicio -->
            <div class="text-center mt-4">
                <a href="index.jsp" class="btn btn-link"><i class="fas fa-arrow-left"></i> Volver al Dashboard</a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS + FontAwesome -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
