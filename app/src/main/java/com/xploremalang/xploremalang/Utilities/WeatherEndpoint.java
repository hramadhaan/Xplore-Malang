package com.xploremalang.xploremalang.Utilities;

import com.xploremalang.xploremalang.Weather.data.OpenWeatherMap;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherEndpoint {
    @GET("weather")
    Call<OpenWeatherMap> getWeatherFromLatLon(@Query("lat")String lat, @Query("lon")String lon, @Query("appid")String appid);

    @GET("weather")
    Call<OpenWeatherMap> getWeatherFromLocationName(@Query("q")String q, @Query("appid")String appid);
}
