package com.example.proyecto_final_iot.Administrador.Adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.Administrador.Data.Usuario_data;
import com.example.proyecto_final_iot.R;

import java.util.List;

public class UsuarioListAdapter extends RecyclerView.Adapter<UsuarioListAdapter.ViewHolder> {

    private List<Usuario_data> usuario_dataList;

    public UsuarioListAdapter(List<Usuario_data> usuario_dataList) {
        this.usuario_dataList = usuario_dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_usuario_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioListAdapter.ViewHolder holder, int position) {
        Usuario_data usuario_data = usuario_dataList.get(position);
        holder.NombreUsuario_admin.setText(usuario_data.getNombreUsuarioAdmin());
    }

    @Override
    public int getItemCount() {return usuario_dataList.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView NombreUsuario_admin;


        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            NombreUsuario_admin = itemView.findViewById(R.id.NombreUsuario_admin);



        }


    }
}
