package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.NotificationHelper;
import com.example.proyecto_final_iot.R;
import com.example.proyecto_final_iot.Supervisor.Activity.EquipoDetalleActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.EquipoEditarActivity;
import com.example.proyecto_final_iot.Supervisor.Activity.EquiposSupervisorActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class Admin_usuario_editar extends AppCompatActivity {

    EditText id_nombreUser_et, id_apellidoUser_et, id_dniUSer_et, id_correoUser_et, id_telefonoUser_et,
            id_domicilioUser_et;
    Button saveButton_user_edit, saveButton_back_edit;

    FirebaseFirestore db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_usuario_editar);
        db = FirebaseFirestore.getInstance();

        id_nombreUser_et = findViewById(R.id.id_nombreUser);
        id_apellidoUser_et = findViewById(R.id.id_apellidoUser);
        id_dniUSer_et = findViewById(R.id.id_dniUSer);
        id_correoUser_et = findViewById(R.id.id_correoUser);
        id_telefonoUser_et = findViewById(R.id.id_telefonoUser);
        id_domicilioUser_et = findViewById(R.id.id_domicilioUser);


        Intent intent = getIntent();
        String id_nombreUser = intent.getStringExtra("id_nombreUser");
        String id_apellidoUser = intent.getStringExtra("id_apellidoUser");
        String id_dniUSer = intent.getStringExtra("id_dniUSer");
        String id_correoUser = intent.getStringExtra("id_correoUser");
        String id_telefonoUser = intent.getStringExtra("id_telefonoUser");
        String id_domicilioUser = intent.getStringExtra("id_domicilioUser");

        id_nombreUser_et.setText(id_nombreUser);
        id_apellidoUser_et.setText(id_apellidoUser);
        id_dniUSer_et.setText(id_dniUSer);
        id_correoUser_et.setText(id_correoUser);
        id_telefonoUser_et.setText(id_telefonoUser);
        id_domicilioUser_et.setText(id_domicilioUser);

        saveButton_user_edit = findViewById((R.id.saveButton_user_edit));
        saveButton_user_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nuevoNombre = id_nombreUser_et.getText().toString();
                String nuevoApellido = id_apellidoUser_et.getText().toString();
                String nuevoDNI = id_dniUSer_et.getText().toString();
                String nuevoCorreo = id_correoUser_et.getText().toString();
                String nuevoTelefono = id_telefonoUser_et.getText().toString();
                String nuevoDomicilio = id_domicilioUser_et.getText().toString();

                ConfirmacionPopup(nuevoNombre, nuevoApellido, nuevoDNI, nuevoCorreo, nuevoTelefono, nuevoDomicilio);
               /*
                if (validarCampos()) {
                    ConfirmacionPopup(nuevoNombre, nuevoApellido, nuevoDNI, nuevoCorreo, nuevoTelefono, nuevoDomicilio);
                }*/
            }

        });

        saveButton_back_edit = findViewById((R.id.saveButton_back_edit));
        saveButton_back_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin_usuario_editar.this, Admin_usuario_detalles.class);
                intent.putExtra("id_nombreUser", id_nombreUser);
                intent.putExtra("id_apellidoUser", id_apellidoUser );
                intent.putExtra("id_dniUSer", id_dniUSer);
                intent.putExtra("id_correoUser", id_correoUser);
                intent.putExtra("id_telefonoUser",id_telefonoUser);
                intent.putExtra("id_domicilioUser", id_domicilioUser);
                v.getContext().startActivity(intent);
                startActivity(intent);



            }
        });
    }

    private void ConfirmacionPopup(String id_nombreUser, String id_apellidoUser, String id_dniUSer, String id_correoUser, String  id_telefonoUser, String  id_domicilioUser) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro de guardar los cambios?");


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editarUsuario(id_nombreUser, id_apellidoUser, id_dniUSer, id_correoUser, id_telefonoUser, id_domicilioUser);
                Intent intent = new Intent(Admin_usuario_editar.this, Admin_lista_usuario.class);
                startActivity(intent);
                dialog.dismiss();

                NotificationHelper.createNotificationChannel(Admin_usuario_editar.this);
                NotificationHelper.sendNotification(Admin_usuario_editar.this, "Usuario", "usuario editado");
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


    private void editarUsuario(String id_nombreUser, String id_apellidoUser, String id_dniUSer, String id_correoUser, String  id_telefonoUser, String  id_domicilioUser) {
        db.collection("supervisorAdmin")
                .whereEqualTo("id_nombreUser", id_nombreUser)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            DocumentReference usuarioRef = task.getResult().getDocuments().get(0).getReference();

                            // Actualizar el documento
                            usuarioRef.update("id_nombreUser", id_nombreUser,
                                            "id_apellidoUser", id_apellidoUser,
                                            "id_dniUSer", id_dniUSer,
                                            "id_correoUser", id_correoUser,
                                            "id_telefonoUser", id_telefonoUser,
                                    "id_domicilioUser", id_domicilioUser)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Admin_usuario_editar", "Usuario actualizado con éxito");
                                        // Éxito al actualizar el equipo
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("Admin_usuario_editar", "Error al actualizar el usuario", e);
                                        // Error al actualizar el equipo
                                    });
                        }

                        else {
                            // No se encontró ningún documento con el `sku` especificado
                            Log.e("Admin_usuario_editar", "El documento con Nombre " + id_nombreUser + " no existe.");
                        }
                    } else {
                        Log.e("Admin_usuario_editar", "Error al obtener el documento", task.getException());
                    }
                });
    }
/*
    private boolean validarCampos() {
        String nombre = id_nombreUser_et.getText().toString();
        String apellido = id_apellidoUser_et.getText().toString();
        String dni = id_dniUSer_et.getText().toString();
        String correo = id_correoUser_et.getText().toString();
        String telefono = id_telefonoUser_et.getText().toString();
        String domicilio = id_domicilioUser_et.getText().toString();

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
    }*/



}


