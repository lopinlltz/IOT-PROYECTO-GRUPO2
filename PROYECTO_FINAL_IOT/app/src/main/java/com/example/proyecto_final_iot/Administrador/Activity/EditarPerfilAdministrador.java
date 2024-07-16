package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class EditarPerfilAdministrador extends AppCompatActivity {
    private static final String TAG = "EditarPerfilAdmin";
    private static final int GALLERY_REQUEST_CODE = 123;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private TextView nombreTextView, dniTextView, correoTextView, domicilioTextView;
    private EditText telefonoEditText;
    private ImageView profileImageView;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_perfil_administrador);

        nombreTextView = findViewById(R.id.textViewNombre);
        dniTextView = findViewById(R.id.textViewDni);
        correoTextView = findViewById(R.id.textViewCorreo);
        domicilioTextView = findViewById(R.id.textViewDomicilio);
        telefonoEditText = findViewById(R.id.telefonoEditText);
        profileImageView = findViewById(R.id.circleImageView);

        Button buttonGuardar = findViewById(R.id.buttonGuardar);
        profileImageView.setOnClickListener(v -> openGallery());

        buttonGuardar.setOnClickListener(v -> saveProfileChanges());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            loadUserProfile(currentUser);
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Glide.with(this).load(selectedImageUri).into(profileImageView);
        }
    }

    private void loadUserProfile(FirebaseUser currentUser) {
        String userEmail = currentUser.getEmail();

        if (userEmail != null) {
            db.collection("administrador")
                    .whereEqualTo("correoUser", userEmail)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                            updateUI(document);
                        } else {
                            Toast.makeText(EditarPerfilAdministrador.this, "No se encontraron datos para este usuario.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(EditarPerfilAdministrador.this, "Error al cargar datos del perfil.", Toast.LENGTH_SHORT).show());
        }
    }

    private void updateUI(DocumentSnapshot document) {
        String nombre = document.getString("nombreUser");
        String apellido = document.getString("apellidoUser");
        String dni = document.getString("dniUser");
        String correo = document.getString("correoUser");
        String telefono = document.getString("telefonoUser");
        String domicilio = document.getString("domicilioUser");
        String profileImageUrl = document.getString("dataImage");

        if (nombre != null && apellido != null) {
            nombreTextView.setText(nombre + " " + apellido);
        }
        if (dni != null) {
            dniTextView.setText(dni);
        }
        if (correo != null) {
            correoTextView.setText(correo);
        }
        if (domicilio != null) {
            domicilioTextView.setText(domicilio);
        }
        if (telefono != null) {
            telefonoEditText.setText(telefono);
        }

        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
            Glide.with(this).load(profileImageUrl).into(profileImageView);
        }
    }

    private void saveProfileChanges() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            if (userEmail != null) {
                String telefono = telefonoEditText.getText().toString();

                db.collection("administrador")
                        .whereEqualTo("correoUser", userEmail)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                                String documentId = document.getId();
                                if (selectedImageUri != null) {
                                    uploadImageAndSaveProfile(documentId, telefono);
                                } else {
                                    updateProfileWithoutImage(documentId, telefono);
                                }
                            } else {
                                Toast.makeText(EditarPerfilAdministrador.this, "No se encontraron datos para este usuario.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    private void uploadImageAndSaveProfile(String documentId, String telefono) {
        StorageReference storageRef = storage.getReference().child("profileImages/" + UUID.randomUUID().toString());
        storageRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String profileImageUrl = uri.toString();
                    db.collection("administrador").document(documentId)
                            .update("telefonoUser", telefono, "dataImage", profileImageUrl)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(EditarPerfilAdministrador.this, "Perfil actualizado correctamente.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditarPerfilAdministrador.this, Admin_lista_usuario.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> Toast.makeText(EditarPerfilAdministrador.this, "Error al actualizar el perfil.", Toast.LENGTH_SHORT).show());
                }))
                .addOnFailureListener(e -> Toast.makeText(EditarPerfilAdministrador.this, "Error al subir la imagen.", Toast.LENGTH_SHORT).show());
    }

    private void updateProfileWithoutImage(String documentId, String telefono) {
        db.collection("administrador").document(documentId)
                .update("telefonoUser", telefono)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditarPerfilAdministrador.this, "Perfil actualizado correctamente.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditarPerfilAdministrador.this, Admin_lista_usuario.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(EditarPerfilAdministrador.this, "Error al actualizar el perfil.", Toast.LENGTH_SHORT).show());
    }
}
