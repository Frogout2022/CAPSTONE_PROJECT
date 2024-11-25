package com.example.myproyect.actividades.actividades.usuario.pago;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.usuario.Bienvenido_Activity;
import com.example.myproyect.actividades.actividades.usuario.TablaReservaUser_Activity;
import com.example.myproyect.actividades.clases.Fecha;
import com.example.myproyect.actividades.clases.MostrarMensaje;
import com.example.myproyect.actividades.clases.Reservar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Tarjeta_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText txtNombre, txtApellido, txtCorreo, txtNumeroTarjeta, txtCvv, txtVencimiento;
    TextView txtvMontoPago, txtvSalir;
    Button btnTest;
    double total = 0.0;
    Context context= null;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta);

        asignarReferencias();
        Intent retorno = getIntent();
        total = retorno.getDoubleExtra("MontoPagar", 0.0);
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
                if (!hasFocus) {
                    String venci = txtVencimiento.getText().toString().trim();

                    if (!venci.isEmpty()) {
                        // Validar formato MM/AA
                        if (venci.matches("\\d{4}")) {
                            // Dar formato MM/AA
                            String venciFormat = venci.substring(0, 2) + "/" + venci.substring(2);

                            // Validar fecha
                            if (Fecha.validarVenci(venciFormat)) {
                                txtVencimiento.setText(venciFormat);
                                // txtCvv.requestFocus(); // Descomentar si necesitas mover el foco
                            } else {
                                MostrarMensaje.mensaje("Fecha de vencimiento no valida",getApplicationContext());
                                txtVencimiento.setText("");
                            }
                        } else {
                            MostrarMensaje.mensaje("Formato de fecha incorrecto. Use MM/AA",getApplicationContext());
                            txtVencimiento.setText("");
                        }
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
        //CANCELAR COMPRA
        // Mostrar un diálogo de confirmación
        new AlertDialog.Builder(this)
                .setTitle("CANCELAR COMPRA")
                .setMessage("¿Estás seguro de que quieres salir?")
                .setPositiveButton("Sí", (dialog, which) -> {

                    Toast.makeText(this, "Compra cancelada", Toast.LENGTH_SHORT).show();
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        Reservar.realizar("borrar");
                    });
                    Intent iBienvenido = new Intent(this, Bienvenido_Activity.class);
                    startActivity(iBienvenido);
                    //limpiar selecciones previas
                    TablaReservaUser_Activity.listaChkS.clear();
                    TablaReservaUser_Activity.preReserva = false;
                    // Cerrar la actividad manualmente
                    finish();
                })
                .setNegativeButton("No", null)
                .show();

       // super.onBackPressed();
    }

    private void asignarReferencias(){

        progressBar = findViewById(R.id.pb_tarjeta_pay);
        progressBar.setVisibility(View.GONE);

        txtNombre = findViewById(R.id.txtNomPAY);
        txtCorreo = findViewById(R.id.txtEmailPAY);
        txtApellido = findViewById(R.id.txtApellidoPAY);
        txtNumeroTarjeta = findViewById(R.id.txtNumTarjetaPAY);
        txtCvv = findViewById(R.id.txtCvvPAY);
        txtVencimiento = findViewById(R.id.txtVencimientoPAY);

        txtvMontoPago = findViewById(R.id.txtvPagarPAY);

        txtvMontoPago.setOnClickListener(view -> {
            //boton pagar
            txtvMontoPago.setEnabled(false);
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
            txtvMontoPago.setEnabled(true);
        }else reservarBD();

    }
    private void reservarBD(){
        Executor executor = Executors.newSingleThreadExecutor();
        progressBar.setVisibility(View.VISIBLE);
        executor.execute(() -> {
            //Hilo Secundario (No usar Toast, ni ocultar mostrar elementos)
            String msg = Reservar.realizar("aprobado", "tarjeta");
            System.out.println("msg: " +msg);
            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                TablaReservaUser_Activity.listaChkS.clear(); //limpiar listado
                Intent iBienvenido = new Intent(getApplicationContext(), Bienvenido_Activity.class);
                startActivity(iBienvenido);

                finish();
            });
        });
    }

}
