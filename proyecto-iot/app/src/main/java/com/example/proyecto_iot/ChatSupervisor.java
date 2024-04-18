package com.example.proyecto_iot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatSupervisor extends AppCompatActivity {
    private RecyclerView rvChats;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_chat);

        rvChats = findViewById(R.id.rvChats);
        rvChats.setLayoutManager(new LinearLayoutManager(this));

        List<ChatItem> chatItems = new ArrayList<>();
        chatItems.add(new ChatItem("Lana", "lanapic", "12:36"));
        chatItems.add(new ChatItem("Nana", "nanapic", "10:20"));
        chatItems.add(new ChatItem("Tomie", "tomiepic", "08:13"));

        chatAdapter = new ChatAdapter(chatItems);
        rvChats.setAdapter(chatAdapter);
    }
}
