package com.example.myproyect.actividades.modelos;

import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.clases.PasswordEncryptor;
import com.example.myproyect.actividades.conexion.ConexionMySQL;
import com.example.myproyect.actividades.entidades.Usuario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAO_Cliente {
    public static final String tabla = "CLIENTE";


    public static ArrayList<Usuario> listarClientes() {//PARA V. MANTENIMIENTO DE EMPLEADO
        ArrayList<Usuario> lista = new ArrayList<>();
        Connection cnx = null;
        try {
            cnx = ConexionMySQL.getConexion();
            Statement statement = cnx.createStatement();
            String consulta = "SELECT * FROM "+tabla;
            ResultSet rs = statement.executeQuery(consulta);
            Usuario user;
            while (rs.next()) {
                String DNI = rs.getString(1);
                String nom = rs.getString(2);
                String ape = rs.getString(3);
                String correo = rs.getString(4);
                String clave = rs.getString(5);
                String cel = rs.getString(6);
                String fecha = rs.getString(7);
                user = new Usuario(DNI, nom, ape, correo, clave, cel, fecha);
                lista.add(user);
            }
        //cerrar recursos
            rs.close();
            statement.close();
            ConexionMySQL.cerrarConexion(cnx);
        } catch (Exception e) {
            System.out.println("ERROR[DAO_CLI] listarClientes(): " + e);
        }

        return lista;
    }

    public static Usuario ObtenerCLI(String correo, String pass){
        //PARA V. LOGIN
        Usuario user = null;
        try{
            Connection cnx=ConexionMySQL.getConexion();

            //Statement statement = cnx.createStatement();

            String consulta = "SELECT * FROM " + tabla + " WHERE CORREO_CLI = ?";
            PreparedStatement statement = cnx.prepareStatement(consulta);
            statement.setString(1, correo);

            ResultSet rs= statement.executeQuery();

            if(rs.next()) {
                String dni = rs.getString(1);
                String nom = rs.getString(2);
                String ape = rs.getString(3);
                String email = rs.getString(4);
                String claveHash  = rs.getString(5);// Password encriptado
                String cel = rs.getString(6);
                String fecha = rs.getString(7);

                // Verificar contraseña ingresada contra el hash almacenado
                if (PasswordEncryptor.checkPassword(pass, claveHash)){
                    user = new Usuario(dni, nom, ape, email, claveHash, cel, fecha);
                }

            }
            //cerrar recursos
            rs.close();
            statement.close();
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("ERROR[DAO_CLI] ObtenerCLI(): "+e);}

        return user;
    }

    public static String insertarCLI(Usuario user){
        String msg=null;
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta=	cnx.prepareCall("{call sp_InsertarCLI(?,?,?,?,?,?)}");
            csta.setString(1, user.getDNI());
            csta.setString(2, user.getNombre());
            csta.setString(3, user.getApellido());
            csta.setString(4, user.getCorreo());
            csta.setString(5, user.getClave());
            csta.setString(6, user.getCelular());

            int filasAfectadas = csta.executeUpdate();
            if(filasAfectadas>0) msg="Usuario registrado correctamente";
            else msg= "Error al intentar registrar!";

            //cerrar recursos
            csta.close();
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){
            System.out.println("ERROR[DAO_CLI] insertarCLI(): " +e);
            msg= "ERROR al registrar!";
        }
        return msg;
    }

    public static boolean ConsultarCorreo(String correo){
        //PARA V. REGISTRARSE & RESET_PASS
        boolean b = false;
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta=cnx.prepareCall("{call sp_consultarCorreoCLI(?)}");
            csta.setString(1, correo);
            ResultSet rs= csta.executeQuery();
            if(rs.next()) b= true;
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("Error[DAO_CLI] ConsultarCorreo(): "+e);}
        return b;
    }
    public static boolean ConsultarCelular(String celular){

        boolean b = false;
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta=cnx.prepareCall("{call sp_ConsultarCelularCLI(?)}");
            csta.setString(1, celular);
            ResultSet rs= csta.executeQuery();
            if(rs.next()) b= true;
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("ERROR[DAO_CLI] ConsultarCelular(): "+e);}
        return b;
    }


    public static boolean ConsultarDni(String dni){//PARA CONFIRMAR EL RESET PASS
        //PARA V. RESET PASS
        boolean b= false;
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta = cnx.prepareCall("{call sp_consultarDniCLI(?)}");
            csta.setString(1, dni);
            ResultSet rs= csta.executeQuery();
            if(rs.next()) b = true;
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("ERROR[DAO_CLI] ConsultarDni(): "+e);}
        return b;
    }

    public static String editarPass(String dni, String pass){
        String msg=null;

        try {
            Connection cnx = ConexionMySQL.getConexion();
            CallableStatement psta = cnx.prepareCall("{call sp_editarPassCLI(?,?)}");
            psta.setString(1, dni);
            String claveHash = PasswordEncryptor.encryptPassword(pass);
            psta.setString(2, claveHash);
            int filasAfectadas = psta.executeUpdate();

            if(filasAfectadas>0)msg="Se actualizó su contraseña";
            else msg="No se actualizó su contraseña";

            // Cerrar recursos
            psta.close();
            ConexionMySQL.cerrarConexion(cnx);

        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e){
            System.out.println("ERROR[DAO_CLI] editarPass: "+e.getMessage());
        }

        return msg;
    }


    public static  String updateDatos(String correo, String celular){
        String msg= "Error: ";
        String correo_login = Login_Activity.getUsuario().getCorreo();
        String cel_login = Login_Activity.getUsuario().getCelular();
        String dni = Login_Activity.getUsuario().getDNI();
        boolean retorno = false;

        if(!correo_login.equals(correo) ){
            if(ConsultarCorreo(correo)) {
                msg = msg+ "Correo ya existe ";
                retorno = true;
            }
        }
        if(!cel_login.equals(celular)){
            if(ConsultarCelular(celular)){
                msg = msg+" Celular ya existe ";
                retorno = true;
            }

        }
        if(retorno) return msg;
        else{
            try {
                Connection cnx = ConexionMySQL.getConexion();
                CallableStatement psta = cnx.prepareCall("{call sp_EditarDatosCLI(?,?,?)}");
                psta.setString(1, dni);
                psta.setString(2, correo);
                psta.setString(3, celular);
                int cambios = psta.executeUpdate();

                if(cambios >0) msg = "Datos actualizados correctamente";
                else msg = "Datos no actualizados!";

                //cererar recursos
                psta.close();
                ConexionMySQL.cerrarConexion(cnx);

                Login_Activity.usuario.setCorreo(correo);
                Login_Activity.usuario.setCelular(celular);
            } catch (Exception e) {
                System.out.println("ERROR[DAO_CLI] updateDatos(): "+e);
                msg = "Error al actualizar";
            }

            return msg;
        }
    }

    public static String deleteCLI(){
        String msg="";
        String dni = Login_Activity.getUsuario().getDNI();
        try {
            Connection cnx = ConexionMySQL.getConexion();
            CallableStatement psta = cnx.prepareCall("{call sp_EliminarCLI(?)}");
            psta.setString(1, dni);
            psta.executeUpdate();
            ConexionMySQL.cerrarConexion(cnx);
            msg = "Usuario eliminado correctamente";

        } catch (Exception e) {
            System.out.println("ERROR[DAO_CLI] deleteCLI(): "+e);
            msg = "Error al eliminar"+e;
        }
        return msg;
    }

}
