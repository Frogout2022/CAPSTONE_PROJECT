package com.example.myproyect.actividades.modelos;

import com.example.myproyect.actividades.clases.PasswordEncryptor;
import com.example.myproyect.actividades.conexion.ConexionMySQL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class DAO_Administrador {

    public static final String tabla = "ADMIN";
    public static final String colum_correo = "CORREO_ADM"; //pos i:5

    public static boolean ConsultarAdm(String correo, String pass){ //consultar logueo
        //PARA V. LOGIN
        boolean b = false;
        try{
            Connection cnx= ConexionMySQL.getConexion();
            //Statement statement = cnx.createStatement();
            String consulta = "SELECT * FROM "+tabla+" WHERE "+colum_correo+" = ?";
            //CallableStatement csta=cnx.prepareCall("{call sp_consultarADM(?,?)}");
            //csta.setString(1, correo);
            //csta.setString(2, pass);
            PreparedStatement statement = cnx.prepareStatement(consulta);
            statement.setString(1, correo);

            ResultSet rs= statement.executeQuery();
            if(rs.next()) {
                //Tienda.setAdmin(true);
                String claveHash= rs.getString(5);
                if(PasswordEncryptor.checkPassword(pass,claveHash )){
                    b = true;
                }
            }
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("Error AE ConsultarADM(): "+e);}
        return b;
    }

    public static boolean ConsultarCorreoAdm(String correo){ //consultar logueo
        //PARA V. LOGIN
        boolean b = false;
        try{
            Connection cnx= ConexionMySQL.getConexion();
            Statement statement = cnx.createStatement();
            String consulta = "SELECT * FROM ADMIN WHERE CORREO_ADM='"+correo+"' ";
            ResultSet rs= statement.executeQuery(consulta);
            if(rs.next()) {
                //Tienda.setAdmin(true);
                b = true;
            }
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("Error[DAO] ConsultarCorreoADM(): "+e);}
        return b;
    }

    public static boolean ConsultarDni(String dni){
        //PARA V. LOGIN
        boolean b= false;
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta=cnx.prepareCall("{call sp_consultarDniADM(?)}");
            csta.setString(1, dni);
            ResultSet rs= csta.executeQuery();
            if(rs.next()) b = true;
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("ERROR AE ConsultarDni(): "+e);}
        return b;
    }
}
