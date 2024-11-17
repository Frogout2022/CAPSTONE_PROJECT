package com.example.myproyect.actividades.modelos;


import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.conexion.ConexionMySQL;
import com.example.myproyect.actividades.entidades.Reserva;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO_Reserva {


    public static  ArrayList<Reserva> listarReservaSemanal(String tabla, int dia_min, int dia_max) {
        // PARA LA ACT. TABLA RESERVAS - CLI
        ArrayList<Reserva> lista = new ArrayList<>();
        Connection cnx = null;
        CallableStatement csta = null;
        ResultSet rs = null;
        try {
            cnx = ConexionMySQL.getConexion();
            csta = cnx.prepareCall("{call sp_ListarRsv(?,?,?)}");
            csta.setString(1, tabla); // 'reserva_losa1'
            csta.setInt(2, dia_min); // actual
            csta.setInt(3, dia_max); // actual + 7

            rs = csta.executeQuery();
            Reserva reserva;

            while (rs.next()) {
                String[] arrayHorario = new String[3];
                // i:1 -> id tabla
                // i:2 --> id_losa
                String dia = rs.getString(3);
                arrayHorario[0] = rs.getString(4);
                arrayHorario[1] = rs.getString(5);
                arrayHorario[2] = rs.getString(6);

                reserva = new Reserva(dia, arrayHorario);
                lista.add(reserva);
            }

        } catch (SQLException e) {
            System.out.println("Error al LISTAR RSV SEMANAL: " + e.getMessage());
        }finally {
            //cerrar recursos
            try{
                if(rs!=null) rs.close();
                if(csta!= null) csta.close();
                if(cnx!=null) ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return lista;
    }

    public static boolean LlenarTablaFecha()  {
        Connection cnx = null;
        CallableStatement csta = null;
        Boolean b=false;
        try {
            cnx = ConexionMySQL.getConexion();
            csta = cnx.prepareCall("{call sp_insertar_Fechas}");
            b = csta.execute();
        }catch (Exception e){
            System.out.println("Error al LLENAR TABLA RSV: " + e.getMessage());
        }finally {
            //cerrar recursos
            try {
                if (csta!= null) csta.close();
                if(cnx!= null) ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return b;
    }


    public static List<Reserva> ConsultarRsv(String tabla){
        //CONSULTAR RESERVAS DEL CLIENTE
        Connection cnx = null;
        CallableStatement csta = null;
        ResultSet rs = null;
        List<Reserva> lista = new ArrayList<>();
        String dni = Login_Activity.getUsuario().getDNI();
        try{
            cnx=ConexionMySQL.getConexion();csta=cnx.prepareCall("{call sp_ConsultarRsvCLI(?,?)}");
            csta.setString(1, tabla);
            csta.setString(2, dni);
            rs= csta.executeQuery();
            Reserva reserva=null;
            while(rs.next()){
                String dia;
                int id_losa=0;
                String[] arrayDni = new String[3];
                String dniBD = "";
                // i:1 -> ID_tabla
                // i:2 -> ID_LOSA
                id_losa = rs.getInt(2);
                dia = rs.getString(3);

                arrayDni[0] = rs.getString(4);
                arrayDni[1] = rs.getString(5);
                arrayDni[2] = rs.getString(6);

                reserva = new Reserva(dia, arrayDni, id_losa);
                lista.add(reserva);
            }

        }catch(SQLException e){
            System.out.println("Error al CONSULTAR RSV INDIVIDUALES: " + e.getMessage());
        }finally {
            try {
                //cerrar recursos
                if(rs!= null) rs.close();
                if(csta!=null) csta.close();
                if(cnx != null) ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return lista;
    }


    public static List<Reserva> listarReservasCLI(String losa){
    //LISTAR TODAS LAS RESERVAS DEL AÑO
        //PARA ACTV. LISTAR_RSV_ADMIN

        ArrayList<Reserva> lista = new ArrayList<>();
        Connection cnx = null;
        CallableStatement csta = null;
        ResultSet rs = null;
        try {
            cnx = ConexionMySQL.getConexion();
             csta = cnx.prepareCall("{call sp_ListarReservasCLI(?)}");
            csta.setString(1, losa);
             rs = csta.executeQuery();
            Reserva reserva;

            while (rs.next()) {

                String fecha ;
                String[] arrayDni = new String[3];
                String dniBD = ""; //evitar valores nulos

                // i:1 = id_reserva
                // i:2 = id_losa
                fecha = rs.getString(3);

                arrayDni[0] = rs.getString(4);
                arrayDni[1] = rs.getString(5);
                arrayDni[2] = rs.getString(6);

                reserva = new Reserva(fecha, arrayDni);
                lista.add(reserva);
            }

        } catch (SQLException e) {
            System.out.println("Error al LISTAR RESRVAS: " + e.getMessage());
        }finally {
            try {
                //cerrar recursos
                if(rs!= null) rs.close();
                if(csta!=null ) csta.close();
                if(cnx!=null) ConexionMySQL.cerrarConexion(cnx);
            } catch (Exception e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }


        return lista;
    }


    public static String insertarRSV(String tabla, String dia, int hora, String estado){ //PARA ACTV. CLIENTE
        //editar-UPDATE
        String hora_str= "";
        switch (hora){
            case 15:
                hora_str="3pm";break;
            case 17:
                hora_str="5pm";break;
            case 19:
                hora_str="7pm";break;
        }

        String msg="ERROR!";

        String dni = "";
        if(estado.equals("separar")) dni ="11111111";
        if(estado.equals("borrar" )) dni = null;
        if(estado.equals("aprobado")) dni = Login_Activity.getUsuario().getDNI();

        Connection cnx = null;
        CallableStatement csta = null;
        ResultSet rs = null;

        try{
            cnx = ConexionMySQL.getConexion();
            csta = cnx.prepareCall("{call sp_Reservar(?,?,?,?)}");
            csta.setString(1,tabla); // 'reserva_losa1'
            csta.setString(2,dia); // '2023-12-31'
            csta.setString(3,hora_str); // '3pm'
            csta.setString(4,dni); // '12345678'
            rs = csta.executeQuery();
            int fa = 0;
            if(rs.next()){
                 fa = rs.getInt(1);
            }
            if(fa>0){
                //hubo alguna fila afecta por UPDATE
                msg = "Reserva realizada correctamente";
            }else{
                msg="Error al reservar";
                //nignuna fila afectada
            }


        }catch(SQLException e){
            System.out.println("Error al INSERTAR RESERVAS: " + e.getMessage());
            msg = "ERROR al realizar la reserva";
        }finally {
            try {
                //cerrar recursos
                if(rs!=null) rs.close();
                if(csta!=null ) csta.close();
                if(cnx!=null) ConexionMySQL.cerrarConexion(cnx);

            } catch (Exception e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return msg;
    }

}
