package com.example.proyecto_final_iot.Administrador.Activity;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_final_iot.R;

public class Admin_usuario_editar extends AppCompatActivity {
    EditText id_nombreUser, id_apellidoUser, id_dniUSer, id_correoUser, id_telefonoUser,
            id_domicilioUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_usuario_editar);

        id_nombreUser = findViewById(R.id.id_nombreUser);
        id_apellidoUser = findViewById(R.id.id_apellidoUser);
        id_dniUSer = findViewById(R.id.id_dniUSer);
        id_correoUser = findViewById(R.id.id_correoUser);
        id_telefonoUser = findViewById(R.id.id_telefonoUser);
        id_domicilioUser = findViewById(R.id.id_domicilioUser);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id_nombreUser.setText(bundle.getString("id_nombreUser"));
            id_apellidoUser.setText(bundle.getString("id_apellidoUser"));
            id_dniUSer.setText(bundle.getString("id_dniUSer"));
            id_correoUser.setText(bundle.getString("id_correoUser"));
            id_telefonoUser.setText(bundle.getString("id_telefonoUser"));
            id_domicilioUser.setText(bundle.getString("id_domicilioUser"));

    }
}
}

