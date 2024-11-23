package com.example.myproyect.actividades.modelos;


import com.example.myproyect.actividades.conexion.ConexionMySQL;
import com.example.myproyect.actividades.entidades.Pago;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_Pago {


    public static String insertarPago(Pago pago){
        String msg = null;
        Connection cnx = null;
        CallableStatement csta = null;
        try{
            cnx= ConexionMySQL.getConexion();
            //(dni,idLosa,horas,montoTotal, estado, tipo);
            //call insertPago('72673554',1,5,258.4,'aprobado', 'tarjeta');
            csta=	cnx.prepareCall("{call InsertarPago(?,?,?,?,?,?)}");
            csta.setString(1, pago.getDniCliPago()); //DNI
            csta.setString(2, pago.getNombre_losa()); //NOMBRE_LOSA
            csta.setInt(3, pago.getCantidad_horas()); //HORAS
            csta.setDouble(4, pago.getMontoTotal());//MONTO TOTAL
            csta.setString(5, pago.getEstadoPago());//ESTADO
            csta.setString(6, pago.getMedioPago());//TIPO

            int filasAfectadas = csta.executeUpdate();

            if(filasAfectadas>0) msg="Pago validado y registrado";
            else msg= "Error al intentar registrar!";

        }catch(Exception e){
            System.out.println("Error al INSERTAR PAGO: " + e.getMessage());
            msg= "ERROR al insertar Pago!";
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
    public static Pago consultarPago(String dni){
        Pago pago = null;
        //PARA V. LOGIN
        Connection cnx = null;
        CallableStatement callableStatement=null;
        ResultSet rs = null;
        try{
            cnx=ConexionMySQL.getConexion();

            callableStatement = cnx.prepareCall("{call ConsultarPagoPorDNI(?)}");
            callableStatement.setString(1, dni);

            rs = callableStatement.executeQuery();

            if(rs.next()) {
                String fechaPago = rs.getString(1);
                String codPago = rs.getString(2);
                String dniCli = rs.getString(3);
                String nom_losa = rs.getString(4);
                int cantHoras  = rs.getInt(5);
                String estadoPago = rs.getString(6);
                Double montoTotal = rs.getDouble(7);
                Double igvPago = rs.getDouble(8);
                String mediopago = rs.getString(9);

                pago = new Pago(fechaPago,codPago,dniCli,nom_losa,cantHoras,estadoPago,montoTotal,igvPago,mediopago);

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
