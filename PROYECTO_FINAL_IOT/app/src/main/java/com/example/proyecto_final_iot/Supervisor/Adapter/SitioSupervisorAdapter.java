package com.example.proyecto_final_iot.Supervisor.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.SitioData;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioDetalleActivity;

import java.util.List;

public class SitioSupervisorAdapter extends RecyclerView.Adapter<SitioSupervisorAdapter.ViewHolder> {
    private List<SitioData> sitioList;

    public SitioSupervisorAdapter(List<SitioData> sitioList) {
        this.sitioList = sitioList;
    }

    public void setFilteredList_sitio(List<SitioData> filteredList_sitio) {
        this.sitioList = filteredList_sitio;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_sitio_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SitioData sitioData = sitioList.get(position);
        holder.siteNameTextView.setText(sitioData.getId_codigodeSitio());
        holder.locationTextView.setText(sitioData.getId_provincia());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SitioDetalleActivity.class);
                intent.putExtra("site_name", sitioData.getId_codigodeSitio());
                intent.putExtra("ubicacion", sitioData.getId_provincia());
                intent.putExtra("departamento", sitioData.getId_departamento());
                intent.putExtra("provincia", sitioData.getId_provincia());
                intent.putExtra("distrito", sitioData.getId_distrito());
                intent.putExtra("ubigeo", sitioData.getId_ubigeo());
                intent.putExtra("latitud_longitud", sitioData.getId_latitud_long());
                //intent.putExtra("tipo_zona", sitioData.getId_tipo_de_zona());
                //intent.putExtra("tipo_sitio", sitioData.getId_tipo_de_sitio());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sitioList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView siteNameTextView;
        TextView locationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            siteNameTextView = itemView.findViewById(R.id.item_site_name);
            locationTextView = itemView.findViewById(R.id.item_location);
        }
    }
}