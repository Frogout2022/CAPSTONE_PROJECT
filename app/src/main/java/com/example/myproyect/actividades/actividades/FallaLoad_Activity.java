package com.example.myproyect.actividades.actividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myproyect.BuildConfig;
import com.example.myproyect.R;

public class FallaLoad_Activity extends AppCompatActivity {

    Button btnRefresh;
    TextView txtvBD, txtvZone, txtvWifi, txtvVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_falla_load);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        asginarReferencias();
        txtvVersion.setText("v"+ BuildConfig.VERSION_NAME);
        validar();

    }
    void asginarReferencias(){
        txtvVersion = findViewById(R.id.txtvVersionFailLoad);
        txtvBD = findViewById(R.id.txtvBdFailLoad);
        txtvZone = findViewById(R.id.txtvZoneFailLoad);
        txtvWifi = findViewById(R.id.txtvInternetFailLoad);
        btnRefresh = findViewById(R.id.btnRecargar_FallaLoad);
    }
    void validar(){
        Intent intent = getIntent();
        boolean bd = intent.getBooleanExtra("BD", false);
        boolean zone = intent.getBooleanExtra("zone", false);
        boolean wifi = intent.getBooleanExtra("wifi", false);

        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        if(!bd) {
            txtvBD.setText("- No se pudo conectar al Servidor.");
            txtvBD.setTextColor(Color.parseColor("#FF5733"));
        } else {
            txtvBD.setText("- Conexion correcta con el servidor.");
        }

        if(!zone) {
            txtvZone.setText("- Cambiar la zona horaria a America/Lima.");
            txtvZone.setTextColor(Color.parseColor("#FF5733"));
        } else {
            txtvZone.setText("- La zona horaria es America/Lima.");
        }
        if(!wifi) {
            txtvWifi.setText("- No tienes conexion a Internet.");
            txtvWifi.setTextColor(Color.parseColor("#FF5733"));
        }
        else {
            txtvWifi.setText("- Tienes conexion a Internet.");
        }


        btnRefresh.setOnClickListener(view -> {
            btnRefresh.setEnabled(false);
            Intent intent1 = new Intent(this, Carga_Activity.class);
            startActivity(intent1);
            this.finish();
        });

    }
}