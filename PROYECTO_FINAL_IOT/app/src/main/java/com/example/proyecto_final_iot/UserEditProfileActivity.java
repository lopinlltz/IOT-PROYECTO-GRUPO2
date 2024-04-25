package com.example.proyecto_final_iot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserEditProfileActivity extends AppCompatActivity {
    private static final int REQUEST_GALLERY = 1;
    private CircleImageView circleImageView;
    private Button buttonActualizar, buttonCancelarEdit;
    private ImageButton buttonAgregarFoto;
    private EditText editTextTelefono, editTextDomicilio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        buttonActualizar = findViewById(R.id.buttonActualizar);
        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserEditProfileActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        buttonCancelarEdit = findViewById(R.id.buttonCancelarEdit);
        buttonCancelarEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserEditProfileActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

        buttonAgregarFoto = findViewById(R.id.buttonChangePhoto);
        buttonAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        editTextTelefono = findViewById(R.id.editTextPhone);
        editTextDomicilio = findViewById(R.id.editTextAddress);

        editTextTelefono.setText("999111111");
        editTextDomicilio.setText("Av. Brasil 1234, Pueblo Libre");

        editTextTelefono.requestFocus();
        editTextTelefono.setEnabled(true);
        editTextDomicilio.setEnabled(true);

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            circleImageView.setImageURI(imageUri);
        }
    }

    /*private void updateProfile() {
        // aun no c como se envia aqi losdatos al user profile
    }

    public void onUpdateButtonClick(View view) {
        // Obtener el nuevo texto de los EditText y guardar los cambios
        String nuevoTelefono = editTextTelefono.getText().toString();
        String nuevoDomicilio = editTextDomicilio.getText().toString();

        // Después de guardar los cambios, volver a la actividad UserProfileActivity
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
        finish();
    }*/

}
