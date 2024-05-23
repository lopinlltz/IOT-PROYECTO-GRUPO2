package com.example.proyecto_final_iot;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificacionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotificacionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_list);

        recyclerView = findViewById(R.id.recyclerViewNotificaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Notificacion> notificaciones = NotificacionManager.obtenerNotificaciones();

        adapter = new NotificacionAdapter(this, notificaciones);
        recyclerView.setAdapter(adapter);
    }
}
