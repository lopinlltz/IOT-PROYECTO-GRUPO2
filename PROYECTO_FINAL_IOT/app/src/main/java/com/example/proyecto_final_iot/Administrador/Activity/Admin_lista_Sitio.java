package com.example.proyecto_final_iot.Administrador.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyecto_final_iot.Administrador.Adapter.SitioAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioSelectAdapter;
import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Activity.EquiposSupervisorActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.NuevoReporteActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin_lista_Sitio extends AppCompatActivity{

    FloatingActionButton fab;
    private SitioAdminAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView_sitio;
    List<Sitio_Data> data_List = new ArrayList<>();
    List<Supervisor_Data> supervisor_SelectdataList = new ArrayList<>();

    FirebaseFirestore firestore_lista;
    String selectedSupervisorId ;
    String siteId;
    private String selectedSupervisorName;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(Admin_lista_Sitio.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sitio_lista);

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

//--------------Firestore--------------
        firestore_lista = FirebaseFirestore.getInstance();

        data_List = new ArrayList<>();
        supervisor_SelectdataList = new ArrayList<>();


        recyclerView = findViewById(R.id.recyclerView_lista_sitios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SitioAdminAdapter(data_List, supervisor_SelectdataList,selectedSupervisorName);
        recyclerView.setAdapter(adapter);


        fab = findViewById(R.id.fab_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_Sitio.this, Admin_nuevo_sitio.class);
                startActivity(intent);
            }
        });

        ImageView imageProfileAdmin = findViewById(R.id.imageProfileAdmin);
        imageProfileAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_Sitio.this, Admin_perfil.class);
                startActivity(intent);
            }
        });

        ImageView imageChateAdmin = findViewById(R.id.imageChateAdmin);
        imageChateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_Sitio.this, ChatActivity.class);
                startActivity(intent);
            }
        });


        adapter.setOnItemClickListener(new SitioAdminAdapter.OnItemClickListener() {
            @Override
            public void onReportButtonClick(int position) {
                Sitio_Data sitioData = data_List.get(position);
                Intent intent = new Intent(Admin_lista_Sitio.this, Admin_select_supervisor.class);
                intent.putExtra("siteId", sitioData.getId_codigodeSitio());
                startActivityForResult(intent, 1);
            }
        });

        CargarDatos_lista_sitio();


    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String siteId = data.getStringExtra("siteId");
            String supervisorName = data.getStringExtra("selectedSupervisorId");  // Make sure this is the supervisor name
            Log.e("siteId onActivityResult", siteId);
            Log.e("supervisorName onActivityResult", supervisorName);
            adapter.updateSupervisor(siteId, supervisorName);  // Update supervisor in the adapter
        }
    }



    private void CargarDatos_lista_sitio() {
        data_List.clear();
        firestore_lista.collection("sitio")
                .addSnapshotListener((value, error) -> {

                    if (error != null){
                        Toast.makeText(this, "Lista de Sitios Cargando" , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()){
                        if (dc.getType() == DocumentChange.Type.ADDED){
                            Sitio_Data sitioData = dc.getDocument().toObject(Sitio_Data.class);
                            // Verificar si este es el sitio que coincide con siteId
                            if (sitioData.getId_codigodeSitio().equals(siteId)) {
                                // Asignar el supervisor seleccionado a este sitio
                                sitioData.setSupervisorName(selectedSupervisorId);
                            }
                            data_List.add(sitioData);
                        }
                    }

                        adapter.notifyDataSetChanged();


                });
}

    private void filterList_sitio(String text_sitio) {
        List<Sitio_Data> filteredList_sitio = new ArrayList<>();

        for(Sitio_Data item_sitio : data_List ){
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

}