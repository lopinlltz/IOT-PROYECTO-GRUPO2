package com.example.proyecto_final_iot.Supervisor.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.EquipoData;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Adapter.EquipoSupervisorAdapter;

import java.util.ArrayList;
import java.util.List;

public class EquiposSupervisorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EquipoSupervisorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_equipo_lista);

        recyclerView = findViewById(R.id.recycler_view_equipo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<EquipoData> equipoList = new ArrayList<>();
        equipoList.add(new EquipoData("Equipo", "TIPO DE EQUIPO"));
        equipoList.add(new EquipoData("Equipo", "TIPO DE EQUIPO"));
        equipoList.add(new EquipoData("Equipo", "TIPO DE EQUIPO"));
        equipoList.add(new EquipoData("Equipo", "TIPO DE EQUIPO"));
        equipoList.add(new EquipoData("Equipo", "TIPO DE EQUIPO"));
        equipoList.add(new EquipoData("Equipo", "TIPO DE EQUIPO"));
        equipoList.add(new EquipoData("Equipo", "TIPO DE EQUIPO"));
        equipoList.add(new EquipoData("Equipo", "TIPO DE EQUIPO"));

        adapter = new EquipoSupervisorAdapter(equipoList);
        recyclerView.setAdapter(adapter);
    }
}
