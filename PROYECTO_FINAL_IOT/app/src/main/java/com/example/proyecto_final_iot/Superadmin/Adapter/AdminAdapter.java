package com.example.proyecto_final_iot.Superadmin.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    private List<Admin> adminLists;
    public AdminAdapter(List<Admin> adminLists) {
        this.adminLists = adminLists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item, parent, false);
        return new AdminAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.ViewHolder holder, int position) {
        Admin admin = adminLists.get(position);
        holder.NombreAdmin.setText(admin.getNombreAdmin());
        holder.hora.setText(admin.getHora());

    }

    @Override
    public int getItemCount() {
        return adminLists.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView NombreAdmin;
        TextView hora;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NombreAdmin = itemView.findViewById(R.id.NombreAdmin);
            hora = itemView.findViewById(R.id.hora);
        }
    }
}
