package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.R;

public class SitioDetalleActivity extends AppCompatActivity {
    TextView textViewNombreSitio;
    TextView textViewUbicacion;
    Button buttonEquipoSitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_detalles_sitio);

        Intent intent = getIntent();
        String nombreSitio = intent.getStringExtra("site_name");
        String ubicacion = intent.getStringExtra("location");

        textViewNombreSitio = findViewById(R.id.textViewNombreSitio);
        textViewUbicacion = findViewById(R.id.textViewUbicacion);

        textViewNombreSitio.setText(nombreSitio);
        textViewUbicacion.setText(ubicacion);

        buttonEquipoSitio =  findViewById(R.id.buttonEquipoSitio);
        buttonEquipoSitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SitioDetalleActivity.this, SitioEquipoSupervisorActivity.class));
            }
        });

    }
}
