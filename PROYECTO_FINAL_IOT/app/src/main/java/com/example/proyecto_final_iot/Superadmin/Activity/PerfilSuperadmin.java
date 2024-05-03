package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.UserProfileActivity;

public class PerfilSuperadmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_superadmin);
        ImageButton btnHome = findViewById(R.id.imageButtonhome);
        ImageButton btnHistory = findViewById(R.id.imagenhistorial);


        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilSuperadmin.this, superadmin_logs.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Revisar si necesitas recargar la misma actividad o realizar otra acción
                Intent intent = new Intent(PerfilSuperadmin.this, Superadmin_vista_principal1.class);
                startActivity(intent);
                //recreate(); // Recrea la actividad actual
            }
        });
    }
}