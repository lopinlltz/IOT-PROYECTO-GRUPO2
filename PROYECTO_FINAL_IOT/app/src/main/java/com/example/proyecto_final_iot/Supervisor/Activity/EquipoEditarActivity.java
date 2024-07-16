package com.example.proyecto_final_iot.Supervisor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Activity.superadmin_editar_administrador;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EquipoEditarActivity extends AppCompatActivity {


    private ConstraintLayout atras;
    private ConstraintLayout Guardar;
    private ImageView imageView;
    private String imagenURL;
    private Button subirimagen;
    private Uri image;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    StorageReference storageReference;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent loginIntent = new Intent(EquipoEditarActivity.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }

    private static final int PICK_IMAGE_REQUEST = 1;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    Guardar.setEnabled(true);
                    Glide.with(getApplicationContext()).load(image).into(imageView);
                }
            } else {
                Toast.makeText(EquipoEditarActivity.this, "Selecciona una imagen, por favor", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_editar_equipo);

        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String sku = intent.getStringExtra("sku");
        String serie = intent.getStringExtra("serie");
        String marca = intent.getStringExtra("marca");
        String modelo = intent.getStringExtra("modelo");
        String descripcion = intent.getStringExtra("descripcion");
        String fecha = intent.getStringExtra("fecha");


        // Referencias a los EditTexts
        TextView skuText = findViewById(R.id.id_sku);
        EditText marcaEditText = findViewById(R.id.marca);
        EditText modeloEditText = findViewById(R.id.modelo);
        EditText descripcionEditText = findViewById(R.id.descripción);
        subirimagen = findViewById(R.id.subirimagen);
        imageView = findViewById(R.id.imagensubir);

        skuText.setText(sku);
        marcaEditText.setText(marca);
        modeloEditText.setText(modelo);
        descripcionEditText.setText(descripcion);


        String imagenUrl = intent.getStringExtra("dataImage_equipo");
        if (imagenUrl != null && !imagenUrl.isEmpty()) {
            Glide.with(this).load(imagenUrl).into(imageView);
        }

        atras = findViewById(R.id.atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EquipoEditarActivity.this, EquipoDetalleActivity.class);
                intent.putExtra("modelo", modelo);
                intent.putExtra("marca", marca);
                intent.putExtra("sku", sku);
                intent.putExtra("serie", serie);
                intent.putExtra("descripcion", descripcion);
                intent.putExtra("fecha", fecha);
                v.getContext().startActivity(intent);
                startActivity(intent);
            }
        });

        Guardar = findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevaMarca = marcaEditText.getText().toString();
                String nuevoModelo = modeloEditText.getText().toString();
                String nuevaDescripcion = descripcionEditText.getText().toString();
                Log.d("EquipoEditarActivity", "Datos obtenidos para edición: " +
                        "sku=" + sku + ", nuevaMarca=" + nuevaMarca + ", nuevoModelo=" + nuevoModelo + ", nuevaDescripcion=" + nuevaDescripcion);

                ConfirmacionPopup(sku, nuevaMarca, nuevoModelo, nuevaDescripcion);
            }
        });

        subirimagen.setOnClickListener(v -> {
            Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
            pickImageIntent.setType("image/*");
            activityResultLauncher.launch(pickImageIntent);
        });

    }


    private void ConfirmacionPopup(String sku, String nuevaMarca, String nuevoModelo, String nuevaDescripcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            if (image != null) {
                uploadImagenEquipo(sku, nuevaMarca, nuevoModelo, nuevaDescripcion, image);
                Log.e("imagenEditarConfirmacionPopup", String.valueOf(image));
            } else {
                editarEquipo(sku, nuevaMarca, nuevoModelo, nuevaDescripcion, null);
            }
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void uploadImagenEquipo(String sku, String nuevaMarca, String nuevoModelo, String nuevaDescripcion, Uri imageUri) {
        if (imageUri == null) {
            editarEquipo(sku, nuevaMarca, nuevoModelo, nuevaDescripcion, null);
            return;
        }

        StorageReference reference = storageReference.child("FotosdeEquipos/" + UUID.randomUUID().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(EquipoEditarActivity.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        reference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
            uriTask.addOnCompleteListener(uriTaskResult -> {
                dialog.dismiss();
                if (uriTaskResult.isSuccessful()) {
                    Uri urlImageEquipo = uriTaskResult.getResult();
                    if (urlImageEquipo != null) {
                        imagenURL = urlImageEquipo.toString();
                        editarEquipo(sku, nuevaMarca, nuevoModelo, nuevaDescripcion, imagenURL);
                    } else {
                        Toast.makeText(EquipoEditarActivity.this, "Error al obtener la URL de la imagen", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EquipoEditarActivity.this, "Error al subir la imagen: " + uriTaskResult.getException(), Toast.LENGTH_SHORT).show();
                    Log.e("EquipoEditarActivity", "Error al obtener la URL de la imagen", uriTaskResult.getException());
                }
            });
        }).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(EquipoEditarActivity.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
        });
    }
    private void editarEquipo(String sku, String nuevaMarca, String nuevoModelo, String nuevaDescripcion, String imagenUrl) {
        db.collection("equipo")
                .whereEqualTo("sku", sku)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentReference equipoRef = task.getResult().getDocuments().get(0).getReference();
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("marca", nuevaMarca);
                        updates.put("modelo", nuevoModelo);
                        updates.put("descripcion", nuevaDescripcion);
                        if (imagenUrl != null) {
                            updates.put("imagenUrl", imagenUrl);
                            Log.e("imagenUrleditarEquipo",imagenUrl);
                        }

                        equipoRef.update(updates)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("EquipoEditarActivity", "Equipo actualizado con éxito");
                                    NotificationHelper.createNotificationChannel(EquipoEditarActivity.this);
                                    NotificationHelper.sendNotification(EquipoEditarActivity.this, "Equipos", "equipo editado");

                                    Intent intent = new Intent(EquipoEditarActivity.this, EquipoDetalleActivity.class);
                                    intent.putExtra("sku", sku);
                                    intent.putExtra("marca", nuevaMarca);
                                    intent.putExtra("modelo", nuevoModelo);
                                    intent.putExtra("descripcion", nuevaDescripcion);
                                    intent.putExtra("dataImage_equipo", imagenUrl); // Pass the image URL
                                    Log.e("imagenUrlequipoRef.update(updates)",imagenUrl);
                                    startActivity(intent);
                                    finish();
                                })
                                .addOnFailureListener(e -> Log.e("EquipoEditarActivity", "Error al actualizar el equipo", e));
                    } else {
                        Log.e("EquipoEditarActivity", "El documento con SKU " + sku + " no existe.");
                    }
                });
    }
}
