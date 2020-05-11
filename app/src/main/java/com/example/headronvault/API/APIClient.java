package com.example.headronvault.API;

import retrofit2.Retrofit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient
{
    static private String ApiUrl = "";
    private static Retrofit retrofit = null;
    private static String user="";
    private static String password="";
    public static Retrofit getClient()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(user, password))
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return retrofit;
    }
}
