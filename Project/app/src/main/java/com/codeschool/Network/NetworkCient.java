package com.codeschool.Network;

import com.codeschool.Models.LoginModel;
import com.codeschool.Models.LoginStatusModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class NetworkCient {

    public interface ServerCommunicator{

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/login")
        Call<LoginStatusModel> sendLoginData(@Body LoginModel model);
    }

    public static ServerCommunicator getClient(String url){
        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ServerCommunicator.class);
    }
}
