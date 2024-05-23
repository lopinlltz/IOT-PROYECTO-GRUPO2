package com.example.proyecto_final_iot.Supervisor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_nuevo_Data;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.SitioData;
import com.example.proyecto_final_iot.Supervisor.Adapter.SitioSupervisorAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.cache.DiskLruCache;

public class SitioSupervisorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SitioSupervisorAdapter adapter;
    //DB
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_sitio_lista);

        recyclerView = findViewById(R.id.recycler_view_sitio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<SitioData> dataList = new ArrayList<>();

        //BD

        db = FirebaseFirestore.getInstance();

        db.collection("sitio")
                .addSnapshotListener(  (snapshot, error) -> {
                    if (error!= null) {
                    return;
                    }

                    for (QueryDocumentSnapshot document : snapshot){
                        Sitio_nuevo_Data sitioNuevoData = document.toObject(Sitio_nuevo_Data.class);
                        dataList.add(new SitioData(sitioNuevoData.getId_codigodeSitio(), sitioNuevoData.getId_provincia() + sitioNuevoData.getId_distrito()));
                        Log.d("msg-resultado", "Nombre: " + sitioNuevoData.getId_codigodeSitio());

                    }

                    adapter = new SitioSupervisorAdapter(dataList);
                    recyclerView.setAdapter(adapter);

                }) ;





    }
}
