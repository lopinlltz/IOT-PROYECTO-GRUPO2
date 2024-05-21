package com.example.proyecto_final_iot.Administrador.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyecto_final_iot.Administrador.Adapter.SitioAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Admin_lista_Sitio extends AppCompatActivity{

    FloatingActionButton fab;
    private SitioAdminAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_sitio_lista);

        recyclerView = findViewById(R.id.recyclerView_lista_sitios);

        List<Sitio_Data> data_List = new ArrayList<>();
        data_List.add(new Sitio_Data("Nombre de sitio 1"));
        data_List.add(new Sitio_Data("Nombre de sitio 2"));
        data_List.add(new Sitio_Data("Nombre de sitio 3"));
        data_List.add(new Sitio_Data("Nombre de sitio 4"));
        data_List.add(new Sitio_Data("Nombre de sitio 5"));
        data_List.add(new Sitio_Data("Nombre de sitio 6"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SitioAdminAdapter(data_List);
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
        //TextView titlelistaSitio = findViewById(R.id.title_listaSitio_admin);


        adapter.setOnItemClickListener(new SitioAdminAdapter.OnItemClickListener() {
            @Override
            public void onReportButtonClick(int position) {
                Intent intent = new Intent(Admin_lista_Sitio.this, Admin_sitio_detalles.class);
                startActivity(intent);
            }
        });


        adapter.setOnItemClickListener(new SitioAdminAdapter.OnItemClickListener2() {
            @Override
            public void onReportButtonClick(int position) {
                Intent intent = new Intent(Admin_lista_Sitio.this, Admin_supervisor.class);
                startActivity(intent);
            }
        });


    }





}