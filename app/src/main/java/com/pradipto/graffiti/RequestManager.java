package com.pradipto.graffiti;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {

    private static Retrofit retrofit = null;
    public static final String API_KEY = "563492ad6f9170000100000177d3a5b403ee46d7a9067f3e291bbeb7";

    @NonNull
    public static ApiInterface getApiInterface(){

        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiInterface.class);
    }
}
