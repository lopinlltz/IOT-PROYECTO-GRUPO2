package com.example.proyecto_final_iot.Administrador.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.Administrador.Activity.Admin_lista_Sitio;
import com.example.proyecto_final_iot.Administrador.Activity.Admin_sitio_detalles;
import com.example.proyecto_final_iot.Administrador.Activity.Admin_sitio_editar;
import com.example.proyecto_final_iot.Administrador.Activity.Admin_usuario_detalles;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UsuarioSelectAdapter  extends RecyclerView.Adapter<UsuarioSelectAdapter.ViewHolder> {

    private List<Supervisor_Data> supervisor_Select_dataList;
    private  int checkedPosition = 0;
    private Context context;

    private OnSupervisorSelectedListener mListener;

    public interface OnSupervisorSelectedListener {
        void onSupervisorSelected(String supervisorName, String supervisorImage);
    }

    public void setOnSupervisorSelectedListener(OnSupervisorSelectedListener listener) {
        this.mListener = listener;
    }

    public UsuarioSelectAdapter(Context context, List<Supervisor_Data> supervisor_Select_dataList) {
        this.context = context;
        this.supervisor_Select_dataList = supervisor_Select_dataList;
    }
    public void SetSupervisor(List<Supervisor_Data> supervisor_Select_dataList){
        this.supervisor_Select_dataList = supervisor_Select_dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsuarioSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_usuario_select_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioSelectAdapter.ViewHolder holder, int position) {

        Supervisor_Data supervisorData = supervisor_Select_dataList.get(position);
        holder.bind(supervisorData);
        holder.item_id_status_admin.setText(supervisorData.getStatus_admin());

        Glide.with(holder.image_select_user.getContext())
                .load(supervisorData.getDataImage())
                .into(holder.image_select_user);

    }

    @Override
    public int getItemCount() {
        return supervisor_Select_dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_nombreUser_select_item, item_id_status_admin;
        ImageView image_select_user;
        ImageView check_super_select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_nombreUser_select_item = itemView.findViewById(R.id.id_nombreUser_select_item);
            image_select_user = itemView.findViewById(R.id.recImage);
            check_super_select = itemView.findViewById(R.id.check_super_select);
            item_id_status_admin = itemView.findViewById(R.id.item_id_status_admin);
        }
        void bind(final Supervisor_Data supervisorData){

            // Establece el nombre del supervisor
            id_nombreUser_select_item.setText(supervisorData.getId_nombreUser());


            // Actualiza la visibilidad de check_super_select basado en la posición
            if (checkedPosition == getAdapterPosition()) {
                check_super_select.setVisibility(View.VISIBLE);
            } else {
                check_super_select.setVisibility(View.GONE);
            }

            // Configura el click listener para actualizar la selección
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                        notifyItemChanged(checkedPosition);
                    }
                }
            });
        }
}

    public Supervisor_Data getSelected(){
        if (checkedPosition != -1) {
            return supervisor_Select_dataList.get(checkedPosition);
        }
        return  null;
    }
}