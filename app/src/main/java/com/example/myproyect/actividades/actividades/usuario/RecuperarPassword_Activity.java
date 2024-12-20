package com.example.myproyect.actividades.actividades.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.clases.MostrarMensaje;
import com.example.myproyect.actividades.clases.PasswordEncryptor;
import com.example.myproyect.actividades.modelos.DAO_Cliente;

public class RecuperarPassword_Activity extends AppCompatActivity {
    Button btnSalir, btnConfirmar;
    EditText pass1, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);

        asignarReferencias();
        focosCampos();
    }
    void asignarReferencias(){
        btnConfirmar = findViewById(R.id.btnConfirmar_RecupPass);
        btnConfirmar.setOnClickListener(view -> {
            btnConfirmar.setEnabled(false);
            validarConfirmacion();
        });
        btnSalir = findViewById(R.id.btnSalir_RecupPass);
        btnSalir.setOnClickListener(view -> {
            btnSalir.setEnabled(false);
            boolean b = getIntent().getBooleanExtra("login",false);
            if(b ){
                Intent intent = new Intent(this, Login_Activity.class);
                startActivity(intent);
                this.finish();
            }else{
                Intent intent = new Intent(this, ActualizarDatosUSER_Activity.class);
                startActivity(intent);
                this.finish();
            }
        });
        pass1 = findViewById(R.id.txtPass1_RecupPass);
        pass2 = findViewById(R.id.txtPass2_RecupPass);

    }
    void focosCampos(){
        pass1.setOnFocusChangeListener((view, b) -> {
            if(!b){
                //no tiene foco
                String contra1 = pass1.getText().toString();
                if(!contra1.isEmpty()){
                    if(contra1.length()<5){
                        Toast.makeText(this, "Contraseña demasiado corta", Toast.LENGTH_SHORT).show();
                        pass1.setText("");
                    }
                }

            }
        });

        pass2.setOnFocusChangeListener((view, b) -> {
            if(!b){
                //no tiene foco
                String contra2 = pass2.getText().toString();
                if(!contra2.isEmpty()){
                    if(contra2.length()<5){
                        Toast.makeText(this, "Contraseña demasiado corta", Toast.LENGTH_SHORT).show();
                        pass2.setText("");
                    }else{
                        String clave1 = pass2.getText().toString();
                        if(!clave1.isEmpty() && !clave1.equals(contra2)){
                            Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    void validarConfirmacion(){
        String p1,p2;
        p1 = pass1.getText().toString();
        p2 = pass2.getText().toString();

        if(!p1.equals(p2)){
            MostrarMensaje.mensaje("Contraseñas no coinciden", this);
        }else{
            if(!p1.isEmpty() && !p2.isEmpty()){
                //pasa
                String dni = getIntent().getStringExtra("dni");
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //encriptar la contraeña antes de enviar
                String msg = DAO_Cliente.editarPass(dni, p1);
                boolean b = getIntent().getBooleanExtra("login",false);
                if(b ){
                    MostrarMensaje.mensajeToast(msg,this, Login_Activity.class);
                }else{
                    MostrarMensaje.mensajeToast(msg,this, ActualizarDatosUSER_Activity.class);
                }
            }else{
                Toast.makeText(this,"Rellene los campos", Toast.LENGTH_SHORT).show();
                btnSalir.setEnabled(true);
            }

        }
    }
}