package com.example.myproyect.actividades.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myproyect.BuildConfig;
import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.usuario.FallaLoad_Activity;
import com.example.myproyect.actividades.clases.Fecha;
import com.example.myproyect.actividades.clases.Internet;
import com.example.myproyect.actividades.conexion.ConexionMySQL;

import java.sql.Connection;

public class CargaActivity extends AppCompatActivity {

    TextView txtvVersion;
    boolean pass1 = false, pass2 = false, pass3=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga);


        txtvVersion = findViewById(R.id.txtvVersionCargaLoad);
        txtvVersion.setText("v"+ BuildConfig.VERSION_NAME);

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
                    pass1 = true;
                    //Toast.makeText(CargaActivity.this, "Conexion exitosa", Toast.LENGTH_LONG).show();
                }else{
                    pass1 = false;
                }

                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {

                    pass2 = Fecha.getZonaHoraria();
                    pass3 = Internet.tieneConexionInternet(getApplicationContext());
                    if(pass1 && pass2 && pass3){
                        pass1 = false;
                        pass2 = false;
                        pass3 = false;
                        Intent iInicioSesion = new Intent(getApplicationContext(), Login_Activity.class);
                        startActivity(iInicioSesion);

                        finish();
                    }else{
                        Intent iFail = new Intent(getApplicationContext(), FallaLoad_Activity.class);
                        iFail.putExtra("BD", pass1);
                        iFail.putExtra("zoneH",pass2);
                        iFail.putExtra("wifi", pass3);
                        startActivity(iFail);
                        finish();
                        //No se puede usar Toast
                    }
                }
            }
        };
        tCarga.start();
    }
}