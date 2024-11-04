package com.example.myproyect.actividades.actividades.usuario.pago;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.usuario.BienvenidoActivity;
import com.example.myproyect.actividades.clases.MostrarMensaje;


public class Tarjeta_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText txtNombre, txtApellido, txtCorreo, txtNumeroTarjeta, txtCvv, txtVencimiento;
    TextView txtvMontoPago;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);

        asignarReferencias();
        Intent retorno = getIntent();
        double total = retorno.getDoubleExtra("MontoPagar", 0.0);
        txtvMontoPago.setText("Pagar S/ "+total);


    }

    @Override
    public void onClick(View view) {

    }

    private void regresar(){
        super.onBackPressed();
    }
    private void asignarReferencias(){
        txtNombre = findViewById(R.id.txtNomPAY);
        txtCorreo = findViewById(R.id.txtEmailPAY);
        txtApellido = findViewById(R.id.txtApellidoPAY);
        txtNumeroTarjeta = findViewById(R.id.txtNumTarjetaPAY);
        txtCvv = findViewById(R.id.txtCvvPAY);
        txtVencimiento = findViewById(R.id.txtCvvPAY);

        txtvMontoPago = findViewById(R.id.txtvPagarPAY);

        txtvMontoPago.setOnClickListener(view -> {
            validarPago();

        });

    }
    private void validarPago(){

        if(validarDatos()){
            MostrarMensaje.mensaje("Reserva realizada",this, BienvenidoActivity.class);
        }else{
            MostrarMensaje.mensaje("DATOS INCORRECTOS",this);
        }

    }

    boolean validarDatos(){

        boolean emailPASS = false;
        boolean numPASS = false;
        boolean cvvPASS = false;
        boolean nomPASS = false;
        boolean apePASS = false;
        boolean venciPASS = false;


        String correo = txtCorreo.getText().toString();
        boolean arroba = correo.contains("@");
        boolean punto = correo.contains(".");
        if(arroba && punto) {
            emailPASS = true;
        }

        String num = txtNumeroTarjeta.getText().toString();
        numPASS = num.matches("\\d{16,}");

        String cvv = txtCvv.getText().toString();
        cvvPASS = cvv.matches("\\d{3,}");

        String nom = txtNombre.getText().toString();
        nomPASS = nom.matches("[a-zA-Z]+");

        String ape = txtApellido.getText().toString();
        apePASS = ape.matches("[a-zA-Z]+");

        String vencimiento = txtVencimiento.getText().toString();
        venciPASS = vencimiento.matches("\\d{2}/\\d{2}]");
        venciPASS = true;

        List<Boolean> validacion = new ArrayList<>();

        validacion.add(emailPASS);
        validacion.add(numPASS);
        validacion.add(cvvPASS);
        validacion.add(nomPASS);
        validacion.add(apePASS);
        validacion.add(venciPASS);


        for(Boolean valor: validacion){
            if(!valor) return false;
        }
        return true;
    }




}
