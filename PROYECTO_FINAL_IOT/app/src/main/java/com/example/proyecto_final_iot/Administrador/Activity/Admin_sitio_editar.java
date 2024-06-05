package com.example.proyecto_final_iot.Administrador.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.R;

public class Admin_sitio_editar extends AppCompatActivity {

    EditText id_codigodeSitio,id_departamento,id_provincia,id_distrito,id_ubigeo,id_tipo_de_zona,id_tipo_de_sitio,id_latitud_long;

    private String selectTipoDeZona;
    private ArrayAdapter<CharSequence> zonaAdapter;
    private String selectTipoDeSitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sitio_editar);

        id_codigodeSitio = findViewById(R.id.id_codigodeSitio);
        id_departamento = findViewById(R.id.id_departamento);
        id_provincia = findViewById(R.id.id_provincia);
        id_distrito = findViewById(R.id.id_distrito);
        id_ubigeo = findViewById(R.id.id_ubigeo);
        id_tipo_de_zona = findViewById(R.id.id_tipoDeZona);
        id_tipo_de_sitio = findViewById(R.id.id_tipoDeSitio);
        id_latitud_long = findViewById(R.id.id_latitud_long);



    }

}
