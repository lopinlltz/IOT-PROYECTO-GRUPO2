package com.example.proyecto_final_iot.Administrador.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Activity.Admin_sitio_detalles;
import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.Administrador.Data.Usuario_data;
import com.example.proyecto_final_iot.R;

import java.util.List;

public class SitioAdminAdapter extends RecyclerView.Adapter<SitioAdminAdapter.ViewHolder> {


    private List<Sitio_Data> sitio_dataList;
    private OnItemClickListener mListenerlistenerAdmin;
    private OnItemClickListener2 mListenerlistenerAdmin2;

    Context context;


    public void setFilteredList_sitio(List<Sitio_Data> filteredList_sitio) {
        this.sitio_dataList = filteredList_sitio;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onReportButtonClick(int position);
    }

    public interface OnItemClickListener2 {
        void onReportButtonClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listenerAdmin) {
        mListenerlistenerAdmin = listenerAdmin;
    }

    public void setOnItemClickListener(OnItemClickListener2 listenerAdmin2) {
        mListenerlistenerAdmin2 = listenerAdmin2;
    }

    public SitioAdminAdapter(List<Sitio_Data> sitio_dataList) {
        this.sitio_dataList = sitio_dataList;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_sitio_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SitioAdminAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Sitio_Data sitio_data = sitio_dataList.get(position);
        holder.codigo.setText(sitio_data.getId_codigodeSitio());

        holder.imageButton_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListenerlistenerAdmin != null) {
                    mListenerlistenerAdmin.onReportButtonClick(position);
                }
            }
        });

        holder.imageButton_supervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListenerlistenerAdmin2 != null) {
                    mListenerlistenerAdmin2.onReportButtonClick(position);
                }
            }
        });

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, Admin_sitio_detalles.class);
            intent.putExtra("id_codigodeSitio", sitio_data.getId_codigodeSitio());
            intent.putExtra("id_departamento", sitio_data.getId_departamento());
            intent.putExtra("id_provincia", sitio_data.getId_provincia());
            intent.putExtra("id_distrito", sitio_data.getId_distrito());
            intent.putExtra("id_ubigeo", sitio_data.getId_ubigeo());
            intent.putExtra("id_latitud_long", sitio_data.getId_latitud_long());

            context.startActivity(intent);

        });
    }

        @Override
        public int getItemCount () {
            return sitio_dataList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView codigo;
            ImageButton imageButton_info;
            ImageButton imageButton_supervisor;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                codigo = itemView.findViewById(R.id.CodigoSitio_admin);
                imageButton_info = itemView.findViewById(R.id.bottom_lista_info);
                imageButton_supervisor = itemView.findViewById(R.id.bottom_admin_supervisor);

            }


        }



}
