package com.example.proyecto_final_iot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.Administrador.Activity.Admin_lista_Sitio;
import com.example.proyecto_final_iot.Supervisor.Activity.EquiposSupervisorActivity;
import com.example.proyecto_final_iot.Superadmin.Activity.Superadmin_vista_principal1;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       /*----------------
        //Button buttonCerrarSesion = findViewById(R.id.buttonCerrarSesion);
        Button loginbutton = findViewById(R.id.login_button);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_sitio_detalles.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        Button boton_Registrar;
        boton_Registrar = findViewById(R.id.login_button);
        boton_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Admin_lista_Sitio.class);
                startActivity(intent);
            }
        });*/


        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        //    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
         //   return insets;
        //});

        Button buttonSuperadmin = findViewById(R.id.buttonSuperAdmin);
        buttonSuperadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Superadmin_vista_principal1.class);
                startActivity(intent);
            }
        });

        Button buttonAdmin = findViewById(R.id.buttonAdmin);
        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Admin_lista_Sitio.class);
                startActivity(intent);
            }
        });

        Button buttonSupervisor = findViewById(R.id.buttonSupervisor);
        buttonSupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SitioSupervisorActivity.class);
                startActivity(intent);
            }
        });
    }
}

