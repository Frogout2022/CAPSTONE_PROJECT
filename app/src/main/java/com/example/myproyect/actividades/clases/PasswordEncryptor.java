package com.example.myproyect.actividades.clases;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {

    // Método para encriptar la contraseña
    public static String encryptPassword(String plainPassword) {
        // Generar el hash con un "salt" interno
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Método para verificar si una contraseña coincide con el hash
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }


}
