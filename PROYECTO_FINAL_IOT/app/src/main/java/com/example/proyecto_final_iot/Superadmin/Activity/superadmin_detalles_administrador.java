package com.example.proyecto_final_iot.Superadmin.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.R;
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
                        Supervisor_Data administrador = documentSnapshot.toObject(Supervisor_Data.class);
                        if (administrador != null) {
                            nombreTextView.setText(administrador.getId_nombreUser());
                            apellidoTextView.setText(administrador.getId_apellidoUser());
                            dniTextView.setText(administrador.getId_dniUSer());
                            correoTextView.setText(administrador.getId_correoUser());
                            telefonoTextView.setText(administrador.getId_telefonoUser());
                            domicilioTextView.setText(administrador.getId_domicilioUser());
                        }
                    }
                });
    }
}
