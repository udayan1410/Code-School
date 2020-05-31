package com.codeschool.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.codeschool.Models.FindMatchStatusModel;
import com.codeschool.Models.LoginStatusModel;
import com.codeschool.Models.PlayerDatum;
import com.codeschool.Models.Playerstatus;
import com.codeschool.Models.QuizAnswerModel;
import com.codeschool.Models.QuizAnswerStatusModel;
import com.codeschool.Models.QuizQuestionStatusModel;
import com.codeschool.Network.NetworkCient;
import com.codeschool.Network.WebSocket;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class MultiplayerQuiz extends AppCompatActivity {

    CardView option1, option2, option3, option4, submitButton, currentlySelectedCard = null;
    TextView optionText1, optionText2, optionText3, optionText4, questionText, yourScoreText, enemyScoreText, currentlySelectedText = null, yourName, enemyName;
    NetworkCient.ServerCommunicator communicator;
    String sessionId, playerId;
    Dialog fetchingNextQuestionDialog, submitAnswerDialog;
    boolean called = false;

    Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_quiz);

        init();
        //Showing dialog as initially we will need to fetch the question as we enter the activity
        fetchingNextQuestionDialog.show();
    }

    public void init() {
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        //Disabling the submit button initially
        submitButton = findViewById(R.id.submitButton);
        submitButton.setEnabled(false);

        optionText1 = findViewById(R.id.option1Text);
        optionText2 = findViewById(R.id.option2Text);
        optionText3 = findViewById(R.id.option3Text);
        optionText4 = findViewById(R.id.option4Text);

        option1.setOnClickListener(new OptionClickHandler());
        option2.setOnClickListener(new OptionClickHandler());
        option3.setOnClickListener(new OptionClickHandler());
        option4.setOnClickListener(new OptionClickHandler());
        submitButton.setOnClickListener(new OptionClickHandler());

        questionText = findViewById(R.id.quizQuestion);
        yourScoreText = findViewById(R.id.yourScore);
        enemyScoreText = findViewById(R.id.enemyScore);
        yourName = findViewById(R.id.yourName);
        enemyName = findViewById(R.id.enemyName);

        mSocket = WebSocket.getSocket();
        mSocket.on("GetQuestionEvent", new QuestionGetter());
        mSocket.on("GameOver", new GameOverHandler());

        Gson gson = new Gson();
        //Getting player ID in json format
        String loginStatusModelJson = Misc.getStringFromSharedPref(MultiplayerQuiz.this, Constants.USERDATA, Constants.USERDATA);

        //Converting to Object and getting string
        LoginStatusModel loginStatusModel = gson.fromJson(loginStatusModelJson, LoginStatusModel.class);
        playerId = loginStatusModel.getUserData().getId();

        //Getting the session Data which has session id
        String findMatchStatusJson = Misc.getStringFromSharedPref(MultiplayerQuiz.this, Constants.SESSIONDATA, Constants.SESSIONDATA);

        //Setting the session ID
        FindMatchStatusModel findMatchStatusModel = gson.fromJson(findMatchStatusJson, FindMatchStatusModel.class);
        sessionId = findMatchStatusModel.getSessionId();

        //Setting communicator with base url
        communicator = NetworkCient.getClient(Constants.SERVER_URL);

        //Initializing a dialog for sending the answer and fetching next question
        fetchingNextQuestionDialog = Misc.createDialog(MultiplayerQuiz.this, R.layout.dialog_progress, "Fetching Next Question");
        submitAnswerDialog = Misc.createDialog(MultiplayerQuiz.this, R.layout.dialog_progress, "Waiting for Other Player");

    }

/*
    public void fetchQuestion() {

//        Call<QuizQuestionStatusModel> call = communicator.getQuestionData(sessionId);
//        call.enqueue(new QuestionGetterHandler());
    }
*/

