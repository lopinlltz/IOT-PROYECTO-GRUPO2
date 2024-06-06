package com.example.proyecto_final_iot.Supervisor.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Supervisor.Entity.EquipoData;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Activity.EquipoDetalleActivity;
import com.example.proyecto_final_iot.Supervisor.Entity.SitioData;

import java.util.List;

public class EquipoSupervisorAdapter extends RecyclerView.Adapter<EquipoSupervisorAdapter.ViewHolder> {
    private List<EquipoData> equipoList;

    public interface OnItemClickListener {
        void onReportButtonClick(int position, EquipoData equipo);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setFilteredList_sitio(List<EquipoData> filteredList_sitio) {
        this.equipoList = filteredList_sitio;
        notifyDataSetChanged();
    }
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
        holder.equipmentNameTextView.setText(equipoData.getModelo());
        holder.eqTypeTextView.setText(equipoData.getMarca());

        holder.imageButtonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onReportButtonClick(position, equipoData);
                }
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EquipoDetalleActivity.class);
                intent.putExtra("modelo", equipoData.getModelo());
                intent.putExtra("marca", equipoData.getMarca());
                intent.putExtra("sku", equipoData.getSku());
                intent.putExtra("serie", equipoData.getSerie());
                intent.putExtra("descripcion", equipoData.getDescripcion());
                intent.putExtra("fecha", equipoData.getFechaRegistro());
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
        ImageButton imageButtonReport;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            equipmentNameTextView = itemView.findViewById(R.id.item_equipment_name);
            eqTypeTextView = itemView.findViewById(R.id.item_type);
            imageButtonReport = itemView.findViewById(R.id.imageButtonReport);
        }
    }
}