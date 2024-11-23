package com.example.myproyect.actividades.actividades.usuario.pago;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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
import com.example.myproyect.actividades.clases.Reservar;
import com.example.myproyect.actividades.entidades.Pago;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.modelos.DAO_Pago;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Yape_Activity extends AppCompatActivity implements View.OnClickListener {
    TextView txtvSalir, txtvPagar;
    EditText txtTelefono, txtCodigo;
    double total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yape);

        asignarReferencias();

        Intent retorno = getIntent();
        total = retorno.getDoubleExtra("MontoPagar", 0.0);
        txtvPagar.setText("Pagar S/ "+total);

    }

    private void asignarReferencias(){
        txtvSalir = findViewById(R.id.txtvSalirYape);
        txtvPagar = findViewById(R.id.txtvPayYape);
        txtvSalir.setOnClickListener(view -> {
            salir();
        });
        txtvPagar.setOnClickListener(view -> {
            confirmaryapeo();
        });
        txtTelefono = findViewById(R.id.txtTelyape);
        txtCodigo = findViewById(R.id.txtCodYape);

        focos();
    }

    private void  focos(){
        txtTelefono.setOnFocusChangeListener((view, b) -> {
            if(!b) validarTelefono();
        });

        txtCodigo.setOnFocusChangeListener((view, b) -> {
            if(!b) validarCodigo();
        });

    }
    private boolean validarCodigo(){
        boolean passCod = false;
        String cod = txtCodigo.getText().toString();
        if(!cod.isEmpty()) {
            passCod = cod.matches("\\d{6}"); //true
            if(!passCod) {
                Toast.makeText(this, "Código ingresado no valido", Toast.LENGTH_SHORT).show();
                txtCodigo.setText("");
            }
        }else passCod = false;
        return passCod;
    }
    private boolean validarTelefono(){
        boolean passTele = false;
        String tele = txtTelefono.getText().toString();
        if (!tele.isEmpty()) {
            passTele = tele.matches("\\d{9}");
            if(tele.matches("\\d{3} \\d{3} \\d{3}")) return true;
            if (!passTele) {
                Toast.makeText(this, "Número de telefono no valido", Toast.LENGTH_SHORT).show();
                txtTelefono.setText("");
            }else{
                StringBuilder resultado = new StringBuilder();
                int longitud = tele.length();

                for (int i = 0; i < longitud; i++) {
                    resultado.append(tele.charAt(i)); // Agregar el carácter actual a la nueva cadena
                    // Agregar un espacio cada 3 caracteres, excepto después del último carácter
                    if ((i + 1) % 3 == 0 && (i + 1) < longitud) resultado.append(" ");
                }
                txtTelefono.setText(resultado.toString());
            }
        }else passTele = false;

        return passTele;
    }

    @Override
    public void onClick(View view) {

    }

    private void salir(){
        //CANCELAR COMPRA
        // Mostrar un diálogo de confirmación
        new AlertDialog.Builder(this)
                .setTitle("Cancelar compra")
                .setMessage("¿Estás seguro de que quieres salir?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Cerrar la actividad manualmente
                    Toast.makeText(this, "Compra cancelada", Toast.LENGTH_SHORT).show();
                    Reservar.realizar("borrar");
                    Intent iBienvenido = new Intent(this, BienvenidoActivity.class);
                    startActivity(iBienvenido);
                    //limpiar selecciones previas
                    TablaReservaUser_Activity.listaChkS.clear();
                    TablaReservaUser_Activity.preReserva = false;
                    finish();
                })
                .setNegativeButton("No", null)
                .show();

    }
    private void regresarpago() {
        Toast.makeText(getApplicationContext(),"El pago se verificara presencialmente", Toast.LENGTH_SHORT).show();
        Intent iPago = new Intent(this, PagoActivity.class);
        startActivity(iPago);
        finish();
    }

    private void confirmaryapeo() {
        if(txtCodigo.getText().toString().isEmpty() || txtTelefono.getText().toString().isEmpty()){
            Toast.makeText(this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            if(validarCodigo() && validarTelefono()){
                registrarPago();
                insertarReserva();
                Intent iBienvenido = new Intent(this, BienvenidoActivity.class);
                startActivity(iBienvenido);
                finish();
            }//else Toast.makeText(this, "Datos ingresados incorrectos.", Toast.LENGTH_SHORT).show();

        }
    }
    private void insertarReserva(){
        //PAGO ACEPTADO
        String msg = Reservar.realizar("aprobado"); //<--
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        TablaReservaUser_Activity.listaChkS.clear();
    }
    private void registrarPago(){
        String dniCliPago= Login_Activity.getUsuario().getDNI();
        String nombre_losa = TablaReservaUser_Activity.nombre_losa;
        int cantidad_horas = TablaReservaUser_Activity.listaChkS.size();
        String estadoPago = "Aprobado";
        String medioPago= "Yape";

        Pago pago = new Pago(dniCliPago,nombre_losa,cantidad_horas,estadoPago,total,medioPago);
        String msg = DAO_Pago.insertarPago(pago);
        System.out.print("msg: "+msg);

    }
}