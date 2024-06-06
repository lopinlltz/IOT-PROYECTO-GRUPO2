package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_Data;
import com.example.proyecto_final_iot.databinding.ActivityAdminSitioDetallesBinding;
import com.github.clans.fab.FloatingActionButton;
import com.example.proyecto_final_iot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class Admin_sitio_detalles extends AppCompatActivity {

    FloatingActionButton boton_delete;
    FloatingActionButton boton_edit;
    TextView id_codigodeSitio, id_departamento, id_provincia, id_distrito, id_ubigeo,
            id_tipo_de_zona, id_tipo_de_sitio, id_latitud_long;
    String id_Documento;


    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sitio_detalles);

        id_codigodeSitio = findViewById(R.id.id_codigodeSitio);
        id_departamento = findViewById(R.id.id_departamento);
        id_provincia = findViewById(R.id.id_provincia);
        id_distrito = findViewById(R.id.id_distrito);
        id_ubigeo = findViewById(R.id.id_ubigeo);
        id_tipo_de_zona = findViewById(R.id.id_tipoDeZona_det);
        id_tipo_de_sitio = findViewById(R.id.id_tipoDeSitio_det);
        id_latitud_long = findViewById(R.id.id_latitud_long);



        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            id_codigodeSitio.setText(bundle.getString("id_codigodeSitio"));
            id_departamento.setText(bundle.getString("id_departamento"));
            id_provincia.setText(bundle.getString("id_provincia"));
            id_distrito.setText(bundle.getString("id_distrito"));
            id_ubigeo.setText(bundle.getString("id_ubigeo"));
            id_tipo_de_zona.setText(bundle.getString("id_tipo_de_zona"));
            id_tipo_de_sitio.setText(bundle.getString("id_tipo_de_sitio"));
            id_latitud_long.setText(bundle.getString("id_latitud_long"));
            id_Documento= bundle.getString("documentoID");
        }



        boton_delete = (FloatingActionButton) findViewById(R.id.boton_delete);
        boton_edit = findViewById(R.id.boton_edit);

        boton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String documentId = "test"; // Reemplaza con el ID del documento que deseas eliminar
                db.collection("sitio").document(documentId).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Manejar el éxito de la eliminación

                                Toast.makeText(Admin_sitio_detalles.this, "Deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Admin_lista_Sitio.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Manejar el fallo en la eliminación
                                Toast.makeText(Admin_sitio_detalles.this, "Error al eliminar el documento", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



        boton_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_sitio_detalles.this, Admin_sitio_editar.class);
                startActivity(intent);
            }
        });

    }



}