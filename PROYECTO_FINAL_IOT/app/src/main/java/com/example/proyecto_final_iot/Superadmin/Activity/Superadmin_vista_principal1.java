package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Adapter.AdminAdapter;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Superadmin_vista_principal1 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdminAdapter adapter;
    FirebaseFirestore db;
    private List<Admin> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_vista_principal1);

        ImageButton btnUserProfile = findViewById(R.id.imageButton6);
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        Button button3 = findViewById(R.id.button3);
        SearchView searchAdmin = findViewById(R.id.search_admin);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Superadmin_vista_principal1.this, superadmin_nuevo_administrador.class);
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Superadmin_vista_principal1.this, superadmin_logs.class);
                startActivity(intent);
            }
        });

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Superadmin_vista_principal1.this, PerfilSuperadmin.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate(); // Recrea la actividad actual
            }
        });

        recyclerView = findViewById(R.id.recycler_view_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataList = new ArrayList<>();

        adapter = new AdminAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        db.collection("administrador")
                .addSnapshotListener((snapshot, error) -> {
                    if (error != null) {
                        return;
                    }

                    dataList.clear();
                    for (QueryDocumentSnapshot document : snapshot) {
                        Admin adminData = document.toObject(Admin.class);
                        dataList.add(new Admin(
                                document.getId(),  // Obtener el ID del documento
                                adminData.getNombreUser(),  // Obtener nombre
                                adminData.getApellidoUser(),  // Obtener apellido
                                adminData.getDniUser(),  // Obtener DNI
                                adminData.getCorreoUser(),  // Obtener correo
                                adminData.getTelefonoUser(),  // Obtener teléfono
                                adminData.getDomicilioUser(),
                                adminData.getDataImage(),  // Obtener imagen
                                adminData.getStatus_admin()  // Obtener status
                        ));
                    }

                    adapter.notifyDataSetChanged();
                });

        searchAdmin.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
