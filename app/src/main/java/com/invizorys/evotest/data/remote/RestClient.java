package com.invizorys.evotest.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Roma on 25.02.2017.
 */

public class RestClient {
    private static final String API_BASE_URL = "http://prom.ua/_/graph/";
    private PromService promService;

    public RestClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        promService = retrofit.create(PromService.class);
    }

    public PromService getPromService() {
        return promService;
    }
}
