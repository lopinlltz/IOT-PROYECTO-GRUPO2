    package com.example.proyecto_final_iot.Supervisor.Activity;

    import android.os.Bundle;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.proyecto_final_iot.Supervisor.Entity.HistorialData;
    import com.example.proyecto_final_iot.R;
    import com.example.proyecto_final_iot.Supervisor.Adapter.HistorialSupervisorAdapter;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.QueryDocumentSnapshot;
    import com.google.firebase.firestore.QuerySnapshot;

    import java.util.ArrayList;
    import java.util.List;

    public class HistorialSupervisorActivity extends AppCompatActivity {
        private RecyclerView recyclerView;
        private HistorialSupervisorAdapter adapter;
        //CONEXIÓN BD
        FirebaseFirestore db;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.supervisor_historial_lista);

            recyclerView = findViewById(R.id.recycler_view_historial);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            db = FirebaseFirestore.getInstance();

            List<HistorialData> historialList = new ArrayList<>();

            // Obtener todos los documentos de la colección "historial"
            db.collection("historial")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                // Convertir el documento a un objeto HistorialData y agregarlo a la lista
                                HistorialData historialData = documentSnapshot.toObject(HistorialData.class);
                                historialList.add(historialData);
                            }

                            // Crear y asignar el adaptador al RecyclerView
                            adapter = new HistorialSupervisorAdapter(historialList);
                            recyclerView.setAdapter(adapter);
                        }
                    });
        }
    }
