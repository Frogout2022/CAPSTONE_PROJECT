package com.example.myproyect.actividades.clases.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.clases.Fecha;
import com.example.myproyect.actividades.entidades.Pago;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.modelos.DAO_Pago;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListarRsv_Adapter extends RecyclerView.Adapter<ListarRsv_Adapter.ViewHolder> {
    private List<Reserva> reserval;
    private Context context;
    private String nombre_tabla;
    private Pago pago = null;
    public ListarRsv_Adapter(List<Reserva> reserval, String nombreTabla, Context context) {
        this.reserval = reserval;
        this.nombre_tabla = nombreTabla;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtvDNI,txtvFecha,txtvHora,txtvEstado,txtvFechaR,txtvPos;
        Button btnVerPago;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtvDNI = itemView.findViewById(R.id.txtv_dni_rcv_ListRsv);
            txtvFecha = itemView.findViewById(R.id.txtv_fecha_rcv_ListRsv);
            txtvHora = itemView.findViewById(R.id.txtv_hora_rcv_ListRsv);
            txtvEstado = itemView.findViewById(R.id.txtv_estado_rcv_ListRsv);
            txtvPos = itemView.findViewById(R.id.txtv_position_rcv_ListRsv);


            img = itemView.findViewById(R.id.img_rcv_ListRsv);
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

        //String dni_cli = Login_Activity.getUsuario().getDNI();
        holder.txtvDNI.setText(reserval.get(position).getDni());
        //para vista del ADMIN recibir el DNI del cardview

        holder.txtvFecha.setText(reserval.get(position).getDia());
        holder.txtvHora.setText(reserval.get(position).getHora());

        int pos = position+1;
        holder.txtvPos.setText("#"+pos);
        //funcion estado
        String estado;
        if( Fecha.esFechaPasada(reserval.get(position).getDia().toString())){
            estado = "Concluido";
            holder.img.setImageResource(R.drawable.calendar_days_solid);
        }else{
            estado = "Vigente";
            holder.img.setImageResource(R.drawable.icon_reloj_arena);
        }


        holder.txtvEstado.setText(estado);

        holder.btnVerPago.setOnClickListener(view -> {

            String fecha = reserval.get(position).getDia() ;
            String hora = reserval.get(position).getHora();
            pago = DAO_Pago.consultarPago(fecha, hora);


            StringBuilder sb = new StringBuilder();
            if(pago==null){
                sb.append("Error al recuperar informacion del pago.");
            }else{
                String f = pago.getFechaPago();
                sb.append("Fecha de pago:  "+f.substring(0,16)+"\n");
                sb.append("Codigo de pago:  "+pago.getCodPago()+"\n");
                sb.append("Monto Total:  S/"+pago.getMontoTotal()+"\n");
                sb.append("igv:   S/"+pago.getIgvPago()+"\n");
                sb.append("Medio de pago:  "+pago.getMedioPago()+"\n");
            }

            new AlertDialog.Builder(context)
                    .setTitle("Informacion de pago:") // Opcional: Título del diálogo
                    .setMessage(sb) // Mensaje principal
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Acción al presionar "Aceptar"
                            //Toast.makeText(context, "Botón Aceptar presionado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setCancelable(false) // Impide cerrar tocando fuera del diálogo
                    .show(); // Muestra el diálogo
        });

    }

    @Override
    public int getItemCount() {
        return reserval.size();
    }
}
