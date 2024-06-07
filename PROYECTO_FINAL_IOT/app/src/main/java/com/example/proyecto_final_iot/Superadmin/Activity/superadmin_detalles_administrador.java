package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

        db = FirebaseFirestore.getInstance();

        String adminId = getIntent().getStringExtra("ADMIN_ID");

        db.collection("administrador").document(adminId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Admin administrador = documentSnapshot.toObject(Admin.class);
                        if (administrador != null) {
                            nombreTextView.setText(administrador.getNombreUser());
                            apellidoTextView.setText(administrador.getApellidoUser());
                            dniTextView.setText(String.valueOf(administrador.getDniUser()));
                            correoTextView.setText(administrador.getCorreoUser());
                            telefonoTextView.setText(String.valueOf(administrador.getTelefonoUser()));
                            domicilioTextView.setText(administrador.getDomicilioUser());
                        }
                    }
                });

        ImageButton btnUserProfile = findViewById(R.id.imageButton6);
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        Button atras = findViewById(R.id.button2);
        Button editar = findViewById(R.id.button5);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Vuelve a la actividad anterior
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_detalles_administrador.this, superadmin_editar_administrador.class);
                intent.putExtra("ADMIN_ID", adminId);
                intent.putExtra("nombre", nombreTextView.getText().toString());
                intent.putExtra("apellido", apellidoTextView.getText().toString());
                intent.putExtra("dni", dniTextView.getText().toString());
                intent.putExtra("correo", correoTextView.getText().toString());
                intent.putExtra("telefono", telefonoTextView.getText().toString());
                intent.putExtra("domicilio", domicilioTextView.getText().toString());
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_detalles_administrador.this, superadmin_logs.class);
                startActivity(intent);
            }
        });

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_detalles_administrador.this, PerfilSuperadmin.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_detalles_administrador.this, Superadmin_vista_principal1.class);
                startActivity(intent);
            }
        });
    }
}
