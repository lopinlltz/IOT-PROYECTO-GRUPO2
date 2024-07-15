    package com.example.proyecto_final_iot.Supervisor.Activity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.proyecto_final_iot.MainActivity;
    import com.example.proyecto_final_iot.Supervisor.Entity.HistorialData;
    import com.example.proyecto_final_iot.R;
    import com.example.proyecto_final_iot.Supervisor.Adapter.HistorialSupervisorAdapter;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.Query;
    import com.google.firebase.firestore.QueryDocumentSnapshot;
    import com.google.firebase.firestore.QuerySnapshot;

    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.Comparator;
    import java.util.List;

    public class HistorialSupervisorActivity extends AppCompatActivity {
        private RecyclerView recyclerView;
        private HistorialSupervisorAdapter adapter;
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
                Intent loginIntent = new Intent(HistorialSupervisorActivity.this, MainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();
            }

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.supervisor_historial_lista);

            recyclerView = findViewById(R.id.recycler_view_historial);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            db = FirebaseFirestore.getInstance();

            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String gmail = currentUser.getEmail();

            List<HistorialData> historialList = new ArrayList<>();

            // Obtener todos los documentos de la colección "historial"
            db.collection("historial")
                    .whereEqualTo("supervisorName", gmail)
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
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "Error al obtener documentos: ", e);
                            // Manejar el error, por ejemplo, mostrar un mensaje al usuario
                        }
                    });
        }


    }
