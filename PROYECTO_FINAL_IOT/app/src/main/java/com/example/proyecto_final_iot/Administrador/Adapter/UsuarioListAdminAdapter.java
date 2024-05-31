package com.example.proyecto_final_iot.Administrador.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Activity.Admin_usuario_detalles;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.R;

import java.util.ArrayList;
import java.util.List;

public class UsuarioListAdminAdapter extends RecyclerView.Adapter<UsuarioListAdminAdapter.ViewHolder> {

    private List<Supervisor_Data> supervisor_dataList;

    List<String> supervisorList_0 = new ArrayList<>();
    private OnItemClickListener mListenerlistenerAdmin;

    public UsuarioListAdminAdapter(List<Supervisor_Data>  supervisor_dataList) {
        this.supervisor_dataList = supervisor_dataList;

    }
    public interface OnItemClickListener {
        void onReportButtonClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listenerAdmin) {
        mListenerlistenerAdmin =  listenerAdmin;
    }

    public void setFilteredList(List<Supervisor_Data> filteredList){
        this.supervisor_dataList = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UsuarioListAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_usuario_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioListAdminAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Supervisor_Data supervisor_data = supervisor_dataList.get(position);
        holder.nombre_user_list.setText(supervisor_data.getId_nombreUser());
       /* holder.id_apellidoUser.setText(supervisor_data.getId_apellidoUser());
        holder.id_dniUSer.setText(supervisor_data.getId_dniUSer());
        holder.id_correoUser.setText(supervisor_data.getId_correoUser());
        holder.id_telefonoUser.setText(supervisor_data.getId_telefonoUser());
        holder.id_domicilioUser.setText(supervisor_data.getId_domicilioUser());*/

        /*holder.bottom_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListenerlistenerAdmin != null) {
                    mListenerlistenerAdmin.onReportButtonClick(position);
                }
            }
        });*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Admin_usuario_detalles.class);
                intent.putExtra("id_nombreUser", supervisor_data.getId_nombreUser());
                intent.putExtra("id_apellidoUser", supervisor_data.getId_apellidoUser());
                intent.putExtra("id_dniUSer", supervisor_data.getId_dniUSer());
                intent.putExtra("id_correoUser", supervisor_data.getId_correoUser());
                intent.putExtra("id_telefonoUser", supervisor_data.getId_telefonoUser());
                intent.putExtra("id_domicilioUser", supervisor_data.getId_domicilioUser());


                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() { return supervisor_dataList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre_user_list;
        CheckBox check_box;
        ImageButton bottom_user_info;

        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            nombre_user_list = itemView.findViewById(R.id.item_id_nombreUser);
            check_box =itemView.findViewById(R.id.check_box);

           // bottom_user_info = itemView.findViewById(R.id.bottom_user_info);

        }
    }

}
