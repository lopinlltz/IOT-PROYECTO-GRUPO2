package com.example.proyecto_final_iot.Superadmin.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Activity.DetallesSupervisorSuperadmin;

import java.util.List;

public class UsuarioListAdminAdapter extends RecyclerView.Adapter<UsuarioListAdminAdapter.ViewHolder> {

    private List<Supervisor_Data> supervisor_dataList;
    private OnItemClickListener mListenerlistenerAdmin;

    public UsuarioListAdminAdapter(List<Supervisor_Data> supervisor_dataList) {
        this.supervisor_dataList = supervisor_dataList;
    }

    public interface OnItemClickListener {
        void onReportButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listenerAdmin) {
        mListenerlistenerAdmin = listenerAdmin;
    }

    public void setFilteredList(List<Supervisor_Data> filteredList) {
        this.supervisor_dataList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Supervisor_Data supervisor_data = supervisor_dataList.get(position);
        holder.nombre_user_list.setText(supervisor_data.getId_nombreUser());
        holder.textViewEstado_admin.setText(supervisor_data.getStatus_admin());

        Glide.with(holder.list_item_imagen_user.getContext()).load(supervisor_data.getDataImage()).into(holder.list_item_imagen_user);

        View.OnClickListener detailsClickListener = v -> {
            Intent intent = new Intent(v.getContext(), DetallesSupervisorSuperadmin.class);
            intent.putExtra("id_nombreUser", supervisor_data.getId_nombreUser());
            intent.putExtra("id_apellidoUser", supervisor_data.getId_apellidoUser());
            intent.putExtra("id_dniUSer", supervisor_data.getId_dniUSer());
            intent.putExtra("id_correoUser", supervisor_data.getId_correoUser());
            intent.putExtra("id_telefonoUser", supervisor_data.getId_telefonoUser());
            intent.putExtra("id_domicilioUser", supervisor_data.getId_domicilioUser());
            intent.putExtra("dataImage", supervisor_data.getDataImage());
            intent.putExtra("textViewEstado_admin", supervisor_data.getStatus_admin());
            v.getContext().startActivity(intent);
        };

        holder.list_item_imagen_user.setOnClickListener(detailsClickListener);
        holder.button4.setOnClickListener(detailsClickListener);
    }

    @Override
    public int getItemCount() {
        return supervisor_dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre_user_list, textViewEstado_admin;
        ImageView list_item_imagen_user;
        Button button4; // Añade el botón aquí

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_user_list = itemView.findViewById(R.id.NombreSupervisor);
            list_item_imagen_user = itemView.findViewById(R.id.imageView16);
            textViewEstado_admin = itemView.findViewById(R.id.item_id_status_admin2);
            button4 = itemView.findViewById(R.id.button4); // Inicializa el botón aquí
        }
    }
}
