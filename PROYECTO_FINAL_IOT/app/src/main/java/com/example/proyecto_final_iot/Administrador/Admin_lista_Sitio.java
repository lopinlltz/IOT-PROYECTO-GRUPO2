package com.example.proyecto_final_iot.Administrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Admin_lista_Sitio extends AppCompatActivity {

    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_lista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_Sitio.this, Admin_nuevo_sitio.class);
                startActivity(intent);
            }
        });

        ImageView imageProfileAdmin = findViewById(R.id.imageProfileAdmin);
        imageProfileAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_Sitio.this, Admin_perfil.class);
                startActivity(intent);
            }
        });

        ImageView imageChateAdmin = findViewById(R.id.imageChateAdmin);
        imageChateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_Sitio.this, Admin_chat.class);
                startActivity(intent);
            }
        });
        TextView titlelistaSitio = findViewById(R.id.title_listaSitio_admin);

        titlelistaSitio.setText("Bienvenido @Admin_1!");


        ImageView iconoDetalles = findViewById(R.id.icono_editar1);
        iconoDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_Sitio.this, Admin_sitio_detalles.class);
                startActivity(intent);
            }
        });

        ImageView iconoSupervisor = findViewById(R.id.icono_supervisor1);
        iconoSupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_lista_Sitio.this, Admin_supervisor.class);
                startActivity(intent);
            }
        });

    }
}