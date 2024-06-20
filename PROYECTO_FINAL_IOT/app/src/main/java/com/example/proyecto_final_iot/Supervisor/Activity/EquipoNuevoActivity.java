package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.Administrador.Activity.Admin_nuevo_usuario;
import com.example.proyecto_final_iot.Equipo;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.EquipoData;
import com.example.proyecto_final_iot.Supervisor.Entity.HistorialData;
import com.example.proyecto_final_iot.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class EquipoNuevoActivity extends AppCompatActivity {

    private ConstraintLayout atras;
    private ConstraintLayout Guardar;

    //CONEXIÓN BD
    FirebaseFirestore db;

    // Guaradar:
    private EditText sku;
    private EditText serie;
    private EditText marca;
    private EditText modelo;
    private EditText desccripcion;
    private EditText fechaRegistro;
    private EditText sitio;

    Button boton_upload;
    ImageView imagen_upload;
    String imagenURL_equipo;
    Uri image_equipo;
    private  StorageReference storageReference;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image_equipo = result.getData().getData();
                    boton_upload.setEnabled(true);
                    Glide.with(getApplicationContext()).load(image_equipo).into(imagen_upload);
                }
            }else{
                Toast.makeText(EquipoNuevoActivity.this, "Selecciona una imagen, porfavor", Toast.LENGTH_SHORT).show();
            }

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_nuevo_equipo);
        imagen_upload = findViewById(R.id.imagen_upload);
        boton_upload = findViewById(R.id.boton_upload);

        boton_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });



        atras =  findViewById(R.id.atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EquipoNuevoActivity.this,EquiposSupervisorActivity.class));
            }
        });

        //BD

        db = FirebaseFirestore.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        Guardar =  findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmacionPopup();
            }
        });

    }

    private void uploadImagenEquipo(Uri image) {
        StorageReference reference = storageReference.child("FotosdeEquipos/" + UUID.randomUUID().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(EquipoNuevoActivity.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage_equipo = uriTask.getResult();
                imagenURL_equipo = urlImage_equipo.toString();
                dialog.dismiss();
                guardarEquipo();  // Llamar a guardarEquipo() después de obtener la URL de la imagen
                Toast.makeText(EquipoNuevoActivity.this, "Imagen subida exitosamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(EquipoNuevoActivity.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ConfirmacionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardarEquipo();
                dialog.dismiss();
                uploadImagenEquipo(image_equipo);
                NotificationHelper.createNotificationChannel(EquipoNuevoActivity.this);
                NotificationHelper.sendNotification(EquipoNuevoActivity.this, "Equipos", "equipo creado");
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

    private void guardarEquipo() {
        sku = findViewById(R.id.id_sku);
        String skuString = sku.getText().toString().trim();

        serie = findViewById(R.id.serie);
        String serieString = serie.getText().toString().trim();

        marca = findViewById(R.id.marca);
        String marcaString = marca.getText().toString().trim();

        modelo = findViewById(R.id.modelo);
        String modeloString = modelo.getText().toString().trim();

        desccripcion = findViewById(R.id.descripcion);
        String descripcionString = desccripcion.getText().toString().trim();

        fechaRegistro = findViewById(R.id.fecha_de_registro);
        String fechaRegistroString = fechaRegistro.getText().toString().trim();

        sitio = findViewById(R.id.sitio);
        String sitioString = sitio.getText().toString().trim();

        if (skuString.isEmpty() || serieString.isEmpty() || marcaString.isEmpty() ||
                modeloString.isEmpty() || descripcionString.isEmpty() || sitioString.isEmpty() || fechaRegistroString.isEmpty()) {
            Toast.makeText(EquipoNuevoActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("equipo")
                .whereEqualTo("sku", skuString)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            db.collection("sitio")
                                    .whereEqualTo("id_codigodeSitio", sitioString)
                                    .get()
                                    .addOnCompleteListener(siteTask -> {
                                        if (siteTask.isSuccessful()) {
                                            if (!siteTask.getResult().isEmpty()) {
                                                Equipo equipo = new Equipo();

                                                equipo.setSku(skuString);
                                                equipo.setSerie(serieString);
                                                equipo.setMarca(marcaString);
                                                equipo.setModelo(modeloString);
                                                equipo.setDescripcion(descripcionString);
                                                equipo.setFechaRegistro(fechaRegistroString);
                                                equipo.setId_codigodeSitio(sitioString);

                                                if (imagenURL_equipo != null) {
                                                    equipo.setDataImage_equipo(imagenURL_equipo);
                                                    Log.e("imagendetalle", imagenURL_equipo);
                                                    db.collection("equipo")
                                                            .add(equipo)
                                                            .addOnSuccessListener(unused -> {
                                                                guardarHistorial();
                                                                Toast.makeText(EquipoNuevoActivity.this, "Equipo creado correctamente", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(EquipoNuevoActivity.this, EquiposSupervisorActivity.class);
                                                                startActivity(intent);
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                Toast.makeText(EquipoNuevoActivity.this, "No se creó el equipo", Toast.LENGTH_SHORT).show();
                                                            });
                                                } else {
                                                    Log.e("imagendetalle", "URL de la imagen es null");
                                                }

                                            } else {
                                                Toast.makeText(EquipoNuevoActivity.this, "El sitio no existe, por favor ingrese un sitio válido", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(EquipoNuevoActivity.this, "Error al verificar el sitio en Firebase", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(EquipoNuevoActivity.this, "El SKU ya existe, por favor ingrese un SKU diferente", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EquipoNuevoActivity.this, "Error al verificar el SKU en Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void guardarHistorial() {

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedHour = hourFormat.format(currentDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        HistorialData historial = new HistorialData();
        historial.setActivityName("Guardate un nuevo equipo");
        historial.setSupervisorName("Joselin");
        historial.setDate(formattedDate);
        historial.setHour(formattedHour);

        db.collection("historial")
                .add(historial)
                .addOnSuccessListener(documentReference -> {

                })
                .addOnFailureListener(e -> {

                });

    }

/*
    private Bitmap generateQRCode(String content) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            BitMatrix bitMatrix = barcodeEncoder.encode(content, BarcodeFormat.QR_CODE, 400, 400);
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            Log.e("GenerateQRCode", "Error al generar el QR code: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }*/

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
