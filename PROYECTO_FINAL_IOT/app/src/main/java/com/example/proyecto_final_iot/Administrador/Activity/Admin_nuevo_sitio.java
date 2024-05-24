package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_nuevo_Data;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Reporte;
import com.example.proyecto_final_iot.Supervisor.Activity.EquiposSupervisorActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.NuevoReporteActivity;
import com.example.proyecto_final_iot.databinding.ActivityAdminNuevoSitioBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;


public class Admin_nuevo_sitio extends AppCompatActivity {

    private String selectTipoDeZona;
    private TextView textZonaSpinner;
    private Spinner zonaSpinner;
    private ArrayAdapter<CharSequence> zonaAdapter;
    private String selectTipoDeSitio;
    private TextView textSitioSpinner;
    private Spinner sitioSpinner;
    private ArrayAdapter<CharSequence> sitioAdapter;

    //---------Firebase------------
    ListenerRegistration snapshotListener;
    ActivityAdminNuevoSitioBinding binding_new_sitio;
    FirebaseFirestore db_nuevo_sitio;

    DatabaseReference mDatabase;

    Spinner spinner_zona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nuevo_sitio);
        //----Spinner-----------
        spinner_zona = findViewById(R.id.spinner_tipoDeZona);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        loadZona();

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

    public void loadZona(){
        List<Sitio_nuevo_Data> sitioNuevoData = new ArrayList<>();
        mDatabase.child("sitio").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if( snapshot.exists()){
                    for (DataSnapshot ds: snapshot.getChildren()){

                        String prueba = ds.child("tipoDeZona").getValue().toString();
                        sitioNuevoData.add(new Sitio_nuevo_Data());
                    }
                    ArrayAdapter<Sitio_nuevo_Data> arrayAdapter = new ArrayAdapter<>(Admin_nuevo_sitio.this, android.R.layout.simple_dropdown_item_1line,sitioNuevoData );
                    spinner_zona.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        String id_latitud_long = binding_new_sitio.idUbigeo.getText().toString();
        String id_provincia = binding_new_sitio.idProvincia.getText().toString();
        String tipoDeSitio = binding_new_sitio.spinnerTipoDeSitio.getContext().toString();
        String tipoDeZona = binding_new_sitio.spinnerTipoDeZona.getContext().toString();
        String id_ubigeo = binding_new_sitio.idUbigeo.getText().toString();

        Sitio_nuevo_Data sitioNuevoData = new Sitio_nuevo_Data();
        sitioNuevoData.setId_codigodeSitio(id_codigodeSitio);
        sitioNuevoData.setId_departamento(id_departamento);
        sitioNuevoData.setId_latitud_long(id_latitud_long);
        sitioNuevoData.setId_provincia(id_provincia);
        sitioNuevoData.setId_tipo_de_sitio(tipoDeSitio);
        sitioNuevoData.setId_tipo_de_zona(tipoDeZona);
        sitioNuevoData.setId_ubigeo(id_ubigeo);
        db_nuevo_sitio.collection("sitio")
                .document(id_codigodeSitio)
                .set(sitioNuevoData)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(Admin_nuevo_sitio.this, "Sitio grabado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_nuevo_sitio.this, Admin_lista_Sitio.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Admin_nuevo_sitio.this, "Algo pasó al guardar ", Toast.LENGTH_SHORT).show();
                });
    }


}