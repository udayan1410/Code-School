package com.codeschool.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.codeschool.Models.Question;
import com.codeschool.Models.SubtopicDescriptionModel;
import com.codeschool.Models.SubtopicModel;
import com.codeschool.Models.TopicModel;
import com.codeschool.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private SQLiteDatabase db;
    private static DBHelper dbHelper = null;

    public DBHelper(Context context) {
        db = SQLiteDatabase.openDatabase(context.getCacheDir().getAbsolutePath() + Constants.DBName + ".sqlite", null, SQLiteDatabase.OPEN_READWRITE);
    }


    public static DBHelper getInstance(Context context) {
        if (dbHelper == null)
            dbHelper = new DBHelper(context);
        return dbHelper;
    }


    //Getting Topic list from course name
    public List<TopicModel> getTopicsFromCourse(String coursename) {
        List<TopicModel> topicModelList = new ArrayList<>();
        String query = "Select * from " + Constants.TABLE_COURSEDATA + " where coursename ='" + coursename + "'";
        Cursor c = db.rawQuery(query, null);
        List<String> originalTopicList = new ArrayList<>();
        while (c.moveToNext()) {
            String topic = c.getString(c.getColumnIndex("topicname"));
            if (!originalTopicList.contains(topic)) {
                originalTopicList.add(topic);

                TopicModel model = new TopicModel();
                model.setTopicName(c.getString(c.getColumnIndex("topicname")));
                model.setTopicInfo(c.getString(c.getColumnIndex("topicdescription")));
                model.setTopicCompleted(Integer.parseInt(c.getString(c.getColumnIndex("topiccompleted"))));
                topicModelList.add(model);

                Log.d("TAG", "" + model);
            }
        }
        Log.d("TAG", "Returning " + coursename + " List " + topicModelList.size());
        return topicModelList;
    }


    //Getting SubTopic list from topic name
    public List<SubtopicModel> getSubTopicFromTopic(String topicname) {
        List<SubtopicModel> subtopicModelList = new ArrayList<>();

        String query = "Select * from " + Constants.TABLE_COURSEDATA + " where topicname='" + topicname + "'";
        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            SubtopicModel subtopicModel = new SubtopicModel();
            subtopicModel.setSubtopicName(c.getString(c.getColumnIndex("subtopicname")));
            subtopicModel.setIsSubtopicCompleted(Integer.parseInt(c.getString(c.getColumnIndex("subtopiccompleted"))));
            subtopicModel.setImageLink(c.getString(c.getColumnIndex("subtopicimage")));
            subtopicModelList.add(subtopicModel);
        }
        return subtopicModelList;
    }

    //Getting subtopic Information based of topic and subtopicname
    public SubtopicDescriptionModel getSubtopicDescription(String topicname, String subtopicname) {
        SubtopicDescriptionModel model = new SubtopicDescriptionModel();

        String query = "Select * from " + Constants.TABLE_COURSEDATA + " where topicname='" + topicname + "' and subtopicname='" + subtopicname + "'";

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            model.setSubtopicName(subtopicname);
            model.setSubtopicImage(c.getString(c.getColumnIndex("subtopicimage")) + ".jpg");
            model.setSubtopicInfo(c.getString(c.getColumnIndex("subtopicinformation")));
        }

        return model;
    }

    //Updating that the topic is completed viewing
    public void updateTopicComplete(String topicName) {
        String query = "Update " + Constants.TABLE_COURSEDATA + " set topiccompleted='1' where topicname='" + topicName + "'";
        db.execSQL(query);
    }


    //Updating that the subtopic is completed viewing
    public void updateSubTopicComplete(String subtopicName) {
        String query = "Update " + Constants.TABLE_COURSEDATA + " set subtopiccompleted='1' where subtopicname='" + subtopicName + "'";
        db.execSQL(query);
    }


    public Integer totalCompletionPercent(String courseName) {
        String queryTotalCourses = "SELECT * FROM CourseData where coursename='" + courseName + "'";
        Cursor c = db.rawQuery(queryTotalCourses, null);

        String queryComplete = "SELECT * FROM CourseData where coursename='" + courseName + "' and topiccompleted='1' and subtopiccompleted='1'";
        Cursor c2 = db.rawQuery(queryComplete, null);

        double totalTopicsAvailable = c.getCount();
        double completedTopics = c2.getCount();

        //Return 0 initially as we havent started the actual course
        if ((int) completedTopics <= 1)
            return 0;

        double totalPercent = (completedTopics / totalTopicsAvailable) * 100;
        double roundedPercent = Math.round(Math.ceil(totalPercent));

        String percent = roundedPercent + "";
        percent = percent.substring(0, percent.length() - 2);
        return Integer.parseInt(percent);
    }

    public List<Question> getSinglePlayerQuestions(String courseName) {
        List<Question> questionList = new ArrayList<>();

        String query = "SELECT * FROM SingleQuiz where coursename='" + courseName + "'";

        Cursor c = db.rawQuery(query, null);

        Log.d("TAG","Final Query = "+query+" count = "+c.getCount());
        while (c.moveToNext()) {
            Question question = new Question();
            question.setId(Integer.parseInt(c.getString(c.getColumnIndex("id"))));
            question.setQuestion(c.getString(c.getColumnIndex("question")));
            question.setAnswer(c.getString(c.getColumnIndex("answer")));

            List<String> optionsArray = new ArrayList<>();
            optionsArray.add(c.getString(c.getColumnIndex("option1")));
            optionsArray.add(c.getString(c.getColumnIndex("option2")));
            optionsArray.add(c.getString(c.getColumnIndex("option3")));
            optionsArray.add(c.getString(c.getColumnIndex("option4")));

            question.setOptions(optionsArray);

            Log.d("TAG","Got Question  = "+question);
            questionList.add(question);
        }

        return questionList;
    }


}
