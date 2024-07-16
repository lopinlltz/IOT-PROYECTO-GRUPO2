package com.example.proyecto_final_iot;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatMsgActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messagesList;
    private EditText inputMessage;
    private CircleImageView sendButton;
    private String receiverId;
    private String receiverName;
    private String receiverRole;
    private String receiverImage;
    private String chatId;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    private TextView textViewName;
    private TextView textViewRole;
    private CircleImageView circleImageViewReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        recyclerView = findViewById(R.id.recycler_view_chat);
        inputMessage = findViewById(R.id.editTextMsg);
        sendButton = findViewById(R.id.circleImageViewSendMsg);

        textViewName = findViewById(R.id.textViewName);
        textViewRole = findViewById(R.id.textViewRole);
        circleImageViewReceiver = findViewById(R.id.circleImageView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messagesList, this);
        recyclerView.setAdapter(messageAdapter);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        receiverId = getIntent().getStringExtra("receiverId");
        receiverName = getIntent().getStringExtra("receiverName");
        receiverRole = getIntent().getStringExtra("receiverRole");
        receiverImage = getIntent().getStringExtra("receiverImage");

        chatId = generateChatId(currentUser.getUid(), receiverId);

        textViewName.setText(receiverName);
        textViewRole.setText(receiverRole);
        Glide.with(this)
                .load(receiverImage)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.erro_pic)
                .into(circleImageViewReceiver);


        sendButton.setOnClickListener(v -> sendMessage());

        loadMessages();
    }

    private String generateChatId(String uid1, String uid2) {
        if (uid1 == null || uid2 == null) {
            return "default_chat_id";
        }
        return uid1.compareTo(uid2) > 0 ? uid1 + "_" + uid2 : uid2 + "_" + uid1;
    }

    private void sendMessage() {
        String messageText = inputMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            String chatId = generateChatId(currentUser.getUid(), receiverId);
            // Crear un documento para este chat si no existe
            db.collection("chats").document(chatId).set(new Chat(chatId, currentUser.getUid(), receiverId));

            // Guardar el mensaje bajo el chatId específico
            Message message = new Message(currentUser.getUid(), receiverId, messageText, System.currentTimeMillis());
            db.collection("chats").document(chatId).collection("messages").add(message)
                    .addOnSuccessListener(documentReference -> {
                        inputMessage.setText("");
                        recyclerView.scrollToPosition(messagesList.size() - 1);
                    })
                    .addOnFailureListener(e -> Log.e("ChatMsgActivity", "Error sending message", e));
            /*Message message = new Message(currentUser.getUid(), receiverId, messageText, System.currentTimeMillis());
            CollectionReference messagesRef = db.collection("chats").document(chatId).collection("messages");

            messagesRef.add(message).addOnSuccessListener(documentReference -> {
                inputMessage.setText("");
                recyclerView.scrollToPosition(messagesList.size() - 1);
            }).addOnFailureListener(e -> Log.e("ChatMsgActivity", "Error sending message", e));*/
        }
    }

    private void loadMessages() {
        CollectionReference messagesRef = db.collection("chats").document(chatId).collection("messages");
        messagesRef.orderBy("timestamp", Query.Direction.ASCENDING).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                Log.e("ChatMsgActivity", "Error loading messages", e);
                return;
            }

            if (queryDocumentSnapshots != null) {
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Message message = dc.getDocument().toObject(Message.class);
                            messagesList.add(message);
                            messageAdapter.notifyItemInserted(messagesList.size() - 1);
                            recyclerView.scrollToPosition(messagesList.size() - 1);
                            break;
                        case MODIFIED:
                            // Handle modified messages if needed
                            break;
                        case REMOVED:
                            // Handle removed messages if needed
                            break;
                    }
                }
            }
        });
    }
}
