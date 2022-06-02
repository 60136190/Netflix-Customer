package com.example.moviettn.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.1.223:5000/")
                .client(okHttpClient)
                .build();
        return retrofit;
    }


    public static User getUserService(){
        User userService = getRetrofit().create(User.class);
        return userService;
    }

    public static Film getFilmService(){
        Film filmService = getRetrofit().create(Film.class);
        return filmService;
    }


}
