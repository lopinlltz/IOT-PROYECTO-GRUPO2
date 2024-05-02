package com.example.proyecto_final_iot.Superadmin.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.HistorialData;
import com.example.proyecto_final_iot.HistorialSupervisorAdapter;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.Logs;

import java.util.List;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.ViewHolder>{
    private List<Logs> historialList;
    public LogsAdapter(List<Logs> historialList) {
        this.historialList = historialList;
    }
    @NonNull
    @Override
    public LogsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historial_item_superadmin_item1, parent, false);
        return new LogsAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull LogsAdapter.ViewHolder holder, int position) {
        Logs logs = historialList.get(position);
        holder.activityNameTextView.setText(logs.getActivityName());
        holder.supervisorNameTextView.setText(logs.getSupervisorName());
        holder.dateActivityTextView.setText(logs.getDate());
        holder.hourActivityTextView.setText(logs.getHour());
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
