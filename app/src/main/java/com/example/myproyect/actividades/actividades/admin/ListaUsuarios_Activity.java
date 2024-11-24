package com.example.myproyect.actividades.actividades.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.clases.adapters.ListarUsers_Adapter;
import com.example.myproyect.actividades.entidades.Usuario;
import com.example.myproyect.actividades.modelos.DAO_Cliente;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListaUsuarios_Activity extends AppCompatActivity {

    RecyclerView rvListaUsers;
    ListarUsers_Adapter listarUsersAdapter;
    Button btnRegresar, btnUpdate;
    TextView txtvCantidad;
    ProgressBar progressBar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_u);

        asignarReferencias();
        listar();
        botones();
    }
    private void asignarReferencias(){

        context = this;

        progressBar = findViewById(R.id.pb_ListarUsers_ADM);

        rvListaUsers = findViewById(R.id.rcvListarUsersForADM);
        btnUpdate = findViewById(R.id.btnUpdate_ListarU);
        btnRegresar = findViewById(R.id.btnRegresar_ListarU);
        txtvCantidad = findViewById(R.id.txtvCantUsers_ListU);

        btnRegresar = findViewById(R.id.btnRegresar_ListarU);
        btnUpdate = findViewById(R.id.btnUpdate_ListarU);

    }
    private void botones(){
        btnUpdate.setOnClickListener(view -> {
            rvListaUsers.setVisibility(View.INVISIBLE);
            listar();
        });
        btnRegresar.setOnClickListener(view -> {
            Intent intent = new Intent(this,MenuAdmin_Activity.class );
            startActivity(intent);
            this.finish();
            super.onBackPressed();
        });
    }
    private void listar(){
        progressBar.setVisibility(View.VISIBLE);
        rvListaUsers.setVisibility(View.VISIBLE);

        // Crear un ExecutorService con un solo hilo o un pool de hilos según tu preferencia
        ExecutorService executor = Executors.newFixedThreadPool(1);

        // Ejecutar la tarea en segundo plano
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Realizar consultas en paralelo usando Thread para mejorar el rendimiento
                ArrayList<Usuario> user = DAO_Cliente.listarClientes();

                // Actualizar la UI en el hilo principal
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Configurar el adaptador y los datos en la UI
                        listarUsersAdapter = new ListarUsers_Adapter(user, context);
                        rvListaUsers.setAdapter(listarUsersAdapter);
                        txtvCantidad.setText("Cantidad de usuarios: " + user.size());

                        // Ocultar el ProgressBar después de que la tarea esté completada
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                });
            }
        });


        // Cerrar el ExecutorService cuando ya no sea necesario (por ejemplo, en onDestroy o cuando termines)
        executor.shutdown();


    }

}