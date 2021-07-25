package com.example.listviewexcercise;

public class User {
    private int id;
   String name , lastMessage;
   private byte[] image;

    public User(int id, String name, String lastMessage, byte[] image) {
        this.id = id;
        this.name = name;
        this.lastMessage = lastMessage;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
