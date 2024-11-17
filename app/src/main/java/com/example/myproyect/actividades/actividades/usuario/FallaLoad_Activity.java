package com.example.myproyect.actividades.actividades.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.CargaActivity;

public class FallaLoad_Activity extends AppCompatActivity {

    Button btnRefresh;
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

        validar();

    }

    void validar(){
        Intent intent = getIntent();
        String msg = intent.getStringExtra("MSG");
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        btnRefresh = findViewById(R.id.btnRecargar_FallaLoad);
        btnRefresh.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, CargaActivity.class);
            startActivity(intent1);
            this.finish();
        });

    }
}