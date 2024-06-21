package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.Supervisor.Activity.EquipoDetalleActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.EquiposSupervisorActivity;
import com.example.proyecto_final_iot.databinding.ActivityAdminSitioDetallesBinding;
import com.github.clans.fab.FloatingActionButton;
import com.example.proyecto_final_iot.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class Admin_sitio_detalles extends AppCompatActivity implements OnMapReadyCallback{

    FloatingActionButton boton_delete;
    FloatingActionButton boton_edit;
    TextView id_codigodeSitio_tw, id_departamento_tw, id_provincia_tw, id_distrito_tw, id_ubigeo_tw,
            id_tipo_de_zona_tw, id_tipo_de_sitio_tw, id_latitud_long_tw,id_latitud_latitud_tw;


    GoogleMap mMap;
    private DatabaseReference databaseReference;
    FirebaseFirestore db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sitio_detalles);
        db = FirebaseFirestore.getInstance();

        id_codigodeSitio_tw= findViewById(R.id.id_codigodeSitio);
        id_departamento_tw = findViewById(R.id.id_departamento);
        id_provincia_tw = findViewById(R.id.id_provincia);
        id_distrito_tw = findViewById(R.id.id_distrito);
        id_ubigeo_tw = findViewById(R.id.id_ubigeo);
        id_tipo_de_zona_tw = findViewById(R.id.id_tipoDeZona_det);
        id_tipo_de_sitio_tw = findViewById(R.id.id_tipoDeSitio_det);
        id_latitud_long_tw = findViewById(R.id.id_latitud_long);
        id_latitud_latitud_tw = findViewById(R.id.id_latitud_latitud);

        Intent intent = getIntent();
        String id_codigodeSitio = intent.getStringExtra("id_codigodeSitio");
        String id_departamento = intent.getStringExtra("id_departamento");
        String id_provincia = intent.getStringExtra("id_provincia");
        String id_distrito =  intent.getStringExtra("id_distrito");
        String id_ubigeo =  intent.getStringExtra("id_ubigeo");
        String id_tipo_de_zona =  intent.getStringExtra("id_tipo_de_zona");
        String id_tipo_de_sitio =  intent.getStringExtra("id_tipo_de_sitio");
        String id_latitud_long =  intent.getStringExtra("id_latitud_long");
        String id_latitud_latitud =  intent.getStringExtra("id_latitud_latitud");

        id_codigodeSitio_tw.setText(id_codigodeSitio);
        id_departamento_tw.setText(id_departamento);
        id_provincia_tw.setText(id_provincia);
        id_distrito_tw.setText(id_distrito);
        id_ubigeo_tw.setText(id_ubigeo);
        id_tipo_de_zona_tw.setText(id_tipo_de_zona);
        id_tipo_de_sitio_tw.setText(id_tipo_de_sitio);
        id_latitud_long_tw.setText(id_latitud_long);
        id_latitud_latitud_tw.setText(id_latitud_latitud);



        boton_delete = findViewById(R.id.boton_delete);
        boton_edit = findViewById(R.id.boton_edit);

        boton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_sitio_detalles.this, Admin_lista_Sitio.class);
                ConfirmacionPopup(id_codigodeSitio);
                }
        });

        boton_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_sitio_detalles.this, Admin_sitio_editar.class);
                intent.putExtra("id_codigodeSitio", id_codigodeSitio);
                intent.putExtra("id_departamento", id_departamento);
                intent.putExtra("id_provincia", id_provincia);
                intent.putExtra("id_distrito", id_distrito);
                intent.putExtra("id_ubigeo", id_ubigeo);
                intent.putExtra("id_tipo_de_zona", id_tipo_de_zona);
                intent.putExtra("id_tipo_de_sitio", id_tipo_de_sitio);
                intent.putExtra("id_latitud_long", id_latitud_long);
                intent.putExtra("id_latitud_latitud", id_latitud_latitud);

                startActivity(intent);

            }
        });
    }

    private void setupMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_admin);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.map_admin, mapFragment)
                    .commit();
        }
        mapFragment.getMapAsync(this);
    }
    private void ConfirmacionPopup(String sitioID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de eliminar el sitio?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                borrarSitioporCodigo(sitioID);

                Intent intent = new Intent(Admin_sitio_detalles.this, Admin_lista_Sitio.class);
                intent.putExtra("sitioIDEliminado", sitioID);
                setResult(RESULT_OK, intent);
                finish();

                // Enviar notificación
                NotificationHelper.createNotificationChannel(Admin_sitio_detalles.this);
                NotificationHelper.sendNotification(Admin_sitio_detalles.this, "Sitio", "Sitio borrado");

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

    private void borrarSitioporCodigo(String id_codigodeSitio) {

        db.collection("sitio")
                .whereEqualTo("id_codigodeSitio", id_codigodeSitio)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String sitio_ID = document.getId();
                            db.collection("sitio").document(sitio_ID)
                                    .delete()
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(this, "Sitio con Codigo " + id_codigodeSitio + " eliminado correctamente", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error
                                        Toast.makeText(this, "No se pudo eliminar el Sitio con Codigo " + id_codigodeSitio, Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } /*else {
                        // Error al realizar la consulta
                        Toast.makeText(this, "Error al buscar el equipo con SKU " + sku, Toast.LENGTH_SHORT).show();
                    }*/
                });
    }
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Establecer la posición inicial del mapa
        LatLng peru = new LatLng(-11.9867052, -77.0179864);
        mMap.addMarker(new MarkerOptions().position(peru).title("Perú"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(peru, 10));


        // Desactivar gestos para que el usuario no pueda interactuar con el mapa
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }



}