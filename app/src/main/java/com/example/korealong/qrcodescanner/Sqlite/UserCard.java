package com.example.korealong.qrcodescanner.Sqlite;

public class UserCard {
    public int id;
    public String nameCard;
    public String nameStore;
    public byte[] image;

    public UserCard(int id, String nameCard, String nameStore, byte[] image) {
        this.id = id;
        this.nameCard = nameCard;
        this.nameStore = nameStore;
        this.image = image;
    }

    public UserCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
