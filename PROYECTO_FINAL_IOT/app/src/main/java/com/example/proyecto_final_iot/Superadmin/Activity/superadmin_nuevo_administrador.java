package com.example.proyecto_final_iot.Superadmin.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;
import com.example.proyecto_final_iot.Superadmin.Data.HistorialData;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(superadmin_nuevo_administrador.this, MainActivity.class);
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
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        ImageButton buttonsupervisor = findViewById(R.id.buttonsupervisor);
        buttonsupervisor.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_nuevo_administrador.this, superadmin_vista_supervisor2.class);
            startActivity(intent);
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Revisar si necesitas recargar la misma actividad o realizar otra acción
                Intent intent = new Intent(superadmin_nuevo_administrador.this, Superadmin_vista_principal1.class);
                startActivity(intent);
                //recreate(); // Recrea la actividad actual
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_nuevo_administrador.this, superadmin_logs.class);
                startActivity(intent);
            }
        });

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

        // Generar un UUID como ID de documento
        String adminId = UUID.randomUUID().toString();

        Admin administrador = new Admin(
                adminId,
                nombreString,
                apellidoString,
                dniString,
                correoString,
                telefonoString,
                domicilioString,
                imagenUrl,
                "Activo"  // ejemplo de status
        );

        db.collection("administrador").document(adminId)
                .set(administrador)
                .addOnSuccessListener(aVoid -> {
                    MainActivity.RegistrarUsuario(correoString);
                    Log.d("NuevoAdmin", "DocumentSnapshot added with ID: " + adminId);
                    Toast.makeText(superadmin_nuevo_administrador.this, "Admin creado con ID: " + adminId, Toast.LENGTH_SHORT).show();
                    guardarHistorial("Creó un nuevo administrador " +nombreString , "Maricielo", "Superadmin");
                    Intent intent = new Intent(superadmin_nuevo_administrador.this, Superadmin_vista_principal1.class);
                    intent.putExtra("ADMIN_ID", adminId); // Pasar el ID del admin al intent
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Log.w("NuevoAdmin", "Error adding document", e);
                    Toast.makeText(superadmin_nuevo_administrador.this, "Error al crear admin", Toast.LENGTH_SHORT).show();
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

        db.collection("historialglobal")
                .add(historial)
                .addOnSuccessListener(documentReference -> {
                    // Historial guardado con éxito
                })
                .addOnFailureListener(e -> {
                    // Error al guardar el historial
                });
    }
}
