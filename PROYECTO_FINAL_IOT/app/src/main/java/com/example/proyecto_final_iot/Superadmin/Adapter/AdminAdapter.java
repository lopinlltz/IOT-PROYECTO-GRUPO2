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

import java.util.ArrayList;
import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    private List<Admin> adminList;
    private List<Admin> adminListFull;
    private Context context;

    public AdminAdapter(Context context, List<Admin> adminList) {
        this.context = context;
        this.adminList = adminList;
        this.adminListFull = new ArrayList<>(adminList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Admin admin = adminList.get(holder.getAdapterPosition());
        holder.NombreAdmin.setText(admin.getNombreUser());

        holder.myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, superadmin_detalles_administrador.class);
                intent.putExtra("ADMIN_ID", admin.getId()); // Asegúrate de que el ID se pasa aquí
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    public void setFilteredList(List<Admin> filteredList) {
        this.adminList = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView NombreAdmin;
        Button myButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NombreAdmin = itemView.findViewById(R.id.NombreAdmin);
            myButton = itemView.findViewById(R.id.button4);
        }
    }
}
