package com.codeschool.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeschool.Database.DBHelper;
import com.codeschool.Models.TopicModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TopicsActivity extends AppCompatActivity {

    private String courseName;
    DBHelper dbHelper;
    ListView topiclistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        init();

    }

    public void init() {
        //Get course name
        courseName = getIntent().getStringExtra("course");

        //Reference
        topiclistView = findViewById(R.id.topiclistView);
    }

    public void getTopicsAndUpdateList(){
        //Get topics from Database
        dbHelper = DBHelper.getInstance(this);
        List<TopicModel> topicModelList = dbHelper.getTopicsFromCourse(courseName);

        //ListView Setting adapter
        topiclistView.setAdapter(new TopicClickHandler(topicModelList));
    }


    private class TopicClickHandler extends BaseAdapter {

        List<TopicModel> topicModelList;

        TopicClickHandler(List<TopicModel> topicModelList) {
            this.topicModelList = topicModelList;
        }

        @Override
        public int getCount() {
            return topicModelList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.listitem_topic_card,parent,false);

            RelativeLayout relativeLayout = convertView.findViewById(R.id.topicLayout);
            TextView topicName, topicInfo;
            ImageView statusImage;

            topicName = convertView.findViewById(R.id.topicName);
            topicInfo = convertView.findViewById(R.id.topicInfo);
            statusImage = convertView.findViewById(R.id.statusImage);

            //Setting the Topic Data
            topicName.setText(topicModelList.get(position).getTopicName());
            topicInfo.setText(topicModelList.get(position).getTopicInfo());
            Glide.with(TopicsActivity.this).load(R.mipmap.tick).into(statusImage);

            //If course is locked then change the image
            if(topicModelList.get(position).isTopicCompleted()==0){
                relativeLayout.setEnabled(false);
                relativeLayout.setClickable(false);
                topicName.setTextColor(getResources().getColor(R.color.darkerGray));
                topicInfo.setTextColor(getResources().getColor(R.color.darkerGray));
                Glide.with(TopicsActivity.this).load(R.mipmap.lock).into(statusImage);
            }

            //Listener for the layout
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TopicsActivity.this,SubtopicsActivity.class);
                    intent.putExtra("topic",topicModelList.get(position).getTopicName());
                    intent.putExtra("nexttopic",topicModelList.get((position+1)%topicModelList.size()).getTopicName());

                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    @Override
    protected void onResume() {
        getTopicsAndUpdateList();
        super.onResume();
    }
}
