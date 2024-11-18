package com.example.myproyect.actividades.clases;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.entidades.Usuario;

import java.util.List;

public class ListarUsers_Adapter extends RecyclerView.Adapter<ListarUsers_Adapter.ViewHolder> {

    List<Integer> numerosList;
    List<Usuario> usuariosList;

    public ListarUsers_Adapter(List<Integer> numerosList){
        this.numerosList = numerosList;
        //System.out.println("ListarUsers_Adapter");
    }

    public void ListarUsers_Adapter2(List<Usuario> usuariosList){
        this.usuariosList = usuariosList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumero;
        TextView txtvDNI,txtvNombre,txtvApellido,txtvCel,txtvEmail,txtvFecha, txtvPos;
        Button btnVerRsv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumero = itemView.findViewById(R.id.txtv_position_rcv_ListUsers); //borrar

            txtvDNI = itemView.findViewById(R.id.txtv_dni_rcv_ListUsers);
            txtvNombre = itemView.findViewById(R.id.txtv_nombre_rcv_ListUsers);
            txtvApellido = itemView.findViewById(R.id.txtv_apellido_rcv_ListUsers);
            txtvCel = itemView.findViewById(R.id.txtv_cel_rcv_ListUsers);
            txtvEmail = itemView.findViewById(R.id.txtv_email_rcv_ListUsers);
            txtvFecha = itemView.findViewById(R.id.txtv_fechaR_rcv_ListUsers);
            txtvPos = itemView.findViewById(R.id.txtv_position_rcv_ListUsers);

            btnVerRsv = itemView.findViewById(R.id.btn_verRsv_rcv_ListUsers);

            //System.out.println("ViewHolder");
        }

    }

    @NonNull
    @Override
    public ListarUsers_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //seleccionar layout de plantilla
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_users, parent, false);
        System.out.println("onCreateViewHolder");
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ListarUsers_Adapter.ViewHolder holder, int position) {
        holder.txtNumero.setText(numerosList.get(position).toString());//borrar

        holder.txtvPos.setText(usuariosList.get(position).toString());


        //System.out.println("-->"+numerosList.get(position).toString());

    }

    @Override
    public int getItemCount() {
        //System.out.println("getItemCount");
        //return numerosList.size();
        return usuariosList.size();
    }
}
