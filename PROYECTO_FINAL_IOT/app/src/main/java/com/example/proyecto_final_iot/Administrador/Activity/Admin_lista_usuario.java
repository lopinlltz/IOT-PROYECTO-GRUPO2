package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Administrador.Adapter.SitioAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioListAdapter;
import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.Administrador.Data.Usuario_data;
import com.example.proyecto_final_iot.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Admin_lista_usuario extends AppCompatActivity {


    private UsuarioListAdapter adapter;
    private RecyclerView recyclerView;
    FloatingActionButton fab_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lista_usuarios);


        recyclerView = findViewById(R.id.recyclerView_lista_usuarios);
        List<Usuario_data> data_List_user = new ArrayList<>();
        data_List_user.add(new Usuario_data("Nombre de Usuario 1"));
        data_List_user.add(new Usuario_data("Nombre de Usuario 2"));
        data_List_user.add(new Usuario_data("Nombre de Usuario 3"));
        data_List_user.add(new Usuario_data("Nombre de Usuario 4"));
        data_List_user.add(new Usuario_data("Nombre de Usuario 5"));
        data_List_user.add(new Usuario_data("Nombre de Usuario 6"));

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
    }


}