package com.example.proyecto_final_iot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistorialSupervisorAdapter extends RecyclerView.Adapter<HistorialSupervisorAdapter.ViewHolder> {
    private List<HistorialData> historialList;

    public HistorialSupervisorAdapter(List<HistorialData> historialList) {
        this.historialList = historialList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_historial_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistorialData historialData = historialList.get(position);
        holder.activityNameTextView.setText(historialData.getActivityName());
        holder.supervisorNameTextView.setText(historialData.getSupervisorName());
        holder.dateActivityTextView.setText(historialData.getDate());
        holder.hourActivityTextView.setText(historialData.getHour());
    }

    @Override
    public int getItemCount() {
        return historialList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView activityNameTextView;
        TextView supervisorNameTextView;
        TextView dateActivityTextView;
        TextView hourActivityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            activityNameTextView = itemView.findViewById(R.id.textViewActivityName);
            supervisorNameTextView = itemView.findViewById(R.id.textViewSupervisorName);
            dateActivityTextView = itemView.findViewById(R.id.textViewDateActivity);
            hourActivityTextView = itemView.findViewById(R.id.textViewHourActivity);
        }
    }
}
