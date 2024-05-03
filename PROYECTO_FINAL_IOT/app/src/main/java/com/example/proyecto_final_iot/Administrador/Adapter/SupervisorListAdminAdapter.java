package com.example.proyecto_final_iot.Administrador.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.R;

import java.util.ArrayList;
import java.util.List;

public class SupervisorListAdminAdapter extends RecyclerView.Adapter<SupervisorListAdminAdapter.ViewHolder> {

    private List<Supervisor_Data> supervisor_dataList;

    List<String> supervisorList_0 = new ArrayList<>();
    QuantityListener quantityListener;


    public SupervisorListAdminAdapter(List<Supervisor_Data>  supervisor_dataList, QuantityListener quantityListener) {
        this.supervisor_dataList = supervisor_dataList;
        this.quantityListener = quantityListener;
    }


    @NonNull
    @Override
    public SupervisorListAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_supervisorlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupervisorListAdminAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Supervisor_Data supervisor_data = supervisor_dataList.get(position);
        holder.nombreSupervisorAdmin.setText(supervisor_data.getNombreSupervisorAdmin());

        if (supervisor_dataList != null && supervisor_dataList.size() >0){

            holder.check_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.check_box.isChecked()){

                        supervisorList_0.add(String.valueOf(supervisor_dataList.get(position)));
                    }else{
                        supervisorList_0.remove(supervisor_dataList.get(position));
                    }
                    quantityListener.onQuantityChange(supervisorList_0);
                }
            });


        }
    }

    @Override
    public int getItemCount() { return supervisor_dataList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreSupervisorAdmin;
        CheckBox check_box;

        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            nombreSupervisorAdmin = itemView.findViewById(R.id.NombreSupervisor_admin);
            check_box =itemView.findViewById(R.id.check_box);

        }
    }

    public interface QuantityListener{
        void onQuantityChange(List<String> supervisor_List);

    }
}
