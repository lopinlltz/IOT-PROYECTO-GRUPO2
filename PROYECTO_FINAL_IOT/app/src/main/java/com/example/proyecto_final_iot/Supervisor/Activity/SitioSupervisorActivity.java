package com.example.proyecto_final_iot.Supervisor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.SitioData;
import com.example.proyecto_final_iot.Supervisor.Adapter.SitioSupervisorAdapter;

import java.util.ArrayList;
import java.util.List;

public class SitioSupervisorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SitioSupervisorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_sitio_lista);

        recyclerView = findViewById(R.id.recycler_view_sitio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SitioData> dataList = new ArrayList<>();
        dataList.add(new SitioData("Pueblo Libre", "Av. Sucre 1023"));
        dataList.add(new SitioData("Sitio 2", "UBICACIÓN"));
        dataList.add(new SitioData("Sitio 3", "UBICACIÓN"));
        dataList.add(new SitioData("Sitio 4", "UBICACIÓN"));
        dataList.add(new SitioData("Sitio 5", "UBICACIÓN"));
        dataList.add(new SitioData("Sitio 6", "UBICACIÓN"));
        dataList.add(new SitioData("Sitio 7", "UBICACIÓN"));
        dataList.add(new SitioData("Sitio 8", "UBICACIÓN"));

        adapter = new SitioSupervisorAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }
}
