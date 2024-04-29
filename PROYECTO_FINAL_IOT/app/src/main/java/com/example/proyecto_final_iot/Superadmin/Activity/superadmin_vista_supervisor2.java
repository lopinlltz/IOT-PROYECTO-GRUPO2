package com.example.proyecto_final_iot.Superadmin.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Superadmin.Adapter.SupervisorAdapter;
import com.example.proyecto_final_iot.Superadmin.Data.Supervisor;

import java.util.ArrayList;
import java.util.List;
public class superadmin_vista_supervisor2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SupervisorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_vista_supervisor2);

        recyclerView = findViewById(R.id.recycler_view_supervisor124);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Supervisor> dataList = new ArrayList<>();
        dataList.add(new Supervisor("13:10", "Maricielo"));
        dataList.add(new Supervisor("13:10", "Joselin"));
        dataList.add(new Supervisor("13:10", "Samantha"));
        dataList.add(new Supervisor("13:10", "Massiel"));
        dataList.add(new Supervisor("13:10", "Ricardo"));
        dataList.add(new Supervisor("13:10", "Diego"));
        dataList.add(new Supervisor("13:10", "Pablo"));
        dataList.add(new Supervisor("13:10", "Nana"));

        adapter = new SupervisorAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }
}