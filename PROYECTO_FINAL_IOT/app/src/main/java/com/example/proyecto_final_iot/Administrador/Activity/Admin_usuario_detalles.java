package com.example.proyecto_final_iot.Administrador.Activity;

import android.net.Uri;
import android.os.Bundle;
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
    private UsuarioSelectAdapter adapter;
    private RecyclerView recyclerView;

    FirebaseFirestore firestore_lista_detalle_usuario;
    List<Supervisor_Data> data_List_detalle_user = new ArrayList<>();
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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id_nombreUser.setText(bundle.getString("id_nombreUser"));
            id_apellidoUser.setText(bundle.getString("id_apellidoUser"));
            id_dniUSer.setText(bundle.getString("id_dniUSer"));
            id_correoUser.setText(bundle.getString("id_correoUser"));
            id_telefonoUser.setText(bundle.getString("id_telefonoUser"));
            dataImage.setImageURI(Uri.parse(bundle.getString("dataImage")));

        }

    }

    }