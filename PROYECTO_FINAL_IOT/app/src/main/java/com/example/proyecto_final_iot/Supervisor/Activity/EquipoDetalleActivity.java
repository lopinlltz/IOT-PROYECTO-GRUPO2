package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.HistorialData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EquipoDetalleActivity extends AppCompatActivity {
     TextView textViewSku;
    TextView textViewNroSerie;
    TextView textViewMarca;
    TextView textViewModelo;
    TextView textViewDescripcion;
    TextView textViewFecha;
    TextView textViewNombreEquipo;
    Button buttonBorrarEq;
    Button buttonEditarEq;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_detalles_equipo);

        Intent intent = getIntent();
        String sku = intent.getStringExtra("sku");
        String serie = intent.getStringExtra("serie");
        String marca = intent.getStringExtra("marca");
        String modelo =  intent.getStringExtra("modelo");
        String descripcion =  intent.getStringExtra("descripcion");
        String fecha =  intent.getStringExtra("fecha");

        db = FirebaseFirestore.getInstance();

        textViewNombreEquipo = findViewById(R.id.textViewNombreEquipo);
        textViewSku = findViewById(R.id.textViewSku);
        textViewNroSerie = findViewById(R.id.textViewNroSerie);
        textViewMarca = findViewById(R.id.textViewMarca);
        textViewModelo = findViewById(R.id.textViewModelo);
        textViewDescripcion = findViewById(R.id.textViewDescripcion);
        textViewFecha = findViewById(R.id.textViewFecha);

        textViewNombreEquipo.setText(sku);
        textViewSku.setText(sku);
        textViewNroSerie.setText(serie);
        textViewMarca.setText(marca);
        textViewModelo.setText(modelo);
        textViewDescripcion.setText(descripcion);
        textViewFecha.setText(fecha);

        buttonBorrarEq =  findViewById(R.id.buttonBorrarEq);
        buttonBorrarEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquiposSupervisorActivity.class);
                ConfirmacionPopup(sku);
            }
        });

        buttonEditarEq =  findViewById(R.id.buttonEditarEq);
        buttonEditarEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquipoEditarActivity.class);
                intent.putExtra("sku", sku);
                intent.putExtra("serie", serie);
                intent.putExtra("marca", marca);
                intent.putExtra("modelo", modelo);
                intent.putExtra("descripcion", descripcion);
                intent.putExtra("fecha", fecha);
                startActivity(intent);

            }
        });

    }

    private void ConfirmacionPopup(String equipoId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de eliminar el equipo?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquiposSupervisorActivity.class);
                borrarEquipoPorSKU( equipoId);
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

    private void borrarEquipoPorSKU(String sku) {
        // Realizar una consulta para encontrar el documento con el SKU deseado
        db.collection("equipo")
                .whereEqualTo("sku", sku)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Obtener el ID del documento que coincide con el SKU
                            String equipoId = document.getId();
                            // Eliminar el documento usando el ID obtenido
                            db.collection("equipo").document(equipoId)
                                    .delete()
                                    .addOnSuccessListener(unused -> {
                                        // Correcto
                                        guardarHistorial();
                                        Toast.makeText(this, "Equipo con SKU " + sku + " eliminado correctamente", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error
                                        Toast.makeText(this, "No se pudo eliminar el equipo con SKU " + sku, Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        // Error al realizar la consulta
                        Toast.makeText(this, "Error al buscar el equipo con SKU " + sku, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void guardarHistorial() {

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedHour = hourFormat.format(currentDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        HistorialData historial = new HistorialData();
        historial.setActivityName("Borraste un equipo");
        historial.setSupervisorName("Joselin");
        historial.setDate(formattedDate);
        historial.setHour(formattedHour);

        db.collection("historial")
                .add(historial)
                .addOnSuccessListener(documentReference -> {

                })
                .addOnFailureListener(e -> {

                });

    }


}
