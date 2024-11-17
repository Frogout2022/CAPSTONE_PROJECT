package com.example.myproyect.actividades.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.admin.ListarReservasADMIN_Activity;
import com.example.myproyect.actividades.actividades.usuario.FallaLoad_Activity;
import com.example.myproyect.actividades.clases.MostrarMensaje;
import com.example.myproyect.actividades.conexion.ConexionMySQL;

import java.sql.Connection;

public class CargaActivity extends AppCompatActivity {

    boolean pass = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);

        Thread tCarga = new Thread(){
            @Override
            public void run() {
                super.run();
                //validar los parametros correctos del programa
                //internet datos y etc
                //domir aprox 3 seg

                Connection cnx = ConexionMySQL.getConexion();
                if(cnx !=null){
                    ConexionMySQL.cerrarConexion(cnx);
                    pass = true;
                    //Toast.makeText(CargaActivity.this, "Conexion exitosa", Toast.LENGTH_LONG).show();
                }else{
                    pass = false;
                }

                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //cargar la siguiente actividad
                    //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    //StrictMode.setThreadPolicy(policy);
                    if(pass){
                        pass = false;
                        Intent iInicioSesion = new Intent(getApplicationContext(), Login_Activity.class);
                        startActivity(iInicioSesion);
                        //no deja historial
                        finish();
                    }else{
                        Intent iFail = new Intent(getApplicationContext(), FallaLoad_Activity.class);
                        iFail.putExtra("BD", "No se pudo conectar al Servidor");
                        startActivity(iFail);
                        finish();
                        //Toast.makeText(CargaActivity.this, "No se pudo conectar a la BD", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        tCarga.start();
    }
}