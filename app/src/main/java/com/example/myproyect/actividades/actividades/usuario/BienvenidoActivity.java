package com.example.myproyect.actividades.actividades.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.clases.InterfaceMenu;
import com.example.myproyect.actividades.entidades.Usuario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BienvenidoActivity extends AppCompatActivity implements InterfaceMenu {

    TextView lblSaludo,txtvHora;
    Button btnSalida,btnReservar, btnActualizarDatos, btnConsultar;
    TextView lblCancha1, lblCancha2, lblCancha3, lblCancha4;
    Usuario usuario = Login_Activity.getUsuario();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        referencias();
        mostrarHora();

        lblSaludo = findViewById(R.id.bieLblSaludo);
        String nomUsuario = usuario.getNombre();
        lblSaludo.setText("Bienvenido "+nomUsuario);

    }
    private void mostrarHora(){
        // Configurar zona horaria de América/Lima
        TimeZone timeZoneLima = TimeZone.getTimeZone("America/Lima");

        // Actualizar la hora cada segundo
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Formato de hora y fecha
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale.getDefault());
                sdf.setTimeZone(timeZoneLima); // Establecer zona horaria

                String currentTime = sdf.format(new Date());
                txtvHora.setText(currentTime);

                handler.postDelayed(this, 1000); // Actualizar cada segundo
            }
        }, 0);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener las actualizaciones cuando la actividad se destruya
        handler.removeCallbacksAndMessages(null);
    }
    private void referencias(){
        txtvHora = findViewById(R.id.txtv_time_User);

        btnConsultar = findViewById(R.id.btnReservasRealizadas_User);
        btnConsultar.setOnClickListener(view -> {
            Intent intent  = new Intent(this, ListaReservas_Activity.class);
            startActivity(intent);
            finish();
        });
        btnReservar = findViewById(R.id.btnRealizarRsv_BienvenidoActy);
        btnReservar.setOnClickListener(view -> {
            Intent intent = new Intent(this, TablaReservaUser_Activity.class);
            startActivity(intent);
            finish();
        });
        btnSalida = findViewById(R.id.actvbtnCerrar);
        btnSalida.setOnClickListener(view -> {

            Intent intent = new Intent(this, Login_Activity.class);
            startActivity(intent);
            this.finish();
        });
        btnActualizarDatos = findViewById(R.id.btnActualizarDatos_BienvenidoActv);
        btnActualizarDatos.setOnClickListener(view -> {
            finish();
            Intent intent = new Intent(this, ActualizarDatosUSER_Activity.class);
            startActivity(intent);

        });
    }

    @Override
    public void onClickMenu(int idBoton) {
        Intent iMenu = new Intent(this, Menu_Losas_Activity.class);
        iMenu.putExtra("idBoton",idBoton);
        startActivity(iMenu);
     //   finish();
    }

    private void cerrarSesion() {
        //borrar sesion de la base de datos interna
        //Sesion sesion = new Sesion(this);
        //sesion.eliminarUsuario(1);
        //destruir historial
        this.finish();
        //mandar al login
        Intent iLogin = new Intent(this, Login_Activity.class);
        startActivity(iLogin);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}