package com.example.proyecto_iot;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MsgSupervisor extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<String> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_msg);

        recyclerView = findViewById(R.id.recyclerViewChat);
        messages = new ArrayList<>();
        adapter = new MessageAdapter(messages);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        messages.add("Hola");
        messages.add("¿Cómo estás?");
        messages.add("¡Bien, gracias!");

        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String message = messages.get(position);
                Toast.makeText(MsgSupervisor.this, "Mensaje: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
