package com.example.syllabuspro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static ApiService getApiService() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class,
                            (JsonDeserializer<LocalDate>) (json, type, context) ->
                                    LocalDate.parse(json.getAsString()))
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://3a8f-99-234-5-5.ngrok-free.app")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}

