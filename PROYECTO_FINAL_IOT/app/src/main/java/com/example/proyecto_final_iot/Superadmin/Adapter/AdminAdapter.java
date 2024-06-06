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
import com.example.proyecto_final_iot.Superadmin.Data.Admin;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    private List<Admin> adminLists;
    private Context context; // Contexto para iniciar actividades

    public AdminAdapter(Context context, List<Admin> adminLists) {
        this.context = context;
        this.adminLists = adminLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Admin admin = adminLists.get(holder.getAdapterPosition());
        holder.NombreAdmin.setText(admin.getNombreCompleto());
        holder.hora.setText(admin.getHora());

        holder.myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llama al método para iniciar la actividad
                performAction(holder.getAdapterPosition());
            }
        });
    }

    private void performAction(int position) {
        // Inicia la actividad superadmin_detalles_administrador
        Intent intent = new Intent(context, superadmin_detalles_administrador.class);
        // Pasa el ID del administrador seleccionado a la actividad de detalles
        Admin selectedAdmin = adminLists.get(position);
        intent.putExtra("ADMIN_ID", selectedAdmin.getId());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return adminLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView NombreAdmin;
        TextView hora;
        Button myButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NombreAdmin = itemView.findViewById(R.id.NombreAdmin);
            hora = itemView.findViewById(R.id.hora);
            myButton = itemView.findViewById(R.id.button4);
        }
    }
}
