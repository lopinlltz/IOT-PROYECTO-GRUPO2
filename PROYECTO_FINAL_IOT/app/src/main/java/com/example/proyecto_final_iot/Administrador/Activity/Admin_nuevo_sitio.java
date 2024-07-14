package com.example.proyecto_final_iot.Administrador.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_nuevo_Data;
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.HistorialData;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.example.proyecto_final_iot.databinding.ActivityAdminNuevoSitioBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Admin_nuevo_sitio extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    //---------Firebase------------
    ListenerRegistration snapshotListener;
    ActivityAdminNuevoSitioBinding binding_new_sitio;
    FirebaseFirestore db_nuevo_sitio;
    GoogleMap mMap;
    TextView txtLatitud, txtLongitud;
    private Map<String, String> ubigeoMap;

    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(Admin_nuevo_sitio.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nuevo_sitio);



        binding_new_sitio = ActivityAdminNuevoSitioBinding.inflate(getLayoutInflater());
        setContentView(binding_new_sitio.getRoot());

        db_nuevo_sitio = FirebaseFirestore.getInstance();

        txtLatitud = binding_new_sitio.idLatitudLatitud;
        txtLongitud = binding_new_sitio.idLatitudLong;


        binding_new_sitio.GuardarNewSitio.setOnClickListener(view -> {
            ConfirmacionPopup();
        });

        // Configurar el fragmento del mapa
        setupMapFragment();

    // Inicializar el mapa de ubigeo
        cargarUbigeo();

        // Configurar el listener para el botón "Mostrar Ubigeo"
        Button btnMostrarUbigeo = findViewById(R.id.btnMostrarUbigeo);
        btnMostrarUbigeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarUbigeo();
            }
        });


        // Agregar TextWatcher para el campo de texto del departamento
        binding_new_sitio.idDepartamento.addTextChangedListener(new TextWatcher() {
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

    private void mostrarUbigeo() {
        String departamento = binding_new_sitio.idDepartamento.getText().toString();
        String provincia = binding_new_sitio.idProvincia.getText().toString();
        String distrito = binding_new_sitio.idDistrito.getText().toString();

        // Obtener el código ubigeo
        String ubigeo = obtenerCodigoUbigeo(departamento, provincia, distrito);
        if (ubigeo != null) {
            binding_new_sitio.idUbigeo.setText(ubigeo);
        } else {
            binding_new_sitio.idUbigeo.setError("No se encontró un código ubigeo para la combinación proporcionada");
        }
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
                binding_new_sitio.idLatitudLatitud.setText(String.valueOf(address.getLatitude()));
                binding_new_sitio.idLatitudLong.setText(String.valueOf(address.getLongitude()));
            } else {
                Toast.makeText(this, "Departamento no encontrado", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al buscar el departamento", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (snapshotListener != null)
            snapshotListener.remove();
    }
    private void ConfirmacionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estás seguro de guardar los cambios?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Validar los datos antes de guardar
                if (validarDatosSitio()) {
                    try {
                        guardarSitio();
                        dialog.dismiss();
                        NotificationHelper.createNotificationChannel(Admin_nuevo_sitio.this);
                        NotificationHelper.sendNotification(Admin_nuevo_sitio.this, "Sitio", "Sitio guardado");
                    } catch (Exception e) {
                        Toast.makeText(Admin_nuevo_sitio.this, "Error al guardar el sitio", Toast.LENGTH_SHORT).show();
                        Log.e("ErrorGuardarSitio", e.getMessage(), e);
                    }
                } else {
                    Toast.makeText(Admin_nuevo_sitio.this, "Por favor, corrija los errores antes de guardar", Toast.LENGTH_SHORT).show();
                }
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
    private void cargarUbigeo() {
        ubigeoMap = new HashMap<>();
        // Agrega aquí los códigos ubigeo. Ejemplo:
        ubigeoMap.put("Lima,Lima,Lima", "140101");
        ubigeoMap.put("Lima,Lima,Ancon", "140102");
        ubigeoMap.put("Lima,Lima,Ate", "140103");
        ubigeoMap.put("Lima,Lima,Breña", "140104");
        ubigeoMap.put("Lima,Lima,Carabayllo", "140105");
        ubigeoMap.put("Lima,Lima,Comas", "140106");
        ubigeoMap.put("Lima,Lima,Chaclacayo", "140107");
        ubigeoMap.put("Lima,Lima,Chorrillos", "140108");
        ubigeoMap.put("Lima,Lima,La Victoria", "140109");
        ubigeoMap.put("Lima,Lima,La Molina", "140110");
        ubigeoMap.put("Lima,Lima,Lince", "140111");
        ubigeoMap.put("Lima,Lima,Lurigancho", "140112");
        ubigeoMap.put("Lima,Lima,Lurin", "140113");
        ubigeoMap.put("Lima,Lima,Magdalena del Mar", "140114");
        ubigeoMap.put("Lima,Lima,Miraflores", "140115");
        ubigeoMap.put("Lima,Lima,Pachacamac", "140116");
        ubigeoMap.put("Lima,Lima,Pueblo Libre", "140117");
        ubigeoMap.put("Lima,Lima,Pucusana", "140118");
        ubigeoMap.put("Lima,Lima,Puente Piedra", "140119");
        ubigeoMap.put("Lima,Lima,Punta Hermosa", "140120");
        ubigeoMap.put("Lima,Lima,Punta Negra", "140121");
        ubigeoMap.put("Lima,Lima,Rimac", "140122");
        ubigeoMap.put("Lima,Lima,San Bartolo", "140123");
        ubigeoMap.put("Lima,Lima,San Isidro", "140124");
        ubigeoMap.put("Ayacucho,Huamanga,Ayacucho", "050101");
        ubigeoMap.put("Amazonas,Chachapoyas,Chachapoyas", "010101");
        ubigeoMap.put("Ancash,Huaraz,Huaraz", "020101");
        ubigeoMap.put("Apurimac,Abancay,Abancay", "030101");
        ubigeoMap.put("Arequipa,Arequipa,Arequipa", "040101");
        ubigeoMap.put("Cusco,Cusco,Cusco", "070101");



    }
    //https://www.reniec.gob.pe/Adherentes/jsp/ListaUbigeos.jsp

    private void guardarSitio() {
        String id_codigodeSitio = binding_new_sitio.idCodigodeSitio.getText().toString();
        String id_departamento = binding_new_sitio.idDepartamento.getText().toString();
        String id_provincia = binding_new_sitio.idProvincia.getText().toString();
        String id_distrito = binding_new_sitio.idDistrito.getText().toString();
        String id_latitud_long = binding_new_sitio.idLatitudLong.getText().toString();
        String id_latitud_latitud = binding_new_sitio.idLatitudLatitud.getText().toString();
        String id_tipo_de_sitio = binding_new_sitio.idTipoDeSitio.getText().toString();
        String id_tipo_de_zona = binding_new_sitio.idTipoDeZona.getText().toString();

        // Obtener el código ubigeo
        String id_ubigeo = obtenerCodigoUbigeo(id_departamento, id_provincia, id_distrito);
        if (id_ubigeo == null) {
            binding_new_sitio.idUbigeo.setError("No se encontró un código ubigeo para la combinación proporcionada");
            return; // Detener si no se encontró un ubigeo
        }

        // Validar los datos
        if (!validarDatosSitio()) {
            return; // Si los datos no son válidos, no continuar con el guardado
        }

        Sitio_nuevo_Data sitioNuevoData = new Sitio_nuevo_Data();
        sitioNuevoData.setId_codigodeSitio(id_codigodeSitio);
        sitioNuevoData.setId_departamento(id_departamento);
        sitioNuevoData.setId_provincia(id_provincia);
        sitioNuevoData.setId_distrito(id_distrito);
        sitioNuevoData.setId_latitud_long(id_latitud_long);
        sitioNuevoData.setId_latitud_latitud(id_latitud_latitud);
        sitioNuevoData.setId_tipo_de_sitio(id_tipo_de_sitio);
        sitioNuevoData.setId_tipo_de_zona(id_tipo_de_zona);
        sitioNuevoData.setId_ubigeo(id_ubigeo);

        db_nuevo_sitio.collection("sitio")
                .document(id_codigodeSitio)
                .set(sitioNuevoData)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(Admin_nuevo_sitio.this, "Sitio grabado", Toast.LENGTH_SHORT).show();
                    guardarHistorial("Creó un nuevo sitio: " + id_codigodeSitio, "Samantha", "Administrador");
                    Intent intent = new Intent(Admin_nuevo_sitio.this, Admin_lista_Sitio.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Admin_nuevo_sitio.this, "Algo pasó al guardar ", Toast.LENGTH_SHORT).show();
                });
    }

    private String obtenerCodigoUbigeo(String departamento, String provincia, String distrito) {
        String key = departamento + "," + provincia + "," + distrito;
        return ubigeoMap.get(key);
    }

    private boolean validarDatosSitio() {
        boolean isValid = true;

        String id_codigodeSitio = binding_new_sitio.idCodigodeSitio.getText().toString();
        String id_departamento = binding_new_sitio.idDepartamento.getText().toString();
        String id_distrito = binding_new_sitio.idDistrito.getText().toString();
        String id_latitud_long = binding_new_sitio.idLatitudLong.getText().toString();
        String id_latitud_latitud = binding_new_sitio.idLatitudLatitud.getText().toString();
        String id_provincia = binding_new_sitio.idProvincia.getText().toString();
        String id_tipo_de_sitio = binding_new_sitio.idTipoDeSitio.getText().toString();
        String id_tipo_de_zona = binding_new_sitio.idTipoDeZona.getText().toString();
        String id_ubigeo = binding_new_sitio.idUbigeo.getText().toString();

        if (id_codigodeSitio.isEmpty()) {
            binding_new_sitio.idCodigodeSitio.setError("El código de sitio no puede estar vacío");
            isValid = false;
        }

        if (id_departamento.isEmpty()) {
            binding_new_sitio.idDepartamento.setError("El departamento no puede estar vacío");
            isValid = false;
        }

        if (id_distrito.isEmpty()) {
            binding_new_sitio.idDistrito.setError("El distrito no puede estar vacío");
            isValid = false;
        }

        if (id_latitud_long.isEmpty()) {
            binding_new_sitio.idLatitudLong.setError("La longitud no puede estar vacía");
            isValid = false;
        }

        if (id_latitud_latitud.isEmpty()) {
            binding_new_sitio.idLatitudLatitud.setError("La latitud no puede estar vacía");
            isValid = false;
        }

        if (id_provincia.isEmpty()) {
            binding_new_sitio.idProvincia.setError("La provincia no puede estar vacía");
            isValid = false;
        }

        if (id_tipo_de_sitio.isEmpty() || (!id_tipo_de_sitio.equalsIgnoreCase("Movil") && !id_tipo_de_sitio.equalsIgnoreCase("Fijo"))) {
            binding_new_sitio.idTipoDeSitio.setError("El tipo de sitio debe ser 'Movil' o 'Fijo'");
            isValid = false;
        }

        if (id_tipo_de_zona.isEmpty() || (!id_tipo_de_zona.equalsIgnoreCase("Urbana") && !id_tipo_de_zona.equalsIgnoreCase("Rural"))) {
            binding_new_sitio.idTipoDeZona.setError("El tipo de zona debe ser 'Urbana' o 'Rural'");
            isValid = false;
        }

        if (id_ubigeo.isEmpty()) {
            binding_new_sitio.idUbigeo.setError("El ubigeo no puede estar vacío");
            isValid = false;
        }

        return isValid;
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


public void onMapReady(@NonNull GoogleMap googleMap) {
    mMap = googleMap;

    // Configurar listeners de clic
    mMap.setOnMapClickListener(this);
    mMap.setOnMapLongClickListener(this);

    // Establecer la posición inicial del mapa
    LatLng peru = new LatLng(-11.9867052, -77.0179864);
    mMap.addMarker(new MarkerOptions().position(peru).title("Perú"));
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(peru, 10));

    // Configurar la interfaz de usuario del mapa
    mMap.getUiSettings().setRotateGesturesEnabled(true);
    mMap.getUiSettings().setZoomControlsEnabled(true);
}

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        txtLatitud.setText(String.valueOf(latLng.latitude));
        txtLongitud.setText(String.valueOf(latLng.longitude));

        mMap.clear();
        LatLng peru = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(peru).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(peru));
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        txtLatitud.setText(String.valueOf(latLng.latitude));
        txtLongitud.setText(String.valueOf(latLng.longitude));
        mMap.clear();
        LatLng peru = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(peru).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(peru));
    }
}
