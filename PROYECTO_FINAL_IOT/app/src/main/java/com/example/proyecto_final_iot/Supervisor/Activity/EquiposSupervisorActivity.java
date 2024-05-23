package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_nuevo_Data;
import com.example.proyecto_final_iot.Equipo;
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.Superadmin.Activity.Superadmin_vista_principal1;
import com.example.proyecto_final_iot.Supervisor.Adapter.SitioSupervisorAdapter;
import com.example.proyecto_final_iot.Supervisor.Entity.EquipoData;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Adapter.EquipoSupervisorAdapter;
import com.example.proyecto_final_iot.Supervisor.Entity.SitioData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class EquiposSupervisorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EquipoSupervisorAdapter adapter;
    private FloatingActionButton fab;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_equipo_lista);

        recyclerView = findViewById(R.id.recycler_view_equipo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<EquipoData> equipoList = new ArrayList<>();
        adapter = new EquipoSupervisorAdapter(equipoList);
        recyclerView.setAdapter(adapter);

        //BD

        db = FirebaseFirestore.getInstance();

        db.collection("equipo")
                .addSnapshotListener(  (snapshot, error) -> {
                    if (error!= null) {
                        return;
                    }

                    for (QueryDocumentSnapshot document : snapshot){

                        Equipo equipo = document.toObject(Equipo.class);
                        equipoList.add(new EquipoData(equipo.getMarca(), equipo.getSerie()));

                    }

                    adapter.notifyDataSetChanged();

                }) ;

        fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EquiposSupervisorActivity.this,EquipoNuevoActivity.class));
            }
        });

        adapter.setOnItemClickListener(new EquipoSupervisorAdapter.OnItemClickListener() {
            @Override
            public void onReportButtonClick(int position) {
                Intent intent = new Intent(EquiposSupervisorActivity.this, NuevoReporteActivity.class);
                startActivity(intent);
            }
        });

        ImageButton cameraButton = findViewById(R.id.imageButtonCamera);
        /*cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });*/
        cameraButton.setOnClickListener(view -> dispatchTakePictureIntent());

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(takePictureIntent);
        }
    }
}
