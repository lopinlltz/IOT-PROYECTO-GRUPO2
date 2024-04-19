package com.example.proyecto_iot.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_iot.R;

public class SupervisorEquipoForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_editar_equipo);

        Button atras = findViewById(R.id.AtrasEquipoButton);
        Button actualizar = findViewById(R.id.ActualizarEquipoButton);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupervisorEquipoForm.this, SupervisorEquipoForm.class);
                startActivity(intent);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupervisorEquipoForm.this, SupervisorInicio.class);
                startActivity(intent);
            }
        });
    }
}
