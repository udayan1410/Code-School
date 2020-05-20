package com.codeschool.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codeschool.Database.DBHelper;
import com.codeschool.Models.CoursesEnrolledModel;
import com.codeschool.Models.TopicModel;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;
import com.google.gson.Gson;

import java.util.List;

public class CourseEnrollment extends AppCompatActivity {

    ImageView courseImage;
    Button enrollButton;
    ListView topicList;
    String courseName, courseDescription;
    int courseImageResource;
    List<TopicModel> topicModelList;
    CoursesEnrolledModel coursesEnrolledModel;
    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enrollment);

        courseName = getIntent().getStringExtra("course");

        if (courseName.equalsIgnoreCase("Android")) {
            courseDescription = "Android from Grass Root Level";
            courseImageResource = R.drawable.android_image;
        } else {
            courseDescription = "Java: An Object Oriented Approach";
            courseImageResource = R.mipmap.java_image;
        }

        getSupportActionBar().setTitle(courseName);
        init();
        getPreviousData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        enrollButton.setOnClickListener(new CourseEnrollmentHandler());
    }


    public void init() {
        courseImage = findViewById(R.id.courseImage);
        enrollButton = findViewById(R.id.enrollButton);
        topicList = findViewById(R.id.topicList);

        courseImage.setImageResource(courseImageResource);

        //Getting topics from DB based on course
        topicModelList = DBHelper.getInstance(this).getTopicsFromCourse(courseName);

        topicList.setAdapter(new TopicRenderrer(topicModelList));
    }

    public void getPreviousData() {
        //Getting shared preferences for the list of courses already enrolled
        String coursesEnrolledJsonString = Misc.getStringFromSharedPref(CourseEnrollment.this, Constants.COURSEENROLLED, Constants.COURSEENROLLED);

        //If no course enrolled then create a new object
        if (coursesEnrolledJsonString.equalsIgnoreCase("Default Value"))
            coursesEnrolledModel = new CoursesEnrolledModel();

            //Already enrolled for a course before so extract list from the old model saved in SP
        else {
            coursesEnrolledModel = gson.fromJson(coursesEnrolledJsonString, CoursesEnrolledModel.class);
            if (coursesEnrolledModel.getCoursesEnrolled().contains(courseName)) {
                enrollButton.setText("Already Enrolled");
                enrollButton.setEnabled(false);
                enrollButton.setClickable(false);
                enrollButton.setBackgroundColor(getResources().getColor(R.color.green));
            }
        }

    }


    private class TopicRenderrer extends BaseAdapter {

        List<TopicModel> topicModelList;

        TopicRenderrer(List<TopicModel> topicModelList) {
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
            TextView topicNumber, topicName, topicInfo;

            convertView = getLayoutInflater().inflate(R.layout.listitem_topics, parent, false);
            topicNumber = convertView.findViewById(R.id.topicNumber);
            topicName = convertView.findViewById(R.id.topicName);
            topicInfo = convertView.findViewById(R.id.topicInfo);

            topicNumber.setText("" + (position + 1));
            topicName.setText(topicModelList.get(position).getTopicName());
            topicInfo.setText(topicModelList.get(position).getTopicInfo());

            return convertView;
        }
    }


    private class CourseEnrollmentHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //Check if the user enrolls for a course already enrolled
            if (!coursesEnrolledModel.getCoursesEnrolled().contains(courseName)) {
                coursesEnrolledModel.getCoursesEnrolled().add(courseName);
                coursesEnrolledModel.getCoursesEnrolledInformation().add(courseDescription);
                coursesEnrolledModel.getCourseImage().add(courseImageResource);
            }

            enrollButton.setText("Already Enrolled");
            enrollButton.setEnabled(false);
            enrollButton.setClickable(false);
            enrollButton.setBackgroundColor(getResources().getColor(R.color.green));

            //Save back object to SP
            Misc.addStringToSharedPref(CourseEnrollment.this, Constants.COURSEENROLLED, Constants.COURSEENROLLED, gson.toJson(coursesEnrolledModel));
            Misc.showToast(CourseEnrollment.this, "Enrolled Successfully");
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


}
