package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EquipoEditarActivity extends AppCompatActivity {


    private ConstraintLayout atras;
    private ConstraintLayout Guardar;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(EquipoEditarActivity.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_editar_equipo);
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String sku = intent.getStringExtra("sku");
        String serie = intent.getStringExtra("serie");
        String marca = intent.getStringExtra("marca");
        String modelo =  intent.getStringExtra("modelo");
        String descripcion =  intent.getStringExtra("descripcion");
        String fecha =  intent.getStringExtra("fecha");

        // Referencias a los EditTexts
        TextView skuText= findViewById(R.id.id_sku);
        EditText marcaEditText = findViewById(R.id.marca);
        EditText modeloEditText = findViewById(R.id.modelo);
        EditText descripcionEditText = findViewById(R.id.descripción);

        skuText.setText(sku);
        marcaEditText.setText(marca);
        modeloEditText.setText(modelo);
        descripcionEditText.setText(descripcion);

        atras =  findViewById(R.id.atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EquipoEditarActivity.this,EquipoDetalleActivity.class);
                intent.putExtra("modelo", modelo);
                intent.putExtra("marca", marca );
                intent.putExtra("sku", sku);
                intent.putExtra("serie", serie);
                intent.putExtra("descripcion",descripcion);
                intent.putExtra("fecha", fecha);
                v.getContext().startActivity(intent);
                startActivity(intent);
            }
        });

        Guardar =  findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nuevaMarca = marcaEditText.getText().toString();
                String nuevoModelo = modeloEditText.getText().toString();
                String nuevaDescripcion = descripcionEditText.getText().toString();
                Log.d("EquipoEditarActivity", "Datos obtenidos para edición: " +
                        "sku=" + sku + ", nuevaMarca=" + nuevaMarca + ", nuevoModelo=" + nuevoModelo + ", nuevaDescripcion=" + nuevaDescripcion);

                ConfirmacionPopup(sku, nuevaMarca, nuevoModelo, nuevaDescripcion);
            }
        });

    }


    private void ConfirmacionPopup(String sku, String nuevaMarca, String nuevoModelo, String nuevaDescripcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                editarEquipo(sku, nuevaMarca, nuevoModelo, nuevaDescripcion);
                Intent intent = new Intent(EquipoEditarActivity.this, EquiposSupervisorActivity.class);
                startActivity(intent);
                dialog.dismiss();

                NotificationHelper.createNotificationChannel(EquipoEditarActivity.this);
                NotificationHelper.sendNotification(EquipoEditarActivity.this, "Equipos", "equipo editado");
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void editarEquipo(String sku, String nuevaMarca, String nuevoModelo, String nuevaDescripcion) {
        db.collection("equipo")
                .whereEqualTo("sku", sku)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            // Obtener la referencia del primer documento encontrado
                            DocumentReference equipoRef = task.getResult().getDocuments().get(0).getReference();

                            // Actualizar el documento
                            equipoRef.update("marca", nuevaMarca,
                                            "modelo", nuevoModelo,
                                            "descripcion", nuevaDescripcion)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("EquipoEditarActivity", "Equipo actualizado con éxito");
                                        // Éxito al actualizar el equipo
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("EquipoEditarActivity", "Error al actualizar el equipo", e);
                                        // Error al actualizar el equipo
                                    });
                        } else {
                            // No se encontró ningún documento con el `sku` especificado
                            Log.e("EquipoEditarActivity", "El documento con SKU " + sku + " no existe.");
                        }
                    } else {
                        Log.e("EquipoEditarActivity", "Error al obtener el documento", task.getException());
                    }
                });
    }


}