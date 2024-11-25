package com.example.myproyect.actividades.actividades.usuario.pago;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.usuario.Bienvenido_Activity;
import com.example.myproyect.actividades.actividades.usuario.TablaReservaUser_Activity;
import com.example.myproyect.actividades.clases.Reservar;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Yape_Activity extends AppCompatActivity implements View.OnClickListener {
    TextView txtvSalir, txtvPagar,txtvTimer;
    EditText txtTelefono, txtCodigo;
    ProgressBar progressBar;
    double total;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yape);

        asignarReferencias();
        Intent retorno = getIntent();
        long timeLeftInMillis = retorno.getLongExtra("TIME_LEFT", 0);
        startTimer(timeLeftInMillis);


        total = retorno.getDoubleExtra("MontoPagar", 0.0);
        txtvPagar.setText("Pagar S/ "+total);

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
                    txtvPagar.setEnabled(false);
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
        txtvTimer = findViewById(R.id.txtv_timer_yape);

        progressBar = findViewById(R.id.pb_load_yape);
        progressBar.setVisibility(View.GONE);
        txtvSalir = findViewById(R.id.txtvSalirYape);
        txtvPagar = findViewById(R.id.txtvPayYape);
        txtvSalir.setOnClickListener(view -> {
            txtvSalir.setEnabled(false);
            salir();
        });
        txtvPagar.setOnClickListener(view -> {
            txtvPagar.setEnabled(false);
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

    private void cancelar(){
        // Cerrar la actividad manualmente
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Reservar.realizar("borrar");
            runOnUiThread(() -> {
                //limpiar selecciones previas
                TablaReservaUser_Activity.listaChkS.clear();
                TablaReservaUser_Activity.preReserva = false;
                Toast.makeText(this, "Compra cancelada", Toast.LENGTH_SHORT).show();
                Intent iBienvenido = new Intent(this, Bienvenido_Activity.class);
                startActivity(iBienvenido);
                finish();
            });
        });
    }
    private void salir(){
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
            txtvPagar.setEnabled(true);
        }else{
            if(validarCodigo() && validarTelefono()){
                txtvPagar.setEnabled(false);
                insertarReserva();
            }else{
                Toast.makeText(this, "Datos ingresados incorrectos.", Toast.LENGTH_SHORT).show();
                txtvPagar.setEnabled(true);
            }
        }
    }
    private void insertarReserva(){
        progressBar.setVisibility(View.VISIBLE);
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            //PAGO ACEPTADO
            final String msg = Reservar.realizar("aprobado", "Yape"); //<--
            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                TablaReservaUser_Activity.listaChkS.clear();

                Intent iBienvenido = new Intent(this, Bienvenido_Activity.class);
                startActivity(iBienvenido);
                finish();
            });

        });

    }
}