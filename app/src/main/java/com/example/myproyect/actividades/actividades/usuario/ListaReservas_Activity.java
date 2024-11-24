package com.example.myproyect.actividades.actividades.usuario;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.myproyect.actividades.clases.Fecha;
import com.example.myproyect.actividades.clases.adapters.ListarRsv_Adapter;
import com.example.myproyect.actividades.entidades.CanchaDeportiva;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.modelos.DAO_Losa;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListaReservas_Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_lista_rsv);

        asignarReferencias();
        funSpinner();
        listar();
        botones();
    }

    private void asignarReferencias(){
        context = this;

        progressBar = findViewById(R.id.pb_ListarRsv_CLI);
        swLista = findViewById(R.id.sw_Listar_rsv);
        spnLosas = findViewById(R.id.spnListar_Rsv);

        rvListarRsv = findViewById(R.id.rcvListarRsvForUSR);
        btnUpdate = findViewById(R.id.btnUpdate_ListarRsv);
        btnRegresar = findViewById(R.id.btnRegresar_ListarRsv);
        txtvCantidad = findViewById(R.id.txtvCantRsv_ListRsv);

    }

    private void botones(){
        btnUpdate.setOnClickListener(view -> {
            rvListarRsv.setVisibility(View.INVISIBLE);
            listar();
        });
        btnRegresar.setOnClickListener(view -> {
            Intent intent = new Intent(this, Bienvenido_Activity.class );
            startActivity(intent);
            this.finish();
            super.onBackPressed();
        });
        swLista.setOnClickListener(view -> {
            System.out.println("is check");
            listar();
        });
    }

    private void listar() {
        progressBar.setVisibility(View.VISIBLE); // Mostrar progreso al iniciar
        rvListarRsv.setVisibility(View.GONE); // Ocultar el RecyclerView hasta tener datos
        swLista.setEnabled(false);
        txtvCantidad.setText(""); // Limpiar texto de cantidad

        String dni_cli = Login_Activity.getUsuario().getDNI();
        // Crear ExecutorService con un solo hilo
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            // Hilo secundario para consultas

            listaRsv = DAO_Reserva.ConsultarRsv(nombre_tabla, dni_cli);

            runOnUiThread(() -> {
                // Hilo principal para actualizar UI
                progressBar.setVisibility(View.GONE); // Ocultar el ProgressBar

                if (!listaRsv.isEmpty()) {
                    // Si hay datos, mostrar el RecyclerView y configurarlo
                    rvListarRsv.setVisibility(View.VISIBLE);
                    swLista.setEnabled(true);

                    List<Reserva> listaR = new ArrayList<>();
                    List<Reserva> lista_rsv_vigentes = new ArrayList<>();
                    int cont = 0;

                    for (Reserva reserva : listaRsv) {
                        for (int j = 0; j < 3; j++) {
                            String dniReserva = reserva.getArrayDni()[j];
                            if (dniReserva != null && dniReserva.equals(dni_cli)) {
                                int hora = 3 + (2 * j);
                                boolean b = Fecha.esFechaPasada(reserva.getDia());
                                if(!b){
                                    lista_rsv_vigentes.add(new Reserva(reserva.getDia(),hora+"pm",dniReserva));
                                }
                                listaR.add(new Reserva(reserva.getDia(), hora + "pm", dniReserva));
                            }
                        }
                        cont++;
                    }

                    if(swLista.isChecked()){
                        listarRsvAdapter = new ListarRsv_Adapter(lista_rsv_vigentes, nombre_tabla, context);
                        txtvCantidad.setText("Cantidad de reservas: " + lista_rsv_vigentes.size());
                    }else{
                        listarRsvAdapter = new ListarRsv_Adapter(listaR, nombre_tabla, context);
                        txtvCantidad.setText("Cantidad de reservas: " + listaR.size());
                    }

                    rvListarRsv.setAdapter(listarRsvAdapter);

                } else {
                    // Si no hay datos, ocultar el RecyclerView y mostrar mensaje
                    rvListarRsv.setVisibility(View.GONE);
                    txtvCantidad.setText("No hay reservas disponibles.");
                }
            });
        });

        // Cerrar el ExecutorService
        executor.shutdown();
    }

    private void funSpinner(){

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            // Hilo secundario para consultas
            List<CanchaDeportiva> lista;
            lista = DAO_Losa.listarNombres();
            runOnUiThread(() -> {
                //hilo principal

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
                        listar(); //llamar a la funcion principal
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            });

        });

        // Cerrar el ExecutorService
        executor.shutdown();

    }

}