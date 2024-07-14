package com.example.proyecto_final_iot.Administrador.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioListAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioSelectAdapter;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin_select_supervisor extends AppCompatActivity implements Serializable {

    private UsuarioSelectAdapter adapter;
    private RecyclerView recyclerView;
    List<Supervisor_Data> supervisorList  = new ArrayList<>();
    FirebaseFirestore firestore_lista_select_usuario;
    Button btn;
    private String siteId;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(Admin_select_supervisor.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_select_usuario);

        btn = findViewById(R.id.sumit);

        //--------------Firestore--------------
        firestore_lista_select_usuario = FirebaseFirestore.getInstance();
        supervisorList  = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView_lista_select_supervisor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsuarioSelectAdapter(this, supervisorList );
        recyclerView.setAdapter(adapter);

        // Obtener el ID del sitio desde el Intent
        siteId = getIntent().getStringExtra("siteId");
        Log.e("siteId onCreate", siteId);  // Log the siteId onCreate

        // Botón para obtener el supervisor seleccionado
        btn = findViewById(R.id.sumit);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Supervisor_Data selectedSupervisor = adapter.getSelected();
                if (selectedSupervisor != null) {
                    Intent intent = new Intent();
                    intent.putExtra("siteId", siteId);  // Site ID del sitio que estás editando
                    intent.putExtra("selectedSupervisorId", selectedSupervisor.getId_nombreUser());  // ID del supervisor seleccionado
                    intent.putExtra("selectedSupervisorName", selectedSupervisor.getId_nombreUser());  // Nombre del supervisor seleccionado
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    Toast.makeText(Admin_select_supervisor.this, "No seleccionaste un Supervisor", Toast.LENGTH_SHORT).show();
                }
            }
        });




        /*cargar datos de la Firebase a recycler*/
        CargarDatos_lista_usuario();


    }

    private void CargarDatos_lista_usuario() {
        supervisorList .clear();
        firestore_lista_select_usuario.collection("supervisorAdmin")
                .addSnapshotListener((value, error) -> {

                    if (error != null){
                        Toast.makeText(this, "Lista de Supervisor Cargando" , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()){

                        if (dc.getType() == DocumentChange.Type.ADDED){
                            supervisorList .add(dc.getDocument().toObject(Supervisor_Data.class));
                        }

                        adapter.notifyDataSetChanged();
                    }

                });

    }

    private void ShowToast(String mng){
        Toast.makeText(this, mng , Toast.LENGTH_SHORT).show();
    }
    private void CreateList(){
        supervisorList  = new ArrayList<>();
        for (int i=0 ; i<20 ; i++ ){
            Supervisor_Data supervisorData = new Supervisor_Data();
            supervisorData.setId_nombreUser("Superrrr" + (i+1));
            supervisorList .add(supervisorData);

        }
        adapter.SetSupervisor(supervisorList );
    }

}