package com.example.proyecto_final_iot.Administrador.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.HistorialData;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Admin_sitio_editar extends AppCompatActivity {

    EditText id_departamento_et, id_provincia_et, id_distrito_et, id_ubigeo_et, id_tipo_de_zona_et, id_tipo_de_sitio_et, id_latitud_long_et;
    TextView id_codigodeSitio_tv;

    FirebaseFirestore db;
    FloatingActionButton boton_guardar_sitio;
    FloatingActionButton boton_atras_sitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sitio_editar);
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String id_codigodeSitio = intent.getStringExtra("id_codigodeSitio");
        String id_departamento = intent.getStringExtra("id_departamento");
        String id_provincia = intent.getStringExtra("id_provincia");
        String id_distrito = intent.getStringExtra("id_distrito");
        String id_ubigeo = intent.getStringExtra("id_ubigeo");
        String id_tipo_de_zona = intent.getStringExtra("id_tipo_de_zona");
        String id_tipo_de_sitio = intent.getStringExtra("id_tipo_de_sitio");
        String id_latitud_long = intent.getStringExtra("id_latitud_long");

        id_codigodeSitio_tv = findViewById(R.id.id_codigodeSitio);
        id_departamento_et = findViewById(R.id.id_departamento);
        id_provincia_et = findViewById(R.id.id_provincia);
        id_distrito_et = findViewById(R.id.id_distrito);
        id_ubigeo_et = findViewById(R.id.id_ubigeo);
        id_tipo_de_zona_et = findViewById(R.id.id_tipoDeZona);
        id_tipo_de_sitio_et = findViewById(R.id.id_tipoDeSitio);
        id_latitud_long_et = findViewById(R.id.id_latitud_long);

        id_codigodeSitio_tv.setText(id_codigodeSitio);
        id_departamento_et.setText(id_departamento);
        id_provincia_et.setText(id_provincia);
        id_distrito_et.setText(id_distrito);
        id_ubigeo_et.setText(id_ubigeo);
        id_tipo_de_zona_et.setText(id_tipo_de_zona);
        id_tipo_de_sitio_et.setText(id_tipo_de_sitio);
        id_latitud_long_et.setText(id_latitud_long);

        boton_atras_sitio = findViewById(R.id.boton_back_sitio);
        boton_atras_sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_sitio_editar.this, Admin_lista_Sitio.class);
                intent.putExtra("id_codigodeSitio", id_codigodeSitio);
                intent.putExtra("id_departamento", id_departamento);
                intent.putExtra("id_provincia", id_provincia);
                intent.putExtra("id_distrito", id_distrito);
                intent.putExtra("id_ubigeo", id_ubigeo);
                intent.putExtra("id_tipo_de_zona", id_tipo_de_zona);
                intent.putExtra("id_tipo_de_sitio", id_tipo_de_sitio);
                intent.putExtra("id_latitud_long", id_latitud_long);
                v.getContext().startActivity(intent);
                startActivity(intent);
            }
        });

        boton_guardar_sitio = findViewById(R.id.boton_save_sitio);
        boton_guardar_sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevaDepartamento = id_departamento_et.getText().toString();
                String nuevoProvincia = id_provincia_et.getText().toString();
                String nuevaDistrito = id_distrito_et.getText().toString();
                String nuevaUbigeo = id_ubigeo_et.getText().toString();
                String nuevaZona = id_tipo_de_zona_et.getText().toString();
                String nuevaSitio = id_tipo_de_sitio_et.getText().toString();
                String nuevaLong = id_latitud_long_et.getText().toString();

                Log.d("Admin_sitio_editar", "Datos obtenidos para edición: " +
                        "id_codigodeSitio=" + id_codigodeSitio +
                        "nuevaDepartamento=" + nuevaDepartamento +
                        ", nuevoProvincia=" + nuevoProvincia +
                        ", nuevaDistrito=" + nuevaDistrito +
                        ", nuevaUbigeo=" + nuevaUbigeo +
                        ", nuevaZona=" + nuevaZona +
                        ", nuevaSitio=" + nuevaSitio +
                        ", nuevaLong=" + nuevaLong);

                ConfirmacionPopup(id_codigodeSitio, nuevaDepartamento, nuevoProvincia, nuevaDistrito, nuevaUbigeo, nuevaZona, nuevaSitio, nuevaLong);
            }
        });
    }

    private void ConfirmacionPopup(String id_codigodeSitio, String nuevaDepartamento, String nuevoProvincia, String nuevaDistrito, String nuevaUbigeo, String nuevaZona, String nuevaSitio, String nuevaLong) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editarSitio(id_codigodeSitio, nuevaDepartamento, nuevoProvincia, nuevaDistrito, nuevaUbigeo, nuevaZona, nuevaSitio, nuevaLong);
                guardarHistorial("Editó el sitio: " + id_codigodeSitio, "Nombre del Administrador", "Administrador");
                Intent intent = new Intent(Admin_sitio_editar.this, Admin_lista_Sitio.class);
                startActivity(intent);
                dialog.dismiss();

                NotificationHelper.createNotificationChannel(Admin_sitio_editar.this);
                NotificationHelper.sendNotification(Admin_sitio_editar.this, "Sitio", "sitio editado");
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

    private void editarSitio(String id_codigodeSitio, String nuevaDepartamento, String nuevoProvincia, String nuevaDistrito, String nuevaUbigeo, String nuevaZona, String nuevaSitio, String nuevaLong) {
        db.collection("sitio")
                .whereEqualTo("id_codigodeSitio", id_codigodeSitio)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            DocumentReference sitioRef = task.getResult().getDocuments().get(0).getReference();

                            // Actualizar el documento
                            sitioRef.update("id_departamento", nuevaDepartamento,
                                            "id_provincia", nuevoProvincia,
                                            "id_distrito", nuevaDistrito,
                                            "id_ubigeo", nuevaUbigeo,
                                            "id_tipo_de_zona", nuevaZona,
                                            "id_tipo_de_sitio", nuevaSitio,
                                            "id_latitud_long", nuevaLong)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Admin_sitio_editar", "Sitio actualizado con éxito");
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("Admin_sitio_editar", "Error al actualizar el sitio", e);
                                    });
                        } else {
                            Log.e("Admin_sitio_editar", "El documento con Codigo " + id_codigodeSitio + " no existe.");
                        }
                    } else {
                        Log.e("Admin_sitio_editar", "Error al obtener el documento", task.getException());
                    }
                });
    }

    private void guardarHistorial(String actividad, String usuario, String rol) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedHour = hourFormat.format(currentDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        HistorialData historial = new HistorialData(actividad, usuario, rol, formattedDate, formattedHour);

        db.collection("historialglobal")
                .add(historial)
                .addOnSuccessListener(documentReference -> {
                    // Historial guardado con éxito
                })
                .addOnFailureListener(e -> {
                    // Error al guardar el historial
                });
    }
}
