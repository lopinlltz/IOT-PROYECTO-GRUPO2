package com.example.proyecto_final_iot.Supervisor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Reporte;
import com.example.proyecto_final_iot.Supervisor.Entity.HistorialData;

import java.util.List;

public class ReporteAdapter extends RecyclerView.Adapter<ReporteAdapter.ViewHolder> {
    private List<Reporte> reporteList;

    public ReporteAdapter(List<Reporte> reporteList) {
        this.reporteList = reporteList;
    }

    @NonNull
    @Override
    public ReporteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_reporte_item, parent, false);
        return new ReporteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReporteAdapter.ViewHolder holder, int position) {
        Reporte historialData = reporteList.get(position);
        holder.activityNameTextView.setText(historialData.getNombre());
        holder.supervisorNameTextView.setText(historialData.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return reporteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView activityNameTextView;
        TextView supervisorNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            activityNameTextView = itemView.findViewById(R.id.textViewActivityName);
            supervisorNameTextView = itemView.findViewById(R.id.textViewSupervisorName);
        }
    }
}
