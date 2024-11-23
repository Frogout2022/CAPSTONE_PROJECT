package com.example.myproyect.actividades.modelos;


import android.os.StrictMode;

import com.example.myproyect.actividades.conexion.ConexionMySQL;
import com.example.myproyect.actividades.entidades.Pago;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Pago {

    public static Pago consultarPago(String fecha,String hora){
        System.out.println("consultarPago");
        Pago pago = null;
        Connection cnx = null;
        CallableStatement callableStatement=null;
        ResultSet rs = null;
        try{
            cnx=ConexionMySQL.getConexion();
            callableStatement = cnx.prepareCall("{call sp_ConsultarPago(?,?)}");
            callableStatement.setString(1, fecha);
            callableStatement.setString(2,hora);
            System.out.println("fecha: "+fecha+" hora: "+hora);
            rs = callableStatement.executeQuery();

            if(rs.next()) {
                String fechaPago = rs.getString(2);
                String codPago = rs.getString(3);
                Double montoTotal = rs.getDouble(6);
                Double igvPago = rs.getDouble(7);
                String mediopago = rs.getString(8);

                pago = new Pago(fechaPago,codPago,montoTotal,igvPago,mediopago);

            }

        }catch(Exception e){
            System.out.println("Error al obtetenerDatosPago: " + e.getMessage());
        }finally {
            try {
                //CERRAR RECURSOS
                if(rs!=null) rs.close();
                if(callableStatement!=null) callableStatement.close();
                if(cnx!=null) ConexionMySQL.cerrarConexion(cnx);
            }catch (SQLException e){
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return pago;
    }
}
