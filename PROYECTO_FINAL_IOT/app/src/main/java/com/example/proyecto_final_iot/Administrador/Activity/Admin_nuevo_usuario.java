package com.example.proyecto_final_iot.Administrador.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Activity.EquipoEditarActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.EquiposSupervisorActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Admin_nuevo_usuario extends AppCompatActivity {

    Button backButton_user;
    Button saveButton_user;
    FloatingActionButton fab_user;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nuevo_usuario);


        backButton_user = findViewById(R.id.backButton_user);
        backButton_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_nuevo_usuario.this, Admin_lista_usuario.class);
                startActivity(intent);
            }
        });

        saveButton_user = findViewById(R.id.saveButton_user);
        saveButton_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_nuevo_usuario.this, Admin_lista_usuario.class);
                startActivity(intent);
                ConfirmacionPopup();
            }
        });



    }

    private void ConfirmacionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Admin_nuevo_usuario.this, Admin_lista_usuario.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}