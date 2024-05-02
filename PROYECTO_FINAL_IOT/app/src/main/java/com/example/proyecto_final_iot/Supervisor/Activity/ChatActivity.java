package com.example.proyecto_final_iot.Supervisor.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.HistorialData;
import com.example.proyecto_final_iot.HistorialSupervisorAdapter;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Adapter.ChatAdapter;
import com.example.proyecto_final_iot.Supervisor.Entity.ChatData;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;

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
