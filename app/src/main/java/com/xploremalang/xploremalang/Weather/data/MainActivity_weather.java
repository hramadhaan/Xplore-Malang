package com.xploremalang.xploremalang.Weather.data;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xploremalang.xploremalang.BuildConfig;
import com.xploremalang.xploremalang.R;
import com.xploremalang.xploremalang.Utilities.ServiceHelper;
import com.xploremalang.xploremalang.Utilities.WeatherEndpoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_weather extends AppCompatActivity {
    private WeatherEndpoint helper = new ServiceHelper().getWeatherService();
    private ImageView weatherIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        weatherIcon = findViewById(R.id.imgView_iconWeather);
        getLocation("Malang");
    }

    private void getLocation(String name){
        helper.getWeatherFromLocationName(name, BuildConfig.API_KEY)
                .enqueue(new Callback<OpenWeatherMap>() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onResponse(@SuppressWarnings("NullableProblems") Call<OpenWeatherMap> call, @SuppressWarnings("NullableProblems") Response<OpenWeatherMap> response) {
                        OpenWeatherMap result = response.body();

                        if (result!=null){
                            ((TextView)findViewById(R.id.textView_condition)).setText(result.getWeatherList().get(0).getDescription());
                            ((TextView)findViewById(R.id.textView_location)).setText(result.getName());
                            double temp = result.getMain().getTemp()-273;
                            ((TextView)findViewById(R.id.textView_temperature)).setText(String.format("%.2f C °", temp));
                            Glide.with(MainActivity_weather.this).load(String.format("http://openweathermap.org/img/w/%s.png", result.getWeatherList().get(0).getIcon()))
                                    .into(weatherIcon);
                        }else {
                            Toast.makeText(MainActivity_weather.this, "Gak muncul datanya", Toast.LENGTH_SHORT).show();
                        }

                        call.cancel();
                    }

                    @Override
                    public void onFailure(Call<OpenWeatherMap> call, Throwable t) {
                        call.cancel();
                        Log.w("Home Fragment", t.getLocalizedMessage());

                    }
                });
    }
}