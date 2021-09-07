package com.example.tankeo10.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankeo10.Entidades.Movimientos;
import com.example.tankeo10.Entidades.Recargas;
import com.example.tankeo10.R;

import java.util.ArrayList;

public class AdapterRecarga extends RecyclerView.Adapter<AdapterRecarga.ViewHolder> {

    LayoutInflater inflater;
    ArrayList<Recargas> model;

    public AdapterRecarga(Context context, ArrayList<Recargas> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_recargas, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String fecha = model.get(position).getFecha();
        String tipo = model.get(position).getTipo();
        String valor = model.get(position).getValor();


        holder.fecha.setText(fecha);
        holder.tipo.setText(tipo);
        holder.valor.setText(valor);


    }


    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView fecha,tipo,valor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fecha = itemView.findViewById(R.id.fechaRecTxt);
            tipo = itemView.findViewById(R.id.tipoRecTxt);
            valor = itemView.findViewById(R.id.valorRecTxt);

        }
    }
}
