package com.example.myproyect.actividades.actividades.admin;

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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<Usuario> user = DAO_Cliente.listarClientes();
        listarUsersAdapter = new ListarUsers_Adapter(user);

        rvListaUsers.setAdapter(listarUsersAdapter);
    }

}