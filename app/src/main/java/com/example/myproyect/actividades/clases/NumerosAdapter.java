package com.example.myproyect.actividades.clases;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproyect.R;

import org.w3c.dom.Text;

import java.util.List;

public class NumerosAdapter extends RecyclerView.Adapter<NumerosAdapter.ViewHolder> {

    List<Integer> numerosList;
    public NumerosAdapter(List<Integer> numerosList){
        this.numerosList = numerosList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNumero;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNumero = itemView.findViewById(R.id.txtvNum);
        }
    }

    @NonNull
    @Override
    public NumerosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vissta = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_users_adm, parent, false);
        return new ViewHolder(vissta);
    }

    @Override
    public void onBindViewHolder(@NonNull NumerosAdapter.ViewHolder holder, int position) {

        holder.txtNumero.setText(numerosList.get(position).toString());

    }

    @Override
    public int getItemCount() {


        return numerosList.size();
    }
}
