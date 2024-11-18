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

import java.util.ArrayList;
import java.util.List;

public class ListarUsers_Adapter extends RecyclerView.Adapter<ListarUsers_Adapter.ViewHolder> {

    List<Integer> numerosList;
    ArrayList<Usuario> usuariosList;


    public ListarUsers_Adapter(ArrayList<Usuario> usuariosList){
        this.usuariosList = usuariosList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtvDNI,txtvNombre,txtvApellido,txtvCel,txtvEmail,txtvFecha, txtvPos;
        Button btnVerRsv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtvDNI = itemView.findViewById(R.id.txtv_dni_rcv_ListUsers);
            txtvNombre = itemView.findViewById(R.id.txtv_nombre_rcv_ListUsers);
            txtvApellido = itemView.findViewById(R.id.txtv_apellido_rcv_ListUsers);
            txtvCel = itemView.findViewById(R.id.txtv_cel_rcv_ListUsers);
            txtvEmail = itemView.findViewById(R.id.txtv_email_rcv_ListUsers);
            txtvFecha = itemView.findViewById(R.id.txtv_fechaR_rcv_ListUsers);
            txtvPos = itemView.findViewById(R.id.txtv_position_rcv_ListUsers);

            btnVerRsv = itemView.findViewById(R.id.btn_verRsv_rcv_ListUsers);

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
        //rellenar todos los datos
        holder.txtvDNI.setText(usuariosList.get(position).getDNI().toString());
        holder.txtvNombre.setText(usuariosList.get(position).getNombre().toString());
        holder.txtvApellido.setText(usuariosList.get(position).getApellido().toString());
        holder.txtvCel.setText(usuariosList.get(position).getCelular());
        holder.txtvEmail.setText(usuariosList.get(position).getCorreo());
        String hora_registro = usuariosList.get(position).getFecha_registro();
        holder.txtvFecha.setText(hora_registro.substring(0,10)+" "+hora_registro.substring(11,16));


        int pos = position+1;
        holder.txtvPos.setText("#"+pos);

        //System.out.println("-->"+numerosList.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return usuariosList.size();
    }
}
