package com.example.proyecto_iot.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_iot.MainActivity;
import com.example.proyecto_iot.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;

public class SupervisorInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_inicio);

        LinearLayout ingresar = findViewById(R.id.firstItem);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupervisorInicio.this, SupervisorSitioDetalles.class);
                startActivity(intent);
            }
        });

    }

}
