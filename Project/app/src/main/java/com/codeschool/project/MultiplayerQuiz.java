package com.codeschool.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MultiplayerQuiz extends AppCompatActivity {

    CardView option1,option2,option3,option4,submitButton;
    TextView optionText1,optionText2,optionText3,optionText4;
    CardView currentlySelectedCard = null;
    TextView currentlySelectedText = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_quiz);

        init();

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

        option1.setOnClickListener(new OptionClickHandler());
        option2.setOnClickListener(new OptionClickHandler());
        option3.setOnClickListener(new OptionClickHandler());
        option4.setOnClickListener(new OptionClickHandler());

    }



    private class OptionClickHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            submitButton.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            if(currentlySelectedCard!=null){
                currentlySelectedCard.setCardBackgroundColor(getResources().getColor(R.color.white));
                currentlySelectedText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }

            switch (v.getId()){

                case R.id.option1:
                    currentlySelectedCard = option1;
                    currentlySelectedText=optionText1;
                    changeColors(option1,optionText1,getResources().getColor(R.color.colorPrimaryDark),getResources().getColor(R.color.white));
                    break;

                case R.id.option2:
                    currentlySelectedCard = option2;
                    currentlySelectedText=optionText2;
                    changeColors(option2,optionText2,getResources().getColor(R.color.colorPrimaryDark),getResources().getColor(R.color.white));
                    break;

                case R.id.option3:
                    currentlySelectedCard = option3;
                    currentlySelectedText=optionText3;
                    changeColors(option3,optionText3,getResources().getColor(R.color.colorPrimaryDark),getResources().getColor(R.color.white));
                    break;

                case R.id.option4:
                    currentlySelectedCard = option4;
                    currentlySelectedText=optionText4;
                    changeColors(option4,optionText4,getResources().getColor(R.color.colorPrimaryDark),getResources().getColor(R.color.white));
                    break;
            }

        }

        public void changeColors(CardView card,TextView text,int cardColor,int textColor){
            card.setCardBackgroundColor(cardColor);
            text.setTextColor(textColor);
        }
    }

}
