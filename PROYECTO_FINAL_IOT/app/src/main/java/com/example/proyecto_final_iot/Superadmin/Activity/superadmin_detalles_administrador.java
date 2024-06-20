package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;
import com.google.firebase.firestore.FirebaseFirestore;

public class superadmin_detalles_administrador extends AppCompatActivity {
    private FirebaseFirestore db;
    private TextView nombreTextView;
    private TextView apellidoTextView;
    private TextView dniTextView;
    private TextView correoTextView;
    private TextView telefonoTextView;
    private TextView domicilioTextView;
    private ImageView imagenAdministrador;
    private String adminId;
    private String imagenUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_detalles_administrador);

        nombreTextView = findViewById(R.id.nombre);
        apellidoTextView = findViewById(R.id.apellido);
        dniTextView = findViewById(R.id.dni);
        correoTextView = findViewById(R.id.correo);
        telefonoTextView = findViewById(R.id.telefono);
        domicilioTextView = findViewById(R.id.domicilio);
        imagenAdministrador = findViewById(R.id.imagenadmin);

        db = FirebaseFirestore.getInstance();

        adminId = getIntent().getStringExtra("ADMIN_ID");

        if (adminId == null) {
            Log.e("AdminDetalles", "Admin ID is null");
            Toast.makeText(this, "Error: Admin ID is null", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db.collection("administrador").document(adminId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Admin administrador = documentSnapshot.toObject(Admin.class);
                        if (administrador != null) {
                            Log.d("AdminDetalles", "Admin ID: " + adminId);
                            nombreTextView.setText(administrador.getNombreUser());
                            apellidoTextView.setText(administrador.getApellidoUser());
                            dniTextView.setText(administrador.getDniUser());
                            correoTextView.setText(administrador.getCorreoUser());
                            telefonoTextView.setText(administrador.getTelefonoUser());
                            domicilioTextView.setText(administrador.getDomicilioUser());
                            imagenUrl = administrador.getDataImage();
                            if (imagenUrl != null && !imagenUrl.isEmpty()) {
                                Glide.with(this).load(imagenUrl).into(imagenAdministrador);
                            }
                        } else {
                            Log.e("AdminDetalles", "Admin is null");
                            Toast.makeText(this, "Error: Admin data is null", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("AdminDetalles", "Document not found");
                        Toast.makeText(this, "Error: Admin document not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("AdminDetalles", "Error loading admin details", e);
                    Toast.makeText(this, "Error loading admin details", Toast.LENGTH_SHORT).show();
                });

        ImageButton btnUserProfile = findViewById(R.id.imageButton6);
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        Button atras = findViewById(R.id.button2);
        Button editar = findViewById(R.id.button5);
        ImageButton buttonsupervisor = findViewById(R.id.buttonsupervisor);
        buttonsupervisor.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_detalles_administrador.this, superadmin_vista_supervisor2.class);
            startActivity(intent);
        });

        atras.setOnClickListener(v -> finish());

        editar.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_detalles_administrador.this, superadmin_editar_administrador.class);
            intent.putExtra("ADMIN_ID", adminId);
            intent.putExtra("nombre", nombreTextView.getText().toString());
            intent.putExtra("apellido", apellidoTextView.getText().toString());
            intent.putExtra("dni", dniTextView.getText().toString());
            intent.putExtra("correo", correoTextView.getText().toString());
            intent.putExtra("telefono", telefonoTextView.getText().toString());
            intent.putExtra("domicilio", domicilioTextView.getText().toString());
            intent.putExtra("imagenUrl", imagenUrl);
            startActivity(intent);
        });

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_detalles_administrador.this, superadmin_logs.class);
            startActivity(intent);
        });


        btnUserProfile.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_detalles_administrador.this, PerfilSuperadmin.class);
            startActivity(intent);
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_detalles_administrador.this, Superadmin_vista_principal1.class);
            startActivity(intent);
        });
    }
}
