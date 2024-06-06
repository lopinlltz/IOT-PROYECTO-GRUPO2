package com.example.proyecto_final_iot.Administrador.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
import com.example.proyecto_final_iot.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Admin_usuario_detalles extends AppCompatActivity {

    TextView id_nombreUser, id_apellidoUser, id_dniUSer, id_correoUser
            , id_telefonoUser, id_domicilioUser;
    ImageView dataImage;
    private UsuarioListAdminAdapter adapter;
    private RecyclerView recyclerView;
    Button editButton_user;

    FirebaseFirestore firestore_lista_detalle_usuario;
    List<Supervisor_Data> data_List_detalle_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_usuario_detalles);
        FirebaseApp.initializeApp(this);
        id_nombreUser = findViewById(R.id.id_nombreUser);
        id_apellidoUser = findViewById(R.id.id_apellidoUser);
        id_dniUSer = findViewById(R.id.id_dniUSer);
        id_correoUser = findViewById(R.id.id_correoUser);
        id_telefonoUser = findViewById(R.id.id_telefonoUser);
        id_domicilioUser = findViewById(R.id.id_domicilioUser);
        dataImage = findViewById(R.id.imagenview_detalles);
        editButton_user = findViewById(R.id.editButton_user);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id_nombreUser.setText(bundle.getString("id_nombreUser"));
            id_apellidoUser.setText(bundle.getString("id_apellidoUser"));
            id_dniUSer.setText(bundle.getString("id_dniUSer"));
            id_correoUser.setText(bundle.getString("id_correoUser"));
            id_telefonoUser.setText(bundle.getString("id_telefonoUser"));
            id_domicilioUser.setText(bundle.getString("id_domicilioUser"));
            //dataImage.setImageURI(Uri.parse(bundle.getString("dataImage")));
            if (bundle != null && bundle.getString("dataImage") != null) {
                String imageUrl = bundle.getString("dataImage");
                Glide.with(this)
                        .load(imageUrl)
                        .into(dataImage);
            }

        }

        editButton_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_usuario_detalles.this, Admin_usuario_editar.class);
                startActivity(intent);
            }
        });

    }
    
    }