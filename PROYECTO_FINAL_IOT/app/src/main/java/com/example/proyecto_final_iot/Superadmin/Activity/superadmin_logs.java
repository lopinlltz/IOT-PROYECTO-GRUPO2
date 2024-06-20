package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Adapter.HistorialAdapter;
import com.example.proyecto_final_iot.Superadmin.Data.HistorialData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class superadmin_logs extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HistorialAdapter adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_superadmin_logs);
        ImageButton btnUserProfile = findViewById(R.id.imageButton6);
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        ImageButton buttonsupervisor = findViewById(R.id.buttonsupervisor);
        buttonsupervisor.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_logs.this, superadmin_vista_supervisor2.class);
            startActivity(intent);
        });

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_logs.this, superadmin_logs.class);
            startActivity(intent);
        });

        btnUserProfile.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_logs.this, PerfilSuperadmin.class);
            startActivity(intent);
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_logs.this, Superadmin_vista_principal1.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recycler_view_historial1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        List<HistorialData> historialList = new ArrayList<>();

        db.collection("historialglobal")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            HistorialData historialData = documentSnapshot.toObject(HistorialData.class);
                            historialList.add(historialData);
                        }

                        adapter = new HistorialAdapter(historialList);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }
}
