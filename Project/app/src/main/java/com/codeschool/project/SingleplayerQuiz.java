package com.codeschool.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codeschool.Models.Question;
import com.codeschool.Models.QuizAnswerModel;
import com.google.gson.Gson;

import java.util.List;

public class SingleplayerQuiz extends AppCompatActivity {

    CardView option1,option2,option3,option4,submitButton,currentlySelectedCard = null;
    TextView yourScore,optionText1, optionText2, optionText3, optionText4, questionText,currentlySelectedText=null;
    int index=0;
    List<Question> questionList;
    int singlePlayerScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer_quiz);

        init();
        setQuestionAndOption();


        submitButton.setOnClickListener(v->{

            //Answer correct , goes in IF
            if(currentlySelectedText.getText().toString().equalsIgnoreCase(questionList.get(index).getAnswer())){
                Integer score = Integer.parseInt(yourScore.getText().toString());
                score+=1;
                yourScore.setText(score);
                index++;

                setQuestionAndOption();
            }
        });
    }

    public void init(){
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        submitButton = findViewById(R.id.submitButton);

        optionText1 = findViewById(R.id.option1Text);
        optionText2 = findViewById(R.id.option2Text);
        optionText3 = findViewById(R.id.option3Text);
        optionText4 = findViewById(R.id.option4Text);
        questionText = findViewById(R.id.quizQuestion);

        yourScore = findViewById(R.id.yourScore);
        //get Question list from DB

        //Set on click listener on cards
        option1.setOnClickListener(new OptionClickHandler());
        option2.setOnClickListener(new OptionClickHandler());
        option3.setOnClickListener(new OptionClickHandler());
        option4.setOnClickListener(new OptionClickHandler());

    }


    public void setQuestionAndOption(){
        //Getting options from the question's index's arraylist position
        optionText1.setText(questionList.get(index).getOptions().get(0));
        optionText2.setText(questionList.get(index).getOptions().get(1));
        optionText3.setText(questionList.get(index).getOptions().get(2));
        optionText4.setText(questionList.get(index).getOptions().get(3));

        //Setting question
        questionText.setText(questionList.get(index).getQuestion());

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
                    if(currentlySelectedText.getText().toString().equalsIgnoreCase(questionList.get(index).getAnswer()))
                        singlePlayerScore+=1;

                    index+=1;
                    if(questionList.size() == index)
                        Toast.makeText(SingleplayerQuiz.this, "End of Quiz", Toast.LENGTH_SHORT).show();

                    break;
            }

            if (submitButton.isClickable())
                submitButton.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

}
