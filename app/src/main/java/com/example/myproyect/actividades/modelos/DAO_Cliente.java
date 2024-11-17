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
    public static String msg = "ERROR!";

    public static ArrayList<Usuario> listarClientes() {//PARA V. MANTENIMIENTO DE EMPLEADO
        ArrayList<Usuario> lista = new ArrayList<>();
        Connection cnx = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            cnx = ConexionMySQL.getConexion();
            statement = cnx.createStatement();
            String consulta = "SELECT * FROM "+tabla;
            rs = statement.executeQuery(consulta);
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

        } catch (SQLException e) {
            System.out.println("Error al LISTAR CLIENTES: " + e.getMessage());
        }finally {
            try {
                //cerrar recursos
                if(rs!=null) rs.close();
                if(statement!=null) statement.close();
                if(cnx!=null)ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return lista;
    }

    public static Usuario ObtenerCLI(String correo, String pass){
        //PARA V. LOGIN
        Usuario user = null;
        Connection cnx = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
             cnx=ConexionMySQL.getConexion();

            //Statement statement = cnx.createStatement();

            String consulta = "SELECT * FROM " + tabla + " WHERE CORREO_CLI = ?";
            statement = cnx.prepareStatement(consulta);
            statement.setString(1, correo);

            rs= statement.executeQuery();

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

        }catch(Exception e){
            System.out.println("Error al obtetenerDAtosCLI: " + e.getMessage());
        }finally {
            try {
                //CERRAR RECURSOS
                if(rs!=null) rs.close();
                if(statement!=null) statement.close();
                if(cnx!=null) ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return user;
    }

    public static String insertarCLI(Usuario user){
        Connection cnx = null;
        CallableStatement csta = null;
        try{
            cnx=ConexionMySQL.getConexion();
            csta=	cnx.prepareCall("{call sp_InsertarCLI(?,?,?,?,?,?)}");
            csta.setString(1, user.getDNI());
            csta.setString(2, user.getNombre());
            csta.setString(3, user.getApellido());
            csta.setString(4, user.getCorreo());
            csta.setString(5, user.getClave());
            csta.setString(6, user.getCelular());

            int filasAfectadas = csta.executeUpdate();

            if(filasAfectadas>0) msg="Usuario registrado correctamente";
            else msg= "Error al intentar registrar!";

        }catch(Exception e){
            System.out.println("Error al INSERTAR CLIENTE: " + e.getMessage());
            msg= "ERROR al registrar!";
        }finally {
            try {
                //CERRAR RECURSOS
                if(csta!=null) csta.close();
                if(cnx!=null) ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return msg;
    }

    public static boolean ConsultarCorreo(String correo){
        //PARA V. REGISTRARSE & RESET_PASS
        boolean b = false;
        Connection cnx = null;
        CallableStatement csta = null;
        ResultSet rs = null;
        try{
            cnx=ConexionMySQL.getConexion();
            csta=cnx.prepareCall("{call sp_consultarCorreoCLI(?)}");
            csta.setString(1, correo);
            rs= csta.executeQuery();
            if(rs.next()) b= true;

        }catch(SQLException e){
            System.out.println("Error al CONSULTAR CORREO: " + e.getMessage());
        }finally {
            try {
                if(rs!=null) rs.close();
                if(csta!=null) csta.close();
                if(cnx!=null) ConexionMySQL.cerrarConexion(cnx);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return b;
    }

    public static boolean ConsultarCelular(String celular){

        boolean b = false;
        Connection cnx = null;
        CallableStatement csta = null;
        ResultSet rs = null;
        try{
            cnx=ConexionMySQL.getConexion();
            csta=cnx.prepareCall("{call sp_ConsultarCelularCLI(?)}");
            csta.setString(1, celular);
            rs= csta.executeQuery();
            if(rs.next()) b= true;

        }catch(SQLException e){
            System.out.println("Error al CONSULTAR CELULAR: " + e.getMessage());
        }finally {
            try {
                //cerrar recursos
                if(rs!=null) rs.close();
                if(csta!=null) csta.close();
                if(cnx != null) ConexionMySQL.cerrarConexion(cnx);
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return b;
    }


    public static boolean ConsultarDni(String dni){//PARA CONFIRMAR EL RESET PASS
        //PARA V. RESET PASS
        boolean b= false;
        Connection cnx = null;
        CallableStatement csta = null;
        ResultSet rs = null;
        try{
            cnx=ConexionMySQL.getConexion();
            csta = cnx.prepareCall("{call sp_consultarDniCLI(?)}");
            csta.setString(1, dni);
            rs= csta.executeQuery();
            if(rs.next()) b = true;

        }catch(Exception e){
            System.out.println("Error al cerrar CONSULTAR DNI CLI: " + e.getMessage());
        }finally {
            try {
                //cerrar recursos
                if(rs!=null) rs.close();
                if(csta!=null) csta.close();
                if(cnx !=null) ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return b;
    }

    public static String editarPass(String dni, String pass){
        Connection cnx= null;
        CallableStatement csta = null;
        try {
            cnx = ConexionMySQL.getConexion();
            csta = cnx.prepareCall("{call sp_editarPassCLI(?,?)}");
            csta.setString(1, dni);
            String claveHash = PasswordEncryptor.encryptPassword(pass);
            csta.setString(2, claveHash);
            int filasAfectadas = csta.executeUpdate();

            if(filasAfectadas>0) msg="Se actualizó su contraseña";
            else msg="No se actualizó su contraseña";

        } catch (SQLException e) {
            System.out.println("Error al EDITAR PASS CLI: " + e.getMessage());
        }finally {
            try {
                // Cerrar recursos
                if(csta!=null) csta.close();
                if(cnx !=null) ConexionMySQL.cerrarConexion(cnx);

            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return msg;
    }


    public static String updateDatos(String correo, String celular){
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
            Connection cnx = null;
            CallableStatement csta = null;

            try {
                cnx = ConexionMySQL.getConexion();
                csta = cnx.prepareCall("{call sp_EditarDatosCLI(?,?,?)}");
                csta.setString(1, dni);
                csta.setString(2, correo);
                csta.setString(3, celular);

                int cambios = csta.executeUpdate();

                if(cambios >0) msg = "Datos actualizados correctamente";
                else msg = "Datos no actualizados!";

                Login_Activity.usuario.setCorreo(correo);
                Login_Activity.usuario.setCelular(celular);
            } catch (SQLException e) {
                System.out.println("Error UPDATE DATOS CLI: " + e.getMessage());
            }finally {
                try {
                    //cererar recursos
                    if(csta!=null)csta.close();
                    if(cnx!=null)ConexionMySQL.cerrarConexion(cnx);
                }catch (SQLException e){
                    System.out.println("Error al cerrar recursos: " + e.getMessage());
                }
            }

            return msg;
        }
    }

    public static String deleteCLI(){

        String dni = Login_Activity.getUsuario().getDNI();

        Connection cnx = null;
        CallableStatement csta = null;

        try {
             cnx = ConexionMySQL.getConexion();
             csta = cnx.prepareCall("{call sp_EliminarCLI(?)}");
            csta.setString(1, dni);
            csta.executeUpdate();

            msg = "Usuario eliminado correctamente";

        } catch (Exception e) {
            System.out.println("Error al cerrar recursos: " + e.getMessage());
        }finally {
            try {
                //ceerar recursos
                if(csta!=null) csta.close();
                if(cnx!=null) ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return msg;
    }

}
