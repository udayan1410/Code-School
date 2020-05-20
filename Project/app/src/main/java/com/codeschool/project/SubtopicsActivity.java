package com.codeschool.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeschool.Database.DBHelper;
import com.codeschool.Models.SubtopicModel;
import com.codeschool.Models.TopicModel;

import org.w3c.dom.Text;

import java.util.List;

public class SubtopicsActivity extends AppCompatActivity {

    private String topicName,nextTopic;
    DBHelper dbHelper;
    ListView subtopicListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopics);


        init();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(topicName);
    }


    public void init(){
        //Get topicName
        topicName = getIntent().getStringExtra("topic");

        //Getting the next topic to update the DB
        nextTopic = getIntent().getStringExtra("nexttopic");

        //Getting listview
        subtopicListView = findViewById(R.id.subtopicListview);
    }

    public void getSubTopicAndUpdateList(){
        //Get subtopics from Database
        dbHelper = DBHelper.getInstance(this);
        List<SubtopicModel> subtopicModelList = dbHelper.getSubTopicFromTopic(topicName);

        //Setting new adapter to list view
        subtopicListView.setAdapter(new SubTopicClickHandler(subtopicModelList));
    }


    private class SubTopicClickHandler extends BaseAdapter {

        List<SubtopicModel> subtopicModelList;

        SubTopicClickHandler(List<SubtopicModel> subtopicModelList){
            this.subtopicModelList = subtopicModelList;
        }

        @Override
        public int getCount() {
            return subtopicModelList.size();
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

            convertView = getLayoutInflater().inflate(R.layout.listitem_subtopic_card,parent,false);

            RelativeLayout relativeLayout = convertView.findViewById(R.id.subtopicLayout);

            ImageView statusImage = convertView.findViewById(R.id.statusImage);
            TextView subtopicName = convertView.findViewById(R.id.subtopicName);

            //Setting the subtopic Data
            Glide.with(SubtopicsActivity.this).load(R.mipmap.tick).into(statusImage);
            subtopicName.setText(subtopicModelList.get(position).getSubtopicName());

            //The specifc subtopic is locked
            if(subtopicModelList.get(position).getIsSubtopicCompleted()==0){
                relativeLayout.setClickable(false);
                relativeLayout.setEnabled(false);
                Glide.with(SubtopicsActivity.this).load(R.mipmap.lock).into(statusImage);
                subtopicName.setTextColor(getResources().getColor(R.color.darkerGray));
            }

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position == subtopicModelList.size()-1)
                        dbHelper.updateTopicComplete(nextTopic);

                    dbHelper.updateSubTopicComplete(subtopicModelList.get((position+1)%subtopicModelList.size()).getSubtopicName());

                    //Launching intent to show everything in detail
                    Intent intent = new Intent(SubtopicsActivity.this,FullViewActivity.class);
                    intent.putExtra("topic",topicName);
                    intent.putExtra("subtopic",subtopicModelList.get(position).getSubtopicName());
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        getSubTopicAndUpdateList();
        super.onResume();
    }
}
