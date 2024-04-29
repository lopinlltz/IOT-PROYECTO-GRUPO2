package com.example.proyecto_final_iot;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Supervisor.Adapter.SitioSupervisorAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatContactoListaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatContactoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chat_contacto_lista);

        recyclerView = findViewById(R.id.recycler_view_contactoChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ChatContactoData> dataList = new ArrayList<>();
        dataList.add( new ChatContactoData("Nina", "hola, ¿cómo estás?"));
        dataList.add( new ChatContactoData("Abril", "Hemos cambiado la asignación del sitio..."));
        dataList.add( new ChatContactoData("Samantha", "Todavía estamos coordinando la entrega."));
        dataList.add( new ChatContactoData("Maricielo", "Llegó hace unos días el nuvo stock de ..."));

        adapter = new ChatContactoAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }
}
