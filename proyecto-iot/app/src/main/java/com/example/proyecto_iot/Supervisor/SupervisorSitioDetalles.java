package com.example.proyecto_iot.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_iot.MainActivity;
import com.example.proyecto_iot.R;

public class SupervisorSitioDetalles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_sitio_detalle);

        Button ingresar = findViewById(R.id.equiposButton);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupervisorSitioDetalles.this, SupervisorSitioEquipo.class);
                startActivity(intent);
            }
        });
    }


}
