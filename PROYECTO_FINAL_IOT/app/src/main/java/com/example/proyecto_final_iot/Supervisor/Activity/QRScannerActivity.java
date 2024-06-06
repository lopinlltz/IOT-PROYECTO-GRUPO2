package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRScannerActivity extends AppCompatActivity {

    private static final String TAG = QRScannerActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar el escáner de QR
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Obtener el resultado del escaneo de QR
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Si se cancela el escaneo
                Log.d(TAG, "Escaneo cancelado");
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Si se encuentra un código QR
                String qrContent = result.getContents();
                Log.d(TAG, "Contenido del código QR: " + qrContent);
                Intent intent = new Intent();
                intent.putExtra("qr_content", qrContent);
                setResult(RESULT_OK, intent);
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
