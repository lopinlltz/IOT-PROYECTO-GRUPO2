package com.example.proyecto_final_iot.Superadmin.Activity;

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
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Superadmin.Data.Admin;
import com.example.proyecto_final_iot.Superadmin.Data.HistorialData;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

public class superadmin_editar_administrador extends AppCompatActivity {
    private FirebaseFirestore db;
    private EditText editNombre;
    private EditText editApellido;
    private EditText editDni;
    private EditText editCorreo;
    private EditText editTelefono;
    private EditText editDomicilio;
    private ImageView imageView;
    private String imagenURL;
    private Button uploadImagen, subirimagen;
    private Uri image;
    private String adminId;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(superadmin_editar_administrador.this, MainActivity.class);
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
                Toast.makeText(superadmin_editar_administrador.this, "Selecciona una imagen, por favor", Toast.LENGTH_SHORT).show();
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superadmin_editar_administrador);

        editNombre = findViewById(R.id.editNombre);
        editApellido = findViewById(R.id.editApellido);
        editDni = findViewById(R.id.editDni);
        editCorreo = findViewById(R.id.editCorreo);
        editTelefono = findViewById(R.id.editTelefono);
        editDomicilio = findViewById(R.id.editDomicilio);
        imageView = findViewById(R.id.imagensubir);
        subirimagen = findViewById(R.id.subirimagen);
        uploadImagen = findViewById(R.id.button5);

        ImageButton btnUserProfile = findViewById(R.id.imageButton6);
        ImageButton btnHome = findViewById(R.id.buttonhomesuper);
        ImageButton btnHistory = findViewById(R.id.buttonhistorialsuper);
        Button atras = findViewById(R.id.button2);
        //Button guardar = findViewById(R.id.button5);
        ImageButton buttonsupervisor = findViewById(R.id.buttonsupervisor);

        buttonsupervisor.setOnClickListener(v -> {
            Intent intent = new Intent(superadmin_editar_administrador.this, superadmin_vista_supervisor2.class);
            startActivity(intent);
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        adminId = intent.getStringExtra("ADMIN_ID");
        editNombre.setText(intent.getStringExtra("nombre"));
        editApellido.setText(intent.getStringExtra("apellido"));
        editDni.setText(intent.getStringExtra("dni"));
        editCorreo.setText(intent.getStringExtra("correo"));
        editTelefono.setText(intent.getStringExtra("telefono"));
        editDomicilio.setText(intent.getStringExtra("domicilio"));

        String imagenUrl = intent.getStringExtra("imagenUrl");
        if (imagenUrl != null && !imagenUrl.isEmpty()) {
            Glide.with(this).load(imagenUrl).into(imageView);
        }

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Vuelve a la actividad anterior
            }
        });


        subirimagen.setOnClickListener(v -> {
            Intent pickImageIntent  = new Intent(Intent.ACTION_PICK);
            pickImageIntent .setType("image/*");
            activityResultLauncher.launch(pickImageIntent );
        });

        uploadImagen.setOnClickListener(v -> {
            if (validarCampos()) {
                showSaveConfirmationDialog();
            }
        });


        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_editar_administrador.this, superadmin_logs.class);
                startActivity(intent);
            }
        });

        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_editar_administrador.this, PerfilSuperadmin.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(superadmin_editar_administrador.this, Superadmin_vista_principal1.class);
                startActivity(intent);
            }
        });
    }

    private boolean validarCampos() {
        String nombre = editNombre.getText().toString().trim();
        String apellido = editApellido.getText().toString().trim();
        String dni = editDni.getText().toString().trim();
        String correo = editCorreo.getText().toString().trim();
        String telefono = editTelefono.getText().toString().trim();
        String domicilio = editDomicilio.getText().toString().trim();

        Pattern nombreApellidoPattern = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
        Pattern dniPattern = Pattern.compile("^\\d{8}$");
        Pattern telefonoPattern = Pattern.compile("^\\d{9}$");

        if (nombre.isEmpty() || !nombreApellidoPattern.matcher(nombre).matches()) {
            Toast.makeText(this, "Nombre inválido. Solo letras y espacios son permitidos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (apellido.isEmpty() || !nombreApellidoPattern.matcher(apellido).matches()) {
            Toast.makeText(this, "Apellido inválido. Solo letras y espacios son permitidos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dni.isEmpty() || !dniPattern.matcher(dni).matches()) {
            Toast.makeText(this, "DNI inválido. Debe tener 8 dígitos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Correo electrónico inválido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (telefono.isEmpty() || !telefonoPattern.matcher(telefono).matches()) {
            Toast.makeText(this, "Teléfono inválido. Debe tener 9 dígitos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (domicilio.isEmpty()) {
            Toast.makeText(this, "Domicilio no puede estar vacío.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void showSaveConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar");
        builder.setMessage("¿Está seguro que quiere guardar?");
        builder.setPositiveButton("Sí", (dialog, which) -> {
            uploadImagen(image);
            dialog.dismiss();
            NotificationHelper.createNotificationChannel(superadmin_editar_administrador.this);
            NotificationHelper.sendNotification(superadmin_editar_administrador.this, "Usuarios", "Administrador editado");
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

        StorageReference reference = storageReference.child("FotosdeAdministradores/" + adminId + "/" + UUID.randomUUID().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(superadmin_editar_administrador.this);
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
            Toast.makeText(superadmin_editar_administrador.this, "Imagen subida exitosamente", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(superadmin_editar_administrador.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    private void guardarAdministrador(String imagenUrl) {
        String nombre = editNombre.getText().toString().trim();
        String apellido = editApellido.getText().toString().trim();
        String dni = editDni.getText().toString().trim();
        String correo = editCorreo.getText().toString().trim();
        String telefono = editTelefono.getText().toString().trim();
        String domicilio = editDomicilio.getText().toString().trim();

        Admin administrador = new Admin(adminId, nombre, apellido, dni, correo, telefono, domicilio, imagenUrl, "activo");

        db.collection("administrador").document(adminId)
                .set(administrador)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(superadmin_editar_administrador.this, "Datos guardados", Toast.LENGTH_SHORT).show();
                    guardarHistorial("Se editó un administrador: " + nombre, "Maricielo", "Superadmin");
                    NotificationHelper.createNotificationChannel(superadmin_editar_administrador.this);
                    NotificationHelper.sendNotification(superadmin_editar_administrador.this, "Usuarios", "Administrador editado");
                    Intent intent = new Intent(superadmin_editar_administrador.this, Superadmin_vista_principal1.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(superadmin_editar_administrador.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
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
