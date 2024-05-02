package com.example.proyecto_final_iot.Administrador.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.R;

import java.util.List;

public class SitioAdminAdapter extends RecyclerView.Adapter<SitioAdminAdapter.ViewHolder> {



    private List<Sitio_Data> sitio_dataList;
    private OnItemClickListener mListenerlistenerAdmin;
    private OnItemClickListener2 mListenerlistenerAdmin2;


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

    public SitioAdminAdapter(List<Sitio_Data> sitio_dataList ) {
        this.sitio_dataList = sitio_dataList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_sitio_item, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull SitioAdminAdapter.ViewHolder holder, int position) {
        Sitio_Data sitio_data = sitio_dataList.get(position);
        holder.nombreSitio.setText(sitio_data.getNombreSitio());

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
    }

    @Override
    public int getItemCount() {
        return sitio_dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreSitio;
        ImageButton imageButton_info ;
        ImageButton imageButton_supervisor;

        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            nombreSitio = itemView.findViewById(R.id.NombreSitio_admin);
            imageButton_info = itemView.findViewById(R.id.bottom_lista_info);
            imageButton_supervisor = itemView.findViewById(R.id.bottom_admin_supervisor);

        }


    }


}
