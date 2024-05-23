package com.example.proyecto_final_iot.Administrador.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.R;

public class Admin_perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_perfil);


        ImageView icono_atras_profile = findViewById(R.id.icono_atras);
        icono_atras_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_perfil.this, Admin_lista_Sitio.class);
                startActivity(intent);
            }
        });

        Button buttonCerrarSesion_admin = findViewById(R.id.buttonCerrarSesion_admin);
        buttonCerrarSesion_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_perfil.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}