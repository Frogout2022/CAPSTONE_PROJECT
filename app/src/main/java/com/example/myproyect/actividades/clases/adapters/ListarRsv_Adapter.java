package com.example.myproyect.actividades.clases.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private List<Reserva> reservasList;
    private int cantidad;
    private Context context;
    public static int cantidadRsv = 0;

    public ListarRsv_Adapter(List<Reserva> reservasList){
        this.reservasList = reservasList;
    }
    public ListarRsv_Adapter(List<Reserva> reservasList,Context context){
        this.reservasList = reservasList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtvDNI,txtvFecha,txtvHora,txtvLosa,txtvEstado,txtvFechaR;
        Button btnVerPago;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtvDNI = itemView.findViewById(R.id.txtv_dni_rcv_ListUsers);
            txtvFecha = itemView.findViewById(R.id.txtv_nombre_rcv_ListUsers);
            txtvHora = itemView.findViewById(R.id.txtv_apellido_rcv_ListUsers);
            txtvLosa = itemView.findViewById(R.id.txtv_losa_rcv_ListRsv);
            txtvEstado = itemView.findViewById(R.id.txtv_estado_rcv_ListRsv);
            txtvFechaR = itemView.findViewById(R.id.txtv_fechaR_rcv_ListRsv);

            btnVerPago = itemView.findViewById(R.id.btn_verRsv_rcv_ListUsers);

        }

    }

    @NonNull
    @Override
    public ListarRsv_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //seleccionar layout de plantilla
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_users, parent, false);
        System.out.println("onCreateViewHolder");
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ListarRsv_Adapter.ViewHolder holder, int position) {
        //rellenar todos los datos
        String dni_cli = Login_Activity.getUsuario().getDNI();
        int contador=0;
        for (Reserva reserva : reservasList) {
            for (int j = 0; j < 3; j++) {
                String dni = reserva.getArrayDni()[j];
                if (dni != null && dni.equals(dni_cli)) {
                    holder.txtvFecha.setText(reserva.getDia());
                    int hora = 3 + (2 * j);
                    holder.txtvHora.setText(hora+"pm");
                    contador++;
                }
            }
        }
        cantidadRsv = contador;

        holder.txtvDNI.setText(dni_cli);
        //para vista del ADMIN recibir el DNI del cardview
        holder.btnVerPago.setOnClickListener(view -> {

        });
        holder.txtvLosa.setText("");

        //System.out.println("-->"+numerosList.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return cantidad;
    }
}
