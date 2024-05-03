package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Adapter.AdminAdapter;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;
import com.example.proyecto_final_iot.UserProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class Superadmin_vista_principal1 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdminAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_vista_principal1);

        ImageButton btnUserProfile = findViewById(R.id.imageButton6);
        ImageButton btnHome = findViewById(R.id.imageButton3);
        ImageButton btnHistory = findViewById(R.id.imageButton4);
        Button button3 = findViewById(R.id.button3);

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
                // Revisar si necesitas recargar la misma actividad o realizar otra acción
                // Intent intent = new Intent(Superadmin_vista_principal1.this, Superadmin_vista_principal1.class);
                // startActivity(intent);
                recreate(); // Recrea la actividad actual
            }
        });

        recyclerView = findViewById(R.id.recycler_view_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Admin> dataList = new ArrayList<>();
        dataList.add(new Admin("13:10", "Maricielo"));
        dataList.add(new Admin("13:10", "Joselin"));
        dataList.add(new Admin("13:10", "Samantha"));
        dataList.add(new Admin("13:10", "Massiel"));
        dataList.add(new Admin("13:10", "Ricardo"));
        dataList.add(new Admin("13:10", "Diego"));
        dataList.add(new Admin("13:10", "Pablo"));
        dataList.add(new Admin("13:10", "Nana"));

        adapter = new AdminAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
    }
}
