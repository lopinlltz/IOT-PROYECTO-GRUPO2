package com.example.proyecto_final_iot.Superadmin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;
import com.example.proyecto_final_iot.Superadmin.Data.Supervisor;

import java.util.List;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.SitioData;
import com.example.proyecto_final_iot.Superadmin.Adapter.AdminAdapter;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;
import com.example.proyecto_final_iot.Supervisor.Adapter.SitioSupervisorAdapter;

import java.util.ArrayList;
import java.util.List;

public class SupervisorAdapter extends RecyclerView.Adapter<SupervisorAdapter.ViewHolder> {
    private List<Supervisor> supervisorList;
    public SupervisorAdapter(List<Supervisor> supervisorList) {
        this.supervisorList = supervisorList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_item, parent, false);
        return new SupervisorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupervisorAdapter.ViewHolder holder, int position) {
        Supervisor supervisor = supervisorList.get(position);
        holder.NombreSupervisor.setText(supervisor.getNombreSupervisor());
        holder.hora.setText(supervisor.getHora());

    }

    @Override
    public int getItemCount() {
        return supervisorList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView NombreSupervisor;
        TextView hora;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NombreSupervisor = itemView.findViewById(R.id.NombreSupervisor);
            hora = itemView.findViewById(R.id.hora);
        }
    }
}
