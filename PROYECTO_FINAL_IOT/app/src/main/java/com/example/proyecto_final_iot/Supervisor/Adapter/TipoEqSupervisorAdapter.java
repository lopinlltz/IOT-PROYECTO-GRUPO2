package com.example.proyecto_final_iot.Supervisor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.TipoEqData;

import java.util.List;

public class TipoEqSupervisorAdapter extends RecyclerView.Adapter<TipoEqSupervisorAdapter.ViewHolder> {
    private List<TipoEqData> tipoEquipoList;

    public TipoEqSupervisorAdapter(List<TipoEqData> tipoEquipoList) {
        this.tipoEquipoList = tipoEquipoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_tipoequipo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TipoEqData tipoEqData = tipoEquipoList.get(position);
        holder.typeEquipmentNameTextView.setText(tipoEqData.getTypeName());

    }

    @Override
    public int getItemCount() {
        return tipoEquipoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeEquipmentNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeEquipmentNameTextView = itemView.findViewById(R.id.item_type_name);
        }
    }
}

