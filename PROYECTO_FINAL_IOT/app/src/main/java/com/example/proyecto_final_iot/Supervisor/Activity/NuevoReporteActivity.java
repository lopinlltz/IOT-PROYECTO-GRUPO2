package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.proyecto_final_iot.Equipo;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Reporte;
import com.google.firebase.firestore.FirebaseFirestore;

public class NuevoReporteActivity extends AppCompatActivity {

    private ConstraintLayout atras;
    private ConstraintLayout Guardar;

    //CONEXIÓN BD
    FirebaseFirestore db;

    // Guaradar:
    private EditText nombre;
    private EditText descripcion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_nuevo_resporte);

        Intent intent = getIntent();
        String equipoSku = intent.getStringExtra("equipo_sku");

        Log.d("mensajeConfirmacion", equipoSku);

        atras =  findViewById(R.id.atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NuevoReporteActivity.this,EquiposSupervisorActivity.class));
            }
        });

        //BD

        db = FirebaseFirestore.getInstance();


        Guardar =  findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmacionPopup( equipoSku);
            }
        });

    }

    private void ConfirmacionPopup(String equipoSku) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de enviar el reporte?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardarEquipo( equipoSku);
                dialog.dismiss();

                NotificationHelper.createNotificationChannel(NuevoReporteActivity.this);
                NotificationHelper.sendNotification(NuevoReporteActivity.this, "Reporte", "Nuevo reporte creado");
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

    private void guardarEquipo(String equipoSku) {
        nombre = findViewById(R.id.nombre);
        String nombreString = nombre.getText().toString().trim();

        descripcion = findViewById(R.id.descripcion);
        String descripcionString = descripcion.getText().toString().trim();

        // Verificar que los campos no estén vacíos
        if (nombreString.isEmpty() || descripcionString.isEmpty()) {
            Toast.makeText(NuevoReporteActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Reporte reporte =  new Reporte();
        reporte.setNombre(nombreString);
        reporte.setDescripcion(descripcionString);
        reporte.setidEquipo(equipoSku);

        db.collection("reportes")
                .add(reporte)
                .addOnSuccessListener(unused -> {
                    // Correcto
                    Toast.makeText(NuevoReporteActivity.this, "Reporte creado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NuevoReporteActivity.this, EquiposSupervisorActivity.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    // Error
                    Toast.makeText(NuevoReporteActivity.this, "No se creó el reporte", Toast.LENGTH_SHORT).show();
                });
    }

}
