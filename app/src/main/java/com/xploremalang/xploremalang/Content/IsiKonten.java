package com.xploremalang.xploremalang.Content;

import com.google.firebase.database.Exclude;

public class IsiKonten {
    String judul;
    String deskripsi;
    String latitude;
    String longtitude;
    String namaKonten;
    String mImageUrl;
    private int position;
    private String key;

    public IsiKonten(){
//        empty
    }

    public IsiKonten (int position) {
        this.position = position;
    }

    public IsiKonten(String judul, String deskripsi, String latitude, String longtitude, String mImageUrl, String namaKonten){
        if (judul.trim().equals("")){
            judul = "Tidak Ada Judul";
        }
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.mImageUrl = mImageUrl;
        this.namaKonten = namaKonten;

    }

    public String getJudul() {
        return judul;
    }

    public void setJudul (String judul){
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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

    public String getNamaKonten() {
        return namaKonten;
    }

    public void setNamaKonten(String namaKonten) {
        this.namaKonten = namaKonten;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @Exclude
    public String getKey(){
        return key;
    }

    @Exclude
    public void setKey(String key){
        this.key = key;
    }

}