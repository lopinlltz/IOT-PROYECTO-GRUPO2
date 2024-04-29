package com.example.proyecto_final_iot.Supervisor.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Supervisor.Entity.EquipoData;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Adapter.EquipoSupervisorAdapter;

import java.util.ArrayList;
import java.util.List;

public class SitioEquipoSupervisorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EquipoSupervisorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_sitioequipo_lista);

        recyclerView = findViewById(R.id.recycler_view_sitioequipo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<EquipoData> equipoList = new ArrayList<>();
        equipoList.add(new EquipoData("EquipoTest", "Switch uwu"));
        equipoList.add(new EquipoData("Equipo2", "Antena"));
        equipoList.add(new EquipoData("Equipo3", "Router"));
        equipoList.add(new EquipoData("Equipo4", "TIPO DE EQUIPO"));
        equipoList.add(new EquipoData("Equipo5", "TIPO DE EQUIPO"));

        adapter = new EquipoSupervisorAdapter(equipoList);
        recyclerView.setAdapter(adapter);

    }
}
