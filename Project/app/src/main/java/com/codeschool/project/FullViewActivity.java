package com.codeschool.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeschool.Database.DBHelper;
import com.codeschool.Models.SubtopicDescriptionModel;

public class FullViewActivity extends AppCompatActivity {

    ImageView subtopicImage;
    TextView subtopicNameText,subtopicInfoText;

    String subtopicName,topicName;
    DBHelper db;
    SubtopicDescriptionModel descriptionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        topicName = getIntent().getStringExtra("topic");
        subtopicName = getIntent().getStringExtra("subtopic");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    public void init(){
        subtopicNameText = findViewById(R.id.subtopicName);
        subtopicInfoText = findViewById(R.id.subtopicInfo);
        subtopicImage = findViewById(R.id.subtopicImage);

        //Getting subtopicname and topicname from DB
        db = DBHelper.getInstance(FullViewActivity.this);
        descriptionModel = db.getSubtopicDescription(topicName,subtopicName);


        if(descriptionModel.getSubtopicImage().equalsIgnoreCase("null.jpg")) {
            subtopicImage.setVisibility(View.GONE);
        }
        else {
            Glide.with(this).load(descriptionModel.getSubtopicImage()).placeholder(R.drawable.loading).into(subtopicImage);
        }

        //Setting name and infor
        subtopicNameText.setText(subtopicName);
        subtopicInfoText.setText(descriptionModel.getSubtopicInfo());
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
