package com.example.proyecto_final_iot.Superadmin.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;
import java.util.regex.Pattern;

public class superadmin_nuevo_administrador extends AppCompatActivity {
    private FirebaseFirestore db;
    private EditText nombre;
    private EditText apellido;
    private EditText dni;
    private EditText correo;
    private EditText telefono;
    private EditText domicilio;
    private Button uploadImagen, subirimagen;
    private ImageView imageView;
    private String imagenURL;
    private Uri image;

    private static final int PICK_IMAGE_REQUEST = 1;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    uploadImagen.setEnabled(true);
                    Glide.with(getApplicationContext()).load(image).into(imageView);
                }
            } else {
                Toast.makeText(superadmin_nuevo_administrador.this, "Selecciona una imagen, por favor", Toast.LENGTH_SHORT).show();
            }
        }
    });

    // ---------Firebase------------
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_nuevo_administrador);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        dni = findViewById(R.id.dni);
        correo = findViewById(R.id.correo);
        telefono = findViewById(R.id.telefono);
        domicilio = findViewById(R.id.domicilio);
        imageView = findViewById(R.id.imagensubir);
        subirimagen = findViewById(R.id.subirimagen);
        uploadImagen = findViewById(R.id.button5);

        subirimagen.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            activityResultLauncher.launch(intent);
        });

        uploadImagen.setOnClickListener(v -> {
            if (validarCampos()) {
                showConfirmationDialog();
            }
        });
    }

    private boolean validarCampos() {
        String nombreString = nombre.getText().toString().trim();
        String apellidoString = apellido.getText().toString().trim();
        String dniString = dni.getText().toString().trim();
        String correoString = correo.getText().toString().trim();
        String telefonoString = telefono.getText().toString().trim();
        String domicilioString = domicilio.getText().toString().trim();

        Pattern nombreApellidoPattern = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
        Pattern dniPattern = Pattern.compile("^\\d{8}$");
        Pattern telefonoPattern = Pattern.compile("^\\d{9}$");

        if (nombreString.isEmpty() || !nombreApellidoPattern.matcher(nombreString).matches()) {
            Toast.makeText(this, "Nombre inválido. Solo letras y espacios son permitidos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (apellidoString.isEmpty() || !nombreApellidoPattern.matcher(apellidoString).matches()) {
            Toast.makeText(this, "Apellido inválido. Solo letras y espacios son permitidos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dniString.isEmpty() || !dniPattern.matcher(dniString).matches()) {
            Toast.makeText(this, "DNI inválido. Debe tener 8 dígitos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (correoString.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correoString).matches()) {
            Toast.makeText(this, "Correo electrónico inválido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (telefonoString.isEmpty() || !telefonoPattern.matcher(telefonoString).matches()) {
            Toast.makeText(this, "Teléfono inválido. Debe tener 9 dígitos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (domicilioString.isEmpty()) {
            Toast.makeText(this, "Domicilio no puede estar vacío.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar");
        builder.setMessage("¿Está seguro que quiere guardar?");
        builder.setPositiveButton("Sí", (dialog, which) -> {
            uploadImagen(image);
            dialog.dismiss();
            NotificationHelper.createNotificationChannel(superadmin_nuevo_administrador.this);
            NotificationHelper.sendNotification(superadmin_nuevo_administrador.this, "Usuarios", "Nuevo administrador creado");
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void uploadImagen(Uri imageUri) {
        if (imageUri == null) {
            guardarAdministrador(null);
            return;
        }

        StorageReference reference = storageReference.child("FotosdeAdministradores/" + UUID.randomUUID().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(superadmin_nuevo_administrador.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        reference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!uriTask.isComplete());
            Uri urlImage = uriTask.getResult();
            imagenURL = urlImage.toString();
            dialog.dismiss();
            guardarAdministrador(imagenURL);
            Toast.makeText(superadmin_nuevo_administrador.this, "Imagen subida exitosamente", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(superadmin_nuevo_administrador.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    private void guardarAdministrador(String imagenUrl) {
        String nombreString = nombre.getText().toString().trim();
        String apellidoString = apellido.getText().toString().trim();
        String dniString = dni.getText().toString().trim();
        String correoString = correo.getText().toString().trim();
        String telefonoString = telefono.getText().toString().trim();
        String domicilioString = domicilio.getText().toString().trim();

        Admin administrador = new Admin(
                null,  // ID será generado por Firestore
                nombreString,
                apellidoString,
                dniString,
                correoString,
                telefonoString,
                domicilioString,
                imagenUrl,
                "activo"  // ejemplo de status
        );

        db.collection("administrador")
                .add(administrador)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(superadmin_nuevo_administrador.this, "Administrador creado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(superadmin_nuevo_administrador.this, Superadmin_vista_principal1.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(superadmin_nuevo_administrador.this, "No se creó el administrador", Toast.LENGTH_SHORT).show();
                });
    }
}
