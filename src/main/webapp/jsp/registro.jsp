<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Usuario - MultiWorks Group</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        body {
            background-color: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .register-card {
            width: 100%;
            max-width: 500px;
            padding: 2rem;
            border-radius: 0.5rem;
            background-color: white;
            box-shadow: 0 0.5rem 1rem rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>

<div class="register-card mx-auto">
    <h4 class="mb-4 text-center">Registrar Nuevo Usuario</h4>

    <!-- Mostrar error si hay -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/usuarios" method="post">
        <input type="hidden" name="action" value="guardar">

        <div class="mb-3">
            <label for="nombre" class="form-label">Nombre Completo</label>
            <input type="text" name="nombre" id="nombre" class="form-control" placeholder="Ej. Juan Pérez" required>
        </div>

        <div class="mb-3">
            <label for="correo" class="form-label">Correo Electrónico</label>
            <input type="email" name="correo" id="correo" class="form-control" placeholder="ejemplo@correo.com" required>
        </div>

        <div class="mb-3">
            <label for="clave" class="form-label">Contraseña</label>
            <input type="password" name="clave" id="clave" class="form-control" placeholder="Mínimo 6 caracteres" required minlength="6">
        </div>

        <div class="mb-3">
            <label for="rol" class="form-label">Rol de Usuario</label>
            <select name="rol" id="rol" class="form-select" required>
                <option value="" selected disabled>Selecciona un rol</option>
                <option value="admin">Administrador</option>
                <option value="empleado">Empleado</option>
                <option value="cliente">Cliente</option>
            </select>
        </div>

        <div class="d-grid gap-2 mt-4">
            <button type="submit" class="btn btn-primary">Registrar Usuario</button>
        </div>

        <div class="mt-3 text-center">
            <a href="${pageContext.request.contextPath}/jsp/login.jsp"><i class="fas fa-arrow-left"></i> Volver al Inicio de Sesión</a>
        </div>
    </form>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>