package com.example.proyecto_final_iot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
