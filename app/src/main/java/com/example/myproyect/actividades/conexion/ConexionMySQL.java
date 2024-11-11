package com.example.myproyect.actividades.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionMySQL {
    public static Connection getConexion() {
        Connection conexion = null;
        try {

            String url = "jdbc:mysql://milhos.kguard.org:3308/app_canchafacil";
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(url, "root", "admin");
            System.out.println("Conexión exitosa a la base de datos.");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }



    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }



    public static void main(String[] args) {
        Connection conexion = getConexion();
        // Aquí puedes realizar operaciones en la base de datos utilizando la conexión
        cerrarConexion(conexion);
    }
}


