package com.codeschool.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeschool.Models.FindMatchStatusModel;
import com.codeschool.Models.LoginModel;
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
    ImageView imageView;
    TextView gameText;
    CardView gameDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        init();
        fetchWinner();
    }


    public void init(){
        imageView = findViewById(R.id.imageView);
        gameText = findViewById(R.id.gameOverText);
        gameDone = findViewById(R.id.gameDone);
        gameDone.setOnClickListener(new GameDoneHandler());

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


            if(winnerStatusModel.getWinnerId() == null){
                Misc.showToast(GameOverActivity.this,"Its a Tie");
                gameText.setText("TIE :|");
                Glide.with(GameOverActivity.this).asGif().load(R.drawable.itsatie).into(imageView);
            }

            else if(winnerStatusModel.getWinnerId().equalsIgnoreCase(playerId)) {
                gameText.setText("Congratulations :D");
                Misc.showToast(GameOverActivity.this, "You Won");
                Glide.with(GameOverActivity.this).asGif().load(R.drawable.youwin).into(imageView);
            }

            else{
                gameText.setText("You Lose :(");
                Misc.showToast(GameOverActivity.this,"You Lose");
                Glide.with(GameOverActivity.this).asGif().load(R.drawable.you_lose).into(imageView);
            }
        }

        @Override
        public void onFailure(Call<WinnerStatusModel> call, Throwable t) {
            dialog.dismiss();
            Misc.showToast(GameOverActivity.this,"Something Went Wrong : Game Over");
        }
    }

    private class GameDoneHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            //Show Dialog as we are fetching new streak data
            dialog = Misc.createDialog(GameOverActivity.this,R.layout.dialog_progress,"Wrapping Up!");
            dialog.show();

            Gson gson = new Gson();
            String loginStatusModelJSon = Misc.getStringFromSharedPref(GameOverActivity.this,Constants.USERDATA,Constants.USERDATA);
            LoginStatusModel statusModel = gson.fromJson(loginStatusModelJSon,LoginStatusModel.class);

            LoginModel loginModel = new LoginModel(statusModel.getUserData().getEmail(),statusModel.getUserData().getPassword());
            Call<LoginStatusModel> call = communicator.sendLoginData(loginModel);
            call.enqueue(new LoginSuccess());

        }
    }

    private class LoginSuccess implements Callback<LoginStatusModel>{
        @Override
        public void onResponse(Call<LoginStatusModel> call, Response<LoginStatusModel> response) {

            //Saving new data to Shared pref as we need to update multiplayer score
            LoginStatusModel loginStatusModel = response.body();
            Gson gson = new Gson();
            Misc.addStringToSharedPref(GameOverActivity.this,Constants.USERDATA,Constants.USERDATA,gson.toJson(loginStatusModel));

            dialog.dismiss();
            finish();
        }

        @Override
        public void onFailure(Call<LoginStatusModel> call, Throwable t) {
            dialog.dismiss();

        }
    }

}
