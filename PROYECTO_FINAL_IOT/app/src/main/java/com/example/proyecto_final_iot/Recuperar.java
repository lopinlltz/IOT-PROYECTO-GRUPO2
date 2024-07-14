package com.example.proyecto_final_iot;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.Administrador.Activity.Admin_lista_Sitio;
import com.example.proyecto_final_iot.Superadmin.Activity.Superadmin_vista_principal1;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class Recuperar extends AppCompatActivity {

    EditText editCorreo;
    Button enviar,  volver;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseFirestore db;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.recuperar_contra);
        db = FirebaseFirestore.getInstance();


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        editCorreo = findViewById(R.id.login_email);
        enviar = findViewById(R.id.enviar);
        volver = findViewById(R.id.volver);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo;
                correo = String.valueOf(editCorreo.getText());

                if (TextUtils.isEmpty(correo)) {
                    Toast.makeText(Recuperar.this, "Por favor, ingrese el correo", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.fetchSignInMethodsForEmail(correo).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if (task.isSuccessful()) {
                                SignInMethodQueryResult result = task.getResult();
                                List<String> signInMethods = result.getSignInMethods();

                                if (signInMethods != null && !signInMethods.isEmpty()) {
                                    // El correo existe en Firebase Authentication
                                    mAuth.sendPasswordResetEmail(correo)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> resetTask) {
                                                    if (resetTask.isSuccessful()) {
                                                        Intent intent = new Intent(Recuperar.this, MainActivity.class);
                                                        startActivity(intent);
                                                        Toast.makeText(Recuperar.this, "Correo de restablecimiento enviado", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(Recuperar.this, "Error al enviar el correo de restablecimiento", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    // El correo no está registrado en Firebase Authentication
                                    Toast.makeText(Recuperar.this, "El correo no está registrado", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Error en la consulta
                                Toast.makeText(Recuperar.this, "Error al verificar el correo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Recuperar.this, MainActivity.class);
                startActivity(intent);

            }
        });


    }
    
}
