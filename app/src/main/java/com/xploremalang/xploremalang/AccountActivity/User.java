package com.xploremalang.xploremalang.AccountActivity;

public class User {

    private String email;
    private String id;
    private String imageurl;
    private String nama;

    public User(String email, String id, String imageurl, String nama) {
        this.email = email;
        this.id = id;
        this.imageurl = imageurl;
        this.nama = nama;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
