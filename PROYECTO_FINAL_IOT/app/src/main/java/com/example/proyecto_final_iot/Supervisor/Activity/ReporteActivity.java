package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Reporte;
import com.example.proyecto_final_iot.Supervisor.Adapter.HistorialSupervisorAdapter;
import com.example.proyecto_final_iot.Supervisor.Adapter.ReporteAdapter;
import com.example.proyecto_final_iot.Supervisor.Entity.HistorialData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Repo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReporteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReporteAdapter adapter;
    //CONEXIÓN BD
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(ReporteActivity.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_reporte_lista);

        Intent intent = getIntent();
        String sku = intent.getStringExtra("sku");

        recyclerView = findViewById(R.id.recycler_view_reporte);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        List<Reporte> historialList = new ArrayList<>();

        // Obtener todos los documentos de la colección "reportes" donde el atributo "idEquipo"
        db.collection("reportes")
                .whereEqualTo("idEquipo", sku)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Convertir el documento a un objeto Reporte y agregarlo a la lista
                            Reporte historialData = documentSnapshot.toObject(Reporte.class);
                            historialList.add(historialData);
                        }

                        // Crear y asignar el adaptador al RecyclerView
                        adapter = new ReporteAdapter(historialList);
                        recyclerView.setAdapter(adapter);
                    }
                });

    }
}
