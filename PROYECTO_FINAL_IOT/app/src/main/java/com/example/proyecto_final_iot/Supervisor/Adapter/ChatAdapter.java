package com.example.proyecto_final_iot.Supervisor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.ChatData;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {


    private List<ChatData> listaChat;

    public ChatAdapter(List<ChatData> listaChat){
        this.listaChat = listaChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_chat1_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatData chatData = listaChat.get(position);
        holder.textViewMensaje.setText(chatData.getMensaje());
        holder.textViewHour.setText(chatData.getHour());

    }

    @Override
    public int getItemCount() {
        return listaChat.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMensaje;
        TextView textViewHour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMensaje = itemView.findViewById(R.id.textViewMensaje);
            textViewHour = itemView.findViewById(R.id.textViewHour);
        }
    }


}
