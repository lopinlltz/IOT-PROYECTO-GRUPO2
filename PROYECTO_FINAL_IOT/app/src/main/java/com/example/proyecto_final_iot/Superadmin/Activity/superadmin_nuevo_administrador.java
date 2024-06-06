package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.Superadmin.Data.Admin;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class superadmin_nuevo_administrador extends AppCompatActivity {

    // CONEXIÓN BD
    FirebaseFirestore db;

    // Guardar:
    private EditText nombre;
    private EditText apellido;
    private EditText dni;
    private EditText correo;
    private EditText telefono;
    private EditText domicilio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_nuevo_administrador);
        ImageButton btnUserProfile = findViewById(R.id.imageButton6);
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        Button atras = findViewById(R.id.button2);
        Button registrar = findViewById(R.id.button5);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_nuevo_administrador.this, Superadmin_vista_principal1.class);
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_nuevo_administrador.this, superadmin_logs.class);
                startActivity(intent);
            }
        });

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_nuevo_administrador.this, PerfilSuperadmin.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_nuevo_administrador.this, Superadmin_vista_principal1.class);
                startActivity(intent);
            }
        });

        // BD
        db = FirebaseFirestore.getInstance();

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar");
        builder.setMessage("¿Está seguro que quiere guardar?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardarAdministrador();
                dialog.dismiss();
                NotificationHelper.createNotificationChannel(superadmin_nuevo_administrador.this);
                NotificationHelper.sendNotification(superadmin_nuevo_administrador.this, "Usuarios", "Nuevo administrador creado");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void guardarAdministrador() {
        nombre = findViewById(R.id.nombre);
        String nombreString = nombre.getText().toString().trim();

        apellido = findViewById(R.id.apellido);
        String apellidoString = apellido.getText().toString().trim();

        dni = findViewById(R.id.dni);
        int dniint = Integer.parseInt(dni.getText().toString().trim());

        correo = findViewById(R.id.correo);
        String correoString = correo.getText().toString().trim();

        telefono = findViewById(R.id.telefono);
        int telefeonoInt = Integer.parseInt(telefono.getText().toString().trim());

        domicilio = findViewById(R.id.domicilio);
        String domicilioString = domicilio.getText().toString().trim();

        Admin administrador = new Admin();
        administrador.setNombreUser(nombreString);
        administrador.setApellidoUser(apellidoString);
        administrador.setDniUser(dniint);
        administrador.setCorreoUser(correoString);
        administrador.setTelefonoUser(telefeonoInt);
        administrador.setDomicilioUser(domicilioString);


        db.collection("administrador")
                .add(administrador)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(superadmin_nuevo_administrador.this, "Administrador creado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(superadmin_nuevo_administrador.this, Superadmin_vista_principal1.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(superadmin_nuevo_administrador.this, "No se creó el administrador", Toast.LENGTH_SHORT).show();
                });
    }
}
