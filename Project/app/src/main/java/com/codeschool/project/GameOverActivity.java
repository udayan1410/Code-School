package com.codeschool.project;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import com.codeschool.Models.FindMatchStatusModel;
import com.codeschool.Models.LoginStatusModel;
import com.codeschool.Models.WinnerStatusModel;
import com.codeschool.Network.NetworkCient;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;
import com.google.gson.Gson;

public class GameOverActivity extends AppCompatActivity {

    String sessionId,playerId;
    NetworkCient.ServerCommunicator communicator;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        init();
        fetchWinner();
    }


    public void init(){
        //Fetching the session ID
        Gson gson = new Gson();
        String findMatchStatusJson = Misc.getStringFromSharedPref(GameOverActivity.this, Constants.SESSIONDATA, Constants.SESSIONDATA);
        FindMatchStatusModel findMatchStatusModel = gson.fromJson(findMatchStatusJson, FindMatchStatusModel.class);
        //Getting the session ID
        sessionId = findMatchStatusModel.getSessionId();

        //Getting player id
        String loginStatusModelJson = Misc.getStringFromSharedPref(GameOverActivity.this, Constants.USERDATA, Constants.USERDATA);
        LoginStatusModel loginStatusModel = gson.fromJson(loginStatusModelJson, LoginStatusModel.class);
        playerId = loginStatusModel.getUserData().getId();

        //Loading dialog for fetching
        dialog = Misc.createDialog(GameOverActivity.this,R.layout.dialog_progress,"Fetching Result");
    }

    public void fetchWinner(){
        dialog.show();
        communicator = NetworkCient.getClient(Constants.SERVER_URL);
        Call<WinnerStatusModel> call = communicator.getWinner(sessionId);
        call.enqueue(new WinnerHandler());
    }

    private class WinnerHandler implements Callback<WinnerStatusModel>{
        @Override
        public void onResponse(Call<WinnerStatusModel> call, Response<WinnerStatusModel> response) {
            dialog.dismiss();

            WinnerStatusModel winnerStatusModel = response.body();
            Log.d("TAG","Winner = "+winnerStatusModel);

            if(winnerStatusModel.getWinnerId() == null)
                Misc.showToast(GameOverActivity.this,"Its a Tie");

            else if(winnerStatusModel.getWinnerId().equalsIgnoreCase(playerId))
                Misc.showToast(GameOverActivity.this,"You Won");

            else
                Misc.showToast(GameOverActivity.this,"You Lose");
        }

        @Override
        public void onFailure(Call<WinnerStatusModel> call, Throwable t) {
            dialog.dismiss();
            Misc.showToast(GameOverActivity.this,"Something Went Wrong : Game Over");
        }
    }

}
