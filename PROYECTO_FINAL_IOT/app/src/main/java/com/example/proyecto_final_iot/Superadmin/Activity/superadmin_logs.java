package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Adapter.LogsAdapter;
import com.example.proyecto_final_iot.Superadmin.Data.Logs;

import java.util.ArrayList;
import java.util.List;

public class superadmin_logs extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LogsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_superadmin_logs);
        ImageButton btnUserProfile = findViewById(R.id.imageButton6);
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        ImageButton buttonsupervisor = findViewById(R.id.buttonsupervisor);
        buttonsupervisor.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_logs.this, superadmin_vista_supervisor2.class);
            startActivity(intent);
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_logs.this, superadmin_logs.class);
                startActivity(intent);
            }
        });

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_logs.this, PerfilSuperadmin.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Revisar si necesitas recargar la misma actividad o realizar otra acción
                 Intent intent = new Intent(superadmin_logs.this, Superadmin_vista_principal1.class);
                startActivity(intent);
                //recreate(); // Recrea la actividad actual
            }
        });

        recyclerView = findViewById(R.id.recycler_view_historial1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Logs> historialList = new ArrayList<>();
        historialList.add(new Logs("Creación de equipo", "Joselin", "29/04/2024", "13:28"));
        historialList.add(new Logs("Edición de equipo", "Joselin", "29/04/2024", "10:25"));
        historialList.add(new Logs("Fin en sitio x", "Massiel", "28/04/2024", "15:30"));
        historialList.add(new Logs("Borrado de equipo", "Joselin", "28/04/2024", "11:09"));
        historialList.add(new Logs("Creación de equipo", "Massiel", "27/04/2024", "18:04"));
        historialList.add(new Logs("Edición de equipo", "Joselin", "27/04/2024", "14:18"));
        historialList.add(new Logs("Edición de equipo", "Massiel", "27/04/2024", "09:20"));
        adapter = new LogsAdapter(historialList);
        recyclerView.setAdapter(adapter);
    }

}