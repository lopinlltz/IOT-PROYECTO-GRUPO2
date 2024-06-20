package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_nuevo_Data;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.HistorialData;
import com.example.proyecto_final_iot.databinding.ActivityAdminNuevoSitioBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Admin_nuevo_sitio extends AppCompatActivity {

    //---------Firebase------------
    ListenerRegistration snapshotListener;
    ActivityAdminNuevoSitioBinding binding_new_sitio;
    FirebaseFirestore db_nuevo_sitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nuevo_sitio);

        binding_new_sitio = ActivityAdminNuevoSitioBinding.inflate(getLayoutInflater());
        setContentView(binding_new_sitio.getRoot());

        db_nuevo_sitio = FirebaseFirestore.getInstance();
        binding_new_sitio.GuardarNewSitio.setOnClickListener(view -> {
            ConfirmacionPopup();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (snapshotListener != null)
            snapshotListener.remove();
    }

    private void ConfirmacionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardarSitio();
                dialog.dismiss();
                NotificationHelper.createNotificationChannel(Admin_nuevo_sitio.this);
                NotificationHelper.sendNotification(Admin_nuevo_sitio.this, "Sitio", "Sitio guardado");
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

    private void guardarSitio() {
        String id_codigodeSitio = binding_new_sitio.idCodigodeSitio.getText().toString();
        String id_departamento = binding_new_sitio.idDepartamento.getText().toString();
        String id_distrito = binding_new_sitio.idDistrito.getText().toString();
        String id_latitud_long = binding_new_sitio.idLatitudLong.getText().toString();
        String id_provincia = binding_new_sitio.idProvincia.getText().toString();
        String id_tipo_de_sitio = binding_new_sitio.idTipoDeSitio.getText().toString();
        Log.d("Debug", "id_tipo_de_sitio: " + binding_new_sitio.idTipoDeSitio.getText().toString());
        String id_tipo_de_zona = binding_new_sitio.idTipoDeZona.getText().toString();
        String id_ubigeo = binding_new_sitio.idUbigeo.getText().toString();

        Sitio_nuevo_Data sitioNuevoData = new Sitio_nuevo_Data();
        sitioNuevoData.setId_codigodeSitio(id_codigodeSitio);
        sitioNuevoData.setId_departamento(id_departamento);
        sitioNuevoData.setId_distrito(id_distrito);
        sitioNuevoData.setId_latitud_long(id_latitud_long);
        sitioNuevoData.setId_provincia(id_provincia);
        sitioNuevoData.setId_tipo_de_sitio(id_tipo_de_sitio);
        sitioNuevoData.setId_tipo_de_zona(id_tipo_de_zona);
        sitioNuevoData.setId_ubigeo(id_ubigeo);
        db_nuevo_sitio.collection("sitio")
                .document(id_codigodeSitio)
                .set(sitioNuevoData)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(Admin_nuevo_sitio.this, "Sitio grabado", Toast.LENGTH_SHORT).show();
                    guardarHistorial("Creó un nuevo sitio: " + id_codigodeSitio, "Nombre del Administrador", "Administrador");
                    Intent intent = new Intent(Admin_nuevo_sitio.this, Admin_lista_Sitio.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Admin_nuevo_sitio.this, "Algo pasó al guardar ", Toast.LENGTH_SHORT).show();
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

        db_nuevo_sitio.collection("historialglobal")
                .add(historial)
                .addOnSuccessListener(documentReference -> {
                    // Historial guardado con éxito
                })
                .addOnFailureListener(e -> {
                    // Error al guardar el historial
                });
    }
}
