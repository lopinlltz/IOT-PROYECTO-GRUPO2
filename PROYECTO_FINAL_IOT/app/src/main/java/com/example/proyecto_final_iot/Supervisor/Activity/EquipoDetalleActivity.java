package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.R;

public class EquipoDetalleActivity extends AppCompatActivity {
    TextView textViewNombreEquipo;
    TextView textViewTipoEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_detalles_equipo);

        Intent intent = getIntent();
        String nombreEquipo = intent.getStringExtra("equipment_name");
        String tipoEquipo = intent.getStringExtra("type_eq");

        textViewNombreEquipo = findViewById(R.id.textViewNombreEquipo);
        textViewTipoEquipo = findViewById(R.id.textViewTipoEquipo);

        textViewNombreEquipo.setText(nombreEquipo);
        textViewTipoEquipo.setText(tipoEquipo);
    }
}
