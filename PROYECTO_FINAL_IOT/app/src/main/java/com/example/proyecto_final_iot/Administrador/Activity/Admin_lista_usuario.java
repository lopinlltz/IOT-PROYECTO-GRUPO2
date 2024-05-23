package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Adapter.SitioAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioListAdapter;
import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_nuevo_Data;
import com.example.proyecto_final_iot.Administrador.Data.Usuario_data;
import com.example.proyecto_final_iot.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin_lista_usuario extends AppCompatActivity {


    private UsuarioListAdapter adapter;
    private RecyclerView recyclerView;
    FloatingActionButton fab_user;
    private SearchView searchView;

    List<Supervisor_nuevo_Data> data_List_user = new ArrayList<>();

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
        adapter = new UsuarioListAdapter(data_List_user);
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

    }

    private void CargarDatos_lista_usuario() {
        data_List_user.clear();
        firestore_lista_usuario.collection("supervisorAdmin").orderBy("nombre", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {

                    if (error != null){
                        Toast.makeText(this, "Lista de Sitios Cargando" , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()){

                        if (dc.getType() == DocumentChange.Type.ADDED){
                            data_List_user.add(dc.getDocument().toObject(Supervisor_nuevo_Data.class));
                        }

                        adapter.notifyDataSetChanged();
                    }

                });

    }

    private void filterList(String text) {
        List<Supervisor_nuevo_Data> filteredList = new ArrayList<>();
        for(Supervisor_nuevo_Data item : data_List_user ){
            if (item.getNombre().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            Toast.makeText(this, "Supervisor no encontrado" , Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(filteredList);
        }


    }


}