/*

    private class QuestionGetterHandler implements Callback<QuizQuestionStatusModel> {

        public void setQuestionText(TextView questionText, String question) {
            questionText.setText(question);
        }

        @Override
        public void onResponse(Call<QuizQuestionStatusModel> call, Response<QuizQuestionStatusModel> response) {
            //Setting the model
            QuizQuestionStatusModel quizQuestionStatusModel = response.body();

            //Taking all the questions, options and scores in strings
            String question = quizQuestionStatusModel.getQuestion();
            String option1 = quizQuestionStatusModel.getOptions().get(0);
            String option2 = quizQuestionStatusModel.getOptions().get(1);
            String option3 = quizQuestionStatusModel.getOptions().get(2);
            String option4 = quizQuestionStatusModel.getOptions().get(3);
            String yourScore = new String("");
            String enemyScore = new String("");

            List<Playerstatus> playerStatuses = quizQuestionStatusModel.getPlayerStatus();
            for (Playerstatus playerstatus : playerStatuses) {
                if (playerstatus.getPlayerId().equals(playerId))
                    yourScore = playerstatus.getPlayerScore();
                else
                    enemyScore = playerstatus.getPlayerScore();
            }

            //Setting the String text to the textviews
            setQuestionText(questionText, question);
            setQuestionText(optionText1, option1);
            setQuestionText(optionText2, option2);
            setQuestionText(optionText3, option3);
            setQuestionText(optionText4, option4);
            setQuestionText(yourScoreText, yourScore);
            setQuestionText(enemyScoreText, enemyScore);

            fetchingNextQuestionDialog.dismiss();
        }

        @Override
        public void onFailure(Call<QuizQuestionStatusModel> call, Throwable t) {
            Log.d("TAG", "Error " + t.getMessage());
        }
    }
*/


    private class OptionClickHandler implements View.OnClickListener {

        //Changing colors of cards
        public void changeColors(CardView card, TextView text, int cardColor, int textColor) {
            card.setCardBackgroundColor(cardColor);
            text.setTextColor(textColor);
        }

        @Override
        public void onClick(View v) {

            //If the currently selected card is not null then make the previous card as unselected
            if (currentlySelectedCard != null) {
                currentlySelectedCard.setCardBackgroundColor(getResources().getColor(R.color.white));
                currentlySelectedText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }

            switch (v.getId()) {
                case R.id.option1:
                    currentlySelectedCard = option1;
                    currentlySelectedText = optionText1;
                    changeColors(option1, optionText1, getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.white));
                    submitButton.setEnabled(true);
                    break;

                case R.id.option2:
                    currentlySelectedCard = option2;
                    currentlySelectedText = optionText2;
                    changeColors(option2, optionText2, getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.white));
                    submitButton.setEnabled(true);
                    break;

                case R.id.option3:
                    currentlySelectedCard = option3;
                    currentlySelectedText = optionText3;
                    changeColors(option3, optionText3, getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.white));
                    submitButton.setEnabled(true);
                    break;

                case R.id.option4:
                    currentlySelectedCard = option4;
                    currentlySelectedText = optionText4;
                    changeColors(option4, optionText4, getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.white));
                    submitButton.setEnabled(true);
                    break;

                case R.id.submitButton:
                    submitAnswerDialog.show();

                    String playerid = playerId;
                    String answer = currentlySelectedText.getText().toString();

                    //Setting up the model with player's answer, playerid and sessionid
                    QuizAnswerModel quizAnswerModel = new QuizAnswerModel(playerid, answer, sessionId);
                    Gson gson = new Gson();

                    //Sending data to backend
                    mSocket.emit("submitAnswer", gson.toJson(quizAnswerModel));
                    break;
            }

            if (submitButton.isClickable())
                submitButton.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    /*
    private class AnswerPostedHandler implements Callback<QuizAnswerStatusModel> {
        @Override
        public void onResponse(Call<QuizAnswerStatusModel> call, Response<QuizAnswerStatusModel> response) {
            QuizAnswerStatusModel quizAnswerStatusModel = response.body();
            submitAnswerDialog.dismiss();
            //Quiz is not ended
            if (!quizAnswerStatusModel.getIsEndOfQuiz()) {

            } else {
                startActivity(new Intent(MultiplayerQuiz.this, GameOverActivity.class));
                finish();
            }

        }

        @Override
        public void onFailure(Call<QuizAnswerStatusModel> call, Throwable t) {
            Log.d("TAG", "Error " + t.getMessage());
        }
    }
*/

    private class QuestionGetter implements Emitter.Listener {

        public void setQuestionText(TextView questionText, String question) {
            runOnUiThread(() -> questionText.setText(question));
        }

        @Override
        public void call(Object... args) {
            if (submitAnswerDialog.isShowing())
                submitAnswerDialog.dismiss();

            JSONObject jsonObject = (JSONObject) args[0];
            String jsonObjectString = jsonObject.toString();

            Gson gson = new Gson();
            QuizQuestionStatusModel quizQuestionStatusModel = gson.fromJson(jsonObjectString, QuizQuestionStatusModel.class);

            String question = quizQuestionStatusModel.getQuestion().getQuestion();
            String option1 = quizQuestionStatusModel.getQuestion().getOptions().get(0);
            String option2 = quizQuestionStatusModel.getQuestion().getOptions().get(1);
            String option3 = quizQuestionStatusModel.getQuestion().getOptions().get(2);
            String option4 = quizQuestionStatusModel.getQuestion().getOptions().get(3);

            Integer your_score = 0;
            Integer enemy_score = 0;
            String your_name = "";
            String enemy_name = "";

            //Parsing the player score to get your and enemy score
            List<PlayerDatum> playerData = quizQuestionStatusModel.getPlayerData();
            for (PlayerDatum datum : playerData) {
                if (datum.getPlayerid().equalsIgnoreCase(playerId)) {
                    your_score = datum.getPlayerScore();
                    your_name = datum.getUserName();
                } else {
                    enemy_score = datum.getPlayerScore();
                    enemy_name = datum.getUserName();
                }
            }

            //Setting the String text to the textviews
            setQuestionText(questionText, question);
            setQuestionText(optionText1, option1);
            setQuestionText(optionText2, option2);
            setQuestionText(optionText3, option3);
            setQuestionText(optionText4, option4);
            setQuestionText(yourScoreText, String.valueOf(your_score));
            setQuestionText(enemyScoreText, String.valueOf(enemy_score));
            setQuestionText(yourName, your_name);
            setQuestionText(enemyName, enemy_name);

            //Dismissing the dialog as we have fetched question
            fetchingNextQuestionDialog.dismiss();
        }
    }

    private class GameOverHandler implements Emitter.Listener {
        @Override
        public void call(Object... args) {
            if (submitAnswerDialog.isShowing())
                submitAnswerDialog.dismiss();

            if (!called) {
                startActivity(new Intent(MultiplayerQuiz.this, GameOverActivity.class));
                finish();
            }

            called = true;
            finish();
        }
    }

}
