package com.example.proyecto_final_iot.Administrador.Adapter;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.Administrador.Activity.Admin_lista_usuario;
import com.example.proyecto_final_iot.Administrador.Activity.Admin_usuario_detalles;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.R;
import com.google.android.material.transition.Hold;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsuarioListAdminAdapter extends RecyclerView.Adapter<UsuarioListAdminAdapter.ViewHolder> {

    private List<Supervisor_Data> supervisor_dataList;
    private OnItemClickListener mListenerlistenerAdmin;

    List<String> supervisorList_0 = new ArrayList<>();


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
        holder.textViewEstado_admin.setText(supervisor_data.getStatus_admin());


        Glide.with(holder.list_item_imagen_user.getContext()).load(supervisor_data.getDataImage()).into(holder.list_item_imagen_user);

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
                intent.putExtra("dataImage", supervisor_data.getDataImage());
                intent.putExtra("textViewEstado_admin", supervisor_data.getStatus_admin());
                intent.putExtra("usuarioId", position);

                Log.d("Debug", "usuarioID : " + position);

                v.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() { return supervisor_dataList.size();
    }
    public void searchDataList(ArrayList<Supervisor_Data> searchList){
        supervisor_dataList = searchList;
        notifyDataSetChanged();

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre_user_list, textViewEstado_admin;
        ImageView list_item_imagen_user ;

        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            nombre_user_list = itemView.findViewById(R.id.item_id_nombreUser);
            list_item_imagen_user = itemView.findViewById(R.id.list_item_imagen_user);
            textViewEstado_admin = itemView.findViewById(R.id.item_id_status_admin);


        }
    }

    public void actualizarEstadoUsuario(int position, String nuevoEstado) {
        supervisor_dataList.get(position).setStatus_admin(nuevoEstado);
        notifyItemChanged(position);
    }

}
