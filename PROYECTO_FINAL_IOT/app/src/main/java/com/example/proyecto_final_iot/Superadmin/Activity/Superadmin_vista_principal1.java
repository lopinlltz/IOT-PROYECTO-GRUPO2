package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Adapter.AdminAdapter;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Superadmin_vista_principal1 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdminAdapter adapter;
    FirebaseFirestore db;
    private List<Admin> dataList;
    private SearchView searchAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_vista_principal1);

        ImageButton btnUserProfile = findViewById(R.id.imageButton6);
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        Button button3 = findViewById(R.id.button3);
        searchAdmin = findViewById(R.id.search_admin);

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

        CargarDatos_lista_administrador();

        searchAdmin.clearFocus();
        searchAdmin.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void CargarDatos_lista_administrador() {
        dataList.clear();
        db.collection("administrador")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Error al cargar administradores", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            dataList.add(dc.getDocument().toObject(Admin.class));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void filterList(String text) {
        List<Admin> filteredList = new ArrayList<>();
        for (Admin item : dataList) {
            if (item.getNombreUser().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Administrador no encontrado", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }
}
