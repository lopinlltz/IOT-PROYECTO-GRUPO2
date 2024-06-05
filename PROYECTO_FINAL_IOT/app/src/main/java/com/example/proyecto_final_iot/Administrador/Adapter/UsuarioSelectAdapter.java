package com.example.proyecto_final_iot.Administrador.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsuarioSelectAdapter  extends RecyclerView.Adapter<UsuarioSelectAdapter.ViewHolder> {

    private List<Supervisor_Data> supervisor_Select_dataList;

    public UsuarioSelectAdapter(List<Supervisor_Data> supervisor_Select_dataList) {
        this.supervisor_Select_dataList = supervisor_Select_dataList;
    }

    @NonNull
    @Override
    public UsuarioSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_usuario_select_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioSelectAdapter.ViewHolder holder, int position) {
        Supervisor_Data supervisor_data = supervisor_Select_dataList.get(position);
        holder.id_nombreUser_select_item.setText(supervisor_data.getId_nombreUser());

        Glide.with(holder.image_select_user.getContext()).load(supervisor_data.getDataImage()).into(holder.image_select_user);
        Picasso.get().load(supervisor_data.getDataImage()).into(holder.image_select_user);
    }

    @Override
    public int getItemCount() {
        return supervisor_Select_dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_nombreUser_select_item;
        ImageView image_select_user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_nombreUser_select_item = itemView.findViewById(R.id.id_nombreUser_select_item);
            image_select_user = itemView.findViewById(R.id.recImage);
        }
    }
}
