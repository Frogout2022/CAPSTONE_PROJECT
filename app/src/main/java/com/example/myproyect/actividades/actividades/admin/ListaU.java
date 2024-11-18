package com.example.myproyect.actividades.actividades.admin;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.clases.ListarUsers_Adapter;
import com.example.myproyect.actividades.clases.NumerosAdapter;
import com.example.myproyect.actividades.entidades.Usuario;
import com.example.myproyect.actividades.modelos.DAO_Cliente;

import java.util.ArrayList;

public class ListaU extends AppCompatActivity {

    ArrayList<Integer> numerosList = new ArrayList<>();
    RecyclerView rvListaUsers;
    ListarUsers_Adapter listarUsersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_u);

        rvListaUsers = findViewById(R.id.rcvListarUsersForADM);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<Usuario> user = DAO_Cliente.listarClientes();
        listarUsersAdapter = new ListarUsers_Adapter(user);

        rvListaUsers.setAdapter(listarUsersAdapter);


    }

}