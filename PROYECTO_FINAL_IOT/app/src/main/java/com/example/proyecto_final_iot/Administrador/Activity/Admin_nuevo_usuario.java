package com.example.proyecto_final_iot.Administrador.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioListAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.HistorialData;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.example.proyecto_final_iot.databinding.ActivityAdminNuevoUsuarioBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import java.util.List;
import java.util.Objects;

public class Admin_nuevo_usuario extends AppCompatActivity {

    Button uploadImagen, selecImage;
    ImageView imageView;
    String imagenURL;
    Uri image;
    private List<Supervisor_Data> dataList;
    private static final int PICK_IMAGE_REQUEST = 1;
    UsuarioListAdminAdapter adapter;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(Admin_nuevo_usuario.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    uploadImagen.setEnabled(true);
                    Glide.with(getApplicationContext()).load(image).into(imageView);
                    Log.d("SelectedImage", "URI de la imagen seleccionada: " + image.toString());
                }
            } else {
                Toast.makeText(Admin_nuevo_usuario.this, "Selecciona una imagen, por favor", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @SuppressLint("CutPasteId")
    //---------Firebase------------
    ListenerRegistration snapshotListener;
    ActivityAdminNuevoUsuarioBinding binding_new_supervisor;
    FirebaseFirestore db_nuevo_supervisor;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nuevo_usuario);

        db_nuevo_supervisor = FirebaseFirestore.getInstance();

        binding_new_supervisor = ActivityAdminNuevoUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding_new_supervisor.getRoot());

        db_nuevo_supervisor = FirebaseFirestore.getInstance();
        binding_new_supervisor.saveButtonUser.setOnClickListener(view -> {
            ConfirmacionPopup();
        });

        FirebaseApp.initializeApp(Admin_nuevo_usuario.this);
        storageReference = FirebaseStorage.getInstance().getReference();

        imageView = findViewById(R.id.imagenview);
        selecImage = findViewById(R.id.selecImage);
        uploadImagen = findViewById(R.id.saveButton_user);

        selecImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });
    }

    private void uploadImagen(Uri image) {
        StorageReference reference = storageReference.child("FotosdeSupervisores/" + UUID.randomUUID().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_nuevo_usuario.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        Log.d("SelectedImageCLASEUPLOAD", "URI de la imagen seleccionada: " + image.toString());

        reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri urlImage = task.getResult();
                            imagenURL = urlImage.toString();
                            Log.d("Upload", "URL de la imagen subida: " + imagenURL);
                            dialog.dismiss();
                            guardarSupervisor();
                            Toast.makeText(Admin_nuevo_usuario.this, "Imagen subida exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("Upload", "Error al obtener la URL de la imagen", task.getException());
                            dialog.dismiss();
                            Toast.makeText(Admin_nuevo_usuario.this, "Error al obtener la URL de la imagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Upload", "Error al subir la imagen: ", e);
                dialog.dismiss();
                Toast.makeText(Admin_nuevo_usuario.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ConfirmacionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estás seguro de guardar los cambios?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardarSupervisor();
                uploadImagen(image);
                Log.d("SelectedImageACEPTAR", "URI de la imagen seleccionada: " + image.toString());
                dialog.dismiss();
                NotificationHelper.createNotificationChannel(Admin_nuevo_usuario.this);
                NotificationHelper.sendNotification(Admin_nuevo_usuario.this, "Supervisor", "Nuevo Supervisor creado");
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (snapshotListener != null)
            snapshotListener.remove();
    }

    private void guardarSupervisor() {
        String nombre = binding_new_supervisor.idNombreUser.getText().toString();
        String apellido = binding_new_supervisor.idApellidoUser.getText().toString();
        String dni = binding_new_supervisor.idDniUSer.getText().toString();
        String correo = binding_new_supervisor.idCorreoUser.getText().toString();
        String telefono = binding_new_supervisor.idTelefonoUser.getText().toString();
        String domicilio = binding_new_supervisor.idDomicilioUser.getText().toString();
        String textViewEstado_admin = binding_new_supervisor.textViewEstadoAdmin.getText().toString();

        Supervisor_Data supervisorNuevoData = new Supervisor_Data(nombre, apellido, dni, correo, telefono, domicilio, imagenURL, textViewEstado_admin);
        supervisorNuevoData.setId_nombreUser(nombre);
        supervisorNuevoData.setId_apellidoUser(apellido);
        supervisorNuevoData.setId_dniUSer(dni);
        supervisorNuevoData.setId_correoUser(correo);
        supervisorNuevoData.setId_telefonoUser(telefono);
        supervisorNuevoData.setId_domicilioUser(domicilio);
        supervisorNuevoData.setDataImage(imagenURL);
        supervisorNuevoData.setStatus_admin(textViewEstado_admin);

        db_nuevo_supervisor.collection("supervisorAdmin")
                .document(nombre)
                .set(supervisorNuevoData)
                .addOnSuccessListener(unused -> {
                    MainActivity.RegistrarUsuario(correo);
                    Toast.makeText(Admin_nuevo_usuario.this, "Supervisor grabado", Toast.LENGTH_SHORT).show();
                    guardarHistorial("Creó un nuevo supervisor: " + nombre, "Samantha", "Administrador");
                    Intent intent = new Intent(Admin_nuevo_usuario.this, Admin_lista_usuario.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Admin_nuevo_usuario.this, "Algo pasó al guardar", Toast.LENGTH_SHORT).show();
                });
    }

    private void guardarHistorial(String actividad, String usuario, String rol) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedHour = hourFormat.format(currentDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        HistorialData historial = new HistorialData(actividad, usuario, rol, formattedDate, formattedHour);

        db_nuevo_supervisor.collection("historialglobal")
                .add(historial)
                .addOnSuccessListener(documentReference -> {
                    // Historial guardado con éxito
                })
                .addOnFailureListener(e -> {
                    // Error al guardar el historial
                });
    }
}
