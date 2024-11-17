package com.example.myproyect.actividades.modelos;

import com.example.myproyect.actividades.clases.PasswordEncryptor;
import com.example.myproyect.actividades.conexion.ConexionMySQL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DAO_Administrador {

    public static final String tabla = "ADMIN";
    public static final String colum_correo = "CORREO_ADM"; //pos i:5

    public static boolean ConsultarAdm(String correo, String pass){ //consultar logueo
        //PARA V. LOGIN
        boolean b = false;
        Connection cnx = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
             cnx= ConexionMySQL.getConexion();
            //Statement statement = cnx.createStatement();
            String consulta = "SELECT * FROM "+tabla+" WHERE "+colum_correo+" = ?";
            //CallableStatement csta=cnx.prepareCall("{call sp_consultarADM(?,?)}");
            //csta.setString(1, correo);
            //csta.setString(2, pass);
            statement = cnx.prepareStatement(consulta);
            statement.setString(1, correo);

            rs= statement.executeQuery();
            if(rs.next()) {
                //Tienda.setAdmin(true);
                String claveHash= rs.getString(5);
                if(PasswordEncryptor.checkPassword(pass,claveHash )){
                    b = true;
                }
            }

        }catch(Exception e){
            System.out.println("Error AE ConsultarADM(): "+e);
        }finally {
            try {
                //cerrar recursos
                if(rs!=null) rs.close();
                if(statement!= null) statement.close();
                if(cnx != null) ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return b;
    }

    public static boolean ConsultarCorreoAdm(String correo){ //consultar logueo
        //PARA V. LOGIN
        boolean b = false;
        Connection cnx = null;
        Statement statement = null;
        ResultSet rs = null;
        try{
            cnx= ConexionMySQL.getConexion();statement = cnx.createStatement();
            String consulta = "SELECT * FROM ADMIN WHERE CORREO_ADM='"+correo+"' ";
            rs= statement.executeQuery(consulta);
            if(rs.next()) {
                //Tienda.setAdmin(true);
                b = true;
            }

        }catch(Exception e){
            System.out.println("Error[DAO] ConsultarCorreoADM(): "+e);
        }finally {
            try {
                if(rs!=null) rs.close();
                if(statement!=null)  statement.close();
                if(cnx != null)  ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return b;
    }

    public static boolean ConsultarDni(String dni){
        //PARA V. LOGIN
        boolean b= false;
        Connection cnx = null;
        CallableStatement csta = null;
        ResultSet rs = null;
        try{
            cnx=ConexionMySQL.getConexion();
            csta=cnx.prepareCall("{call sp_consultarDniADM(?)}");
            csta.setString(1, dni);
            rs= csta.executeQuery();
            if(rs.next()) b = true;

        }catch(Exception e){
            System.out.println("ERROR AE ConsultarDni(): "+e);
        }finally {
            try {
                //cerrar recursos
                if(rs!=null) rs.close();
                if(csta!= null) csta.close();
                if(cnx!=null) ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return b;
    }
}
