package com.example.proyecto_iot;

public class ChatItem {
    private String name;
    private String profilePictureUrl;
    private String lastMessageTime;

    public ChatItem(String name, String profilePictureUrl, String lastMessageTime) {
        this.name = name;
        this.profilePictureUrl = profilePictureUrl;
        this.lastMessageTime = lastMessageTime;
    }

    public String getName() {
        return name;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }
}
