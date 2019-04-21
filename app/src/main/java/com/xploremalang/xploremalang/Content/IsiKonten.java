package com.xploremalang.xploremalang.Content;

import android.text.Editable;

import com.google.firebase.database.Exclude;

public class IsiKonten {
    private String postId;
    private String postImage;
    private String description;
    private String latitude;
    private String longtitude;
    private String publisher;
    private String wisata;
    private String jenis_konten;

    public String getJenia_konten() {
        return jenis_konten;
    }

    public void setJenia_konten(String jenia_konten) {
        this.jenis_konten = jenia_konten;
    }

    public IsiKonten(String postId, String postImage, String description, String latitude, String longtitude, String publisher, String wisata, String jenis_konten) {
        this.postId = postId;
        this.postImage = postImage;
        this.description = description;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.publisher = publisher;
        this.wisata = wisata;
        this.jenis_konten = jenis_konten;
    }

    public IsiKonten() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getWisata() {
        return wisata;
    }

    public void setWisata(String wisata) {
        this.wisata = wisata;
    }
}