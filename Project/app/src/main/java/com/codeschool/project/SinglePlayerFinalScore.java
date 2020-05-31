package com.codeschool.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SinglePlayerFinalScore extends AppCompatActivity {

    CardView singlePlayerQuizDone;
    TextView finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_final_score);

        singlePlayerQuizDone = findViewById(R.id.singlePlayerQuizDone);
        finalScore = findViewById(R.id.finalScore);
        finalScore.setText(""+getIntent().getStringExtra("score"));

        singlePlayerQuizDone.setOnClickListener(v -> finish());

    }
}
