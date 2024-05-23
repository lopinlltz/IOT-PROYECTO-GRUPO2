package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;

public class EquipoDetalleActivity extends AppCompatActivity {
    TextView textViewNombreEquipo;
    TextView textViewTipoEquipo;
    Button buttonBorrarEq;
    Button buttonEditarEq;

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

        buttonBorrarEq =  findViewById(R.id.buttonBorrarEq);
        buttonBorrarEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquiposSupervisorActivity.class);
                ConfirmacionPopup();
            }
        });

        buttonEditarEq =  findViewById(R.id.buttonEditarEq);
        buttonEditarEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquipoEditarActivity.class);
                startActivity(intent);
            }
        });

    }

    private void ConfirmacionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de eliminar el equipo?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquiposSupervisorActivity.class);
                //ConfirmacionPopup();
                startActivity(intent);
                dialog.dismiss();

                NotificationHelper.createNotificationChannel(EquipoDetalleActivity.this);
                NotificationHelper.sendNotification(EquipoDetalleActivity.this, "Equipos", "equipo borrado");
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
