package com.example.proyecto_final_iot.Supervisor.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.SitioData;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioDetalleActivity;

import java.util.List;

public class SitioSupervisorAdapter extends RecyclerView.Adapter<SitioSupervisorAdapter.ViewHolder> {
    private List<SitioData> sitioList;

    public SitioSupervisorAdapter(List<SitioData> sitioList) {
        this.sitioList = sitioList;
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
        holder.siteNameTextView.setText(sitioData.getSiteName());
        holder.locationTextView.setText(sitioData.getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SitioDetalleActivity.class);
                intent.putExtra("site_name", sitioData.getSiteName());
                intent.putExtra("location", sitioData.getLocation());
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
