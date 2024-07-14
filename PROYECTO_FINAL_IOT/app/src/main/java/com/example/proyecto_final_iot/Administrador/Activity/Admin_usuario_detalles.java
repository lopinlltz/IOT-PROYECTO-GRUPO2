package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_final_iot.Administrador.Adapter.SitioAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioListAdminAdapter;
import com.example.proyecto_final_iot.Administrador.Adapter.UsuarioSelectAdapter;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.MainActivity;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin_usuario_detalles extends AppCompatActivity {

    TextView id_nombreUser_tw, id_apellidoUser_tw, id_dniUSer_tw,
            id_correoUser_tw, id_telefonoUser_tw,
            id_domicilioUser_tw, textViewEstado_admin_tw;
    ImageView dataImage;
    Button editButton_user, backButton_back_det;
    Button buttonCambiarEstado_admin , buttonCambiarEstadoActivar_admin;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent loginIntent = new Intent(Admin_usuario_detalles.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_usuario_detalles);

        db = FirebaseFirestore.getInstance();

        id_nombreUser_tw = findViewById(R.id.id_nombreUser);
        id_apellidoUser_tw = findViewById(R.id.id_apellidoUser);
        id_dniUSer_tw = findViewById(R.id.id_dniUSer);
        id_correoUser_tw = findViewById(R.id.id_correoUser);
        id_telefonoUser_tw = findViewById(R.id.id_telefonoUser);
        id_domicilioUser_tw = findViewById(R.id.id_domicilioUser);
        dataImage = findViewById(R.id.imagenview_detalles);
        textViewEstado_admin_tw = findViewById(R.id.textViewEstado_admin);
        buttonCambiarEstado_admin = findViewById(R.id.buttonCambiarEstado_admin);
        editButton_user = findViewById(R.id.editButton_user);
        backButton_back_det = findViewById(R.id.backButton_back_det);
        buttonCambiarEstadoActivar_admin = findViewById(R.id.buttonCambiarEstadoActivar_admin);

        Intent intent = getIntent();
        String id_nombreUser = intent.getStringExtra("id_nombreUser");
        String id_apellidoUser = intent.getStringExtra("id_apellidoUser");
        String id_dniUSer = intent.getStringExtra("id_dniUSer");
        String id_correoUser = intent.getStringExtra("id_correoUser");
        String id_telefonoUser = intent.getStringExtra("id_telefonoUser");
        String id_domicilioUser = intent.getStringExtra("id_domicilioUser");
        String textViewEstado_admin = intent.getStringExtra("textViewEstado_admin");

        id_nombreUser_tw.setText(id_nombreUser);
        id_apellidoUser_tw.setText(id_apellidoUser);
        id_dniUSer_tw.setText(id_dniUSer);
        id_correoUser_tw.setText(id_correoUser);
        id_telefonoUser_tw.setText(id_telefonoUser);
        id_domicilioUser_tw.setText(id_domicilioUser);
        textViewEstado_admin_tw.setText(textViewEstado_admin);

        Bundle bundle = intent.getExtras();

            String imageUrl = bundle.getString("dataImage");
            Glide.with(this)
                    .load(imageUrl)
                    .into(dataImage);





        editButton_user.setOnClickListener(v -> {
            Intent editIntent = new Intent(Admin_usuario_detalles.this, Admin_usuario_editar.class);
            editIntent.putExtra("id_nombreUser", id_nombreUser_tw.getText().toString());
            editIntent.putExtra("id_apellidoUser", id_apellidoUser_tw.getText().toString());
            editIntent.putExtra("id_dniUSer", id_dniUSer_tw.getText().toString());
            editIntent.putExtra("id_correoUser", id_correoUser_tw.getText().toString());
            editIntent.putExtra("id_telefonoUser", id_telefonoUser_tw.getText().toString());
            editIntent.putExtra("id_domicilioUser", id_domicilioUser_tw.getText().toString());
            editIntent.putExtra("dataImage", imageUrl);
            startActivity(editIntent);
        });

        backButton_back_det.setOnClickListener(v -> {
            finish(); // Regresar a la actividad anterior
        });

        buttonCambiarEstado_admin.setOnClickListener(v -> {
            // Cambiar el estado del usuario
            String nuevoEstado = "DESACTIVADO";
            textViewEstado_admin_tw.setText(nuevoEstado);
            Log.d("Debug", "TextoDesactivado: " + textViewEstado_admin_tw.getText().toString());

            ConfirmacionPopup(id_nombreUser);


        });
        buttonCambiarEstadoActivar_admin.setOnClickListener(v -> {
            // Cambiar el estado del usuario
            String nuevoEstado = "ACTIVO";
            textViewEstado_admin_tw.setText(nuevoEstado);
            Log.d("Debug", "TextoActivado: " + textViewEstado_admin_tw.getText().toString());

            ConfirmacionPopupActivado(id_nombreUser);


        });



    }

    private void ConfirmacionPopup(String id_nombreUser) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de DESACTIVAR este usuario?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                CambiarEstadoUsuarioDESAC(id_nombreUser);
                Intent intent = new Intent(Admin_usuario_detalles.this, Admin_lista_usuario.class);
                startActivity(intent);
                dialog.dismiss();

                NotificationHelper.createNotificationChannel(Admin_usuario_detalles.this);
                NotificationHelper.sendNotification(Admin_usuario_detalles.this, "Usuario", "Usuario DESACTIVADO");
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

    private  void CambiarEstadoUsuarioDESAC(String id_nombreUser){

        db.collection("supervisorAdmin")
                .whereEqualTo("id_nombreUser", id_nombreUser)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            // Obtén la referencia del documento
                            DocumentReference usuarioRef = task.getResult().getDocuments().get(0).getReference();

                            // Actualiza el campo `estado` con el nuevo valor
                            usuarioRef.update("status_admin", "DESACTIVADO")
                                    .addOnSuccessListener(aVoid -> {
                                        // La actualización fue exitosa
                                        Log.d("Firebase", "Estado actualizado exitosamente.");
                                        finish();
                                        Intent intent = new Intent(Admin_usuario_detalles.this, Admin_lista_usuario.class);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.w("Firebase", "Error al actualizar estado", e);
                                    });
                        } else {

                            Log.d("Firebase", "No se encontró ningún documento con el id_nombreUser especificado.");
                        }
                    } else {
                        Log.w("Firebase", "Error al realizar la consulta", task.getException());
                    }
                });

    }

    private void ConfirmacionPopupActivado(String id_nombreUser) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de ACTIVAR este usuario?");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                CambiarEstadoUsuarioACT(id_nombreUser);
                Intent intent = new Intent(Admin_usuario_detalles.this, Admin_lista_usuario.class);
                startActivity(intent);
                dialog.dismiss();

                NotificationHelper.createNotificationChannel(Admin_usuario_detalles.this);
                NotificationHelper.sendNotification(Admin_usuario_detalles.this, "Usuario", "Usuario ACTIVADO");
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

    private  void CambiarEstadoUsuarioACT(String id_nombreUser){

        db.collection("supervisorAdmin")
                .whereEqualTo("id_nombreUser", id_nombreUser)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            // Obtén la referencia del documento
                            DocumentReference usuarioRef = task.getResult().getDocuments().get(0).getReference();

                            // Actualiza el campo `estado` con el nuevo valor
                            usuarioRef.update("status_admin", "ACTIVO")
                                    .addOnSuccessListener(aVoid -> {
                                        // La actualización fue exitosa
                                        Log.d("Firebase", "Estado actualizado exitosamente.");
                                        finish();
                                        Intent intent = new Intent(Admin_usuario_detalles.this, Admin_lista_usuario.class);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.w("Firebase", "Error al actualizar estado", e);
                                    });
                        } else {

                            Log.d("Firebase", "No se encontró ningún documento con el id_nombreUser especificado.");
                        }
                    } else {
                        Log.w("Firebase", "Error al realizar la consulta", task.getException());
                    }
                });

    }




}



    
