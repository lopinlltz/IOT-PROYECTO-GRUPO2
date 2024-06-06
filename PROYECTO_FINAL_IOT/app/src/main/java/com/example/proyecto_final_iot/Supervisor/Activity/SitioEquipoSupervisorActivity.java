package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Supervisor.Entity.EquipoData;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Adapter.EquipoSupervisorAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SitioEquipoSupervisorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EquipoSupervisorAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_sitioequipo_lista);

        Intent intent = getIntent();
        String nombreSitio = intent.getStringExtra("nombreSitio");
        Log.d("mensajeRECIBE", nombreSitio != null ? nombreSitio : "nombreSitio es null");

        recyclerView = findViewById(R.id.recycler_view_sitioequipo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<EquipoData> equipoList = new ArrayList<>();


        adapter = new EquipoSupervisorAdapter(equipoList);
        recyclerView.setAdapter(adapter);

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Consultar los equipos con id_codigodeSitio igual a nombreSitio
        db.collection("equipo")
                .whereEqualTo("id_codigodeSitio", nombreSitio)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Limpiar la lista antes de agregar los nuevos equipos
                        equipoList.clear();

                        // Iterar sobre los resultados y agregarlos a la lista
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            EquipoData equipo = document.toObject(EquipoData.class);
                            equipoList.add(equipo);
                        }

                        // Notificar al adaptador sobre los cambios en los datos
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("mensaje", "Error getting documents: ", task.getException());
                    }
                });

    }
}
