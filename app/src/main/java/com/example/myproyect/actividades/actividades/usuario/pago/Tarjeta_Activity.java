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
import com.example.myproyect.actividades.actividades.usuario.TablaReservaUser_Activity;
import com.example.myproyect.actividades.clases.MostrarMensaje;


public class Tarjeta_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText txtNombre, txtApellido, txtCorreo, txtNumeroTarjeta, txtCvv, txtVencimiento;
    TextView txtvMontoPago, txtvSalir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);

        asignarReferencias();
        Intent retorno = getIntent();
        double total = retorno.getDoubleExtra("MontoPagar", 0.0);
        txtvMontoPago.setText("Pagar S/ "+total);

        focosDeCampos();


    }

    @Override
    public void onClick(View view) {

    }
    private void focosDeCampos(){


        txtNumeroTarjeta.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    //Toast.makeText(Tarjeta_Activity.this, "Enfocado", Toast.LENGTH_SHORT).show();
                }else{

                    boolean numPASS = false;
                    String num = txtNumeroTarjeta.getText().toString();

                    if(num.isEmpty()){
                        //txtVencimiento.requestFocus();
                    }else{
                        numPASS = num.matches("\\d{16,}");

                        if(!numPASS){
                            Toast.makeText(Tarjeta_Activity.this, "Datos de Tarjeta no validos", Toast.LENGTH_SHORT).show();
                            txtNumeroTarjeta.setText("");
                            //txtVencimiento.requestFocus();
                        }else{
                            StringBuilder resultado = new StringBuilder();
                            int longitud = num.length();

                            for (int i = 0; i < longitud; i++) {
                                resultado.append(num.charAt(i)); // Agregar el carácter actual a la nueva cadena

                                // Agregar un espacio cada 4 caracteres, excepto después del último carácter
                                if ((i + 1) % 4 == 0 && (i + 1) < longitud) {
                                    resultado.append(" ");
                                }
                            }
                            txtNumeroTarjeta.setText(resultado.toString());
                        }
                    }
                    //Toast.makeText(Tarjeta_Activity.this, "SinFoco", Toast.LENGTH_SHORT).show();
                }
            }

        });

        txtVencimiento.setOnFocusChangeListener(new View.OnFocusChangeListener(){


            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else{//pierde el foco
                    boolean venciPASS = false;
                    String venci = txtVencimiento.getText().toString();
                    if(!venci.isEmpty()){
                        venciPASS = venci.matches("\\d{4}");
                        if(!venciPASS){
                            Toast.makeText(Tarjeta_Activity.this, "Fecha de vencimiento no valida", Toast.LENGTH_SHORT).show();
                            txtVencimiento.setText("");
                        }else {
                            txtVencimiento.setText(venci.substring(0,2)+"/"+venci.substring(2));
                            //txtCvv.requestFocus();
                        }

                    }else {
                        //txtCvv.requestFocus();
                    }

                }
            }
        });


        txtCvv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String cvv = txtCvv.getText().toString();
                    if(!cvv.isEmpty()){
                        boolean cvvPASS = false;
                        cvvPASS = cvv.matches("\\d{3}");
                        if(!cvvPASS){
                            Toast.makeText(Tarjeta_Activity.this, "CVV no valido", Toast.LENGTH_SHORT).show();
                            txtCvv.setText("");
                        }
                    }

                }else{
                    //Tiene foco
                }
            }
        });

        txtNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String nom = txtNombre.getText().toString();
                    if(!nom.isEmpty()){
                        boolean nomPASS = false;
                        nomPASS = nom.matches("[a-zA-Z]+");
                        if(!nomPASS){
                            Toast.makeText(Tarjeta_Activity.this, "Nombre invalido", Toast.LENGTH_SHORT).show();
                            txtNombre.setText("");
                        }
                    }

                }else{
                    //tiene foco
                }
            }
        });

        txtApellido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){

                    String ape = txtApellido.getText().toString();
                    if(!ape.isEmpty()){
                        boolean apePASS= false;
                        apePASS = ape.matches("[a-zA-Z]+");
                        if(!apePASS){
                            Toast.makeText(Tarjeta_Activity.this, "Apellido invalido", Toast.LENGTH_SHORT).show();
                            txtApellido.setText("");
                        }
                    }

                }else{
                    //tiene foco
                }
            }
        });

        txtCorreo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    String correo = txtCorreo.getText().toString();
                    if(!correo.isEmpty()){
                        boolean arroba = correo.contains("@");
                        boolean punto = correo.contains(".");
                        boolean largo = false;
                        if(correo.length()<7)  largo = false;
                        else largo = true;

                        boolean emailPASS = false;
                        if(arroba && punto && largo) emailPASS = true;
                        else emailPASS = false;

                        if(!emailPASS){
                            Toast.makeText(Tarjeta_Activity.this, "Correo no valido", Toast.LENGTH_SHORT).show();
                            txtCorreo.setText("");
                        }

                    }

                }else{
                    //Tiene el foco
                }
            }
        });



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
        txtVencimiento = findViewById(R.id.txtVencimientoPAY);

        txtvMontoPago = findViewById(R.id.txtvPagarPAY);

        txtvMontoPago.setOnClickListener(view -> {
            validarPago();

        });
        txtvSalir = findViewById(R.id.btnSalirPAY);
        txtvSalir.setOnClickListener(view -> {
            regresar();
        });

    }
    private void validarPago(){
        String num, fecha, cvv, nom, ape,email;
        num = txtNumeroTarjeta.getText().toString();
        fecha = txtVencimiento.getText().toString();
        nom = txtNombre.getText().toString();
        ape = txtApellido.getText().toString();
        email = txtCorreo.getText().toString();

        if(num.isEmpty() || fecha.isEmpty() || nom.isEmpty() || ape.isEmpty() || email.isEmpty()){
            Toast.makeText(Tarjeta_Activity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            reservarBD();
        }



    }
    private void reservarBD(){
        {


        }
    }




}
