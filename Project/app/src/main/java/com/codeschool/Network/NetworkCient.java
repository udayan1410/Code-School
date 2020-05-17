package com.codeschool.Network;

import com.codeschool.Models.FindMatchModel;
import com.codeschool.Models.FindMatchStatusModel;
import com.codeschool.Models.LoginModel;
import com.codeschool.Models.LoginStatusModel;
import com.codeschool.Models.QuizAnswerModel;
import com.codeschool.Models.QuizAnswerStatusModel;
import com.codeschool.Models.QuizQuestionStatusModel;
import com.codeschool.Models.SignupModel;
import com.codeschool.Models.SignupStatusModel;
import com.codeschool.Models.TechNewsModel;
import com.codeschool.Models.WinnerStatusModel;
import com.codeschool.Utils.Constants;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
        @POST("/multiplayer/cancelfind")
        Call<FindMatchStatusModel> cancelFindMatch(@Body FindMatchModel model);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/multiplayer/session/{sessionId}")
        Call<QuizAnswerStatusModel> postAnswer(@Path("sessionId") String sessionId, @Body QuizAnswerModel model);

        @GET("/multiplayer/session/{sessionId}")
        Call<QuizQuestionStatusModel> getQuestionData(@Path("sessionId") String sessionId);

        @GET("/multiplayer/session/quiz-over/{sessionId}")
        Call<WinnerStatusModel> getWinner(@Path("sessionId") String sessionId);

        @GET(Constants.TECH_NEWS_FILTERED)
        Call<TechNewsModel> getTechNews();

    }

    public static ServerCommunicator getClient(String url){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ServerCommunicator.class);
    }
}
