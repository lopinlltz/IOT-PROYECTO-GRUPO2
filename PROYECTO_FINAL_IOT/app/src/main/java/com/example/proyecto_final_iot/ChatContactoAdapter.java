package com.example.proyecto_final_iot;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.proyecto_final_iot.Supervisor.Activity.ChatActivity;

import java.util.List;

public class ChatContactoAdapter extends RecyclerView.Adapter<ChatContactoAdapter.ViewHolder> {
    private List<ChatContactoData> contactoList;
    public ChatContactoAdapter(List<ChatContactoData> contactoList) {
        this.contactoList = contactoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_contacto_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatContactoAdapter.ViewHolder holder, int position) {
        ChatContactoData chatContactoData = contactoList.get(position);
        holder.nameAdminTextView.setText(chatContactoData.getNombreAdmin());
        holder.shortMsgTextView.setText(chatContactoData.getShortMsg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameAdminTextView;
        TextView shortMsgTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameAdminTextView = itemView.findViewById(R.id.ContactName);
            shortMsgTextView = itemView.findViewById(R.id.ContactMsg);
        }
    }
}
