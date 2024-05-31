package com.example.proyecto_final_iot.Administrador.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioListAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioSelectAdapter;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin_select_supervisor extends AppCompatActivity implements Serializable {

    private UsuarioSelectAdapter adapter;
    private RecyclerView recyclerView;
    List<Supervisor_Data> data_List_select_user = new ArrayList<>();
    FirebaseFirestore firestore_lista_select_usuario;

    private boolean isChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_select_usuario);


        //--------------Firestore--------------
        firestore_lista_select_usuario = FirebaseFirestore.getInstance();
        data_List_select_user = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView_lista_select_supervisor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsuarioSelectAdapter(data_List_select_user);
        recyclerView.setAdapter(adapter);



        /*cargar datos de la Firebase a recycler*/
        CargarDatos_lista_usuario();


    }

    private void CargarDatos_lista_usuario() {
        data_List_select_user.clear();
        firestore_lista_select_usuario.collection("supervisorAdmin")
                .addSnapshotListener((value, error) -> {

                    if (error != null){
                        Toast.makeText(this, "Lista de Supervisor Cargando" , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()){

                        if (dc.getType() == DocumentChange.Type.ADDED){
                            data_List_select_user.add(dc.getDocument().toObject(Supervisor_Data.class));
                        }

                        adapter.notifyDataSetChanged();
                    }

                });

    }

    public void onQuantityChange(List<String> supervisor_List) {
            Toast.makeText(this, "Seleccionaste este Supervisor " , Toast.LENGTH_SHORT).show();
        }
}