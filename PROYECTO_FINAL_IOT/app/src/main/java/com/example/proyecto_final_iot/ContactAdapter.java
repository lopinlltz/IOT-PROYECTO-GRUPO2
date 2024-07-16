package com.example.proyecto_final_iot;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.ChatMsgActivity;
import com.example.proyecto_final_iot.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;

    public ContactAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_contacto_item, parent, false);
        //return new ContactViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        /*holder.bind(user);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatMsgActivity.class);
            //intent.putExtra("userId", user.getId());
            //intent.putExtra("userName", user.getName());
            intent.putExtra("receiverId", user.getId());
            intent.putExtra("receiverName", user.getName());
            context.startActivity(intent);
            Log.d("ContactAdapter", "Starting ChatMsgActivity for user: " + user.getName());
        });*/

        holder.txtNombre.setText(user.getNombreUser());
        holder.txtApellido.setText(user.getApellidoUser());
        holder.txtRol.setText(user.getRol());

        // Cargar la imagen usando Glide o Picasso
        Glide.with(context)
                .load(user.getDataImage())
                .placeholder(R.drawable.placeholder_image) // Imagen de carga por defecto
                .error(R.drawable.erro_pic) // Imagen de error si falla la carga
                .into(holder.imgUser);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatMsgActivity.class);
            intent.putExtra("receiverId", user.getId());
            intent.putExtra("receiverName", user.getNombreUser());
            intent.putExtra("receiverRole", user.getRol());
            intent.putExtra("receiverImage", user.getDataImage());
            context.startActivity(intent);
            Log.d("ContactAdapter", "Starting ChatMsgActivity for user: " + user.getNombreUser());
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    /*public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewRole;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.ContactName);
            textViewRole = itemView.findViewById(R.id.ContactMsg);
        }

        public void bind(User user) {
            textViewName.setText(user.getName());
            textViewRole.setText(user.getRole()); // Asegúrate de que este método exista en tu User class
        }
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtApellido, txtRol;
        CircleImageView imgUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.ContactName);
            txtApellido = itemView.findViewById(R.id.ContactApellido);
            txtRol = itemView.findViewById(R.id.ContactMsg);
            imgUser = itemView.findViewById(R.id.circleImageView);
        }
    }
}

