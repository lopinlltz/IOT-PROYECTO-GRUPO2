package com.example.proyecto_final_iot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_final_iot.ChatMsgActivity;
import com.example.proyecto_final_iot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<User> userList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_contacto_lista);

        recyclerView = findViewById(R.id.recycler_view_contacto);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        loadContacts();
    }

    private void loadContacts() {
        userList = new ArrayList<>();
        loadContactsFromCollection("administrador");
        loadContactsFromCollection("superadmi");
        loadContactsFromCollection("supervisorAdmin");
    }

    private void loadContactsFromCollection(String collectionName) {
        db.collection(collectionName).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    User user = document.toObject(User.class);
                    user.setRol(collectionName);

                    // Utilizamos el ID generado por Firestore
                    String userId = document.getId();
                    user.setId(userId);

                    // Añadimos el usuario a la lista si no es el usuario actual
                    if (!userId.equals(mAuth.getCurrentUser().getUid())) {
                        userList.add(user);
                    }

                    /*if (!user.getId().equals(mAuth.getCurrentUser().getUid())) {
                        userList.add(user);
                    }*/
                }
                if (contactAdapter == null) {
                    contactAdapter = new ContactAdapter(userList, this);
                    recyclerView.setAdapter(contactAdapter);
                    Log.d("ContactActivity", "Adapter set with " + userList.size() + " users.");
                } else {
                    contactAdapter.notifyDataSetChanged();
                }
            } else {
                Log.e("ContactActivity", "Error getting documents: ", task.getException());
            }
        });
    }
}


