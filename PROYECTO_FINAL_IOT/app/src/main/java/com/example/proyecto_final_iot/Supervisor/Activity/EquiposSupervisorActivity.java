package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EquiposSupervisorActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private Button qrSearchButton;
    private RecyclerView recyclerView;
    private EquipoSupervisorAdapter adapter;
    private FloatingActionButton fab;
    FirebaseFirestore db;
    List<EquipoData> equipoList = new ArrayList<>();
    private SearchView searchView_sitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_equipo_lista);

        // buscador
        searchView_sitio = findViewById(R.id.search_sitio);
        searchView_sitio.clearFocus();

        searchView_sitio.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText_sitio) {
                filterList_sitio(newText_sitio);
                return true;
            }
        });

        qrSearchButton = findViewById(R.id.qr_search_button);
        qrSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de escaneo de QR
                Intent intent = new Intent(EquiposSupervisorActivity.this, QRScannerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
            }
        });

        //BD

        db = FirebaseFirestore.getInstance();
        equipoList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_equipo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EquipoSupervisorAdapter(equipoList);
        recyclerView.setAdapter(adapter);


        fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EquiposSupervisorActivity.this,EquipoNuevoActivity.class));
            }
        });

        adapter.setOnItemClickListener(new EquipoSupervisorAdapter.OnItemClickListener() {
            @Override
            public void onReportButtonClick(int position, EquipoData equipo) {
                Intent intent = new Intent(EquiposSupervisorActivity.this, NuevoReporteActivity.class);
                intent.putExtra("equipo_sku", equipo.getSku()); // Enviar el SKU del equipo
                // Puedes enviar otros datos del equipo aquí si es necesario
                startActivity(intent);
            }
        });

        //cameraButton.setOnClickListener(view -> dispatchTakePictureIntent());

        CargarDatos_lista_sitio();

    }

    private void filterList_sitio(String text_sitio) {
        List<EquipoData> filteredList_sitio = new ArrayList<>();

        for(EquipoData item_sitio : equipoList ){
            if (String.valueOf(item_sitio.getSku()).toLowerCase().contains(text_sitio.toLowerCase())){
                filteredList_sitio.add(item_sitio);
            }
        }

        if (filteredList_sitio.isEmpty()){
            Toast.makeText(this, "Sitio no encontrado" , Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList_sitio(filteredList_sitio);
        }


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(takePictureIntent);
        }
    }

    private void CargarDatos_lista_sitio() {
        equipoList.clear();
        db.collection("equipo")
                .addSnapshotListener((value, error) -> {

                    if (error != null){
                        Toast.makeText(this, "Lista de Equipos Cargando" , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()){

                        if (dc.getType() == DocumentChange.Type.ADDED){
                            equipoList.add(dc.getDocument().toObject(EquipoData.class));
                        }

                        adapter.notifyDataSetChanged();
                    }

                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QR_SCAN && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("qr_result")) {
                String qrResult = data.getStringExtra("qr_result");

                Toast.makeText(this, "QR Code: " + qrResult, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
