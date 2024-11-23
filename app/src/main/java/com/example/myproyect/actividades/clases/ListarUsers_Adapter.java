package com.example.myproyect.actividades.clases;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.CargaActivity;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.entidades.Pago;
import com.example.myproyect.actividades.entidades.Usuario;
import com.example.myproyect.actividades.modelos.DAO_Pago;

import java.util.ArrayList;
import java.util.List;

public class ListarUsers_Adapter extends RecyclerView.Adapter<ListarUsers_Adapter.ViewHolder> {

    private ArrayList<Usuario> usuariosList;
    private int cantidad;
    private Context context;


    public ListarUsers_Adapter(ArrayList<Usuario> usuariosList){
        this.usuariosList = usuariosList;
    }
    public ListarUsers_Adapter(ArrayList<Usuario> usuariosList, int cantidad){
        this.usuariosList = usuariosList;
        this.cantidad = cantidad;
    }
    public ListarUsers_Adapter(ArrayList<Usuario> usuariosList, int cantidad, Context context){
        this.usuariosList = usuariosList;
        this.cantidad = cantidad;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtvDNI,txtvNombre,txtvApellido,txtvCel,txtvEmail,txtvFecha, txtvPos;
        Button btnVerRsv;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Actualizar y comprobar los ID
            txtvDNI = itemView.findViewById(R.id.txtv_dni_rcv_ListUsers);
            txtvNombre = itemView.findViewById(R.id.txtv_nombre_rcv_ListUsers);
            txtvApellido = itemView.findViewById(R.id.txtv_apellido_rcv_ListUsers);
            txtvCel = itemView.findViewById(R.id.txtv_cel_rcv_ListUsers);
            txtvEmail = itemView.findViewById(R.id.txtv_email_rcv_ListUsers);
            txtvFecha = itemView.findViewById(R.id.txtv_fechaR_rcv_ListUsers);
            txtvPos = itemView.findViewById(R.id.txtv_position_rcv_ListUsers);
            img = itemView.findViewById(R.id.img_rcv_ListRsv);

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

        holder.btnVerRsv.setText("VER RESERVAS ("+cantidad+")");

        holder.btnVerRsv.setOnClickListener(view -> {
            mosrtrarPago();

        });

        //System.out.println("-->"+numerosList.get(position).toString());

    }
    private void mosrtrarPago(){
        Pago pago = DAO_Pago.consultarPago(Login_Activity.getUsuario().getDNI());
        new AlertDialog.Builder(context)
                .setTitle("Informacion de pago:") // Opcional: Título del diálogo
                .setMessage("Este es el mensaje del diálogo.") // Mensaje principal
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acción al presionar "Aceptar"
                        //Toast.makeText(context, "Botón Aceptar presionado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false) // Impide cerrar tocando fuera del diálogo
                .show(); // Muestra el diálogo


    }

    @Override
    public int getItemCount() {
        return usuariosList.size();
    }
}
