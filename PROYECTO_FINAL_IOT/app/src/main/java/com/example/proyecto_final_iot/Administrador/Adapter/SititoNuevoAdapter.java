package com.example.proyecto_final_iot.Administrador.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;

public class SititoNuevoAdapter extends RecyclerView.Adapter<SupervisorListAdminAdapter.ViewHolder> {
    @NonNull
    @Override
    public SupervisorListAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SupervisorListAdminAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView ) {
            super(itemView);

            ;

        }
    }
}
