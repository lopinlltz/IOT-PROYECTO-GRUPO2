package com.example.proyecto_final_iot.Supervisor.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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

import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;

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

    //ImageView qrCodeImageView;
    //Button saveQRCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_detalles_equipo);

        Intent intent = getIntent();
        String sku = intent.getStringExtra("sku");
        String serie = intent.getStringExtra("serie");
        String marca = intent.getStringExtra("marca");
        String modelo =  intent.getStringExtra("modelo");
        String descripcion =  intent.getStringExtra("descripcion");
        String fecha =  intent.getStringExtra("fecha");

        /*String qrCodeBase64 = intent.getStringExtra("qrCodeBase64");
        Log.d("QR_CODE_DEBUG", "QR Code recibido: " + qrCodeBase64);

        if (qrCodeBase64 != null) {
            Log.d("EquipoDetalleActivity", "QR Code recibido: " + qrCodeBase64);
        } else {
            Log.e("EquipoDetalleActivity", "No se recibió el QR Code");
        }*/

        textViewNombreEquipo = findViewById(R.id.textViewNombreEquipo);
        textViewSku = findViewById(R.id.textViewSku);
        textViewNroSerie = findViewById(R.id.textViewNroSerie);
        textViewMarca = findViewById(R.id.textViewMarca);
        textViewModelo = findViewById(R.id.textViewModelo);
        textViewDescripcion = findViewById(R.id.textViewDescripcion);
        textViewFecha = findViewById(R.id.textViewFecha);

        //qrCodeImageView = findViewById(R.id.qr_code_image);
        //saveQRCodeButton = findViewById(R.id.save_qr_code_button);

        textViewNombreEquipo.setText(sku);
        textViewSku.setText(sku);
        textViewNroSerie.setText(serie);
        textViewMarca.setText(marca);
        textViewModelo.setText(modelo);
        textViewDescripcion.setText(descripcion);
        textViewFecha.setText(fecha);


        buttonBorrarEq =  findViewById(R.id.buttonBorrarEq);
        buttonBorrarEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquiposSupervisorActivity.class);
                ConfirmacionPopup();
            }
        });

        buttonEditarEq =  findViewById(R.id.buttonEditarEq);
        buttonEditarEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquipoEditarActivity.class);
                startActivity(intent);
            }
        });



        /*
        if (qrCodeBase64 != null) {
            Bitmap qrCodeBitmap = base64ToBitmap(qrCodeBase64);
            qrCodeImage.setImageBitmap(qrCodeBitmap);
            qrCodeImage.setVisibility(View.VISIBLE);
            saveQRCodeButton.setVisibility(View.VISIBLE);
        }

        saveQRCodeButton.setOnClickListener(v -> {
            Bitmap qrCodeBitmap = ((BitmapDrawable) qrCodeImage.getDrawable()).getBitmap();
            if (saveQRCodeToStorage(qrCodeBitmap)) {
                Toast.makeText(EquipoDetalleActivity.this, "QR Code guardado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EquipoDetalleActivity.this, "Error al guardar QR Code", Toast.LENGTH_SHORT).show();
            }
        });*/

        /*
        // Mostrar QR code
        //&& !qrCodeBase64.isEmpty()
        if (qrCodeBase64 != null ) {
            byte[] decodedString = Base64.decode(qrCodeBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            qrCodeImageView.setImageBitmap(decodedByte);
            qrCodeImageView.setVisibility(View.VISIBLE);
            saveQRCodeButton.setVisibility(View.VISIBLE);

            String qrContent = new String(decodedString);
            Log.d("QR_Content", "Contenido del QR: " + qrContent);
            //Toast.makeText(this, "Contenido del QR: " + qrContent, Toast.LENGTH_LONG).show();
        } else {
            Log.e("EquipoDetalleActivity", "No se recibió el QR Code");
            Toast.makeText(this, "Error: No se recibió el QR Code", Toast.LENGTH_SHORT).show();
            /*Bitmap decodedBitmap = null;
            try {
                byte[] decodedBytes = Base64.decode(qrCodeBase64, Base64.DEFAULT);
                decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            } catch (IllegalArgumentException e) {
                Log.e("EquipoDetalleActivity", "Error al decodificar la cadena Base64: " + e.getMessage());
            }

            if (decodedBitmap != null) {
                qrCodeImageView.setImageBitmap(decodedBitmap);
                qrCodeImageView.setVisibility(View.VISIBLE);
                saveQRCodeButton.setVisibility(View.VISIBLE);
            } else {

                Log.e("EquipoDetalleActivity", "El Bitmap decodificado del QR Code es nulo");
                Toast.makeText(this, "Error: No se pudo decodificar el QR Code", Toast.LENGTH_SHORT).show();
            }
        }

        saveQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean hasPermission = (ContextCompat.checkSelfPermission(EquipoDetalleActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermission) {
                    ActivityCompat.requestPermissions(EquipoDetalleActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_STORAGE);
                } else {
                    saveQrCodeToFile();
                }
            }
        });*/

    }

    private void ConfirmacionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de eliminar el equipo?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EquipoDetalleActivity.this, EquiposSupervisorActivity.class);
                //ConfirmacionPopup();
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

}
