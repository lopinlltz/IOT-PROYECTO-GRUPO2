package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Adapter.ChatAdapter;
import com.example.proyecto_final_iot.Supervisor.Entity.ChatData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(ChatActivity.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        recyclerView = findViewById(R.id.recycler_view_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ChatData> chatList = new ArrayList<>();
        chatList.add(new ChatData("Joselin", "Hola", "13:28"));
        chatList.add(new ChatData("Edición de equipo", "Hola", "10:25"));
        chatList.add(new ChatData("Fin en sitio x", "Hola" ,"15:30"));
        chatList.add(new ChatData("Borrado de equipo", "Hola" ,"11:09"));
        chatList.add(new ChatData("Creación de equipo", "Hola" ,"18:04"));
        chatList.add(new ChatData("Edición de equipo", "Hola" ,"14:18"));
        chatList.add(new ChatData("Edición de equipo", "Hola","09:20"));
        chatList.add(new ChatData("Joselin", "Hola", "13:28"));
        chatList.add(new ChatData("Edición de equipo", "Hola", "10:25"));
        chatList.add(new ChatData("Fin en sitio x", "Hola" ,"15:30"));
        chatList.add(new ChatData("Borrado de equipo", "Hola" ,"11:09"));
        chatList.add(new ChatData("Creación de equipo", "Hola" ,"18:04"));
        chatList.add(new ChatData("Edición de equipo", "Hola" ,"14:18"));
        chatList.add(new ChatData("Edición de equipo", "Hola","09:20"));

        adapter = new ChatAdapter(chatList);
        recyclerView.setAdapter(adapter);
    }

}
