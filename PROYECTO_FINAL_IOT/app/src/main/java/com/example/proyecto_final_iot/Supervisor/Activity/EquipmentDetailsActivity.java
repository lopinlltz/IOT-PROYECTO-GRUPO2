package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.Equipo;
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class EquipmentDetailsActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView textViewSKU;
    private TextView textViewNumeroSerie;
    private TextView textViewMarca;
    private TextView textViewModelo;
    private TextView textViewDescripcion;
    private TextView textViewFechaRegistro;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(EquipmentDetailsActivity.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_equipment_details);

        db = FirebaseFirestore.getInstance();

        textViewSKU = findViewById(R.id.textViewSKU);
        textViewNumeroSerie = findViewById(R.id.textViewNumeroSerie);
        textViewMarca = findViewById(R.id.textViewMarca);
        textViewModelo = findViewById(R.id.textViewModelo);
        textViewDescripcion = findViewById(R.id.textViewDescripcion);
        textViewFechaRegistro = findViewById(R.id.textViewFechaRegistro);

        // Obtener el SKU del intent
        if (getIntent().hasExtra("qr_content")) {
            String qrContent = getIntent().getStringExtra("qr_content");
            buscarEquipoPorSKU(qrContent);
        } else {
            Toast.makeText(this, "No se encontró información del equipo", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //me falta revisar esta vaina pq el sku si lo lee, tengo q ver la bd
    private void buscarEquipoPorSKU(String sku) {
        Log.d("EquipmentDetails", "SKU recibido: " + sku);

        db.collection("equipo")
                .whereEqualTo("sku", sku)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            //EquipoData equipo = document.toObject(EquipoData.class);
                            Equipo equipo = document.toObject(Equipo.class);
                            mostrarDetallesEquipo(equipo);
                            return;
                        }
                    } else {
                        Toast.makeText(this, "No se encontró al equipo con ese SKU", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("EquipmentDetails", "Error al buscar equipo por SKU", e);
                    Toast.makeText(EquipmentDetailsActivity.this, "Error al buscar equipo por SKU", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    private void mostrarDetallesEquipo(Equipo equipo) {
        textViewSKU.setText("SKU: " + equipo.getSku());
        textViewNumeroSerie.setText("Número de serie: " + equipo.getSerie());
        textViewMarca.setText("Marca: " + equipo.getMarca());
        textViewModelo.setText("Modelo: " + equipo.getModelo());
        textViewDescripcion.setText("Descripción: " + equipo.getDescripcion());
        textViewFechaRegistro.setText("Fecha de registro: " + equipo.getFechaRegistro());
    }
}
