package com.xploremalang.xploremalang.Utilities;


import com.xploremalang.xploremalang.BuildConfig;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceHelper {
    public  WeatherEndpoint getWeatherService(){
        HttpLoggingInterceptor intercept = new HttpLoggingInterceptor();
        intercept.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                request.newBuilder().header("Cache-control", "public,max-age=0");
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES).cache(null).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build();

        return retrofit.create(WeatherEndpoint.class);
    }
}
