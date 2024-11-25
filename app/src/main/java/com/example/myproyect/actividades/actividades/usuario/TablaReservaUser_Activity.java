package com.example.myproyect.actividades.actividades.usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.actividades.usuario.pago.PagoActivity;
import com.example.myproyect.actividades.clases.Fecha;
import com.example.myproyect.actividades.clases.Reservar;
import com.example.myproyect.actividades.entidades.CanchaDeportiva;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.entidades.Usuario;
import com.example.myproyect.actividades.modelos.DAO_Losa;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class TablaReservaUser_Activity extends AppCompatActivity {

    CheckBox chkL1,chkL2,chkL3;
    CheckBox chkM1,chkM2,chkM3;
    CheckBox chkMi1,chkMi2,chkMi3;
    CheckBox chkJ1,chkJ2,chkJ3;
    CheckBox chkV1,chkV2,chkV3;
    CheckBox chkS1, chkS2,chkS3;
    TextView lblSemana, lblCantidadPagar, lblTarifa;
    TextView txtv_cl1,txtv_cl2,txtv_cl3,txtv_cl4,txtv_cl5,txtv_cl6;
    Button btnReservar;
    Button   btnVolver;

    List<CheckBox> listaChk = new ArrayList<>();
    List<TextView> listaTxtv = new ArrayList<>();

    public static Double cantidadPagar=0.0, precio_hora=0.0;
    public static ArrayList<Reserva> listaSemanal = new ArrayList<>();
    public static List<Integer> listaChkS = new ArrayList<>();
    public static String tabla;
    public static String nombre_losa;
    public static boolean preReserva = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla);

        asginarReferencias();

        agregarListaChk();
        agregarListaTxtv();
        updateTxtv();

        updateChk(); //consultar a la BD
        consultarPrecioH();
        clickChk(); //actualizar visualización (precio)

        lblSemana.setSingleLine(false);
        lblSemana.setText(Fecha.lblTablaReserva);
        lblSemana.append("\n"+"#"+getIntent().getStringExtra("idLosa")+": "+getIntent().getStringExtra("nombre"));
        nombre_losa = getIntent().getStringExtra("nombre");
        //lblNombreL.setSingleLine(false);


    }
    private void consultarPrecioH(){

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {

            final List<CanchaDeportiva> lista = DAO_Losa.listarLosas();
            tabla = getIntent().getStringExtra("tabla");

            runOnUiThread(() -> {
                int i=0;
                for(CanchaDeportiva canchaDeportiva : lista){
                    if(canchaDeportiva.getNombre_tabla().equals(tabla)) break;
                    i++;
                }
                precio_hora = lista.get(i).getPrecio();
                lblTarifa.setText("Precio/HORA: "+precio_hora+" soles");
            });
        });


    }


    private void updateChk(){
        //consultar BD

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            //hilo secundario
            int dia_siguiente = Fecha.obtenerNumeroDiaActual()+1;
            String tabla = getIntent().getStringExtra("tabla");
            listaSemanal = DAO_Reserva.listarReservaSemanal(tabla, dia_siguiente, dia_siguiente+5);//+7

            runOnUiThread(() -> {
                //hilo principal UI
                if(listaSemanal.size()==0){
                    //lista vacía
                    Toast.makeText(this, "No se pudieron cargar datos de la losa.", Toast.LENGTH_SHORT).show();

                }else{
                    //Toast.makeText(this, "LISTA NO VACIA", Toast.LENGTH_SHORT).show();

                    int index = 0, cantidadDias= 6, cantidadHoras=3;

                    for (int i = 0; i < cantidadDias; i++) {
                        for (int j = 0; j < cantidadHoras; j++) {
                            if(listaSemanal.get(i).getArrayDni()[j]!=null){
                                //true
                                listaChk.get(index).setChecked(true);
                                listaChk.get(index).setText("Ocupado");
                                listaChk.get(index).setEnabled(false);
                            }else{
                                listaChk.get(index).setChecked(false);
                                listaChk.get(index).setText("Libre");
                                listaChk.get(index).setEnabled(true);
                            }
                            index++;
                        }
                    }
                    listaChkS.clear();
                }
            });
        });

    }

    private void agregarListaTxtv(){
        listaTxtv.add(txtv_cl1);
        listaTxtv.add(txtv_cl2);
        listaTxtv.add(txtv_cl3);
        listaTxtv.add(txtv_cl4);
        listaTxtv.add(txtv_cl5);
        listaTxtv.add(txtv_cl6);
    }
    private void updateTxtv(){
        List<String> lista = Fecha.obtenerDiasSemanaProximos();
        for(int i=0; i<listaTxtv.size(); i++){
            listaTxtv.get(i).setText(lista.get(i));
        }
    }
    private void clickChk(){
        View.OnClickListener checkBoxListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Manejar el evento de clic del CheckBox
                CheckBox checkBox = (CheckBox) view;
                int selectedCheckBoxId = checkBox.getId();
                // Obtener el ID del CheckBox seleccionado

                for(int i=0 ; i<listaChk.size(); i++){
                    if(selectedCheckBoxId == listaChk.get(i).getId()){
                        if(listaChk.get(i).isChecked()){
                            listaChk.get(i).setText("Elegido");
                            int color = ContextCompat.getColor(TablaReservaUser_Activity.this, R.color.purple_500);
                            checkBox.setTextColor(color); // Establecer el color del texto utilizando un recurso de color
                            cantidadPagar += precio_hora;
                            listaChkS.add(i);
                            lblCantidadPagar.setText("Pagar: S/"+cantidadPagar);
                        }else{
                            listaChk.get(i).setText("Libre");
                            int color = ContextCompat.getColor(TablaReservaUser_Activity.this, R.color.white);
                            checkBox.setTextColor(color); // Establecer el color del texto utilizando un recurso de color
                            cantidadPagar -= precio_hora;

                            if(listaChkS.size()!=0){//buscar y borrar de la lista
                                for(int j=0; j<listaChkS.size(); j++){
                                    if(listaChkS.get(j) == i){
                                     listaChkS.remove(j);
                                    }
                                }
                            }
                            lblCantidadPagar.setText("Pagar: S/"+cantidadPagar);
                        }
                    }
                }
            }
        };
        for(int i=0 ; i<listaChk.size(); i++){
            listaChk.get(i).setOnClickListener(checkBoxListener);
        }

    }
    private void reservar(){
        if (cantidadPagar==0){
            Toast.makeText(TablaReservaUser_Activity.this, "Elija almenos un horario", Toast.LENGTH_SHORT).show();
            btnReservar.setEnabled(true);
        } else{
            preReserva = true;
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                Reservar.realizar("separar");
                runOnUiThread(() -> {
                    Intent iPago= new Intent(this, PagoActivity.class);
                    iPago.putExtra("MontoPagar",cantidadPagar);
                    startActivity(iPago);
                    btnReservar.setEnabled(true);
                    this.finish();
                });
            });
        }
    }

    private void asginarReferencias(){

        txtv_cl1 = findViewById(R.id.txtv_cl1_TRU);
        txtv_cl2 = findViewById(R.id.txtv_cl2_TRU);
        txtv_cl3 = findViewById(R.id.txtv_cl3_TRU);
        txtv_cl4 = findViewById(R.id.txtv_cl4_TRU);
        txtv_cl5 = findViewById(R.id.txtv_cl5_TRU);
        txtv_cl6 = findViewById(R.id.txtv_cl6_TRU);

        lblSemana = findViewById(R.id.lblSemana_TablaReserva);
        lblCantidadPagar = findViewById(R.id.lblCantidadPagar_TRU);

        lblTarifa = findViewById(R.id.lblTarifa_TRU);

        btnVolver = findViewById(R.id.btnRegresar_TRU);
        btnVolver.setOnClickListener(view -> {
            btnVolver.setEnabled(false);
            listaChkS.clear(); //limpiar selecciones
            Intent intent = new Intent(getApplicationContext(),Bienvenido_Activity.class);
            startActivity(intent);
            this.finish();
        });
        btnReservar = findViewById(R.id.btnReservarTablaUser);
        btnReservar.setOnClickListener(view -> {
            btnReservar.setEnabled(false);
            reservar();
        });

        referenciasChk();


    }
    private void referenciasChk(){
        chkL1 = findViewById(R.id.chkLunes_3pm_TRU);//0
        chkL2 = findViewById(R.id.chkLunes_5pm_TRU);//1
        chkL3 = findViewById(R.id.chkLunes_7pm_TRU);//2

        chkM1 = findViewById(R.id.chkMartes_3pm_TRU);//3
        chkM2 = findViewById(R.id.chkMartes_5pm_TRU);//4
        chkM3 = findViewById(R.id.chkMartes_7pm_TRU);//5

        chkMi1 = findViewById(R.id.chKMiercoles_3pm_TRU);//6
        chkMi2 = findViewById(R.id.chKMiercoles_5pm_TRU);//7
        chkMi3 = findViewById(R.id.chkMiercoles_7pm_TRU);//8

        chkJ1 = findViewById(R.id.chkJueves_3pm_TRU);//9
        chkJ2 = findViewById(R.id.chkJueves_5pm_TRU);//10
        chkJ3 = findViewById(R.id.chkJueves_7pm_TRU);//11

        chkV1 = findViewById(R.id.chkViernes_3pm_TRU);//12
        chkV2 = findViewById(R.id.chkViernes_5pm_TRU);//13
        chkV3 = findViewById(R.id.chkViernes_7pm_TRU);//14

        chkS1 = findViewById(R.id.chkSabado_3pm_TRU);//15
        chkS2 = findViewById(R.id.chkSabado_5pm_TRU);//16
        chkS3 = findViewById(R.id.chkSabado_7pm_TRU);//17
    }
    private void agregarListaChk(){
        listaChk.add(chkL1);
        listaChk.add(chkL2);
        listaChk.add(chkL3);

        listaChk.add(chkM1);
        listaChk.add(chkM2);
        listaChk.add(chkM3);

        listaChk.add(chkMi1);
        listaChk.add(chkMi2);
        listaChk.add(chkMi3);

        listaChk.add(chkJ1);
        listaChk.add(chkJ2);
        listaChk.add(chkJ3);

        listaChk.add(chkV1);
        listaChk.add(chkV2);
        listaChk.add(chkV3);

        listaChk.add(chkS1);
        listaChk.add(chkS2);
        listaChk.add(chkS3);

    }
}