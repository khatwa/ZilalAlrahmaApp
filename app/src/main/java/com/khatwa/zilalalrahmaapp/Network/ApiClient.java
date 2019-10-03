package com.khatwa.zilalalrahmaapp.Network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://5fb0feae.ngrok.io/ZilalApi/";   // todo  URL
    private static Retrofit retrofit = null;
    public static final String IMAGE_BASE_URL = "https://5fb0feae.ngrok.io/ZilalApi/images/";  // todo  URL

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
