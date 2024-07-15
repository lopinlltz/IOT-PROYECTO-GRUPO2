package com.example.proyecto_final_iot.Supervisor.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Entity.HistorialData;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EquipoDetalleActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_STORAGE = 112;
    TextView textViewSku;
    TextView textViewNroSerie;
    TextView textViewMarca;
    TextView textViewModelo;
    TextView textViewDescripcion;
    TextView textViewFecha;
    TextView textViewNombreEquipo;
    Button buttonBorrarEq;
    Button buttonEditarEq;

    Button buttonReportes;
    FirebaseFirestore db;
    ImageView dataImage_equipo;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(EquipoDetalleActivity.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }

    private static final String TAG = "EquipoDetalleActivity";
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_detalles_equipo);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String sku = intent.getStringExtra("sku");
        String serie = intent.getStringExtra("serie");
        String marca = intent.getStringExtra("marca");
        String modelo =  intent.getStringExtra("modelo");
        String descripcion =  intent.getStringExtra("descripcion");
        String fecha =  intent.getStringExtra("fecha");



        FirebaseApp.initializeApp(this);

        textViewNombreEquipo = findViewById(R.id.textViewNombreEquipo);
        textViewSku = findViewById(R.id.textViewSku);
        textViewNroSerie = findViewById(R.id.textViewNroSerie);
        textViewMarca = findViewById(R.id.textViewMarca);
        textViewModelo = findViewById(R.id.textViewModelo);
        textViewDescripcion = findViewById(R.id.textViewDescripcion);
        textViewFecha = findViewById(R.id.textViewFecha);
        dataImage_equipo = findViewById(R.id.imagen_equipo_super);

        textViewNombreEquipo.setText(sku);
        textViewSku.setText(sku);
        textViewNroSerie.setText(serie);
        textViewMarca.setText(marca);
        textViewModelo.setText(modelo);
        textViewDescripcion.setText(descripcion);
        textViewFecha.setText(fecha);

       // loadQRCode(sku);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            dataImage_equipo.setImageURI(Uri.parse(bundle.getString("dataImage_equipo")));
            if (bundle != null && bundle.getString("dataImage_equipo") != null) {
                String imageUrl = bundle.getString("dataImage_equipo");
                Glide.with(this)
                        .load(imageUrl)
                        .into(dataImage_equipo);
            }

        }

        buttonBorrarEq =  findViewById(R.id.buttonBorrarEq);
        buttonBorrarEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquiposSupervisorActivity.class);
                ConfirmacionPopup(sku);
            }
        });

        buttonEditarEq =  findViewById(R.id.buttonEditarEq);
        buttonEditarEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquipoEditarActivity.class);
                intent.putExtra("sku", sku);
                intent.putExtra("serie", serie);
                intent.putExtra("marca", marca);
                intent.putExtra("modelo", modelo);
                intent.putExtra("descripcion", descripcion);
                intent.putExtra("fecha", fecha);
                startActivity(intent);

            }
        });

        buttonReportes = findViewById(R.id.buttonReportes);
        buttonReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipoDetalleActivity.this, ReporteActivity.class);
                intent.putExtra("sku", sku);
                startActivity(intent);

            }
        });

        Button buttonVerQR = findViewById(R.id.buttonVerQR);
        //uttonVerQR.setOnClickListener(v -> verQRCode());

    }

    //TODO ESTO ES PARA QR
   /* private void loadQRCode(String sku) {
        db.collection("equipo").whereEqualTo("sku", sku).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String qrCodeUrl = document.getString("qrCodeUrl");
                    if (qrCodeUrl != null && !qrCodeUrl.isEmpty()) {
                        Log.d(TAG, "QR Code URL found: " + qrCodeUrl);
                        //Picasso.get().load(qrCodeUrl).into(qrCodeImageView);
                        Picasso.get().load(qrCodeUrl).into(qrCodeImageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "QR Code loaded successfully.");
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e(TAG, "Error loading QR Code", e);
                            }
                        });
                    } else {
                        Log.w(TAG, "No QR Code URL found for SKU: " + sku);
                    }
                }
            } else {
                Log.e(TAG, "Error getting documents: ", task.getException());
                Toast.makeText(EquipoDetalleActivity.this, "Error al cargar el código QR", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
/*
    public void verQRCode() {
        Bitmap bitmap = ((BitmapDrawable) qrCodeImageView.getDrawable()).getBitmap();
        String qrFilePath = saveQRToFile(bitmap);
        Intent intent = new Intent(this, QRCodePreviewActivity.class);
        //intent.putExtra("qr_bitmap", bitmap);
        intent.putExtra("qr_file_path", qrFilePath);
        startActivity(intent);
    }*/
    //new ga
    private String saveQRToFile(Bitmap bitmap) {
        String fileName = "QRCode_" + System.currentTimeMillis() + ".png";
        File qrDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "QR_Codes");
        if (!qrDir.exists() && !qrDir.mkdirs()) {
            Toast.makeText(this, "No se pudo crear el directorio para guardar el QR", Toast.LENGTH_SHORT).show();
            return null;
        }

        File qrFile = new File(qrDir, fileName);
        try (FileOutputStream out = new FileOutputStream(qrFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return qrFile.getAbsolutePath();
        } catch (IOException e) {
            Log.e("SitioDetalleActivity", "Error al guardar el QR", e);
            Toast.makeText(this, "Error al guardar el QR", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    //HASTA AQUI

    private void ConfirmacionPopup(String equipoId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de eliminar el equipo?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquiposSupervisorActivity.class);
                borrarEquipoPorSKU( equipoId);
                startActivity(intent);
                dialog.dismiss();

                NotificationHelper.createNotificationChannel(EquipoDetalleActivity.this);
                NotificationHelper.sendNotification(EquipoDetalleActivity.this, "Equipos", "equipo borrado");
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



    /*private Bitmap base64ToBitmap(String base64) {
        byte[] decodedString = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);
        return android.graphics.BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    private boolean saveQRCodeToStorage(Bitmap bitmap) {
        File path = getExternalFilesDir(null);
        File qrCodeFile = new File(path, "QRCode.png");
        try (FileOutputStream outputStream = new FileOutputStream(qrCodeFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }*/


    /*private void saveQrCodeToFile() {
        qrCodeImageView.setDrawingCacheEnabled(true);
        Bitmap bitmap = ((BitmapDrawable) qrCodeImageView.getDrawable()).getBitmap();
        File filePath = new File(getExternalFilesDir(null), "qrCode.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(this, "QR Code guardado en " + filePath.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar el QR Code", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Llamar al método de la superclase
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveQrCodeToFile();
            } else {
                Toast.makeText(this, "Permiso denegado para escribir en el almacenamiento", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
    private void borrarEquipoPorSKU(String sku) {
        // Realizar una consulta para encontrar el documento con el SKU deseado
        db.collection("equipo")
                .whereEqualTo("sku", sku)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Obtener el ID del documento que coincide con el SKU
                            String equipoId = document.getId();
                            // Eliminar el documento usando el ID obtenido
                            db.collection("equipo").document(equipoId)
                                    .delete()
                                    .addOnSuccessListener(unused -> {
                                        // Correcto
                                        guardarHistorial();
                                        Toast.makeText(this, "Equipo con SKU " + sku + " eliminado correctamente", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error
                                        Toast.makeText(this, "No se pudo eliminar el equipo con SKU " + sku, Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        // Error al realizar la consulta
                        Toast.makeText(this, "Error al buscar el equipo con SKU " + sku, Toast.LENGTH_SHORT).show();
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
        historial.setActivityName("Borraste un equipo");
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


}
