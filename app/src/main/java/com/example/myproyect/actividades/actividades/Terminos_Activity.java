package com.example.myproyect.actividades.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.usuario.Registro_Activity;

public class Terminos_Activity extends AppCompatActivity implements View.OnClickListener{

    Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos);
        //asociar
        btnRegresar = findViewById(R.id.termBtnRegresar);
        //asociar el click
        btnRegresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.termBtnRegresar:
            btnRegresar.setEnabled(false);
                regresar();
            break;
        }
    }

    private void regresar() {
        Intent iRegistro = new Intent(this, Registro_Activity.class);
        startActivity(iRegistro);
        finish();
    }
}