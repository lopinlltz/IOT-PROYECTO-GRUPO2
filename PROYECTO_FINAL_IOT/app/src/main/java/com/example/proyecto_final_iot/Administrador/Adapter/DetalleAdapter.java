package com.example.proyecto_final_iot.Administrador.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;

public class DetalleAdapter extends RecyclerView.Adapter<DetalleAdapter.ViewHolder> {

    @NonNull
    @Override
    public DetalleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_admin_sitio_detalles,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextureView id_codigodeSitio;
        TextureView id_departamento;
        TextureView id_provincia;
        TextureView id_distrito;
        TextureView id_ubigeo;
        TextureView id_tipo_de_zona;
        TextureView id_tipo_de_sitio;
        TextureView id_latitud_long;


        @SuppressLint("WrongViewCast")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_codigodeSitio = itemView.findViewById(R.id.id_codigodeSitio);
            id_departamento = itemView.findViewById(R.id.id_departamento);
            id_provincia = itemView.findViewById(R.id.id_provincia);
            id_distrito = itemView.findViewById(R.id.id_distrito);
            id_ubigeo = itemView.findViewById(R.id.id_ubigeo);
            //id_tipo_de_zona = itemView.findViewById(R.id.id_tipo_de_zona);
            //id_tipo_de_sitio = itemView.findViewById(R.id.id_tipo_de_sitio);
            id_latitud_long = itemView.findViewById(R.id.id_latitud_long);
        }
    }
}
