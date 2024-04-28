package com.example.proyecto_final_iot;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EquipoSupervisorAdapter extends RecyclerView.Adapter<EquipoSupervisorAdapter.ViewHolder> {
    private List<EquipoData> equipoList;

    public EquipoSupervisorAdapter(List<EquipoData> equipoList) {
        this.equipoList = equipoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_equipo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EquipoData equipoData = equipoList.get(position);
        holder.equipmentNameTextView.setText(equipoData.getEquipmentName());
        holder.eqTypeTextView.setText(equipoData.getTypeEq());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HistorialSupervisorActivity.class);
                intent.putExtra("equipment_name", equipoData.getEquipmentName());
                intent.putExtra("type_eq", equipoData.getTypeEq());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return equipoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView equipmentNameTextView;
        TextView eqTypeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            equipmentNameTextView = itemView.findViewById(R.id.item_equipment_name);
            eqTypeTextView = itemView.findViewById(R.id.item_type);
        }
    }
}