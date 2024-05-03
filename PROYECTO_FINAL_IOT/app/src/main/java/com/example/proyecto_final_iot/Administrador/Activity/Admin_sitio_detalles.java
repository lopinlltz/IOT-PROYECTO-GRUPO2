package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.github.clans.fab.FloatingActionButton;
import com.example.proyecto_final_iot.R;


public class Admin_sitio_detalles extends AppCompatActivity {

    FloatingActionButton boton_delete;
    FloatingActionButton boton_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sitio_detalles);

        boton_delete = (FloatingActionButton) findViewById(R.id.boton_delete);
        boton_edit = findViewById(R.id.boton_edit);

        boton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_sitio_detalles.this, Admin_lista_Sitio.class);
                startActivity(intent);
            }
        });

        boton_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_sitio_detalles.this, Admin_sitio_editar.class);
                startActivity(intent);
            }
        });
    }


}