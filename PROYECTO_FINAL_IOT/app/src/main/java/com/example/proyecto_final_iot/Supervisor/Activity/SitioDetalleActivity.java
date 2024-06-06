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
    TextView textViewDepartamento;
    TextView textViewProvincia;
    TextView textViewDistrito;
    TextView textViewUbigeo;
    TextView textViewLongLat;
    TextView textViewTipoZona;
    TextView textViewTipoSitio;
    Button buttonEquipoSitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_detalles_sitio);

        Intent intent = getIntent();
        String nombreSitio = intent.getStringExtra("site_name");
        String ubicacion = intent.getStringExtra("location");
        String departamento = intent.getStringExtra("departamento");
        String provincia = intent.getStringExtra("provincia");
        String distrito = intent.getStringExtra("distrito");
        String ubigeo = intent.getStringExtra("ubigeo");
        String latitud_longitud = intent.getStringExtra("latitud_longitud");
        //String tipo_zona = intent.getStringExtra("tipo_zona");
        //String tipo_sitio = intent.getStringExtra("tipo_sitio");

        textViewNombreSitio = findViewById(R.id.textViewNombreSitio);
        textViewUbicacion = findViewById(R.id.textViewIdSitio);
        textViewDepartamento = findViewById(R.id.textViewDepartamento);
        textViewProvincia = findViewById(R.id.textViewProvincia);
        textViewDistrito = findViewById(R.id.textViewDistrito);
        textViewUbigeo = findViewById(R.id.textViewUbigeo);
        textViewLongLat= findViewById(R.id.textViewLongLat);
        //textViewTipoZona = findViewById(R.id.textViewTipoZona);
        //textViewTipoSitio = findViewById(R.id.textViewTipoSitio);

        textViewNombreSitio.setText(nombreSitio);
        textViewUbicacion.setText(ubicacion);
        textViewDepartamento.setText(departamento);
        textViewProvincia.setText(provincia);
        textViewDistrito.setText(distrito);
        textViewUbigeo.setText(ubigeo);
        textViewLongLat.setText(latitud_longitud);
        //textViewTipoZona.setText(tipo_zona);
        //textViewTipoSitio.setText(tipo_sitio);

        buttonEquipoSitio =  findViewById(R.id.buttonEquipoSitio);
        buttonEquipoSitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SitioDetalleActivity.this, SitioEquipoSupervisorActivity.class);
                intent.putExtra("nombreSitio", nombreSitio);
                startActivity(intent);
            }
        });

    }
}
