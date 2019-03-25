package com.xploremalang.xploremalang.Weather.data;

import com.google.gson.annotations.SerializedName;

public class Sys {
    @SerializedName("type")
    private int type;

    @SerializedName("id")
    private int id;

    @SerializedName("message")
    private String message;

    @SerializedName("country")
    private String country;

    @SerializedName("sunrise")
    private double sunrise;

    @SerializedName("sunset")
    private double sunset;

    public Sys(int type, int id, String message, String country, double sunrise, double sunset) {
        this.type = type;
        this.id = id;
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getSunrise() {
        return sunrise;
    }

    public void setSunrise(double sunrise) {
        this.sunrise = sunrise;
    }

    public double getSunset() {
        return sunset;
    }

    public void setSunset(double sunset) {
        this.sunset = sunset;
    }
}
