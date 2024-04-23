package com.example.proyecto_final_iot.Administrador;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.R;


public class UploadActivity extends AppCompatActivity {

    Button BotonAtras;
    Button BotonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        BotonAtras = findViewById(R.id.backButton);
        BotonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UploadActivity.this, Admin_lista.class);
                startActivity(intent);
            }
        });

        BotonGuardar = findViewById(R.id.saveButton);
        BotonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UploadActivity.this, Admin_lista.class);
                startActivity(intent);
            }
        });


    }


}