<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión - MultiWorks Group</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .login-card {
            width: 100%;
            max-width: 400px;
            padding: 2rem;
            border-radius: 0.5rem;
            background-color: white;
            box-shadow: 0 0.5rem 1rem rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>

<div class="login-card shadow-sm mx-auto">
    <h4 class="mb-4 text-center">Iniciar Sesión</h4>

    <!-- Mostrar error si hay -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="mb-3">
            <label for="correo" class="form-label">Correo Electrónico</label>
            <input type="email" name="correo" class="form-control" id="correo" placeholder="ejemplo@correo.com" required>
        </div>
        <div class="mb-3">
            <label for="clave" class="form-label">Contraseña</label>
            <input type="password" name="clave" class="form-control" id="clave" placeholder="Tu contraseña" required>
        </div>
        <div class="d-grid gap-2">
            <button type="submit" class="btn btn-primary">Iniciar Sesión</button>
        </div>
    </form>
    <hr>
    <p class="text-center mb-0">¿No tienes cuenta? <a href="${pageContext.request.contextPath}/jsp/registro.jsp">Regístrate</a></p>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>