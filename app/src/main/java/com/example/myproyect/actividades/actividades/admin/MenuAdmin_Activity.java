package com.example.myproyect.actividades.actividades.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.Login_Activity;

public class MenuAdmin_Activity extends AppCompatActivity {
    Button btnSalir, btnListarUsers ,btnLosas, btnListarRsv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.myproyect.R.layout.activity_menu_admin);

        asignarReferencias();
    }
    void asignarReferencias(){
        btnSalir = findViewById(R.id.btnSalirAdmin);
        btnSalir.setOnClickListener(view -> {
            Intent intent = new Intent(this, Login_Activity.class);
            startActivity(intent);
        });
        btnListarUsers = findViewById(R.id.btnListarUsers_MenuAdm);
        btnListarUsers.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListaUsuarios_Activity.class);
            startActivity(intent);
            finish();
        });

        btnLosas = findViewById(R.id.btn_MngLosas_AdminMenu);
        btnLosas.setOnClickListener(view -> {
            Intent intent = new Intent(this, MantenimientoLosas_Activity.class);
            startActivity(intent);
            finish();
        });

        btnListarRsv = findViewById(R.id.btnListarRsvUsers_MenuAdm);
        btnListarRsv.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListaReservas_ADM_Activity.class);
            startActivity(intent);
            finish();

        });

    }
}