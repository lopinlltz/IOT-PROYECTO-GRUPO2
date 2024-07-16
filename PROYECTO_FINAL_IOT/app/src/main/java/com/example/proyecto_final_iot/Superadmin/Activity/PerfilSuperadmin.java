package com.example.proyecto_final_iot.Superadmin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PerfilSuperadmin extends AppCompatActivity {
    private static final String TAG = "PerfilSuperadmin";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView nombreTextView;
    private TextView dniTextView;
    private TextView correoTextView;
    private TextView telefonoTextView;
    private TextView domicilioTextView;
    private ImageView profileImageView;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent loginIntent = new Intent(PerfilSuperadmin.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        } else {
            loadUserProfile(currentUser);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_superadmin);

        nombreTextView = findViewById(R.id.textView5);
        dniTextView = findViewById(R.id.textView2);
        correoTextView = findViewById(R.id.textView8);
        telefonoTextView = findViewById(R.id.textView9);
        domicilioTextView = findViewById(R.id.textView10);
        profileImageView = findViewById(R.id.circleImageView);

        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        ImageButton buttonsupervisor = findViewById(R.id.buttonsupervisor);
        Button buttonEditar = findViewById(R.id.buttonEditar);
        buttonsupervisor.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilSuperadmin.this, superadmin_vista_supervisor2.class);
            startActivity(intent);
        });
        buttonEditar.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilSuperadmin.this, EditarPerfilSuperadmin.class);
            startActivity(intent);
        });

        Button buttonCerrarSesion = findViewById(R.id.buttonCerrarSesion);

        buttonCerrarSesion.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(PerfilSuperadmin.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilSuperadmin.this, superadmin_logs.class);
            startActivity(intent);
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilSuperadmin.this, Superadmin_vista_principal1.class);
            startActivity(intent);
        });

        db = FirebaseFirestore.getInstance();
    }

    private void loadUserProfile(FirebaseUser currentUser) {
        String userEmail = currentUser.getEmail();

        if (userEmail != null) {
            Log.d(TAG, "Usuario autenticado: " + userEmail);
            searchInCollection("administrador", "correoUser", userEmail);
        } else {
            Log.e(TAG, "El correo del usuario autenticado es null.");
        }
    }

    private void searchInCollection(String collectionName, String emailField, String email) {
        Log.d(TAG, "Buscando en la colección: " + collectionName + " para el correo: " + email);
        db.collection(collectionName)
                .whereEqualTo(emailField, email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        Log.d(TAG, "Documento encontrado en " + collectionName);
                        updateProfileUI(document, collectionName);
                    } else {
                        Log.d(TAG, "No se encontró documento en " + collectionName);
                        switch (collectionName) {
                            case "administrador":
                                searchInCollection("supervisorAdmin", "id_correoUser", email);
                                break;
                            case "supervisorAdmin":
                                searchInCollection("superadmi", "correoUser", email);
                                break;
                            case "superadmi":
                                Toast.makeText(PerfilSuperadmin.this, "No se encontraron datos para este usuario.", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error al buscar en " + collectionName, e);
                    Toast.makeText(PerfilSuperadmin.this, "Error al buscar en " + collectionName, Toast.LENGTH_SHORT).show();
                });
    }

    private void updateProfileUI(DocumentSnapshot document, String collectionName) {
        String nombre = document.getString("nombreUser");
        String apellido = document.getString("apellidoUser");
        String dni = document.getString("dniUser");
        String correo = document.getString("correoUser");
        String telefono = document.getString("telefonoUser");
        String domicilio = document.getString("domicilioUser");
        String profileImageUrl = document.getString("dataImage");

        String rol = "";
        switch (collectionName) {
            case "administrador":
                rol = "Administrador";
                break;
            case "supervisorAdmin":
                rol = "Supervisor";
                break;
            case "superadmi":
                rol = "Superadmin";
                break;
        }

        Log.d(TAG, "Actualizando la UI con los datos del documento.");

        nombreTextView.setText(nombre + " " + apellido + " - " + rol); // Concatenamos el rol al nombre y apellido
        dniTextView.setText(dni);
        correoTextView.setText(correo);
        telefonoTextView.setText(telefono);
        domicilioTextView.setText(domicilio);

        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
            Glide.with(this).load(profileImageUrl).into(profileImageView);
        } else {
            profileImageView.setImageResource(R.drawable.default_profile); // imagen por defecto
        }
    }
}
