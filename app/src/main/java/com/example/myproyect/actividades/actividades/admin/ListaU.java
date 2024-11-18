package com.example.myproyect.actividades.actividades.admin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.clases.ListarUsers_Adapter;
import com.example.myproyect.actividades.clases.NumerosAdapter;
import com.example.myproyect.actividades.entidades.Usuario;
import com.example.myproyect.actividades.modelos.DAO_Cliente;

import java.util.ArrayList;
import java.util.List;

public class ListaU extends AppCompatActivity {

    RecyclerView rvListaUsers;
    ListarUsers_Adapter listarUsersAdapter;
    Button btnRegresar, btnUpdate;
    TextView txtvCantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_u);

        asignarReferencias();
        listar();
    }
    private void asignarReferencias(){
        rvListaUsers = findViewById(R.id.rcvListarUsersForADM);
        btnUpdate = findViewById(R.id.btnUpdate_ListarU);
        btnRegresar = findViewById(R.id.btnRegresar_ListarU);
        txtvCantidad = findViewById(R.id.txtvCantUsers_ListU);

    }
    private void listar(){
        // Ejecutar las consultas en segundo plano
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                // Realizar consultas en paralelo usando Thread para mejorar el rendimiento
                ArrayList<Usuario> user = DAO_Cliente.listarClientes();

                // Después de obtener todos los datos, actualizamos la UI en el hilo principal
                runOnUiThread(() -> {
                    // Configuramos el adaptador y los datos en la UI
                    listarUsersAdapter = new ListarUsers_Adapter(user);
                    rvListaUsers.setAdapter(listarUsersAdapter);
                    txtvCantidad.setText("Cantidad de usuarios: "+String.valueOf(user.size()));
                });

                return null;
            }

        }.execute();  // Ejecutamos el AsyncTask
    }

}