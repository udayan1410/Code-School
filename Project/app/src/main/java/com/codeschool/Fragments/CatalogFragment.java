package com.codeschool.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeschool.Models.CoursesEnrolledModel;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;
import com.codeschool.project.R;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class CatalogFragment extends Fragment {

    CardView courseJava,courseAndroid;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_catalog,container,false);
        initialize(v);
        return v;
    }


    public void initialize(View v){
        courseJava = v.findViewById(R.id.courseJava);
        courseAndroid = v.findViewById(R.id.courseAndroid);

        courseJava.setOnClickListener(new CourseClickedEnrolledHandler());
        courseAndroid.setOnClickListener(new CourseClickedEnrolledHandler());
    }

    //On card click . Show a dialog to choose the course
    private class CourseClickedEnrolledHandler implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.courseJava:
                    dialog = createDialog("Java","Java Basics and Advanced Course",R.mipmap.java_image);
                    dialog.show();
                    break;

                case R.id.courseAndroid:
                    dialog = createDialog("Android","Android from grass root level",R.drawable.android_image);
                    dialog.show();
                    break;
            }
        }
    }


    //Dialog to show the courses available

    public AlertDialog createDialog(String courseName,String courseDescription,Integer img){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select a Course to Enroll")
                .setMessage("Are you sure you want to enroll for "+courseName+"?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    //Getting shared preferences for the list of courses already enrolled
                    Gson gson = new Gson();
                    String coursesEnrolledJsonString = Misc.getStringFromSharedPref(getContext(), Constants.COURSEENROLLED,Constants.COURSEENROLLED);
                    CoursesEnrolledModel coursesEnrolledModel;

                    //If no course enrolled then create a new object
                    if(coursesEnrolledJsonString.equalsIgnoreCase("Default Value"))
                        coursesEnrolledModel = new CoursesEnrolledModel();

                    //Already enrolled for a course before so extract list from the old model saved in SP
                    else
                        coursesEnrolledModel = gson.fromJson(coursesEnrolledJsonString,CoursesEnrolledModel.class);

                    //Check if the user enrolls for a course already enrolled
                    if(!coursesEnrolledModel.getCoursesEnrolled().contains(courseName)) {
                        coursesEnrolledModel.getCoursesEnrolled().add(courseName);
                        coursesEnrolledModel.getCoursesEnrolledInformation().add(courseDescription);
                        coursesEnrolledModel.getCourseImage().add(img);
                    }

                    //Save back object to SP
                    Misc.addStringToSharedPref(getContext(),Constants.COURSEENROLLED,Constants.COURSEENROLLED,gson.toJson(coursesEnrolledModel));
                    dialog.dismiss();

                    Misc.showToast(getContext(),"Enrolled Successfully");
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());

    return  builder.create();
    }

}
