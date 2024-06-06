package com.example.proyecto_final_iot.Superadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Activity.superadmin_detalles_administrador;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> implements Filterable {
    private List<Admin> adminList;
    private List<Admin> adminListFull; // Lista completa para soporte de búsqueda
    private Context context;

    public AdminAdapter(Context context, List<Admin> adminList) {
        this.context = context;
        this.adminList = adminList;
        adminListFull = new ArrayList<>(adminList); // Copia de la lista completa
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
        holder.NombreAdmin.setText(admin.getNombreCompleto());
        holder.hora.setText(admin.getHora());

        holder.myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAction(holder.getAdapterPosition());
            }
        });
    }

    private void performAction(int position) {
        Intent intent = new Intent(context, superadmin_detalles_administrador.class);
        Admin selectedAdmin = adminList.get(position);
        intent.putExtra("ADMIN_ID", selectedAdmin.getId());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    @Override
    public Filter getFilter() {
        return adminFilter;
    }

    private Filter adminFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Admin> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(adminListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Admin admin : adminListFull) {
                    if (admin.getNombreUser().toLowerCase().contains(filterPattern)) {
                        filteredList.add(admin);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adminList.clear();
            adminList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

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
