package com.example.proyecto_final_iot.Administrador.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.HistorialData;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Admin_sitio_editar extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener{

    EditText id_departamento_et, id_provincia_et, id_distrito_et, id_ubigeo_et, id_tipo_de_zona_et, id_tipo_de_sitio_et;
    TextView id_codigodeSitio_tv , id_latitud_long_et, id_latitud_latitud_et ;

    FirebaseFirestore db;
    FloatingActionButton boton_guardar_sitio;
    FloatingActionButton boton_atras_sitio;

    GoogleMap mMap;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(Admin_sitio_editar.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }
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
        String id_latitud_latitud = intent.getStringExtra("id_latitud_latitud");

        id_codigodeSitio_tv = findViewById(R.id.id_codigodeSitio);
        id_departamento_et = findViewById(R.id.id_departamento);
        id_provincia_et = findViewById(R.id.id_provincia);
        id_distrito_et = findViewById(R.id.id_distrito);
        id_ubigeo_et = findViewById(R.id.id_ubigeo);
        id_tipo_de_zona_et = findViewById(R.id.id_tipoDeZona);
        id_tipo_de_sitio_et = findViewById(R.id.id_tipoDeSitio);
        id_latitud_long_et = findViewById(R.id.id_latitud_long);
        id_latitud_latitud_et = findViewById(R.id.id_latitud_latitud);

        id_codigodeSitio_tv.setText(id_codigodeSitio);
        id_departamento_et.setText(id_departamento);
        id_provincia_et.setText(id_provincia);
        id_distrito_et.setText(id_distrito);
        id_ubigeo_et.setText(id_ubigeo);
        id_tipo_de_zona_et.setText(id_tipo_de_zona);
        id_tipo_de_sitio_et.setText(id_tipo_de_sitio);
        id_latitud_long_et.setText(id_latitud_long);
        id_latitud_latitud_et.setText(id_latitud_latitud);

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
                intent.putExtra("id_latitud_latitud", id_latitud_latitud);
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
                String nuevaLat = id_latitud_latitud_et.getText().toString();

                ConfirmacionPopup(id_codigodeSitio, nuevaDepartamento, nuevoProvincia, nuevaDistrito, nuevaUbigeo,nuevaZona,nuevaSitio, nuevaLong, nuevaLat);
            }
        });
        // Configurar el fragmento del mapa
        setupMapFragment();
        id_departamento_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String departamento = s.toString();
                if (!departamento.isEmpty()) {
                    centerMapOnDepartamento(departamento);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
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

    private void centerMapOnDepartamento(String departamento) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(departamento, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                id_latitud_latitud_et.setText(String.valueOf(address.getLatitude()));
                id_latitud_long_et.setText(String.valueOf(address.getLongitude()));
            } else {
                Toast.makeText(this, "Departamento no encontrado", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al buscar el departamento", Toast.LENGTH_SHORT).show();
        }
    }


    private void ConfirmacionPopup(String id_codigodeSitio, String nuevaDepartamento, String nuevoProvincia, String nuevaDistrito, String nuevaUbigeo, String nuevaZona, String nuevaSitio, String nuevaLong, String nuevaLat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editarSitio(id_codigodeSitio, nuevaDepartamento, nuevoProvincia, nuevaDistrito, nuevaUbigeo, nuevaZona, nuevaSitio, nuevaLong, nuevaLat);
                guardarHistorial("Editó el sitio: " + id_codigodeSitio, "Samantha", "Administrador");
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

    private void editarSitio(String id_codigodeSitio, String nuevaDepartamento, String nuevoProvincia, String nuevaDistrito, String nuevaUbigeo, String nuevaZona, String nuevaSitio, String nuevaLong, String nuevaLat) {
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
                                            "id_latitud_long", nuevaLong,
                                            "id_latitud_latitud", nuevaLat)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Admin_sitio_editar", "Sitio actualizado con éxito");

                                        Toast.makeText(Admin_sitio_editar.this, "Usuario actualizado con éxito", Toast.LENGTH_SHORT).show();


                                        id_codigodeSitio_tv.setText(id_codigodeSitio);
                                        id_departamento_et.setText(nuevaDepartamento);
                                        id_provincia_et.setText(nuevoProvincia);
                                        id_distrito_et.setText(nuevaDistrito);
                                        id_ubigeo_et.setText(nuevaUbigeo);
                                        id_tipo_de_zona_et.setText(nuevaZona);
                                        id_tipo_de_sitio_et.setText(nuevaSitio);
                                        id_latitud_long_et.setText(nuevaLong);
                                        id_latitud_latitud_et.setText(nuevaLat);

                                        Intent intent = new Intent(Admin_sitio_editar.this, Admin_lista_Sitio.class);
                                        startActivity(intent);


                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("Admin_sitio_editar", "Error al actualizar el sitio", e);
                                    });
                        } else {
                            Log.e("Admin_sitio_editar", "El documento con Codigo " + id_codigodeSitio + " no existe.");
                        }
                    } else {
                        Log.e("Admin_sitio_editar ", "Error al obtener el documento", task.getException());
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


    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Configurar listeners de clic
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        // Convert the coordinates from TextView to double
        double latitud = Double.parseDouble(id_latitud_latitud_et.getText().toString());
        double longitud = Double.parseDouble(id_latitud_long_et.getText().toString());

        // Centrar el mapa en las coordenadas del sitio
        LatLng sitioLocation = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(sitioLocation).title("Ubicación del Sitio"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sitioLocation, 15));

        // Configurar la interfaz de usuario del mapa
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        id_latitud_latitud_et.setText(String.valueOf(latLng.latitude));
        id_latitud_long_et.setText(String.valueOf(latLng.longitude));

        mMap.clear();
        LatLng peru = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(peru).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(peru));
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        id_latitud_latitud_et.setText(String.valueOf(latLng.latitude));
        id_latitud_long_et.setText(String.valueOf(latLng.longitude));
        mMap.clear();
        LatLng peru = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(peru).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(peru));
    }

}
