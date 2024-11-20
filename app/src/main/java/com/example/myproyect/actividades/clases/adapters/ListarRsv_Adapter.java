package com.example.myproyect.actividades.clases.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.CargaActivity;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.entidades.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ListarRsv_Adapter extends RecyclerView.Adapter<ListarRsv_Adapter.ViewHolder> {
    private List<Pair<String, Integer>> reservasList;
    private Context context;
    private String nombre_tabla;

    public ListarRsv_Adapter(List<Pair<String, Integer>> reservasList){
        this.reservasList = reservasList;
    }
    public ListarRsv_Adapter(List<Pair<String, Integer>> reservasList,String nombre_tabla, Context context){
        this.reservasList = reservasList;
        this.context = context;
        this.nombre_tabla= nombre_tabla;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtvDNI,txtvFecha,txtvHora,txtvLosa,txtvEstado,txtvFechaR,txtvPos;
        Button btnVerPago;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtvDNI = itemView.findViewById(R.id.txtv_dni_rcv_ListRsv);
            txtvFecha = itemView.findViewById(R.id.txtv_fecha_rcv_ListRsv);
            txtvHora = itemView.findViewById(R.id.txtv_hora_rcv_ListRsv);
            txtvLosa = itemView.findViewById(R.id.txtv_losa_rcv_ListRsv);
            txtvEstado = itemView.findViewById(R.id.txtv_estado_rcv_ListRsv);
            txtvFechaR = itemView.findViewById(R.id.txtv_fechaR_rcv_ListRsv);
            txtvPos = itemView.findViewById(R.id.txtv_position_rcv_ListRsv);

            btnVerPago = itemView.findViewById(R.id.btn_verRsv_rcv_ListRsv);
            System.out.println("ViewHolder");
        }

    }

    @NonNull
    @Override
    public ListarRsv_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //seleccionar layout de plantilla!!!! .inflate...
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reservas, parent, false);
        System.out.println("onCreateViewHolder");
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ListarRsv_Adapter.ViewHolder holder, int position) {
        //rellenar todos los datos

        String dni_cli = Login_Activity.getUsuario().getDNI();
        holder.txtvDNI.setText(dni_cli);
        //para vista del ADMIN recibir el DNI del cardview

        holder.txtvFecha.setText(reservasList.get(position).first.toString());
        holder.txtvHora.setText(reservasList.get(position).second.toString()+"pm");

        holder.txtvLosa.setText(nombre_tabla);
        int pos = position+1;
        holder.txtvPos.setText(pos+"");
        //funcion estado
        holder.txtvEstado.setText("Pendiente");


        holder.btnVerPago.setOnClickListener(view -> {
            //mostrar una vista de detalles
            //(implementar CLASE)
        });

        System.out.println("onBindViewHolder: "+getItemCount());
    }

    @Override
    public int getItemCount() {
        return reservasList.size();
    }
}
