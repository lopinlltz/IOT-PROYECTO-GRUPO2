package com.example.proyecto_final_iot.Administrador.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.Administrador.Data.Supervisor_Data;
import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
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
                NotificationHelper.createNotificationChannel(Admin_nuevo_usuario.this);
                NotificationHelper.sendNotification(Admin_nuevo_usuario.this, "Supervisor", "Nuevo Supervisor creado ");

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
        String Domicilio = binding_new_supervisor.idDomicilioUser.getText().toString();
        //String foto = binding_new_supervisor.idfoto.getText().toString();



        Supervisor_Data supervisorNuevoData = new Supervisor_Data();
        supervisorNuevoData.setId_nombreUser(nombre);
        supervisorNuevoData.setId_apellidoUser(apellido);
        supervisorNuevoData.setId_dniUSer(dni);
        supervisorNuevoData.setId_correoUser(correo);
        supervisorNuevoData.setId_telefonoUser(telefono);
        supervisorNuevoData.setId_domicilioUser(Domicilio);
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