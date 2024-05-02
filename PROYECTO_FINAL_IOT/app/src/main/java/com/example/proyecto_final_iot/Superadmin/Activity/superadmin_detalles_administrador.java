package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.UserProfileActivity;

public class superadmin_detalles_administrador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_superadmin_detalles_administrador);
        ImageButton btnUserProfile = findViewById(R.id.imageButton6);
        ImageButton btnHome = findViewById(R.id.imageButton3);
        ImageButton btnHistory = findViewById(R.id.imageButton4);
        Button atras = findViewById(R.id.button2);
        Button registrar = findViewById(R.id.button5);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Revisar si necesitas recargar la misma actividad o realizar otra acción
                Intent intent = new Intent(superadmin_detalles_administrador.this, Superadmin_vista_principal1.class);
                startActivity(intent);
                //recreate(); // Recrea la actividad actual
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Revisar si necesitas recargar la misma actividad o realizar otra acción
                Intent intent = new Intent(superadmin_detalles_administrador.this, superadmin_editar_administrador.class);
                startActivity(intent);
                //recreate(); // Recrea la actividad actual
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
                Intent intent = new Intent(superadmin_detalles_administrador.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Revisar si necesitas recargar la misma actividad o realizar otra acción
                Intent intent = new Intent(superadmin_detalles_administrador.this, Superadmin_vista_principal1.class);
                startActivity(intent);
                //recreate(); // Recrea la actividad actual
            }
        });
    }
}