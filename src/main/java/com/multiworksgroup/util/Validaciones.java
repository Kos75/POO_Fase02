package com.multiworksgroup.util;

public class Validaciones {

    // Validar email con expresión regular
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }

    // Validar que el campo no esté vacío
    public static boolean isCampoVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    // Validar número entero positivo
    public static boolean isNumeroEntero(String valor) {
        try {
            int numero = Integer.parseInt(valor);
            return numero >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validar número decimal positivo
    public static boolean isNumeroDecimal(String valor) {
        try {
            double numero = Double.parseDouble(valor);
            return numero >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validar fecha no menor a hoy
    public static boolean esFechaValida(java.util.Date fechaIngresada) {
        java.util.Date hoy = new java.util.Date();
        return fechaIngresada != null && !fechaIngresada.before(hoy);
    }

    // Validar documento de identidad (Ejemplo: formato simple)
    public static boolean esDocumentoValido(String documento) {
        return documento != null && documento.matches("\\d{8,14}"); // Número de 8 a 14 dígitos
    }

    // Validar teléfono (formato simple)
    public static boolean esTelefonoValido(String telefono) {
        return telefono != null && telefono.matches("\\d{8,15}");
    }

    // Validar que la edad sea entre 18 y 100
    public static boolean esEdadValida(int edad) {
        return edad >= 18 && edad <= 100;
    }

    // Validar longitud mínima de contraseña
    public static boolean esContrasenaValida(String contrasena) {
        return contrasena != null && contrasena.length() >= 6;
    }
}