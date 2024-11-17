package com.example.myproyect.actividades.actividades.usuario;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.actividades.usuario.TerminosActivity;
import com.example.myproyect.actividades.actividades.usuario.pago.Tarjeta_Activity;
import com.example.myproyect.actividades.clases.MostrarMensaje;
import com.example.myproyect.actividades.clases.PasswordEncryptor;
import com.example.myproyect.actividades.entidades.Usuario;
import com.example.myproyect.actividades.modelos.DAO_Cliente;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtNombre, txtApellido, txtCorreo, txtClave,txtClave2, txtFechaNac, txtDni, txtCel;
    Button btnContinuar, btnRegresar;
    CheckBox chkTerminos;
    TextView lblIniciar, lblTerminos;

    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        asignarReferencias();
        focosDeCampos();
        
    }

    private void focosDeCampos(){
        txtDni.setOnFocusChangeListener((view, b) -> {
            if(!b){
                //no tiene foco
                boolean passDNI = false;
                String dni = txtDni.getText().toString();
                if(!dni.isEmpty()){
                    passDNI = dni.matches("\\d{8}");
                    if(!passDNI){
                        Toast.makeText(this, "DNI no valido", Toast.LENGTH_SHORT).show();
                        txtDni.setText("");
                    }
                }
            }
        });

        txtNombre.setOnFocusChangeListener((view, b) -> {
            if(!b){
                //no tiene foco
                boolean passNombre = false;
                String nombre = txtNombre.getText().toString();
                if(!nombre.isEmpty()){
                    passNombre = nombre.matches("[a-zA-Z]+");

                    if(nombre.length()<=3) passNombre=false;
                    else passNombre=true;

                    if(!passNombre){
                        Toast.makeText(this, "Nombre ingresado no valido", Toast.LENGTH_SHORT).show();
                        txtNombre.setText("");
                    }
                }
            }
        });

        txtApellido.setOnFocusChangeListener((view, b) -> {
            if(!b){
                //no tiene foco
                boolean passApellido = false;
                String apellido = txtApellido.getText().toString();
                if(!apellido.isEmpty()){
                    passApellido = apellido.matches("[a-zA-Z]+");

                    if(apellido.length()<=5) passApellido=false;
                    else passApellido=true;

                    if(!passApellido){
                        Toast.makeText(this, "Nombre ingresado no valido", Toast.LENGTH_SHORT).show();
                        txtApellido.setText("");
                    }
                }
            }
        });

        txtCel.setOnFocusChangeListener((view, b) -> {
            if(!b){
                //no tiene foco
                boolean passCel = false;
                String cel = txtCel.getText().toString();
                if(!cel.isEmpty()){
                    passCel = cel.matches("\\d{9}");
                    if(!passCel){
                        Toast.makeText(this, "Celular ingresado no valido", Toast.LENGTH_SHORT).show();
                        txtCel.setText("");
                    }
                }
            }
        });

        txtCorreo.setOnFocusChangeListener((view, b) -> {
            if(!b){
                //no tiene foco
                boolean passEmail = false;
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
                        Toast.makeText(this, "Correo ingresado no valido", Toast.LENGTH_SHORT).show();
                        txtCorreo.setText("");
                    }
                }
            }
        });

        txtClave.setOnFocusChangeListener((view, b) -> {
            if(!b){
                //no tiene foco
                String contra1 = txtClave.getText().toString();
                if(!contra1.isEmpty()){
                    if(contra1.length()<5){
                        Toast.makeText(this, "Contraseña demasiado corta", Toast.LENGTH_SHORT).show();
                        txtClave.setText("");
                    }
                }
            }
        });

        txtClave2.setOnFocusChangeListener((view, b) -> {
            if(!b){
                //no tiene foco
                String contra2 = txtClave2.getText().toString();
                if(!contra2.isEmpty()){
                    if(contra2.length()<5){
                        Toast.makeText(this, "Contraseña demasiado corta", Toast.LENGTH_SHORT).show();
                        txtClave2.setText("");
                    }else{
                        String clave1 = txtClave.getText().toString();
                        if(!clave1.isEmpty() && !clave1.equals(contra2)){
                            Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }
    private void asignarReferencias(){
        //asociacion de la parte
        //logica        con la        grafica
        txtDni = findViewById(R.id.regTxtDni);
        txtNombre = findViewById(R.id.regTxtNombre);
        txtApellido = findViewById(R.id.regTxtApellido);
        txtCorreo = findViewById(R.id.regTxtCorreo);
        txtClave = findViewById(R.id.regTxtClave);
        txtClave2 = findViewById(R.id.regTxtClave2);
        txtCel = findViewById(R.id.regTxtCel);

        //link
        lblIniciar = findViewById(R.id.regLblIniciar);
        lblTerminos = findViewById(R.id.regLblTerminos);
        //chek
        chkTerminos = findViewById(R.id.regChkTerminos);
        //button
        btnRegresar = findViewById(R.id.regBtnRegresar);
        btnContinuar = findViewById(R.id.regBtnContinuar);
        //asociar el evento on click a los controles
        chkTerminos.setOnClickListener(this);
        btnContinuar.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);
        lblIniciar.setOnClickListener(this);
        lblTerminos.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regChkTerminos:
                validarTerminos();
                break;
            case R.id.regLblTerminos:
                cargarTerminos();
                break;
            case R.id.regBtnContinuar:
                registrar();
                break;
            case R.id.regBtnRegresar:
                regresar();
                break;
            case R.id.regLblIniciar:
                cargarActividadIniciar();
                break;
        }
    }

    private void cargarTerminos() {
        Intent iTerminos = new Intent(this, TerminosActivity.class);
        startActivity(iTerminos);
    }

    private void validarTerminos() {
        boolean activo = chkTerminos.isChecked() ? true : false;
        btnContinuar.setEnabled(activo);
    }

    private void capturarDatos(){
        //guardar datos ingresados por el usuario
        String dni, correo, clave, nombre, apellido,   celular;
        dni = txtDni.getText().toString();
        nombre = txtNombre.getText().toString();
        apellido = txtApellido.getText().toString();
        correo = txtCorreo.getText().toString();
        clave =  txtClave.getText().toString();
        celular = txtCel.getText().toString();

        user = new Usuario(dni, nombre, apellido, correo, clave, celular);
    }

    //CONTINUAR
    private void registrar() {
        capturarDatos();
        if(user.getDNI().isEmpty()|| user.getCelular().isEmpty()|| user.getCorreo().isEmpty()
        ||user.getNombre().isEmpty() || user.getApellido().isEmpty() || txtClave2.getText().toString().isEmpty()
        ){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            if(!user.getClave().equals(txtClave2.getText().toString())){
                Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }else{
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                if(DAO_Cliente.ConsultarDni(user.getDNI() ) ){
                    //DNI INGRESADO YA  SE ENCUENTRA REGISTRADO EN LA BD
                    Toast.makeText(this, "DNI ya registrado", Toast.LENGTH_SHORT).show();
                }else{
                    //DNI INGRESADO NO ESTÁ REGISTRADO
                    if(DAO_Cliente.ConsultarCorreo(user.getCorreo())){
                        Toast.makeText(this, "CORREO ya registrado", Toast.LENGTH_SHORT).show();
                    }else{
                        //encriptar clave
                        String hashedPassword = PasswordEncryptor.encryptPassword(user.getClave());
                        user.setClave(hashedPassword);

                        String msg = DAO_Cliente.insertarCLI(user);
                        MostrarMensaje.mensaje(msg, this, Login_Activity.class);
                        //USUARIO REGISTRADO CORRECTAMENTE
                    }
                }
            }
        }
    }
    private void regresar() {
        Intent iIniciarSesion = new Intent(this, Login_Activity.class);
        startActivity(iIniciarSesion);
        finish();
    }

    private void cargarActividadIniciar() {
        Intent iIniciarSesion = new Intent(this, Login_Activity.class);
        startActivity(iIniciarSesion);
        finish();
    }

}