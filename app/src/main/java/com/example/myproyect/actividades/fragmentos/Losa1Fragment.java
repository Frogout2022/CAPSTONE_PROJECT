package com.example.myproyect.actividades.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.usuario.Bienvenido_Activity;
import com.example.myproyect.actividades.actividades.usuario.TablaReservaUser_Activity;
import com.example.myproyect.actividades.clases.ListaTablasBD;
import com.example.myproyect.actividades.clases.MostrarMensaje;
import com.example.myproyect.actividades.entidades.CanchaDeportiva;
import com.example.myproyect.actividades.modelos.DAO_Losa;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Losa1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Losa1Fragment extends Fragment{
    EditText txtFechaRe;

    Button btnReg,btnAceptar,btnReservar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final String nombre_tabla = "reserva_losa1";



    public Losa1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Losa1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Losa1Fragment newInstance(String param1, String param2) {
        Losa1Fragment fragment = new Losa1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_losa1, container, false);




        btnReg = (Button) view.findViewById(R.id.car1BtnRegresar);
        btnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.car1BtnRegresar:
                        btnReg.setEnabled(false);
                        regresar();
                        break;
                    case R.id.car1BtnAceptar:
                        btnReg.setEnabled(false);
                        tablaAceptar();
                        break;
                }
            }
        });
        btnAceptar = (Button) view.findViewById(R.id.car1BtnAceptar);
        btnAceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.car1BtnRegresar:
                        btnAceptar.setEnabled(false);
                        regresar();
                        break;
                    case R.id.car1BtnAceptar:
                        btnAceptar.setEnabled(false);
                        tablaAceptar();
                        break;
                }
            }
        });
        return view;
    }
    private void tablaAceptar(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        List<CanchaDeportiva> lista = new ArrayList<>();
        lista = DAO_Losa.listarLosas();
        final String nombre_losa = ListaTablasBD.cancha1.first;
        if(!lista.get(0).getMantenimiento()){
            Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), TablaReservaUser_Activity.class);
            intent.putExtra("tabla", nombre_tabla);
            intent.putExtra("nombre", nombre_losa);
            intent.putExtra("idLosa", ListaTablasBD.cancha1.second.toString());
            startActivity(intent);
            getActivity().finish();
        }else{
            MostrarMensaje.mensaje(nombre_losa+" en mantenimiento."+"\n"+"Disculpa las molestias", getContext()); //alert
            //Toast.makeText(getContext(), nombre_losa+" EN MANTENIMIENTO", Toast.LENGTH_LONG).show();
        }


    }


    private void regresar() {
        Intent iBienvenido = new Intent(getContext(), Bienvenido_Activity.class);
        startActivity(iBienvenido);
        getActivity().finish();
    }

}