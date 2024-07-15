package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.EquipoData;
import com.example.proyecto_final_iot.Supervisor.Entity.HistorialData;
import com.example.proyecto_final_iot.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;


import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class EquipoNuevoActivity extends AppCompatActivity {

    private static final String TAG = "EquipoNuevoActivity";
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
    private Spinner sitioSpinner;
    Button boton_upload;
    ImageView imagen_upload;
    String imagenURL_equipo;
    Uri image_equipo;
    private  StorageReference storageReference;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(EquipoNuevoActivity.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }

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
        sitioSpinner = findViewById(R.id.sitio_spinner);
        populateSitioSpinner();

        Guardar =  findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                String sitioString = sitioSpinner.getSelectedItem().toString().trim();

                if (skuString.isEmpty() || serieString.isEmpty() || marcaString.isEmpty() ||
                        modeloString.isEmpty() || descripcionString.isEmpty() || sitioString.isEmpty() || fechaRegistroString.isEmpty()) {
                    Toast.makeText(EquipoNuevoActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validación de la imagen seleccionada
                if (image_equipo == null) {
                    Toast.makeText(EquipoNuevoActivity.this, "Por favor, seleccione una imagen", Toast.LENGTH_SHORT).show();
                    return;
                }

                checkSkuAndSave(skuString, serieString, marcaString, modeloString, descripcionString, fechaRegistroString, sitioString);
            }
        });

    }

    private void checkSkuAndSave(String skuString, String serieString, String marcaString, String modeloString, String descripcionString, String fechaRegistroString, String sitioString) {
        db.collection("your_collection_name")  // Reemplaza "your_collection_name" con el nombre de tu colección
                .whereEqualTo("sku", skuString)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                Toast.makeText(EquipoNuevoActivity.this, "El SKU ingresado ya existe. Por favor, ingrese un SKU diferente.", Toast.LENGTH_SHORT).show();
                            } else {
                                ConfirmacionPopup(skuString, serieString, marcaString, modeloString, descripcionString, fechaRegistroString, sitioString);
                            }
                        } else {
                            Toast.makeText(EquipoNuevoActivity.this, "Error al verificar el SKU", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void populateSitioSpinner() {
        List<String> sitios = new ArrayList<>();
        db.collection("sitio")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            sitios.add(document.getString("id_codigodeSitio"));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sitios);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sitioSpinner.setAdapter(adapter);
                    } else {
                        Toast.makeText(EquipoNuevoActivity.this, "Error al cargar sitios", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void ConfirmacionPopup(String skuString,String  serieString, String marcaString,String  modeloString,String  descripcionString, String fechaRegistroString,String sitioString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                uploadImagenEquipo(skuString, serieString, marcaString, modeloString, descripcionString, fechaRegistroString,sitioString,image_equipo);
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

    private void uploadImagenEquipo(String skuString,String  serieString, String marcaString,String  modeloString,String  descripcionString, String fechaRegistroString,String sitioString,Uri image) {
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
                guardarEquipo(skuString, serieString, marcaString, modeloString, descripcionString, fechaRegistroString,sitioString);  // Llamar a guardarEquipo() después de obtener la URL de la imagen

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(EquipoNuevoActivity.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarEquipo(String skuString,String  serieString, String marcaString,String  modeloString,String  descripcionString, String fechaRegistroString,String sitioString) {

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

                                                    //PARA QR
                                                    try {
                                                        Log.d(TAG, "Generando código QR para SKU: " + skuString);
                                                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                                        Bitmap bitmap = barcodeEncoder.encodeBitmap("SKU:" + skuString, BarcodeFormat.QR_CODE, 500, 500);

                                                        if (bitmap != null) {
                                                            Log.d(TAG, "Código QR generado correctamente");
                                                            saveQRCodeToFirebase(bitmap, skuString, equipo);
                                                            //Log.d("CrearEquipo", "QR Code generado correctamente");


                                                        } else {
                                                            Log.e(TAG, "Error al generar el código QR");
                                                        }

                                                    } catch (Exception e) {
                                                        //e.printStackTrace();
                                                        Log.e(TAG, "Excepción al generar el código QR", e);
                                                    }

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


    //PARA GUARDAR QR
    private void saveQRCodeToFirebase(Bitmap bitmap, String sku, Equipo equipo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference qrCodeRef = FirebaseStorage.getInstance().getReference("qrcodes").child(sku + ".png");
        UploadTask uploadTask = qrCodeRef.putBytes(data);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Log.d(TAG, "QR Code subido a Firebase Storage");
            qrCodeRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String qrCodeUrl = uri.toString();
                equipo.setQrCodeUrl(qrCodeUrl);
                Log.d(TAG, "URL del QR Code obtenida: " + qrCodeUrl);

                db.collection("equipo")
                        .add(equipo)
                        .addOnSuccessListener(unused -> {
                            Log.d(TAG, "Equipo creado correctamente en Firestore");
                            guardarHistorial();
                            Toast.makeText(EquipoNuevoActivity.this, "Equipo creado correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EquipoNuevoActivity.this, EquiposSupervisorActivity.class);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> {
                            Log.e(TAG, "Error al crear el equipo en Firestore", e);
                            Toast.makeText(EquipoNuevoActivity.this, "No se creó el equipo", Toast.LENGTH_SHORT).show();
                        });
            }).addOnFailureListener(e -> {
                Log.e(TAG, "Error al obtener la URL del QR Code", e);
                Toast.makeText(EquipoNuevoActivity.this, "Error al obtener la URL del QR Code", Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error al subir el QR Code a Firebase Storage", e);
            Toast.makeText(EquipoNuevoActivity.this, "Error al subir el código QR", Toast.LENGTH_SHORT).show();
        });
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void guardarHistorial() {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedHour = hourFormat.format(currentDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        HistorialData historial = new HistorialData();
        historial.setActivityName("Guardate un nuevo equipo");
        historial.setSupervisorName(currentUser.getEmail());
        historial.setDate(formattedDate);
        historial.setHour(formattedHour);

        db.collection("historial")
                .add(historial)
                .addOnSuccessListener(documentReference -> {

                })
                .addOnFailureListener(e -> {

                });

    }

}
