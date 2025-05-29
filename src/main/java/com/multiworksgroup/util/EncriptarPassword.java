package com.multiworksgroup.util;

import org.mindrot.jbcrypt.BCrypt;

public class EncriptarPassword {

    // Método para encriptar contraseña
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Método para verificar si la contraseña coincide
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
