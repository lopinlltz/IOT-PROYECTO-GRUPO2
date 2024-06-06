package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.proyecto_final_iot.Equipo;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.EquipoData;
import com.example.proyecto_final_iot.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class EquipoNuevoActivity extends AppCompatActivity {

    private ConstraintLayout atras;
    private ConstraintLayout Guardar;

    //CONEXIÓN BD
    FirebaseFirestore db;

    // Guaradar:
    private EditText sku;
    private EditText serie;
    private EditText marca;
    private EditText modelo;
    private EditText desccripcion;
    private EditText fechaRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_nuevo_equipo);

        atras =  findViewById(R.id.atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EquipoNuevoActivity.this,EquiposSupervisorActivity.class));
            }
        });

        //BD

        db = FirebaseFirestore.getInstance();


        Guardar =  findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmacionPopup();
            }
        });

    }


    private void ConfirmacionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardarEquipo();
                dialog.dismiss();

                NotificationHelper.createNotificationChannel(EquipoNuevoActivity.this);
                NotificationHelper.sendNotification(EquipoNuevoActivity.this, "Equipos", "equipo creado");
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

    private void guardarEquipo() {
        sku = findViewById(R.id.id_sku);
        String skuString = sku.getText().toString().trim();

        serie = findViewById(R.id.serie);
        String serieString = serie.getText().toString().trim();

        marca = findViewById(R.id.marca);
        String marcaString = marca.getText().toString().trim();

        modelo = findViewById(R.id.modelo);
        String modeloString = modelo.getText().toString().trim();

        desccripcion = findViewById(R.id.descripcion);
        String descripcionString = desccripcion.getText().toString().trim();

        fechaRegistro = findViewById(R.id.fecha_de_registro);
        String fechaRegistroString = fechaRegistro.getText().toString().trim();


        if (skuString.isEmpty() || serieString.isEmpty() || marcaString.isEmpty() ||
                modeloString.isEmpty() || descripcionString.isEmpty() || fechaRegistroString.isEmpty()) {
            Toast.makeText(EquipoNuevoActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que el SKU no exista en la base de datos
        db.collection("equipo")
                .whereEqualTo("sku", skuString)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            // El SKU no existe, podemos guardar el equipo
                            Equipo equipo = new Equipo();

                            equipo.setSku(skuString);
                            equipo.setSerie(serieString);
                            equipo.setMarca(marcaString);
                            equipo.setModelo(modeloString);
                            equipo.setDescripcion(descripcionString);
                            equipo.setFechaRegistro(fechaRegistroString);

                            db.collection("equipo")
                                    .add(equipo)
                                    .addOnSuccessListener(unused -> {
                                        // Correcto
                                        Toast.makeText(EquipoNuevoActivity.this, "Equipo creado correctamente", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(EquipoNuevoActivity.this, EquiposSupervisorActivity.class);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error
                                        Toast.makeText(EquipoNuevoActivity.this, "No se creó el equipo", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            // El SKU ya existe
                            Toast.makeText(EquipoNuevoActivity.this, "El SKU ya existe, por favor ingrese un SKU diferente", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error al consultar Firebase
                        Toast.makeText(EquipoNuevoActivity.this, "Error al verificar el SKU en Firebase", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
