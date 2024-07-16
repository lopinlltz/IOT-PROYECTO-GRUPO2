package com.example.proyecto_final_iot.Superadmin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditarPerfilSuperadmin extends AppCompatActivity {
    private static final String TAG = "EditarPerfilSuperadmin";
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ImageView editProfileImageView;
    private TextView textViewNombre;
    private TextView editTextDni;
    private TextView editTextCorreo;
    private TextView editTextDomicilio;
    private EditText editTextTelefono;
    private String userEmail;
    private String currentCollection;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_perfil_superadmin);

        editProfileImageView = findViewById(R.id.editProfileImageView);
        textViewNombre = findViewById(R.id.textViewNombre);
        editTextDni = findViewById(R.id.editTextDni);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextDomicilio = findViewById(R.id.editTextDomicilio);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        Button buttonGuardar = findViewById(R.id.buttonGuardar);
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        ImageButton buttonsupervisor = findViewById(R.id.buttonsupervisor);
        buttonsupervisor.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilSuperadmin.this, superadmin_vista_supervisor2.class);
            startActivity(intent);
        });
        Button buttonCerrarSesion = findViewById(R.id.buttonCerrarSesion);

        buttonCerrarSesion.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(EditarPerfilSuperadmin.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilSuperadmin.this, superadmin_logs.class);
            startActivity(intent);
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilSuperadmin.this, Superadmin_vista_principal1.class);
            startActivity(intent);
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userEmail = currentUser.getEmail();
            loadUserProfile(userEmail);
        }

        editProfileImageView.setOnClickListener(v -> selectImage());
        buttonGuardar.setOnClickListener(v -> saveProfileChanges());
    }

    private void loadUserProfile(String email) {
        db.collection("administrador")
                .whereEqualTo("correoUser", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        updateProfileUI(document);
                    } else {
                        db.collection("supervisorAdmin")
                                .whereEqualTo("id_correoUser", email)
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots1 -> {
                                    if (!queryDocumentSnapshots1.isEmpty()) {
                                        DocumentSnapshot document = queryDocumentSnapshots1.getDocuments().get(0);
                                        updateProfileUI(document);
                                    } else {
                                        db.collection("superadmi")
                                                .whereEqualTo("correoUser", email)
                                                .get()
                                                .addOnSuccessListener(queryDocumentSnapshots2 -> {
                                                    if (!queryDocumentSnapshots2.isEmpty()) {
                                                        DocumentSnapshot document = queryDocumentSnapshots2.getDocuments().get(0);
                                                        updateProfileUI(document);
                                                    } else {
                                                        Toast.makeText(this, "No se encontraron datos para este usuario.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    private void updateProfileUI(DocumentSnapshot document) {
        String nombre = document.getString("nombreUser");
        String apellido = document.getString("apellidoUser");
        String dni = document.getString("dniUser");
        String correo = document.getString("correoUser");
        String telefono = document.getString("telefonoUser");
        String domicilio = document.getString("domicilioUser");
        String profileImageUrl = document.getString("dataImage");

        textViewNombre.setText(nombre + " " + apellido);
        editTextDni.setText(dni);
        editTextCorreo.setText(correo);
        editTextDomicilio.setText(domicilio);
        editTextTelefono.setText(telefono);

        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
            Glide.with(this).load(profileImageUrl).into(editProfileImageView);
        } else {
            editProfileImageView.setImageResource(R.drawable.default_profile); // imagen por defecto
        }

        // Guardar la colección actual y el ID del documento para usarlo al guardar los cambios
        currentCollection = document.getReference().getParent().getId();
        userId = document.getId();
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                editProfileImageView.setImageBitmap(bitmap);
                uploadImageToFirebase(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebase(Uri filePath) {
        if (filePath != null) {
            StorageReference ref = storageReference.child("images/" + userId);
            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        db.collection(currentCollection).document(userId).update("dataImage", downloadUrl)
                                .addOnSuccessListener(aVoid -> Log.d(TAG, "Image URL updated successfully"))
                                .addOnFailureListener(e -> Log.e(TAG, "Error updating image URL", e));
                    }))
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to upload image", e);
                        Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void saveProfileChanges() {
        String newTelefono = editTextTelefono.getText().toString();
        Map<String, Object> updates = new HashMap<>();
        updates.put("telefonoUser", newTelefono);

        db.collection(currentCollection).document(userId).update(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditarPerfilSuperadmin.this, Superadmin_vista_principal1.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error updating profile", e);
                    Toast.makeText(this, "Error updating profile", Toast.LENGTH_SHORT).show();
                });
    }
}
