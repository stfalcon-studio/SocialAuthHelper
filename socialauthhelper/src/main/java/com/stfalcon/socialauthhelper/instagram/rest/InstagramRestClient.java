package com.stfalcon.socialauthhelper.instagram.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alex on 15.01.16.
 */
public class InstagramRestClient {

    public static final String API_URL = "https://api.instagram.com/v1/";
    private Retrofit retrofit;
    private Object service;

    public InstagramRestClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Gson gson = new GsonBuilder()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @SuppressWarnings("all")
    public <T> T getService(Class<T> serviceClass) {
        if (service == null || !serviceClass.isInstance(service))
            service = retrofit.create(serviceClass);
        return (T) service;
    }
}