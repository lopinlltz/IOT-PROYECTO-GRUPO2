package com.example.proyecto_final_iot.Supervisor.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.proyecto_final_iot.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class QRCodePreviewActivity extends AppCompatActivity {
    private ImageView qrCodeImageView;
    private Button downloadButton, atrasButton;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_qr_preview);

        qrCodeImageView = findViewById(R.id.qr_code_image);
        downloadButton = findViewById(R.id.download_button);
        atrasButton = findViewById(R.id.atras_button);

        String qrFilePath = getIntent().getStringExtra("qr_file_path");

        if (qrFilePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(qrFilePath);
            qrCodeImageView.setImageBitmap(bitmap);

            downloadButton.setOnClickListener(v -> checkPermissionsAndDownloadQRCode(bitmap));
        } else {
            Toast.makeText(this, "No se pudo obtener el código QR", Toast.LENGTH_SHORT).show();
        }

        atrasButton.setOnClickListener(v -> finish());
    }

    private void checkPermissionsAndDownloadQRCode(Bitmap bitmap) {
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            downloadQRCode(bitmap);
        }*/

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                downloadQRCode(bitmap);
            }
        } else {
            downloadQRCode(bitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Bitmap bitmap = ((BitmapDrawable) qrCodeImageView.getDrawable()).getBitmap();
                downloadQRCode(bitmap);
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void downloadQRCode(Bitmap bitmap) {
        if (bitmap == null) {
            String message = "No se pudo obtener el código QR";
            Log.e("QRCodePreviewActivity", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return;
        }

        /*File qrDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "QR_Codes");

        if (!qrDir.exists() && !qrDir.mkdirs()) {
            String message = "No se pudo crear el directorio para guardar el QR";
            Log.e("QRCodePreviewActivity", message);
            Toast.makeText(this, "No se pudo crear el directorio para guardar la imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = "QRCode_" + System.currentTimeMillis() + ".png";
        File qrFile = new File(qrDir, fileName);

        try (FileOutputStream out = new FileOutputStream(qrFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            String message = "Código QR guardado en: " + qrFile.getAbsolutePath();
            Log.i("QRCodePreviewActivity", message);

            Toast.makeText(this, "Código QR guardado en: " + qrFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("QRCodePreviewActivity", "Error al guardar el código QR", e);
            Toast.makeText(this, "Error al guardar el código QR", Toast.LENGTH_SHORT).show();
        }*/


        OutputStream fos;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "QRCode_" + System.currentTimeMillis() + ".png");
                values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/QR_Codes");

                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    fos = getContentResolver().openOutputStream(uri);
                } else {
                    throw new IOException("Failed to create new MediaStore record.");
                }
            } else {
                File qrDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "QR_Codes");
                if (!qrDir.exists() && !qrDir.mkdirs()) {
                    String message = "No se pudo crear el directorio para guardar el QR";
                    Log.e("QRCodePreviewActivity", message);
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    return;
                }

                String fileName = "QRCode_" + System.currentTimeMillis() + ".png";
                File qrFile = new File(qrDir, fileName);
                fos = new FileOutputStream(qrFile);
            }

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            String message = "Código QR guardado correctamente.";
            Log.i("QRCodePreviewActivity", message);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("QRCodePreviewActivity", "Error al guardar el código QR", e);
            Toast.makeText(this, "Error al guardar el código QR", Toast.LENGTH_SHORT).show();
        }
    }
}


