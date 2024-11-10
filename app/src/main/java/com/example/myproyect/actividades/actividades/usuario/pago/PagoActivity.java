package com.example.myproyect.actividades.actividades.usuario.pago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.usuario.BienvenidoActivity;
import com.example.myproyect.actividades.actividades.usuario.TablaReservaUser_Activity;
import com.example.myproyect.actividades.actividades.usuario.pago.Tarjeta_Activity;
import com.example.myproyect.actividades.actividades.usuario.pago.Yape_Activity;

public class PagoActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtContinuar, txtvSalir;
    RadioGroup rgOpcion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
        asignarReferencias();


    }
    private void asignarReferencias(){
        rgOpcion = findViewById(R.id.rgPay);
        txtContinuar = findViewById(R.id.txtvContinuarPay);
        txtContinuar.setOnClickListener(view -> {
            continuar();
        });
        txtvSalir = findViewById(R.id.txtvSalirPay);
        txtvSalir.setOnClickListener(view -> {
            regresarMenu();
        });
    }
    private void continuar(){
        rgOpcion.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.rbTarjetaPay:

                    break;
                case R.id.rbYape:

                    break;

            }
        });
        int selectedId = rgOpcion.getCheckedRadioButtonId();
        if(selectedId !=-1){
            RadioButton selectedRadioButton = findViewById(selectedId);
            int id = selectedRadioButton.getId();
            if(id == R.id.rbTarjetaPay) {
                ingresarTarjeta();
            }
            if(id == R.id.rbYape){
                ingresarYape();
            }

        }else{
            Toast.makeText(this, "Ninguna opción seleccionada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {

    }

    private void ingresarTarjeta() {
        Intent retorno = getIntent();
        Double cantidadPagar = retorno.getDoubleExtra("MontoPagar",0.0);

        Intent iTarjeta= new Intent(this, Tarjeta_Activity.class);
        iTarjeta.putExtra("MontoPagar", cantidadPagar);
        startActivity(iTarjeta);
        finish();
    }

    private void ingresarYape() {
        Intent iYape = new Intent(this, Yape_Activity.class);
        startActivity(iYape);
        finish();
    }



    private void regresarMenu() {
/*
        Intent iBienvenido = new Intent(this, TablaReservaUser_Activity.class);
        startActivity(iBienvenido);
        finish();

 */


       onBackPressed();
    }

    private void ingresarYapeoTarjeta() {
        //validar metodo de pago
        System.out.println("acá se validara el metodo de pago");
        //procesar
        System.out.println("acá se procesara el metodo de pago");
        //mostrar resultados
        Toast.makeText(getApplicationContext(),"Metodo de pago Registrado", Toast.LENGTH_SHORT).show();

        Intent iBienvenido = new Intent(this, BienvenidoActivity.class);
        String txtNombre = null;
        iBienvenido.putExtra("txtNombre", txtNombre);
        startActivity(iBienvenido);

    }

}