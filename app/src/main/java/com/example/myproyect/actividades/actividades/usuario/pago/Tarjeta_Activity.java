package com.example.myproyect.actividades.actividades.usuario.pago;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.actividades.usuario.BienvenidoActivity;
import com.example.myproyect.actividades.actividades.usuario.TablaReservaUser_Activity;
import com.example.myproyect.actividades.clases.Fecha;
import com.example.myproyect.actividades.clases.ListaTablasBD;
import com.example.myproyect.actividades.clases.ListarUsers_Adapter;
import com.example.myproyect.actividades.clases.MostrarMensaje;
import com.example.myproyect.actividades.clases.Reservar;
import com.example.myproyect.actividades.entidades.Pago;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.entidades.Usuario;
import com.example.myproyect.actividades.modelos.DAO_Cliente;
import com.example.myproyect.actividades.modelos.DAO_Pago;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Tarjeta_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText txtNombre, txtApellido, txtCorreo, txtNumeroTarjeta, txtCvv, txtVencimiento;
    TextView txtvMontoPago, txtvSalir;
    Button btnTest;
    double total = 0.0;

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
                    Reservar.realizar("borrar");
                    Intent iBienvenido = new Intent(this, BienvenidoActivity.class);
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

        btnTest = findViewById(R.id.btnTestRsvPay);
        btnTest.setOnClickListener(view -> {
            reservarBD();
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
        }else reservarBD();

    }
    private void reservarBD(){

        // Crear un ExecutorService con un solo hilo o un pool de hilos según tu preferencia
        ExecutorService executor = Executors.newFixedThreadPool(1);

        // Ejecutar la tarea en segundo plano
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Realizar consultas en paralelo usando Thread para mejorar el rendimiento
                String dni = Login_Activity.getUsuario().getDNI();
                String id_losa = TablaReservaUser_Activity.tabla;
                int cantHoras = TablaReservaUser_Activity.listaChkS.size();
                String estadoPago = "Aprobado";
                String medioPago="Tarjeta";
                Pago pago = new Pago(dni, id_losa,cantHoras,estadoPago,total,medioPago);
                DAO_Pago.insertarPago(pago);
                // Actualizar la UI en el hilo principal
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Configurar el adaptador y los datos en la UI

                    }

                });
            }
        });


        // Cerrar el ExecutorService cuando ya no sea necesario (por ejemplo, en onDestroy o cuando termines)
        executor.shutdown();


        //COMPRA REALIZADA

        String msg = Reservar.realizar("aprobado");
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Intent iBienvenido = new Intent(this, BienvenidoActivity.class);
        startActivity(iBienvenido);
        //limpiar listado
        TablaReservaUser_Activity.listaChkS.clear();
        finish();
    }


}
