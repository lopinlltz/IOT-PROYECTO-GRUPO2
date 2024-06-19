package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioListAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin_lista_usuario extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    private Uri imageUri;
    private UsuarioListAdminAdapter adapter;
    private RecyclerView recyclerView;
    FloatingActionButton fab_user;
    private SearchView searchView;

    List<Supervisor_Data> data_List_user = new ArrayList<>();
    FirebaseFirestore firestore_lista_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lista_usuarios);

        searchView = findViewById(R.id.search_usuario);
        searchView.clearFocus();
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

        //--------------Firestore--------------
        firestore_lista_usuario = FirebaseFirestore.getInstance();
        data_List_user = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView_lista_usuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsuarioListAdminAdapter(data_List_user);
        recyclerView.setAdapter(adapter);

        fab_user = findViewById(R.id.fab_user);
        fab_user.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_usuario.this, Admin_nuevo_usuario.class);
                startActivity(intent);
            }
        });

        /*cargar datos de la Firebase a recycler*/
        CargarDatos_lista_usuario();

        adapter.setOnItemClickListener(new UsuarioListAdminAdapter.OnItemClickListener() {
            @Override
            public void onReportButtonClick(int position) {
                Intent intent = new Intent(Admin_lista_usuario.this, Admin_usuario_detalles.class);
                intent.putExtra("dataImage", data_List_user.get(0).getDataImage());
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

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                int usuarioId = data.getIntExtra("usuarioId", -1);
                String nuevoEstado = data.getStringExtra("nuevoEstado");
                Log.d("Debug", "nuevoEstado: " + nuevoEstado);


                if (usuarioId != -1 && nuevoEstado != null) {
                    adapter.actualizarEstadoUsuario(usuarioId, nuevoEstado);
                    Log.d("Debug", "nuevoEstado: " + nuevoEstado);
                }
            }
        }


    }

    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }
}