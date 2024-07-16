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

        String number = sku.substring(sku.indexOf(":") + 1);


        Log.d("EquipmentDetails", "SKU recibido: " + sku);

        db.collection("equipo")
                .whereEqualTo("sku", number)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            finish();

                            //EquipoData equipo = document.toObject(EquipoData.class);
                            Equipo equipo = document.toObject(Equipo.class);

                            Intent intent = new Intent(EquipmentDetailsActivity.this, EquipoDetalleActivity.class);
                            intent.putExtra("sitio", equipo.getId_codigodeSitio());
                            intent.putExtra("modelo", equipo.getModelo());
                            intent.putExtra("marca", equipo.getMarca());
                            intent.putExtra("sku", equipo.getSku());
                            intent.putExtra("serie", equipo.getSerie());
                            intent.putExtra("descripcion", equipo.getDescripcion());
                            intent.putExtra("fecha", equipo.getFechaRegistro());
                            intent.putExtra("dataImage_equipo", equipo.getDataImage_equipo());
                            startActivity(intent);
                            finish(); // Cierra EquipmentDetailsActivity después de iniciar EquipoDetalleActivity
                            return; // Salir del método una vez que se ha encontrado el equipo
                        }
                    } else {
                        Toast.makeText(EquipmentDetailsActivity.this, "No se encontró al equipo con ese SKU", Toast.LENGTH_SHORT).show();
                    }
                    finish(); // Terminar la actividad después de procesar los resultados
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
