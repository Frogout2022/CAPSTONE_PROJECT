package com.example.myproyect.actividades.actividades.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.clases.NumerosAdapter;

import java.util.ArrayList;

public class ListaU extends AppCompatActivity {

    ArrayList<Integer> numerosList = new ArrayList<>();
    RecyclerView rvListaUsers;
    NumerosAdapter numerosAdapter;
    Integer registroTotales = 35;
    Integer limite = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_u);

        rvListaUsers = findViewById(R.id.rcvListarUsersForADM);
        obternerPrimerosNumeros();
        numerosAdapter = new NumerosAdapter(numerosList);
        rvListaUsers.setAdapter(numerosAdapter);


    }
    public void obternerPrimerosNumeros(){
        if(registroTotales- numerosList.size()< limite){
            for(int i=numerosList.size(); i<registroTotales; i++){
                numerosList.add(i);
            }
        }else{
            int siguienteLimite = numerosList.size() + limite;
            for (int i= numerosList.size() ; i<siguienteLimite;i++){
                numerosList.add(i);
            }
        }
    }
}