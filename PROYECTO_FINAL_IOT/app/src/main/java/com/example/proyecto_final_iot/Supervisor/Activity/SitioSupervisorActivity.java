package com.example.proyecto_final_iot.Supervisor.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Adapter.SitioAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_nuevo_Data;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.SitioData;
import com.example.proyecto_final_iot.Supervisor.Adapter.SitioSupervisorAdapter;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SitioSupervisorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SitioSupervisorAdapter adapter;
    //DB
    FirebaseFirestore db;
    List<SitioData> dataList = new ArrayList<>();

    private SearchView searchView_sitio;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(SitioSupervisorActivity.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_sitio_lista);

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

        //BD

        db = FirebaseFirestore.getInstance();
        dataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_sitio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SitioSupervisorAdapter(dataList);
        recyclerView.setAdapter(adapter);

        CargarDatos_lista_sitio();


    }

    private void filterList_sitio(String text_sitio) {
        List<SitioData> filteredList_sitio = new ArrayList<>();

        for(SitioData item_sitio : dataList ){
            if (String.valueOf(item_sitio.getId_codigodeSitio()).toLowerCase().contains(text_sitio.toLowerCase())){
                filteredList_sitio.add(item_sitio);
            }
        }

        if (filteredList_sitio.isEmpty()){
            Toast.makeText(this, "Sitio no encontrado" , Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList_sitio(filteredList_sitio);
        }


    }

    private void CargarDatos_lista_sitio() {
        dataList.clear();
        db.collection("sitio")
                .addSnapshotListener((value, error) -> {

                    if (error != null){
                        Toast.makeText(this, "Lista de Sitios Cargando" , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()){

                        if (dc.getType() == DocumentChange.Type.ADDED){
                            dataList.add(dc.getDocument().toObject(SitioData.class));
                        }

                        adapter.notifyDataSetChanged();
                    }

                });
    }


}