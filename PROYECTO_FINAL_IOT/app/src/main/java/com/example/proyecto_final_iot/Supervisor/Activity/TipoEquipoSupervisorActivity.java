package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Adapter.TipoEqSupervisorAdapter;
import com.example.proyecto_final_iot.Supervisor.Entity.TipoEqData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class TipoEquipoSupervisorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TipoEqSupervisorAdapter adapter;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(TipoEquipoSupervisorActivity.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_tipoequipo_lista);

        recyclerView = findViewById(R.id.recycler_view_tipoequipo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<TipoEqData> tipoEqList = new ArrayList<>();
        tipoEqList.add(new TipoEqData("Tipo 1"));
        tipoEqList.add(new TipoEqData("Tipo 2"));
        tipoEqList.add(new TipoEqData("Tipo 3"));
        tipoEqList.add(new TipoEqData("Tipo 4"));
        tipoEqList.add(new TipoEqData("Tipo 5"));
        tipoEqList.add(new TipoEqData("Tipo 6"));
        tipoEqList.add(new TipoEqData("Tipo 7"));

        adapter = new TipoEqSupervisorAdapter(tipoEqList);
        recyclerView.setAdapter(adapter);
    }
}
