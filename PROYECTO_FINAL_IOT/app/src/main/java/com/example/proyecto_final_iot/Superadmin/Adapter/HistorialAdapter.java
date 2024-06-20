package com.example.proyecto_final_iot.Superadmin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.HistorialData;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> {
    private List<HistorialData> historialList;

    public HistorialAdapter(List<HistorialData> historialList) {
        this.historialList = historialList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historial_item_superadmin_item1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistorialData historialData = historialList.get(position);
        holder.activityNameTextView.setText(historialData.getActivityName());
        holder.userNameTextView.setText(historialData.getUserName());
        holder.dateActivityTextView.setText(historialData.getDate());
        holder.hourActivityTextView.setText(historialData.getHour());
        holder.roleTextView.setText(historialData.getUserRole());
    }

    @Override
    public int getItemCount() {
        return historialList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView activityNameTextView;
        TextView userNameTextView;
        TextView dateActivityTextView;
        TextView hourActivityTextView;
        TextView roleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            activityNameTextView = itemView.findViewById(R.id.textViewActivityName);
            userNameTextView = itemView.findViewById(R.id.usuariodinosaurio);
            dateActivityTextView = itemView.findViewById(R.id.textViewDateActivity);
            hourActivityTextView = itemView.findViewById(R.id.textViewHourActivity);
            roleTextView = itemView.findViewById(R.id.idrol);
        }
    }
}
