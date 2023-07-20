package com.example.footbalteam;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService(){
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.68.100:5000/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }


//    public static FootbalApi Create(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:5212/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return retrofit.create(FootbalApi.class);
//    }
}
