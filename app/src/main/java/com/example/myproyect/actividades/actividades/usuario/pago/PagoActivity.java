package com.example.myproyect.actividades.actividades.usuario.pago;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.usuario.Bienvenido_Activity;
import com.example.myproyect.actividades.actividades.usuario.TablaReservaUser_Activity;
import com.example.myproyect.actividades.clases.Reservar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PagoActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtContinuar, txtvSalir,txtvTimer;
    RadioGroup rgOpcion;
    private long timeLeftInMillis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
        asignarReferencias();
        startTimer(180000);


    }
    private void startTimer(long millisInFuture) {
        new CountDownTimer(millisInFuture, 1000) { // 1000 milisegundos = 1 segundo
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                // Calculamos los minutos y segundos restantes
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String timeLeft = String.format("%02d:%02d", minutes, seconds);
                txtvTimer.setText(timeLeft); // Actualizamos el TextView con el tiempo restante
                if(timeLeftInMillis<=3000){
                    txtContinuar.setEnabled(false);
                }
            }

            @Override
            public void onFinish() {
                // Acción cuando el temporizador llega a 0
                txtvTimer.setText("00:00"); // Opcional: mostrar 00:00
                onTimerFinished(); // Llamada a la función que deseas ejecutar al finalizar
            }
        }.start();
    }
    private void onTimerFinished() {
        // Aquí realizas la acción que quieras al terminar el tiempo (por ejemplo, mostrar un Toast)
        Toast.makeText(this, "¡Tiempo agotado!", Toast.LENGTH_SHORT).show();
        // Puedes agregar más acciones aquí
        cancelar();
    }

    private void asignarReferencias(){
        txtvTimer = findViewById(R.id.txtv_timer_Pago);
        rgOpcion = findViewById(R.id.rgPay);
        txtContinuar = findViewById(R.id.txtvContinuarPay);
        txtContinuar.setOnClickListener(view -> {
            continuar();
        });
        txtvSalir = findViewById(R.id.txtvSalirPay);
        txtvSalir.setOnClickListener(view -> {
            txtvSalir.setEnabled(false);
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
        iTarjeta.putExtra("TIME_LEFT", timeLeftInMillis);
        startActivity(iTarjeta);
        finish();
    }

    private void ingresarYape() {
        Intent retorno = getIntent();
        Double cantidadPagar = retorno.getDoubleExtra("MontoPagar",0.0);

        Intent iYape = new Intent(this, Yape_Activity.class);
        iYape.putExtra("MontoPagar", cantidadPagar);
        iYape.putExtra("TIME_LEFT", timeLeftInMillis);
        startActivity(iYape);
        finish();
    }


    private void cancelar(){
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
    }
    private void regresarMenu() {
        //CANCELAR COMPRA

        // Mostrar un diálogo de confirmación
        new AlertDialog.Builder(this)
                .setTitle("Cancelar compra")
                .setMessage("¿Estás seguro de que quieres salir?")
                .setPositiveButton("Sí", (dialog, which) -> {

                    cancelar();
                })
                .setNegativeButton("No", null)
                .show();

       //onBackPressed();
    }

    private void ingresarYapeoTarjeta() {
        //validar metodo de pago
        System.out.println("acá se validara el metodo de pago");
        //procesar
        System.out.println("acá se procesara el metodo de pago");
        //mostrar resultados
        Toast.makeText(getApplicationContext(),"Metodo de pago Registrado", Toast.LENGTH_SHORT).show();

        Intent iBienvenido = new Intent(this, Bienvenido_Activity.class);
        String txtNombre = null;
        iBienvenido.putExtra("txtNombre", txtNombre);
        startActivity(iBienvenido);

    }

}