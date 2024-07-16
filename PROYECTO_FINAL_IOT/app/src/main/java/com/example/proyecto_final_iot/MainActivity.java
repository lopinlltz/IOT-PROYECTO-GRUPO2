package com.example.proyecto_final_iot;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_final_iot.Superadmin.Data.HistorialData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.example.proyecto_final_iot.Administrador.Activity.Admin_lista_Sitio;
import com.example.proyecto_final_iot.Supervisor.Activity.EquiposSupervisorActivity;
import com.example.proyecto_final_iot.Superadmin.Activity.Superadmin_vista_principal1;
import com.example.proyecto_final_iot.Supervisor.Activity.SitioSupervisorActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText editCorreo, editContra;
    Button login_button, googlebu, recuperar;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseFirestore db;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 20;

    //private static final String POST_NOTIFICATIONS = "android.permission.POST_NOTIFICATIONS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        createNotificationChannel();
        askPermission();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        editCorreo = findViewById(R.id.login_email);
        editContra = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        googlebu = findViewById(R.id.forget_button);
        recuperar = findViewById(R.id.recuperar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo, contra;
                correo = String.valueOf(editCorreo.getText());
                contra = String.valueOf(editContra.getText());

                if(TextUtils.isEmpty(correo)){
                    Toast.makeText(MainActivity.this, "Por favor, ingrese el correo", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(contra)){
                    Toast.makeText(MainActivity.this, "Por favor, ingrese la contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(correo, contra)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String userEmail = mAuth.getCurrentUser().getEmail();
                                    if (userEmail != null) {
                                        checkUserRoleAndRedirect(userEmail);
                                        guardarHistorial("Inicio de sesión", userEmail, "Usuario");
                                    } else {
                                        Toast.makeText(MainActivity.this, "Error: No se pudo obtener el correo del usuario.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "No existe la cuenta",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        //Google

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googlebu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSingIn();
            }
        });

        // AGREGAR UNA VISTA PARA AGREGAR EL CORREO Y ENVIAR CORREO APRA RESTABLECER CONTRASEÑA
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Recuperar.class);
                startActivity(intent);
            }
        });

    }

    private void createNotificationChannel() {
        String channelId = "channelDefaultPri";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Canal notificaciones default",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Canal para notificaciones con prioridad default");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void askPermission() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{POST_NOTIFICATIONS},
                    101);
        }
    }

    public void lanzarNotificacion() {
        String channelId = "channelDefaultPri";
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notifpic)
                .setContentTitle("Notificación PRUEBA")
                .setContentText("Sí funciona uwu")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }


    public static void RegistrarUsuario(String correo) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Random random = new Random();

        // Generar un valor aleatorio de 8 números
        int min = 10000000; // Mínimo valor de un número de 8 dígitos
        int max = 99999999; // Máximo valor de un número de 8 dígitos
        int numeroAleatorio = random.nextInt(max - min + 1) + min;

        mAuth.createUserWithEmailAndPassword(correo, Integer.toString(numeroAleatorio))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // El usuario se ha creado correctamente
                            // Envía un correo electrónico para restablecer la contraseña
                            mAuth.sendPasswordResetEmail(correo)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> resetTask) {

                                        }
                                    });
                        } else {
                            // Error al crear el usuario
                            Log.e("TAG", "Error al crear el usuario", task.getException());
                        }
                    }
                });

    }


    private  void googleSingIn(){
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RC_SIGN_IN){

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firbaseAuth(account.getIdToken());
                } else {
                    Toast.makeText(this, "No se pudo obtener la cuenta de Google.", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                Toast.makeText(this, "Error en el requestCode: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();  // Para obtener más detalles en el Logcat
            }

        }

    }

    private void firbaseAuth(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", user.getUid());
                            map.put("name", user.getDisplayName());
                            map.put("profile", user.getPhotoUrl().toString());

                            database.getReference().child("users").child(user.getUid()).setValue(map);

                            String userEmail = user.getEmail();
                            if (userEmail != null) {
                                checkUserRoleAndRedirect(userEmail);
                                guardarHistorial("Inicio de sesión", userEmail, "Usuario");
                            } else {
                                Toast.makeText(MainActivity.this, "Error: No se pudo obtener el correo del usuario.", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void checkUserRoleAndRedirect(String email) {
        Log.d("CHECK", email);

        if (email.equals("a20200825@pucp.edu.pe")){
            String nombre = "Superadmin";
            String rol = "Superadmin";
            guardarHistorial("Inicio de sesión", nombre, rol);
            Intent intent = new Intent(MainActivity.this, Superadmin_vista_principal1.class);
            startActivity(intent);
            finish();
        } else {
            db.collection("supervisorAdmin")
                    .whereEqualTo("id_correoUser", email)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String nombre = document.getString("id_nombreUser") + " " + document.getString("id_apellidoUser");
                                String rol = "Supervisor";
                                guardarHistorial("Inicio de sesión", nombre, rol);
                                Intent intent = new Intent(MainActivity.this, SitioSupervisorActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    });

            db.collection("administrador")
                    .whereEqualTo("correoUser", email)
                    .get()
                    .addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task1.getResult()) {
                                String nombre = document.getString("nombreUser") + " " + document.getString("apellidoUser");
                                String rol = "Administrador";
                                guardarHistorial("Inicio de sesión", nombre, rol);
                                Intent intent = new Intent(MainActivity.this, Admin_lista_Sitio.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                        // Si no se encontró al usuario en supervisorAdmin ni administrador
                        guardarHistorial("Intento de inicio de sesión", email, "Desconocido");
                        Toast.makeText(MainActivity.this, "No se encontró el usuario", Toast.LENGTH_SHORT).show();
                    });
        }
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

