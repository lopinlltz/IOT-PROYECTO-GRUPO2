package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.Superadmin.Adapter.SupervisorAdapter;
import com.example.proyecto_final_iot.Superadmin.Data.Supervisor;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
public class superadmin_vista_supervisor2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SupervisorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_vista_supervisor2);
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedRole = parentView.getItemAtPosition(position).toString();
                if (selectedRole.equals("Administrador")) {
                    Intent intent = new Intent(superadmin_vista_supervisor2.this, Superadmin_vista_principal1.class);
                    startActivity(intent);
                } else if (selectedRole.equals("Supervisor")) {
                    Intent intent = new Intent(superadmin_vista_supervisor2.this, superadmin_vista_supervisor2.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No hacer nada
            }
        });

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