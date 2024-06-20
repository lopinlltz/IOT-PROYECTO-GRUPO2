package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Adapter.UsuarioListAdminAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class superadmin_vista_supervisor2 extends AppCompatActivity {
    private Uri imageUri;
    private RecyclerView recyclerView;
    private UsuarioListAdminAdapter adapter;
    private SearchView searchView;
    List<Supervisor_Data> data_List_user = new ArrayList<>();

    FirebaseFirestore firestore_lista_usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_vista_supervisor2);
        searchView = findViewById(R.id.search_supervisor);
        searchView.clearFocus();
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        ImageButton buttonsupervisor = findViewById(R.id.buttonsupervisor);


        buttonsupervisor.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_vista_supervisor2.this, superadmin_vista_supervisor2.class);
            startActivity(intent);
        });
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_vista_supervisor2.this, superadmin_logs.class);
            startActivity(intent);
        });
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_vista_supervisor2.this, Superadmin_vista_principal1.class);
            startActivity(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        firestore_lista_usuario = FirebaseFirestore.getInstance();
        data_List_user = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_supervisor124);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsuarioListAdminAdapter(data_List_user);
        recyclerView.setAdapter(adapter);
        CargarDatos_lista_usuario();

        adapter.setOnItemClickListener(new UsuarioListAdminAdapter.OnItemClickListener() {
            @Override
            public void onReportButtonClick(int position) {
                Intent intent = new Intent(superadmin_vista_supervisor2.this, DetallesSupervisorSuperadmin.class);
                Supervisor_Data supervisorData = data_List_user.get(position);
                intent.putExtra("id_nombreUser", supervisorData.getId_nombreUser());
                intent.putExtra("id_apellidoUser", supervisorData.getId_apellidoUser());
                intent.putExtra("id_dniUSer", supervisorData.getId_dniUSer());
                intent.putExtra("id_correoUser", supervisorData.getId_correoUser());
                intent.putExtra("id_telefonoUser", supervisorData.getId_telefonoUser());
                intent.putExtra("id_domicilioUser", supervisorData.getId_domicilioUser());
                intent.putExtra("dataImage", supervisorData.getDataImage());
                intent.putExtra("textViewEstado_admin", supervisorData.getStatus_admin());
                startActivity(intent);
            }
        });
    }

    private void CargarDatos_lista_usuario() {
        data_List_user.clear();
        firestore_lista_usuario.collection("supervisorAdmin")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Lista de Supervisor Cargando", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            data_List_user.add(dc.getDocument().toObject(Supervisor_Data.class));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void filterList(String text) {
        List<Supervisor_Data> filteredList = new ArrayList<>();
        for (Supervisor_Data item : data_List_user) {
            if (item.getId_nombreUser().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Supervisor no encontrado", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }
}
