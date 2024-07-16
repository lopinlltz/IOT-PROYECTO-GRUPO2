package com.example.proyecto_final_iot;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import static android.Manifest.permission.POST_NOTIFICATIONS;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    FirebaseAuth mAuth;
    TextView textView5;
    TextView textView2;
    TextView textView8;
    TextView textView9;
    TextView textView10;
    ImageView profileImageView;
    FirebaseFirestore db;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent loginIntent = new Intent(UserProfileActivity.this, MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        } else {
            loadUserProfile(currentUser);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        textView5 = findViewById(R.id.textView5);
        textView2 = findViewById(R.id.textView2);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        profileImageView = findViewById(R.id.circleImageView);

        Button buttonCerrarSesion = findViewById(R.id.buttonCerrarSesion);
        Button buttonEditar = findViewById(R.id.buttonEditar);

        buttonCerrarSesion.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
            startActivity(intent);
            lanzarNotificacion();
        });

        buttonEditar.setOnClickListener(v -> {
            Intent intent = new Intent(UserProfileActivity.this, UserEditProfileActivity.class);
            startActivity(intent);
        });

        createNotificationChannel();
        askPermission();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MenuBarFragment menuBarFragment = new MenuBarFragment();
        fragmentTransaction.add(R.id.fragmentContainerView, menuBarFragment);
        fragmentTransaction.commit();
    }

    private void loadUserProfile(FirebaseUser currentUser) {
        String userEmail = currentUser.getEmail();
        if (userEmail != null) {
            db.collection("supervisorAdmin")
                    .whereEqualTo("id_correoUser", userEmail)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                updateUI(document);
                            }
                        } else {
                            Log.e(TAG, "Error al obtener documentos.", task.getException());
                        }
                    });
        }
    }

    private void updateUI(DocumentSnapshot document) {
        String nombre = document.getString("id_nombreUser");
        String apellido = document.getString("id_apellidoUser");
        String dni = document.getString("id_dniUser");
        String correo = document.getString("id_correoUser");
        String telefono = document.getString("id_telefonoUser");
        String domicilio = document.getString("id_domicilioUser");
        String profileImageUrl = document.getString("dataImage");

        String rol = "Supervisor"; // Establece el rol aquí

        if (nombre != null && apellido != null) {
            textView5.setText(nombre + " " + apellido + " - " + rol);
        }
        if (dni != null) {
            textView2.setText(dni);
        }
        if (correo != null) {
            textView8.setText(correo);
        }
        if (telefono != null) {
            textView9.setText(telefono);
        }
        if (domicilio != null) {
            textView10.setText(domicilio);
        }

        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
            Glide.with(this).load(profileImageUrl).into(profileImageView);
        } else {
            profileImageView.setImageResource(R.drawable.default_profile); // Imagen por defecto si no hay URL
        }
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
                .setContentTitle("Cierre de sesión")
                .setContentText("Hasta pronto")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        }
    }
}
