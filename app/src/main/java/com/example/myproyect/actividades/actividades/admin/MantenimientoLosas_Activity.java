package com.example.myproyect.actividades.actividades.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.entidades.CanchaDeportiva;
import com.example.myproyect.actividades.modelos.DAO_Losa;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoLosas_Activity extends AppCompatActivity {
    Spinner spnLosas;
    Button btnGuardar, btnSalir;
    RadioGroup radioGroup;
    RadioButton rbtnSi, rbtnNo;
    EditText txtPrecio;
    TextView txtHorario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_losas);

        referencias();

    }

    private void referencias(){
        btnSalir = findViewById(R.id.btnSalir_ManteLosas_Admin);
        btnSalir.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuAdmin_Activity.class);
            startActivity(intent);
            finish();
        });
        txtHorario = findViewById(R.id.txtHorario_AdminLosas);
        txtPrecio = findViewById(R.id.txtPrecio_AdminLosas);
        funPrecio();
        spnLosas = findViewById(R.id.spnListarLosas_MngAdmin);
        spnFun();
        btnGuardar = findViewById(R.id.btnGuardar_AdminLosas);
        btnGuardar.setOnClickListener(view -> {
            funGuardar();
        });
        radioGroup = findViewById(R.id.rg_nmgLosas);
        rbtnSi = findViewById(R.id.rbtn_si_mngLosas);
        rbtnNo = findViewById(R.id.rbtn_no_mngLosas);


    }
    private void funPrecio(){
        int maxLength = 5; // Número máximo de dígitos permitidos

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxLength);

        txtPrecio.setFilters(filters);
    }

    private void funGuardar(){
        int i = spnLosas.getSelectedItemPosition();
        //Toast.makeText(this, "index: "+i, Toast.LENGTH_SHORT).show();
        boolean mante = false;
        if(rbtnSi.isChecked()) mante = true;
        if(rbtnNo.isChecked()) mante = false;
        double precio = Double.parseDouble(txtPrecio.getText().toString());



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(DAO_Losa.editarLosas(i+1,mante, precio)){
            Toast.makeText(this, "Se actualizó", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
        updateVista();



    }
    private void updateVista(){
        spnLosas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<CanchaDeportiva> lista = new ArrayList<>();
                //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                //StrictMode.setThreadPolicy(policy);
                lista = DAO_Losa.listarLosas();
                //actualizar radio buttons
                if(lista.get(i).getMantenimiento()){
                    rbtnSi.setChecked(true);
                    rbtnNo.setChecked(false);
                }else{
                    rbtnSi.setChecked(false);
                    rbtnNo.setChecked(true);
                }
                //actualizar precio
                txtPrecio.setText(lista.get(i).getPrecio()+"");
                //actualizar horairo
                txtHorario.setText("HORARIO: "+lista.get(i).getHorario());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void spnFun(){
        List<CanchaDeportiva> lista = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        lista = DAO_Losa.listarLosas();

        List<String> opciones = new ArrayList<>();
        int i=1;
        for(CanchaDeportiva canchaDeportiva : lista){
            opciones.add(i+". "+canchaDeportiva.getNombre());
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnLosas.setAdapter(adapter);

        updateVista();

    }
}