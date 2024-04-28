package com.example.proyecto_final_iot.Supervisor.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.HistorialData;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Adapter.HistorialSupervisorAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistorialSupervisorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HistorialSupervisorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_historial_lista);

        recyclerView = findViewById(R.id.recycler_view_historial);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<HistorialData> historialList = new ArrayList<>();
        historialList.add(new HistorialData("Creación de equipo", "Joselin", "29/04/2024", "13:28"));
        historialList.add(new HistorialData("Edición de equipo", "Joselin", "29/04/2024", "10:25"));
        historialList.add(new HistorialData("Fin en sitio x", "Massiel", "28/04/2024", "15:30"));
        historialList.add(new HistorialData("Borrado de equipo", "Joselin", "28/04/2024", "11:09"));
        historialList.add(new HistorialData("Creación de equipo", "Massiel", "27/04/2024", "18:04"));
        historialList.add(new HistorialData("Edición de equipo", "Joselin", "27/04/2024", "14:18"));
        historialList.add(new HistorialData("Edición de equipo", "Massiel", "27/04/2024", "09:20"));

        adapter = new HistorialSupervisorAdapter(historialList);
        recyclerView.setAdapter(adapter);
    }
}
