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
import com.codeschool.Models.Playerstatus;
import com.codeschool.Models.QuizAnswerModel;
import com.codeschool.Models.QuizAnswerStatusModel;
import com.codeschool.Models.QuizQuestionStatusModel;
import com.codeschool.Network.NetworkCient;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;

public class MultiplayerQuiz extends AppCompatActivity {

    CardView option1, option2, option3, option4, submitButton,currentlySelectedCard = null;
    TextView optionText1, optionText2, optionText3, optionText4,questionText, yourScoreText, enemyScoreText,currentlySelectedText = null;
    NetworkCient.ServerCommunicator communicator;
    String sessionId, playerId;
    Dialog fetchingNextQuestionDialog,submitAnswerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_quiz);


        init();
        fetchQuestion();
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
        fetchingNextQuestionDialog = Misc.createDialog(MultiplayerQuiz.this,R.layout.dialog_progress,"Fetching Next Question");
        submitAnswerDialog = Misc.createDialog(MultiplayerQuiz.this,R.layout.dialog_progress,"Answer Submitted. Waiting for other player");

    }

    public void fetchQuestion() {
        fetchingNextQuestionDialog.show();
        Call<QuizQuestionStatusModel> call = communicator.getQuestionData(sessionId);
        call.enqueue(new QuestionGetterHandler());
    }


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
                    //Setting up the model with player's answer and
                    QuizAnswerModel quizAnswerModel = new QuizAnswerModel();
                    quizAnswerModel.setAnswer(answer);
                    quizAnswerModel.setId(playerid);

                    //Sending data to backend
                    Call<QuizAnswerStatusModel> call = communicator.postAnswer(sessionId,quizAnswerModel);
                    call.enqueue(new AnswerPostedHandler());

                    break;
            }

            if(submitButton.isClickable())
                submitButton.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }


    private class AnswerPostedHandler implements Callback<QuizAnswerStatusModel>{
        @Override
        public void onResponse(Call<QuizAnswerStatusModel> call, Response<QuizAnswerStatusModel> response) {
            QuizAnswerStatusModel quizAnswerStatusModel = response.body();
            submitAnswerDialog.dismiss();
            //Quiz is not ended
            if(!quizAnswerStatusModel.getIsEndOfQuiz()){
                fetchQuestion();
            }
            else{
                startActivity(new Intent(MultiplayerQuiz.this,GameOverActivity.class));
                finish();
            }

        }

        @Override
        public void onFailure(Call<QuizAnswerStatusModel> call, Throwable t) {
            Log.d("TAG","Error "+t.getMessage());
        }
    }

}
