package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.proyecto_final_iot.Equipo;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.EquipoData;
import com.example.proyecto_final_iot.Supervisor.Entity.HistorialData;
import com.example.proyecto_final_iot.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_nuevo_equipo);

        atras =  findViewById(R.id.atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EquipoNuevoActivity.this,EquiposSupervisorActivity.class));
            }
        });

        //BD

        db = FirebaseFirestore.getInstance();


        Guardar =  findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                guardarEquipo();
                dialog.dismiss();

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

        // Validar que el SKU no exista en la base de datos
        db.collection("equipo")
                .whereEqualTo("sku", skuString)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {
                            // Validar que el sitio exista en la base de datos
                            db.collection("sitio")
                                    .whereEqualTo("id_codigodeSitio", sitioString)
                                    .get()
                                    .addOnCompleteListener(siteTask -> {
                                        if (siteTask.isSuccessful()) {
                                            if (!siteTask.getResult().isEmpty()) {
                                                // El sitio existe, podemos guardar el equipo
                                                Equipo equipo = new Equipo();

                                                equipo.setSku(skuString);
                                                equipo.setSerie(serieString);
                                                equipo.setMarca(marcaString);
                                                equipo.setModelo(modeloString);
                                                equipo.setDescripcion(descripcionString);
                                                equipo.setFechaRegistro(fechaRegistroString);
                                                equipo.setId_codigodeSitio(sitioString); // Asegúrate de que el modelo Equipo tenga un campo sitio



                            // Generar QR code
                            String qrContent = "SKU: " + skuString + ", Serie: " + serieString;
                            Bitmap qrCodeBitmap = generateQRCode(qrContent);

                            if (qrCodeBitmap != null) {
                                Log.d("CrearEquipo", "QR Code generado correctamente");

                                // Convertir Bitmap a base64
                                String qrCodeBase64 = bitmapToBase64(qrCodeBitmap);
                                equipo.setQrCode(qrCodeBase64);

                               Intent intent = new Intent(EquipoNuevoActivity.this, QRCodePreviewActivity.class);

                                // Agregar el qrCodeBase64 como extra en el Intent
                                intent.putExtra("qrCodeBase64", qrCodeBase64);

                                // Iniciar la actividad EquipoDetalleActivity
                                startActivity(intent);

                            } else {
                                Log.e("CrearEquipo", "Error al generar el QR Code");
                            }



                            /*db.collection("equipo")
                                    .add(equipo)
                                    .addOnSuccessListener(unused -> {
                                        // Correcto
                                        Toast.makeText(EquipoNuevoActivity.this, "Equipo creado correctamente", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(EquipoNuevoActivity.this, EquiposSupervisorActivity.class);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error
                                        Toast.makeText(EquipoNuevoActivity.this, "No se creó el equipo", Toast.LENGTH_SHORT).show();*/

                                                db.collection("equipo")
                                                        .add(equipo)
                                                        .addOnSuccessListener(unused -> {
                                                            // Correcto
                                                            guardarHistorial();
                                                            Toast.makeText(EquipoNuevoActivity.this, "Equipo creado correctamente", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(EquipoNuevoActivity.this, EquiposSupervisorActivity.class);
                                                            startActivity(intent);
                                                        })
                                                        .addOnFailureListener(e -> {
                                                            // Error
                                                            Toast.makeText(EquipoNuevoActivity.this, "No se creó el equipo", Toast.LENGTH_SHORT).show();
                                                        });
                                            } else {
                                                // El sitio no existe
                                                Toast.makeText(EquipoNuevoActivity.this, "El sitio no existe, por favor ingrese un sitio válido", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            // Error al consultar Firebase
                                            Toast.makeText(EquipoNuevoActivity.this, "Error al verificar el sitio en Firebase", Toast.LENGTH_SHORT).show();
                                        }

                                    });
                        } else {
                            // El SKU ya existe
                            Toast.makeText(EquipoNuevoActivity.this, "El SKU ya existe, por favor ingrese un SKU diferente", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error al consultar Firebase
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
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
