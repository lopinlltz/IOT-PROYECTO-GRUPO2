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
import android.widget.Button;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Admin_sitio_editar extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener{

    EditText id_departamento_et, id_provincia_et, id_distrito_et, id_tipo_de_zona_et, id_tipo_de_sitio_et;
    TextView id_codigodeSitio_tv , id_latitud_long_et, id_latitud_latitud_et,id_ubigeo_et ;

    FirebaseFirestore db;
    FloatingActionButton boton_guardar_sitio;
    FloatingActionButton boton_atras_sitio;

    GoogleMap mMap;
    private Map<String, String> ubigeoMap;

    private void addUppercaseTextWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (!input.equals(input.toUpperCase())) {
                    editText.removeTextChangedListener(this); // Remove listener to avoid infinite loop
                    editText.setText(input.toUpperCase());
                    editText.setSelection(editText.getText().length());
                    editText.addTextChangedListener(this); // Add listener back
                }
            }
        });
    }

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

    private void mostrarUbigeo() {
        try {
            String nuevaDepartamento = id_departamento_et.getText().toString().toLowerCase().trim();
            String nuevoProvincia = id_provincia_et.getText().toString().toLowerCase().trim();
            String nuevaDistrito = id_distrito_et.getText().toString().toLowerCase().trim();

            Log.d("mostrarUbigeo", "Departamento: " + nuevaDepartamento + ", Provincia: " + nuevoProvincia + ", Distrito: " + nuevaDistrito);

            // Obtener el código ubigeo
            String ubigeo = obtenerCodigoUbigeo(nuevaDepartamento, nuevoProvincia, nuevaDistrito);
            if (ubigeo != null) {
                id_ubigeo_et.setText(ubigeo);
                Log.d("mostrarUbigeo", "Ubigeo encontrado: " + ubigeo);
            } else {
                id_ubigeo_et.setError("No se encontró un código ubigeo para la combinación proporcionada");
                Log.d("mostrarUbigeo", "No se encontró un código ubigeo para la combinación proporcionada");
            }
        } catch (Exception e) {
            Log.e("mostrarUbigeo", "Error al obtener el código ubigeo", e);
            Toast.makeText(this, "Error al obtener el código ubigeo", Toast.LENGTH_SHORT).show();
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
        builder.setTitle("¿Estás seguro de guardar los cambios?");

        // Use an array to hold nuevaUbigeo so it can be updated within the inner class
        final String[] updatedUbigeo = {nuevaUbigeo};

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtener el ubigeo actualizado
                updatedUbigeo[0] = id_ubigeo_et.getText().toString().trim();

                // Validar los datos antes de guardar
                if (validarDatosSitio(nuevaDepartamento, nuevoProvincia, nuevaDistrito, updatedUbigeo[0], nuevaZona, nuevaSitio, nuevaLong, nuevaLat)) {
                    editarSitio(id_codigodeSitio, nuevaDepartamento, nuevoProvincia, nuevaDistrito, updatedUbigeo[0], nuevaZona, nuevaSitio, nuevaLong, nuevaLat);
                    dialog.dismiss();
                } else {
                    Toast.makeText(Admin_sitio_editar.this, "Por favor, corrija los errores antes de guardar", Toast.LENGTH_SHORT).show();
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

    private boolean validarDatosSitio(String departamento, String provincia, String distrito, String ubigeo, String zona, String sitio, String longitud, String latitud) {
        boolean isValid = true;

        if (departamento.isEmpty()) {
            id_departamento_et.setError("El departamento no puede estar vacío");
            isValid = false;
        }

        if (provincia.isEmpty()) {
            id_provincia_et.setError("La provincia no puede estar vacía");
            isValid = false;
        }

        if (distrito.isEmpty()) {
            id_distrito_et.setError("El distrito no puede estar vacío");
            isValid = false;
        }

        if (ubigeo.isEmpty()) {
            id_ubigeo_et.setError("El ubigeo no puede estar vacío");
            isValid = false;
        }

        if (zona.isEmpty() || (!zona.equalsIgnoreCase("Urbana") && !zona.equalsIgnoreCase("Rural"))) {
            id_tipo_de_zona_et.setError("El tipo de zona debe ser 'Urbana' o 'Rural'");
            isValid = false;
        }

        if (sitio.isEmpty() || (!sitio.equalsIgnoreCase("Movil") && !sitio.equalsIgnoreCase("Fijo"))) {
            id_tipo_de_sitio_et.setError("El tipo de sitio debe ser 'Movil' o 'Fijo'");
            isValid = false;
        }

        if (longitud.isEmpty()) {
            id_latitud_long_et.setError("La longitud no puede estar vacía");
            isValid = false;
        }

        if (latitud.isEmpty()) {
            id_latitud_latitud_et.setError("La latitud no puede estar vacía");
            isValid = false;
        }

        return isValid;
    }

    //https://www.reniec.gob.pe/Adherentes/jsp/ListaUbigeos.jsp
    private void cargarUbigeo() {
        ubigeoMap = new HashMap<>();
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

    private String obtenerCodigoUbigeo(String departamento, String provincia, String distrito) {
        String key = departamento + "," + provincia + "," + distrito;
        key = key.trim().toLowerCase();
        for (Map.Entry<String, String> entry : ubigeoMap.entrySet()) {
            if (entry.getKey().toLowerCase().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
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
