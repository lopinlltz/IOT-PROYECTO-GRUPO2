package com.example.proyecto_final_iot.Superadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Activity.superadmin_detalles_administrador;
import com.example.proyecto_final_iot.Superadmin.Data.Supervisor;

import java.util.List;

public class SupervisorAdapter extends RecyclerView.Adapter<SupervisorAdapter.ViewHolder> {
    private List<Supervisor> supervisorList;
    private Context context;
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
        holder.myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, superadmin_detalles_administrador.class);
                intent.putExtra("ADMIN_ID", supervisor.getId()); // Asegúrate de que el ID se pasa aquí
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return supervisorList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView NombreSupervisor;
        Button myButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NombreSupervisor = itemView.findViewById(R.id.NombreSupervisor);
            myButton = itemView.findViewById(R.id.button4);
        }
    }
}
