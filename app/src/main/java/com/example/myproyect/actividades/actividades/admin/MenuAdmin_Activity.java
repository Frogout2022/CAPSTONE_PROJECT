package com.example.myproyect.actividades.actividades.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MenuAdmin_Activity extends AppCompatActivity {
    FloatingActionButton btnSalir, btnListarUsers, btnListarRsv, btnMante;
    TextView txtvHora;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.myproyect.R.layout.activity_menu_admin);

        asignarReferencias();
        mostrarHora();
    }
    void mostrarHora(){
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
    void asignarReferencias(){
        txtvHora = findViewById(R.id.txtv_hora_adm);


        btnSalir = findViewById(R.id.imgbtn_exit_adm);
        btnSalir.setOnClickListener(view -> {
            Intent intent = new Intent(this, Login_Activity.class);
            startActivity(intent);
            finish();
        });

        btnListarUsers = findViewById(R.id.imgbtn_listusers_adm);
        btnListarUsers.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListaUsuarios_Activity.class);
            startActivity(intent);
            finish();
        });

        btnListarRsv = findViewById(R.id.imgbtn_listRsv_adm);
        btnListarRsv.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListaReservas_ADM_Activity.class);
            startActivity(intent);
            finish();

        });

        btnMante = findViewById(R.id.imgbtn_mante_adm);
        btnMante.setOnClickListener(view -> {
            Intent intent = new Intent(this, MantenimientoLosas_Activity.class);
            startActivity(intent);
            finish();
        });

    }
}