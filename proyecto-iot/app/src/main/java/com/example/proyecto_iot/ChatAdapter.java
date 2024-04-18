package com.example.proyecto_iot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<ChatItem> chatItems;

    public ChatAdapter(List<ChatItem> chatItems) {
        this.chatItems = chatItems;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatItem chatItem = chatItems.get(position);
        holder.bind(chatItem);
    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProfilePicture;
        private TextView tvName;
        private TextView tvTime;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
        }

        public void bind(ChatItem chatItem) {
            // Cargar la imagen de perfil
            if (chatItem.getProfilePictureUrl().equals("lanapic")) {
                ivProfilePicture.setImageResource(R.drawable.lanapic);
            } else if (chatItem.getProfilePictureUrl().equals("nanapic")) {
                ivProfilePicture.setImageResource(R.drawable.nanapic);
            } else if (chatItem.getProfilePictureUrl().equals("tomiepic")) {
                ivProfilePicture.setImageResource(R.drawable.tomiepic);
            } else {
                ivProfilePicture.setImageResource(R.drawable.profilepicicon); // Imagen por defecto
            }

            tvName.setText(chatItem.getName());
            tvTime.setText(chatItem.getLastMessageTime());
        }
    }
}
