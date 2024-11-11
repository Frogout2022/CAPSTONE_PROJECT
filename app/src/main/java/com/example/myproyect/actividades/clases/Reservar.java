package com.example.myproyect.actividades.clases;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.example.myproyect.actividades.actividades.usuario.TablaReservaUser_Activity;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reservar {

    public static String realizar(){
        //PROCESO DE RESERVA EN BD
            //int dia_siguiente = Fecha.obtenerNumeroDiaActual()+1;
            String tabla = TablaReservaUser_Activity.tabla;
            List<Integer> listaChkS = TablaReservaUser_Activity.listaChkS;
            String msg = null;
            List<String> lista = Fecha.getFechas();
            int[][] casos = {
                    {0, 1, 2},     // Casos 0, 1, 2
                    {3, 4, 5},     // Casos 3, 4, 5
                    {6, 7, 8},     // Casos 6, 7, 8
                    {9, 10, 11},   // Casos 9, 10, 11
                    {12, 13, 14},  // Casos 12, 13, 14
                    {15, 16, 17}   // Casos 15, 16, 17
            };
            //Log.d("RESERVA", "lenghListaSemanal: "+listaSemanal.size());
            Log.d("RESERVA", "lenghListaCheks: "+listaChkS.size());

            for (int i = 0; i < listaChkS.size(); i++) {
                int numOrden = listaChkS.get(i); //capturar la posicion en la tabla
                int grupo = -1; //0=dia1(mañana);1=dia2;2=dia3
                String dia;

                // Determinar el grupo al que pertenece numOrden
                for (int j = 0; j < casos.length; j++) {
                    if (Arrays.stream(casos[j]).anyMatch(x -> x == numOrden)) {
                        grupo = j;
                        break;
                    }
                }
                if (grupo != -1) {
                    dia = lista.get(grupo);
                    int hora = 15 + ((numOrden - grupo * 3) * 2);
                   // Log.d("RESERVA","TABLA: "+tabla+" ,DIA: "+dia+" ,HORA: "+hora);
                    if(validar(grupo,hora)){
                        //verificar disponibilidad en tiempo real
                        Log.d("RESERVA", "OCUPADO");
                        msg = "Hora selecciona ya OCUPADA";
                    }else {
                        //insertar reserva
                        Log.d("RESERVA", "NO OCUPADO");
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        msg = DAO_Reserva.insertarRSV(tabla, dia, hora);
                    }
                    Log.d("RESERVA", "-> numOrden:"+numOrden);
                    Log.d("RESERVA", "-> i:"+i);
                    Log.d("RESERVA", "--> group:"+grupo);
                }
            }

        return msg;
    }

    static Boolean validar(int dia,int hora){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ArrayList<Reserva> listaSemanal = new ArrayList<>();
        int dia_siguiente = Fecha.obtenerNumeroDiaActual()+1;
        String tabla = TablaReservaUser_Activity.tabla;
        listaSemanal = DAO_Reserva.listarReservaSemanal(tabla, dia_siguiente, dia_siguiente+5);//+7
        int posHora = (hora-13)/2;
        if(listaSemanal.get(dia).getArrayDni()[posHora-1]!=null) return true;
        else return false;
    }
}
