package com.example.myproyect.actividades.actividades.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.actividades.usuario.BienvenidoActivity;
import com.example.myproyect.actividades.clases.adapters.ListarRsv_Adapter;
import com.example.myproyect.actividades.entidades.CanchaDeportiva;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.modelos.DAO_Losa;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListaReservas_ADM_Activity extends AppCompatActivity {

    RecyclerView rvListarRsv;
    ListarRsv_Adapter listarRsvAdapter;
    Button btnRegresar, btnUpdate;
    TextView txtvCantidad;
    ProgressBar progressBar;
    Spinner spnLosas;
    Switch swLista;

    private Context context;
    private List<Reserva> listaRsv;
    private String nombre_tabla="reserva_losa1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_rsv_adm);

        asignarReferencias();
        funSpinner();
        listar();
        botones();
    }
    private void asignarReferencias(){

        context = this;

        progressBar = findViewById(R.id.pb_ListarRsv_ADM);
        swLista = findViewById(R.id.sw_Listar_rsv_adm);
        spnLosas = findViewById(R.id.spnListar_Rsv_ADM);

        rvListarRsv = findViewById(R.id.rcvListarRsvForADM);
        btnUpdate = findViewById(R.id.btnUpdate_ListarRsv_ADM);
        btnRegresar = findViewById(R.id.btnRegresar_ListarRsv_ADM);
        txtvCantidad = findViewById(R.id.txtvCantRsv_ListRsv_ADM);


    }
    private void botones(){
        btnUpdate.setOnClickListener(view -> {
            rvListarRsv.setVisibility(View.INVISIBLE);
            listar();
        });
        btnRegresar.setOnClickListener(view -> {
            Intent intent = new Intent(this, BienvenidoActivity.class );
            startActivity(intent);
            this.finish();
            super.onBackPressed();
        });
    }
    private void listar(){
        progressBar.setVisibility(View.VISIBLE); // Mostrar progreso al iniciar
        rvListarRsv.setVisibility(View.GONE); // Ocultar el RecyclerView hasta tener datos
        swLista.setEnabled(false);
        txtvCantidad.setText(""); // Limpiar texto de cantidad

        // Crear ExecutorService con un solo hilo
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            // Hilo secundario para consultas
            System.out.println("run");
            listaRsv = DAO_Reserva.listarReservasCLI(nombre_tabla);

            runOnUiThread(() -> {
                // Hilo principal para actualizar UI
                progressBar.setVisibility(View.GONE); // Ocultar el ProgressBar

                if (!listaRsv.isEmpty()) {
                    // Si hay datos, mostrar el RecyclerView y configurarlo
                    rvListarRsv.setVisibility(View.VISIBLE);
                    swLista.setEnabled(true);

                    List<Reserva> listaR = new ArrayList<>();
                    int cont = 0;

                    for (Reserva reserva : listaRsv) {
                        for (int j = 0; j < 3; j++) {
                            String dniReserva = reserva.getArrayDni()[j];
                            int hora = 3 + (2 * j);
                            listaR.add(new Reserva(reserva.getDia(), hora + "pm", dniReserva));
                        }
                        cont++;
                    }

                    System.out.println("contador! " + cont);

                    listarRsvAdapter = new ListarRsv_Adapter(listaR, nombre_tabla, context);
                    rvListarRsv.setAdapter(listarRsvAdapter);
                    txtvCantidad.setText("Cantidad de reservas: " + cont);

                } else {
                    // Si no hay datos, ocultar el RecyclerView y mostrar mensaje
                    rvListarRsv.setVisibility(View.GONE);
                    txtvCantidad.setText("No hay reservas disponibles.");
                    System.out.println("No hay reservas en esta losa.");
                }
            });
        });

        // Cerrar el ExecutorService
        executor.shutdown();
    }

    private void funSpinner(){

        System.out.println("funSpinner");

        List<CanchaDeportiva> lista;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        lista = DAO_Losa.listarNombres();


        List<String> opciones = new ArrayList<>();
        int i=1;
        for(CanchaDeportiva canchaDeportiva : lista){
            opciones.add(i+". "+canchaDeportiva.getNombre());
            i++;
            System.out.println("cancha: "+canchaDeportiva.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spnLosas.setAdapter(adapter);

        List<CanchaDeportiva> finalLista = lista;
        spnLosas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String opcion = (String) adapterView.getItemAtPosition(i);
                nombre_tabla = finalLista.get(i).getNombre_tabla();
                listar(); //llamar a la fucnion principal
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

}