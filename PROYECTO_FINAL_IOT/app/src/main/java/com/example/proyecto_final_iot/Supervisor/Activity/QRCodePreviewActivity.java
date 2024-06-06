package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.R;

public class QRCodePreviewActivity extends AppCompatActivity {
    private static final int DELAY_MILLIS = 5000;
    private ImageView qrCodeImageView;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_qr_preview);

        qrCodeImageView = findViewById(R.id.qr_code_image);
        okButton = findViewById(R.id.ok_button);

        String qrCodeBase64 = getIntent().getStringExtra("qrCodeBase64");

        if (qrCodeBase64 != null) {
            Bitmap qrCodeBitmap = base64ToBitmap(qrCodeBase64);

            qrCodeImageView.setImageBitmap(qrCodeBitmap);
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish(); // Cerrar la actividad
                    }
                }, DELAY_MILLIS);
            }
        });
    }

    private Bitmap base64ToBitmap(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}


