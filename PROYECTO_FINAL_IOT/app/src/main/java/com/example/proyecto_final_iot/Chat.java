package com.example.proyecto_final_iot;

public class Chat {
    private String chatId;
    private String user1Id;
    private String user2Id;

    public Chat() {
        // Constructor vacío requerido por Firebase Firestore
    }

    public Chat(String chatId, String user1Id, String user2Id) {
        this.chatId = chatId;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(String user1Id) {
        this.user1Id = user1Id;
    }

    public String getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(String user2Id) {
        this.user2Id = user2Id;
    }
}

