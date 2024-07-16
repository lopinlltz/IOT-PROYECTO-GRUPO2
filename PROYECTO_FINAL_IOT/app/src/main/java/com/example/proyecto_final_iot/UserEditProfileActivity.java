package com.example.proyecto_final_iot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserEditProfileActivity extends AppCompatActivity {
    private static final int REQUEST_GALLERY = 1;
    private static final String TAG = "UserEditProfileActivity";

    private CircleImageView circleImageView;
    private Button buttonActualizar, buttonCancelarEdit;
    private ImageButton buttonAgregarFoto;
    private EditText editTextTelefono;
    private Uri selectedImageUri;
    private  TextView editTextDomicilio;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        buttonActualizar = findViewById(R.id.buttonActualizar);
        buttonCancelarEdit = findViewById(R.id.buttonCancelarEdit);
        buttonAgregarFoto = findViewById(R.id.buttonChangePhoto);
        circleImageView = findViewById(R.id.circleImageView);
        editTextTelefono = findViewById(R.id.editTextPhone);
        editTextDomicilio = findViewById(R.id.editTextDomicilio);

        buttonActualizar.setOnClickListener(v -> saveProfileChanges());
        buttonCancelarEdit.setOnClickListener(v -> navigateToUserProfile());
        buttonAgregarFoto.setOnClickListener(v -> openGallery());

        loadUserProfile();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            circleImageView.setImageURI(selectedImageUri);
        }
    }

    private void loadUserProfile() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            if (userEmail != null) {
                db.collection("supervisorAdmin")
                        .whereEqualTo("id_correoUser", userEmail)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                                updateUI(document);
                            } else {
                                Toast.makeText(UserEditProfileActivity.this, "No se encontraron datos para este usuario.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(UserEditProfileActivity.this, "Error al cargar datos del perfil.", Toast.LENGTH_SHORT).show());
            }
        }
    }

    private void updateUI(DocumentSnapshot document) {
        String nombre = document.getString("id_nombreUser");
        String apellido = document.getString("id_apellidoUser");
        String dni = document.getString("id_dniUser");
        String correo = document.getString("id_correoUser");
        String telefono = document.getString("id_telefonoUser");
        String domicilio = document.getString("id_domicilioUser");
        String profileImageUrl = document.getString("dataImage");

        if (nombre != null && apellido != null) {
            ((TextView) findViewById(R.id.textView5)).setText(nombre + " " + apellido);
        }
        if (dni != null) {
            ((TextView) findViewById(R.id.textView2)).setText(dni);
        }
        if (correo != null) {
            ((TextView) findViewById(R.id.textView8)).setText(correo);
        }
        if (telefono != null) {
            editTextTelefono.setText(telefono);
        }
        if (domicilio != null) {
            editTextDomicilio.setText(domicilio);
        }

        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
            Glide.with(this).load(profileImageUrl).into(circleImageView);
        } else {
            circleImageView.setImageResource(R.drawable.default_profile); // Imagen por defecto si no hay URL
        }
    }

    private void saveProfileChanges() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            if (userEmail != null) {
                String telefono = editTextTelefono.getText().toString();
                String domicilio = editTextDomicilio.getText().toString();

                db.collection("supervisorAdmin")
                        .whereEqualTo("id_correoUser", userEmail)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                                String documentId = document.getId();
                                if (selectedImageUri != null) {
                                    uploadProfileImage(selectedImageUri, imageUrl -> {
                                        db.collection("supervisorAdmin").document(documentId)
                                                .update("id_telefonoUser", telefono, "id_domicilioUser", domicilio, "dataImage", imageUrl)
                                                .addOnSuccessListener(aVoid -> {
                                                    Toast.makeText(UserEditProfileActivity.this, "Perfil actualizado correctamente.", Toast.LENGTH_SHORT).show();
                                                    navigateToUserProfile();
                                                })
                                                .addOnFailureListener(e -> Toast.makeText(UserEditProfileActivity.this, "Error al actualizar el perfil.", Toast.LENGTH_SHORT).show());
                                    });
                                } else {
                                    db.collection("supervisorAdmin").document(documentId)
                                            .update("id_telefonoUser", telefono, "id_domicilioUser", domicilio)
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(UserEditProfileActivity.this, "Perfil actualizado correctamente.", Toast.LENGTH_SHORT).show();
                                                navigateToUserProfile();
                                            })
                                            .addOnFailureListener(e -> Toast.makeText(UserEditProfileActivity.this, "Error al actualizar el perfil.", Toast.LENGTH_SHORT).show());
                                }
                            } else {
                                Toast.makeText(UserEditProfileActivity.this, "No se encontraron datos para este usuario.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    private void uploadProfileImage(Uri imageUri, OnImageUploadListener listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference profileImageRef = storageRef.child("profileImages/" + UUID.randomUUID().toString());
        UploadTask uploadTask = profileImageRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> profileImageRef.getDownloadUrl().addOnSuccessListener(uri -> listener.onSuccess(uri.toString())))
                .addOnFailureListener(e -> {
                    Toast.makeText(UserEditProfileActivity.this, "Error al subir la imagen.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error al subir la imagen", e);
                });
    }

    private void navigateToUserProfile() {
        Intent intent = new Intent(UserEditProfileActivity.this, UserProfileActivity.class);
        startActivity(intent);
        finish();
    }

    interface OnImageUploadListener {
        void onSuccess(String imageUrl);
    }
}
