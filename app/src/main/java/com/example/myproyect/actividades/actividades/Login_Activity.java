package com.example.myproyect.actividades.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.BuildConfig;
import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.admin.MenuAdmin_Activity;
import com.example.myproyect.actividades.actividades.usuario.Bienvenido_Activity;
import com.example.myproyect.actividades.actividades.usuario.RecuperarPassword_Activity;
import com.example.myproyect.actividades.actividades.usuario.Registro_Activity;
import com.example.myproyect.actividades.clases.MostrarMensaje;
import com.example.myproyect.actividades.entidades.App;
import com.example.myproyect.actividades.entidades.Usuario;
import com.example.myproyect.actividades.modelos.DAO_Administrador;
import com.example.myproyect.actividades.modelos.DAO_Cliente;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Login_Activity extends AppCompatActivity {


    EditText txtCorreo, txtClave, txtHola;
    CheckBox checkRecordar;
    TextView lblRegistrate, lblRecuperarPass, lblVersion;
    Button btnIngresar, btnSalir;

    MostrarMensaje mostrarMensaje = new MostrarMensaje();
    Context context = this;

    public static Usuario usuario = new Usuario();
    public static boolean isAdmin = false;
    //public static Usuario usuario = new Usuario("70829460","Milhos", "Sihuay", "m@g.com", "123", "997653086");
    private String correo=null, clave=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        asignarReferencias();
        txtClave.setText(null);
        txtCorreo.setText(null);
        txtCorreo.setText(null);
        lblVersion.setText("v"+BuildConfig.VERSION_NAME);
        focos();
        App.loadtDatos(this);
        validarRS();

    }
    private void focos(){
        txtClave.setOnFocusChangeListener((view, b) -> {
            if(b){
                //tiene foco
                if(!txtClave.getText().toString().isEmpty()){
                    txtClave.setText(null);
                }
            }
        });
    }
    private void asignarReferencias(){
        lblVersion = findViewById(R.id.txtVersionLogin);
        txtCorreo = findViewById(R.id.logTxtCorreo);
        txtClave = findViewById(R.id.logTxtClave);
        checkRecordar = findViewById(R.id.logChkRecordar);
        lblRegistrate = findViewById(R.id.logLblRegistrar);
        btnIngresar = findViewById(R.id.btn_ingresar_Login);
        btnSalir = findViewById(R.id.logBtnSalir);

        lblRegistrate.setOnClickListener(view -> {
            Intent intent = new Intent(this, Registro_Activity.class);
            startActivity(intent);
        });

        lblRecuperarPass = findViewById(R.id.lblRecuperarPass_Login);
        lblRecuperarPass.setText("Recuperar Contraseña");
        lblRecuperarPass.setOnClickListener(view -> {
            recuperarPass();
        });

        btnIngresar.setOnClickListener(view -> {
            validarFormulario();
        });
        btnSalir.setOnClickListener(view -> {
            finishAffinity();
            finish();
        });
    }
    void recuperarPass() {
        String correo = txtCorreo.getText().toString(); //guardar el correo ingresado

        if (correo.isEmpty()) {
            MostrarMensaje.mensaje("Ingrese su correo", this);
        } else {
            // Crear un ExecutorService para ejecutar la tarea en segundo plano
            ExecutorService executor = Executors.newSingleThreadExecutor();

            // Mostrar un ProgressBar mientras validas los datos
            //progressBar.setVisibility(View.VISIBLE);

            // Ejecutar la tarea en segundo plano
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // Validar si el correo existe (consulta a la base de datos)
                    boolean correoValido = DAO_Cliente.ConsultarCorreo(correo);

                    // Cuando termine la validación, actualizamos la UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Ocultar el ProgressBar
                            //progressBar.setVisibility(View.GONE);

                            if (correoValido) {
                                // Usuario encontrado, solicitar DNI
                                final EditText input = new EditText(context);
                                new AlertDialog.Builder(context)
                                        .setMessage("Ingrese su DNI: ")
                                        .setView(input)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                String dni = input.getText().toString();

                                                // Ahora validamos el DNI (en segundo plano)
                                                ExecutorService executorDni = Executors.newSingleThreadExecutor();
                                                executorDni.execute(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        boolean dniValido = DAO_Cliente.ConsultarDni(dni);

                                                        // Cuando termine la validación, actualizamos la UI
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                if (dniValido) {
                                                                    // DNI válido, iniciar la actividad para recuperar la contraseña
                                                                    Intent intent = new Intent(context, RecuperarPassword_Activity.class);
                                                                    intent.putExtra("dni", dni);
                                                                    intent.putExtra("login", true);
                                                                    startActivity(intent);
                                                                } else {
                                                                    Toast.makeText(context, "DNI incorrecto", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });

                                                // Cerrar el ExecutorService para DNI cuando ya no sea necesario
                                                executorDni.shutdown();
                                            }
                                        })
                                        .setNegativeButton("Cancel", (dialogInterface, i) -> {
                                            txtCorreo.setText(null);
                                            txtClave.setText(null);
                                        })
                                        .show();
                            } else {
                                // Correo no registrado
                                MostrarMensaje.mensaje("Correo no registrado", context);
                            }
                        }
                    });
                }
            });

            // Cerrar el ExecutorService cuando ya no sea necesario
            executor.shutdown();
        }
    }

    void recuperarPass3() {
        String correo = txtCorreo.getText().toString(); //guardar el correo ingresado

        if (correo.isEmpty()) {
            MostrarMensaje.mensaje("Ingrese su correo", this);
        } else {
            // Crear un ExecutorService para ejecutar la tarea en segundo plano
            ExecutorService executor = Executors.newSingleThreadExecutor();

            // Mostrar un ProgressBar mientras validas los datos
            //progressBar.setVisibility(View.VISIBLE);

            // Ejecutar la tarea en segundo plano
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // Validar si el correo existe
                    boolean correoValido = DAO_Cliente.ConsultarCorreo(correo);

                    // Cuando termine la validación, actualizamos la UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Ocultar el ProgressBar
                            //progressBar.setVisibility(View.GONE);

                            if (correoValido) {
                                // Usuario encontrado, solicitar DNI
                                final EditText input = new EditText(context);
                                new AlertDialog.Builder(context)
                                        .setMessage("Ingrese su DNI: ")
                                        .setView(input)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                String dni = input.getText().toString();
                                                // Validar el DNI
                                                if (DAO_Cliente.ConsultarDni(dni)) {
                                                    // DNI válido, iniciar la actividad para recuperar la contraseña
                                                    Intent intent = new Intent(context, RecuperarPassword_Activity.class);
                                                    intent.putExtra("dni", dni);
                                                    intent.putExtra("login", true);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(context, "DNI incorrecto", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .setNegativeButton("Cancel", (dialogInterface, i) -> {
                                            txtCorreo.setText(null);
                                            txtClave.setText(null);
                                        })
                                        .show();
                            } else {
                                // Correo no registrado
                                MostrarMensaje.mensaje("Correo no registrado", context);
                            }
                        }
                    });
                }
            });

            // Cerrar el ExecutorService cuando ya no sea necesario
            executor.shutdown();
        }
    }


    void recuperarPass2(){
        String correo = txtCorreo.getText().toString(); //guardar el correo ingresado
        if(correo.isEmpty()){
            MostrarMensaje.mensaje("Ingrese su correo",this);
        }else{
            //validar si el correo existe
            //dao_usuarios.abrirBD();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            if(DAO_Cliente.ConsultarCorreo(correo)){
                //usuario encontrado
                //validar dni como verificación

                final EditText input = new EditText(context);
                new AlertDialog.Builder(context)
                        //.setTitle("LOGIN ADMIN")
                        .setMessage("Ingrese su DNI: ")
                        .setView(input)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String dni = input.getText().toString();
                                if(DAO_Cliente.ConsultarDni(dni) ){
                                    //DNI ENCONTRADO
                                    Intent intent = new Intent(context, RecuperarPassword_Activity.class);
                                    intent.putExtra("dni", dni);
                                    intent.putExtra("login",true);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(context, "DNI incorrecto", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel",(dialogInterface, i) -> {
                            txtCorreo.setText(null);
                            txtClave.setText(null);
                        })
                        .show();
            }else{
                MostrarMensaje.mensaje("Correo no registrado", this);
            }

        }

    }
    private void validarRS(){
        //validar recordar sesión del usuario
        if(App.recordarS) {
            checkRecordar.setChecked(true);
            txtCorreo.setText(App.correo);
            txtClave.setText(App.clave);
        } else {
            checkRecordar.setChecked(false);
        }

    }
    public static Usuario getUsuario(){
        return usuario;
    }


    private void cargarActividadRegistrate() {
        Intent iRegistro = new Intent(this, Registro_Activity.class);
        startActivity(iRegistro);
        finish();
    }

    private void validarFormulario() {
        String correo = txtCorreo.getText().toString().trim();
        String clave = txtClave.getText().toString().trim();

        if(correo.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Ingrese correctamente sus datos", Toast.LENGTH_SHORT).show();
            return;
        }
        iniciarSesion2(correo, clave);

    }
    private void guardarUsuario(String correo, String clave){
        usuario = DAO_Cliente.ObtenerCLI(correo,clave);
    }
    private void iniciarSesion(String correo, String clave) {
        // Crear un ExecutorService con un solo hilo
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Ejecutar la tarea en segundo plano
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Realizar la consulta de usuario en el hilo en segundo plano
                boolean usuarioEncontrado = DAO_Cliente.ObtenerCLI(correo, clave) != null;

                if (usuarioEncontrado) {
                    // Usuario encontrado
                    runOnUiThread(() -> {
                        guardarUsuario(correo, clave);

                        // Validar recordar sesión
                        if (checkRecordar.isChecked()) {
                            App.uploadDatos(getApplicationContext(), true, correo, clave);
                            Toast.makeText(getApplicationContext(), "Sesión guardada", Toast.LENGTH_SHORT).show();
                        } else {
                            if (App.recordarS) {
                                Toast.makeText(getApplicationContext(), "Sesión dejada de recordar", Toast.LENGTH_SHORT).show();
                            }
                            App.uploadDatos(getApplicationContext(), false, null, null);
                        }

                        Intent intent = new Intent(getApplicationContext(), Bienvenido_Activity.class);
                        startActivity(intent);
                    });

                } else {
                    // Buscar admin si el usuario no fue encontrado
                    boolean esAdmin = DAO_Administrador.ConsultarAdm(correo, clave);

                    if (esAdmin) {
                        runOnUiThread(() -> {
                            final EditText input = new EditText(getApplicationContext());
                            new AlertDialog.Builder(getApplicationContext())
                                    .setMessage("Ingrese su DNI: ")
                                    .setView(input)
                                    .setPositiveButton("Ok", (dialogInterface, i) -> {
                                        String dni = input.getText().toString();
                                        if (DAO_Administrador.ConsultarDni(dni)) {
                                            // DNI encontrado
                                            Intent intent = new Intent(getApplicationContext(), MenuAdmin_Activity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "DNI incorrecto", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                                        txtCorreo.setText(null);
                                        txtClave.setText(null);
                                    })
                                    .show();
                        });

                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(getApplicationContext(), "USUARIO O CLAVE INCORRECTA", Toast.LENGTH_SHORT).show();
                        });
                    }
                }

                // Finalizar el ExecutorService
                executor.shutdown();
            }
        });
    }

    private void iniciarSesion2(String correo, String clave){
        btnIngresar.setEnabled(false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if(DAO_Cliente.ObtenerCLI(correo, clave) != null){
            //usuario encontrado
            guardarUsuario(correo,clave);
            //validar recordar sesion
            if(checkRecordar.isChecked()){
                App.uploadDatos(this, true, correo, clave);
                Toast.makeText(context, "Sesión guardada", Toast.LENGTH_SHORT).show();
            } else{
                if(App.recordarS){
                    Toast.makeText(context, "Sesión dejada de recordar", Toast.LENGTH_SHORT).show();
                }
                App.uploadDatos(this, false, null, null);
            }
            Intent intent = new Intent(this, Bienvenido_Activity.class);
            isAdmin = false;
            startActivity(intent);
            this.finish();
        }else{
            //buscar admin
            if(DAO_Administrador.ConsultarAdm(correo, clave)){
                final EditText input = new EditText(context);
                new AlertDialog.Builder(context)
                        //.setTitle("LOGIN ADMIN")
                        .setMessage("Ingrese su DNI: ")
                        .setView(input)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String dni = input.getText().toString();
                                if(DAO_Administrador.ConsultarDni(dni) ){
                                    //DNI ENCONTRADO
                                    Intent intent = new Intent(context, MenuAdmin_Activity.class);
                                    //intent.putExtra("dni", dni);
                                    isAdmin = true;
                                    startActivity(intent);
                                    finish();

                                }else{
                                    Toast.makeText(context, "DNI incorrecto", Toast.LENGTH_SHORT).show();
                                    // Calcular el tiempo del Toast (por defecto, corto: 2000ms, largo: 3500ms)
                                    int duracionToast = Toast.LENGTH_SHORT == Toast.LENGTH_SHORT ? 2000 : 3500;
                                    // Usar un Handler para habilitar el botón después del Toast
                                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                        btnIngresar.setEnabled(true); // Rehabilitar el botón
                                    }, duracionToast);
                                }
                            }
                        })
                        .setNegativeButton("Cancel",(dialogInterface, i) -> {
                            txtCorreo.setText(null);
                            txtClave.setText(null);
                            btnIngresar.setEnabled(true);
                        })
                        .show();
            }else{
                Toast.makeText(this, "USUARIO O CLAVE INCORRECTA", Toast.LENGTH_SHORT).show();
                // Calcular el tiempo del Toast (por defecto, corto: 2000ms, largo: 3500ms)
                int duracionToast = Toast.LENGTH_SHORT == Toast.LENGTH_SHORT ? 2000 : 3500;
                // Usar un Handler para habilitar el botón después del Toast
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    btnIngresar.setEnabled(true); // Rehabilitar el botón
                }, duracionToast);
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}