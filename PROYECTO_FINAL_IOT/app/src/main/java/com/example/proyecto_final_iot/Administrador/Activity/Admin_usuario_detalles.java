package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
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
import com.example.proyecto_final_iot.Superadmin.Activity.superadmin_detalles_administrador;
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
    Button  cambiarEstadoButtonAdmin;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Supervisor_Data supervisorData;
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
        cambiarEstadoButtonAdmin = findViewById(R.id.buttonCambiarEstado_admin);
        editButton_user = findViewById(R.id.editButton_user);
        backButton_back_det = findViewById(R.id.backButton_back_det);


        Intent intent = getIntent();
        String id_nombreUser = intent.getStringExtra("id_nombreUser");
        String id_apellidoUser = intent.getStringExtra("id_apellidoUser");
        String id_dniUSer = intent.getStringExtra("id_dniUSer");
        String id_correoUser = intent.getStringExtra("id_correoUser");
        String id_telefonoUser = intent.getStringExtra("id_telefonoUser");
        String id_domicilioUser = intent.getStringExtra("id_domicilioUser");
        String textViewEstado_admin = intent.getStringExtra("textViewEstado_admin");

        supervisorData = new Supervisor_Data();
        supervisorData.setId_nombreUser(id_nombreUser);
        supervisorData.setId_apellidoUser(id_apellidoUser);
        supervisorData.setId_dniUSer(id_dniUSer);
        supervisorData.setId_correoUser(id_correoUser);
        supervisorData.setId_telefonoUser(id_telefonoUser);
        supervisorData.setId_domicilioUser(id_domicilioUser);
        supervisorData.setStatus_admin(textViewEstado_admin);

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


        cambiarEstadoButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarEstado();
            }
        });

        // Inicializar el estado del botón al cargar la vista
        if (supervisorData != null) {
            actualizarBotonEstado(supervisorData.getStatus_admin());
        }


    }

    private void actualizarBotonEstado(String estado) {
        Log.d("ActualizarBotonEstado", "Estado: " + estado); // Añadir log
        if ("ACTIVO".equals(estado)) {
            cambiarEstadoButtonAdmin.setText("DESACTIVAR");
            cambiarEstadoButtonAdmin.setBackgroundColor(getResources().getColor(R.color.rojo_desactivar));
        } else {
            cambiarEstadoButtonAdmin.setText("ACTIVAR");
            cambiarEstadoButtonAdmin.setBackgroundColor(getResources().getColor(R.color.verde_activar));
        }
    }

    private void cambiarEstado() {
        if (supervisorData != null) {
            String nuevoEstado = "ACTIVO".equals(supervisorData.getStatus_admin()) ? "DESACTIVADO" : "ACTIVO";
            supervisorData.setStatus_admin(nuevoEstado);
            textViewEstado_admin_tw.setText(nuevoEstado);
            actualizarBotonEstado(nuevoEstado);

            db.collection("supervisorAdmin")
                    .whereEqualTo("id_nombreUser", supervisorData.getId_nombreUser())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                DocumentReference usuarioRef = task.getResult().getDocuments().get(0).getReference();

                                usuarioRef.update("status_admin", nuevoEstado)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(Admin_usuario_detalles.this, "Estado actualizado", Toast.LENGTH_SHORT).show();
                                            Log.d("Firebase", "Estado actualizado exitosamente.");
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.w("Firebase", "Error al actualizar estado", e);
                                            Toast.makeText(Admin_usuario_detalles.this, "Error al actualizar estado", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Log.d("Firebase", "No se encontró ningún documento con el id_nombreUser especificado.");
                                Toast.makeText(Admin_usuario_detalles.this, "No se encontró ningún documento con el id_nombreUser especificado", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w("Firebase", "Error al realizar la consulta", task.getException());
                            Toast.makeText(Admin_usuario_detalles.this, "Error al realizar la consulta", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}



    
