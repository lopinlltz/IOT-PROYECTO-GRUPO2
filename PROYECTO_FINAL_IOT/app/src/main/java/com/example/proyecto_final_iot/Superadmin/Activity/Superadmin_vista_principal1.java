package com.example.proyecto_final_iot.Superadmin.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.SitioData;
import com.example.proyecto_final_iot.Superadmin.Adapter.AdminAdapter;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;
import com.example.proyecto_final_iot.Supervisor.Adapter.SitioSupervisorAdapter;

import java.util.ArrayList;
import java.util.List;


public class Superadmin_vista_principal1 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdminAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_vista_principal1);

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

        adapter = new AdminAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }
}