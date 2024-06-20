package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.R;

public class DetallesSupervisorSuperadmin extends AppCompatActivity {
    private TextView nombreTextView;
    private TextView apellidoTextView;
    private TextView dniTextView;
    private TextView correoTextView;
    private TextView telefonoTextView;
    private TextView domicilioTextView;
    private ImageView imagenSupervisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_supervisor_superadmin);
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        ImageButton buttonsupervisor = findViewById(R.id.buttonsupervisor);
        Button atras = findViewById(R.id.button2);
        atras.setOnClickListener(v -> {
            Intent intent = new Intent(DetallesSupervisorSuperadmin.this, superadmin_vista_supervisor2.class);
            startActivity(intent);
        });
        buttonsupervisor.setOnClickListener(v -> {
            Intent intent = new Intent(DetallesSupervisorSuperadmin.this, superadmin_vista_supervisor2.class);
            startActivity(intent);
        });
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(DetallesSupervisorSuperadmin.this, superadmin_logs.class);
            startActivity(intent);
        });
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(DetallesSupervisorSuperadmin.this, Superadmin_vista_principal1.class);
            startActivity(intent);
        });

        nombreTextView = findViewById(R.id.nombre);
        apellidoTextView = findViewById(R.id.apellido);
        dniTextView = findViewById(R.id.dni);
        correoTextView = findViewById(R.id.correo);
        telefonoTextView = findViewById(R.id.telefono);
        domicilioTextView = findViewById(R.id.domicilio);
        imagenSupervisor = findViewById(R.id.imageView4);

        String nombre = getIntent().getStringExtra("id_nombreUser");
        String apellido = getIntent().getStringExtra("id_apellidoUser");
        String dni = getIntent().getStringExtra("id_dniUSer");
        String correo = getIntent().getStringExtra("id_correoUser");
        String telefono = getIntent().getStringExtra("id_telefonoUser");
        String domicilio = getIntent().getStringExtra("id_domicilioUser");
        String imagenUrl = getIntent().getStringExtra("dataImage");

        nombreTextView.setText(nombre);
        apellidoTextView.setText(apellido);
        dniTextView.setText(dni);
        correoTextView.setText(correo);
        telefonoTextView.setText(telefono);
        domicilioTextView.setText(domicilio);

        if (imagenUrl != null && !imagenUrl.isEmpty()) {
            Glide.with(this).load(imagenUrl).into(imagenSupervisor);
        }
    }
}
