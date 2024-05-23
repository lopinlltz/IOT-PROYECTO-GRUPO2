package com.example.proyecto_final_iot.Administrador.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.Administrador.Data.Sitio_nuevo_Data;
import com.example.proyecto_final_iot.Administrador.Data.Supervisor_nuevo_Data;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Activity.EquipoEditarActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.EquiposSupervisorActivity;
import com.example.proyecto_final_iot.databinding.ActivityAdminNuevoSitioBinding;
import com.example.proyecto_final_iot.databinding.ActivityAdminNuevoUsuarioBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class Admin_nuevo_usuario extends AppCompatActivity {

    Button backButton_user;
    Button saveButton_user;
    FloatingActionButton fab_user;
    @SuppressLint("CutPasteId")

    //---------Firebase------------
    ListenerRegistration snapshotListener;
    ActivityAdminNuevoUsuarioBinding binding_new_supervisor;
    FirebaseFirestore db_nuevo_supervisor;

    DatabaseReference mDatabase_super;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nuevo_usuario);


        backButton_user = findViewById(R.id.backButton_user);
        backButton_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_nuevo_usuario.this, Admin_lista_usuario.class);
                startActivity(intent);
            }
        });

        /*saveButton_user = findViewById(R.id.saveButton_user);
        saveButton_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_nuevo_usuario.this, Admin_lista_usuario.class);
                startActivity(intent);
                ConfirmacionPopup();
            }
        });*/

        binding_new_supervisor = ActivityAdminNuevoUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding_new_supervisor.getRoot());

        db_nuevo_supervisor = FirebaseFirestore.getInstance();
        binding_new_supervisor.saveButtonUser.setOnClickListener(view -> {
            ConfirmacionPopup();
        });


    }

    private void ConfirmacionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardarSupervisor();
                dialog.dismiss();
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
        int dni = Integer.parseInt(binding_new_supervisor.idDniUSer.getText().toString());
        String correo = binding_new_supervisor.idCorreoUser.getText().toString();
        int telefono = Integer.parseInt(binding_new_supervisor.idTelefonoUser.getText().toString());
        String Domicilio = binding_new_supervisor.idDomicilioUser.getText().toString();
        //String foto = binding_new_supervisor.idfoto.getText().toString();



        Supervisor_nuevo_Data supervisorNuevoData = new Supervisor_nuevo_Data();
        supervisorNuevoData.setNombre(nombre);
        supervisorNuevoData.setApellido(apellido);
        supervisorNuevoData.setDni(dni);
        supervisorNuevoData.setCorreo(correo);
        supervisorNuevoData.setTelefono(telefono);
        supervisorNuevoData.setDomicilio(Domicilio);
        //supervisorNuevoData.setFoto(foto);



        db_nuevo_supervisor.collection("supervisorAdmin")
                .document(nombre)
                .set(supervisorNuevoData)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(Admin_nuevo_usuario.this, "Supervisor grabado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_nuevo_usuario.this, Admin_lista_usuario.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Admin_nuevo_usuario.this, "Algo pasó al guardar ", Toast.LENGTH_SHORT).show();
                });
    }


}