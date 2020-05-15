package com.codeschool.Network;

import com.codeschool.Models.FindMatchModel;
import com.codeschool.Models.FindMatchStatusModel;
import com.codeschool.Models.LoginModel;
import com.codeschool.Models.LoginStatusModel;
import com.codeschool.Models.SignupModel;
import com.codeschool.Models.SignupStatusModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class NetworkCient {

    public interface ServerCommunicator{

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/login")
        Call<LoginStatusModel> sendLoginData(@Body LoginModel model);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/signup")
        Call<SignupStatusModel> sendSignupData(@Body SignupModel model);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/multiplayer/find")
        Call<FindMatchStatusModel> findMatch(@Body FindMatchModel model);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/multiplayer/session/{sessionId}")
        Call<FindMatchStatusModel> postAnswer(@Path("sessionId") int sessionId, @Body FindMatchModel model);

//        @GET("/multiplayer/session/{sessionId}")

    }

    public static ServerCommunicator getClient(String url){
        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ServerCommunicator.class);
    }
}
