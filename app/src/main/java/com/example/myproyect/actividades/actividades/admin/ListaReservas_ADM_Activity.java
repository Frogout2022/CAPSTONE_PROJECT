package com.example.myproyect.actividades.actividades.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.usuario.Bienvenido_Activity;
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

public class ListaReservas_ADM_Activity extends AppCompatActivity {

    RecyclerView rvListarRsv;
    ListarRsv_Adapter listarRsvAdapter;
    Button btnRegresar, btnUpdate;
    TextView txtvCantidad;
    ProgressBar progressBar;
    Spinner spnLosas;
    Switch swLista;
    RadioGroup rg;
    RadioButton rb_anio,rb_mes,rb_semana;

    private Context context;
    private List<Reserva> listaRsv;
    private String nombre_tabla="reserva_losa1";
    private String filtro= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_rsv_adm);

        filtro="Año";
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

        rg = findViewById(R.id.rg_rsv_usuarios_adm);
        rb_anio = findViewById(R.id.rb_anio_rsv_adm);
        rb_mes = findViewById(R.id.rb_mes_rsv_adm);
        rb_semana = findViewById(R.id.rb_semana_rsv_adm);

        rb_anio.setChecked(true);


    }
    private void botones(){
        btnUpdate.setOnClickListener(view -> {
            btnUpdate.setEnabled(false);
            rvListarRsv.setVisibility(View.INVISIBLE);
            listar();
        });
        btnRegresar.setOnClickListener(view -> {
            btnRegresar.setEnabled(false);
            Intent intent = new Intent(this, MenuAdmin_Activity.class );
            startActivity(intent);
            this.finish();
        });
        swLista.setOnClickListener(view -> {
            listar();
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                filtro = radioButton.getText().toString();
                listar();
                System.out.println("selected: "+radioButton.getText().toString());
            }
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
            listaRsv = DAO_Reserva.listarReservasCLI(nombre_tabla);
            runOnUiThread(() -> {
                // Hilo principal para actualizar UI
                progressBar.setVisibility(View.GONE); // Ocultar el ProgressBar
                if (!listaRsv.isEmpty()) {
                    // Si hay datos, mostrar el RecyclerView y configurarlo
                    rvListarRsv.setVisibility(View.VISIBLE);
                    swLista.setEnabled(true);
                    List<Reserva> listaR = new ArrayList<>();
                    List<Reserva> lista_rsv_vigentes = new ArrayList<>();

                    List<Reserva> lista_rsv_anual = new ArrayList<>();
                    List<Reserva> lista_rsv_mensual = new ArrayList<>();
                    List<Reserva> lista_rsv_semanal = new ArrayList<>();

                    for (Reserva reserva : listaRsv) {
                        for (int j = 0; j < 3; j++) {
                            String dniReserva = reserva.getArrayDni()[j];
                            if(dniReserva!=null){
                                int hora = 3 + (2 * j);
                                lista_rsv_anual.add(new Reserva(reserva.getDia(),hora+"pm",dniReserva));

                                if(Fecha.validarRangoFecha(reserva.getDia(),-31)){
                                    lista_rsv_mensual.add(new Reserva(reserva.getDia(),hora+"pm",dniReserva));
                                }
                                if(Fecha.validarRangoFecha(reserva.getDia(),-7)){
                                    lista_rsv_semanal.add(new Reserva(reserva.getDia(),hora+"pm",dniReserva));
                                }
                                if(!Fecha.esFechaPasada(reserva.getDia())){
                                    lista_rsv_vigentes.add(new Reserva(reserva.getDia(),hora+"pm",dniReserva));
                                }

                            }
                        }
                    }
                    if(swLista.isChecked()) {
                        listarRsvAdapter = new ListarRsv_Adapter(lista_rsv_vigentes, nombre_tabla, context);
                        txtvCantidad.setText("Cantidad de reservas: " + lista_rsv_vigentes.size());
                    }else{

                        switch (filtro){
                            case "Año":
                                listarRsvAdapter = new ListarRsv_Adapter(lista_rsv_anual, nombre_tabla, context);
                                txtvCantidad.setText("Cantidad de reservas: " + lista_rsv_anual.size());
                                break;
                            case "Mes":
                                listarRsvAdapter = new ListarRsv_Adapter(lista_rsv_mensual, nombre_tabla, context);
                                txtvCantidad.setText("Cantidad de reservas: " + lista_rsv_mensual.size());
                                break;
                            case "Semana":
                                listarRsvAdapter = new ListarRsv_Adapter(lista_rsv_semanal, nombre_tabla, context);
                                txtvCantidad.setText("Cantidad de reservas: " + lista_rsv_semanal.size());
                                break;

                        }

                    }
                    rvListarRsv.setAdapter(listarRsvAdapter);
                } else {
                    // Si no hay datos, ocultar el RecyclerView y mostrar mensaje
                    rvListarRsv.setVisibility(View.GONE);
                    txtvCantidad.setText("No hay reservas disponibles.");
                }
                btnUpdate.setEnabled(true);
            });
        });

        // Cerrar el ExecutorService
        executor.shutdown();
    }

    private void funSpinner(){
        // Crear ExecutorService con un solo hilo

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            //hilo secundario
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
                        listar(); //llamar a la fucnion principal
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