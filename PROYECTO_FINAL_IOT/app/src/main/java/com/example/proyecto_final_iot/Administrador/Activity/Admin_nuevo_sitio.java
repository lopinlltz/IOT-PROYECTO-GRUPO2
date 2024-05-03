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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.proyecto_final_iot.R;

public class Admin_nuevo_sitio extends AppCompatActivity {

    private String selectTipoDeZona;
    private TextView textZonaSpinner;
    private Spinner zonaSpinner;
    private ArrayAdapter<CharSequence> zonaAdapter;
    private String selectTipoDeSitio;
    private TextView textSitioSpinner;
    private Spinner sitioSpinner;
    private ArrayAdapter<CharSequence> sitioAdapter;

    Button Atras_new_sitio;
    Button Guardar_new_sitio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nuevo_sitio);

        /*-------- Tipo de Zona--------------*/
        zonaSpinner = findViewById(R.id.spinner_tipoDeZona);
        zonaAdapter = ArrayAdapter.createFromResource(this, R.array.array_zona, R.layout.spinner_layout);
        zonaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zonaSpinner.setAdapter(zonaAdapter);
        zonaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zonaSpinner = findViewById(R.id.spinner_tipoDeZona);
                selectTipoDeZona = zonaSpinner.getSelectedItem().toString();

                int parentID = parent.getId();
                if(parentID == R.id.spinner_tipoDeZona){
                    switch (selectTipoDeZona){
                        case "Tipo de Zona" : zonaAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                            R.array.array_zona, R.layout.spinner_layout);
                        break;
                        default: break;
                    }


                }
                zonaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                zonaSpinner.setAdapter(zonaAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        /*-------- Tipo de Sitio--------------*/
        sitioSpinner = findViewById(R.id.spinner_tipoDeSitio);
        sitioAdapter = ArrayAdapter.createFromResource(this, R.array.array_sitio, R.layout.spinner_layout);
        sitioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sitioSpinner.setAdapter(sitioAdapter);
        sitioSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zonaSpinner = findViewById(R.id.spinner_tipoDeSitio);
                selectTipoDeSitio = sitioSpinner.getSelectedItem().toString();

                int parentID = parent.getId();
                if(parentID == R.id.spinner_tipoDeSitio){
                    switch (selectTipoDeSitio){
                        case "Tipo de Sitio" : sitioAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_sitio, R.layout.spinner_layout);
                            break;
                        default: break;
                    }


                }
                sitioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                sitioSpinner.setAdapter(sitioAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        Atras_new_sitio = findViewById(R.id.Atras_new_sitio);
        Atras_new_sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_nuevo_sitio.this, Admin_lista_Sitio.class);
                startActivity(intent);
            }
        });

        Guardar_new_sitio = findViewById(R.id.Guardar_new_sitio);
        Guardar_new_sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_nuevo_sitio.this, Admin_lista_Sitio.class);
                startActivity(intent);
                ConfirmacionPopup();
            }
        });

    }
    private void ConfirmacionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Admin_nuevo_sitio.this, Admin_sitio_detalles.class);
                startActivity(intent);
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
}