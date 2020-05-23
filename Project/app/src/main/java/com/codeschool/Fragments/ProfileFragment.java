package com.codeschool.Fragments;

import android.app.Notification;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeschool.Database.DBHelper;
import com.codeschool.Models.LoginStatusModel;
import com.codeschool.Models.NotificationService;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;
import com.codeschool.project.LoginActivity;
import com.codeschool.project.NotificationMaker;
import com.codeschool.project.R;
import com.codeschool.project.SignupActivity;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    TextView welcomeText, multiPlayerStreakText, singlePlayerStreakText, androidCompletedPercent, javaCompletedPercent;
    Gson gson;
    DBHelper dbHelper;
    Handler mHandler;
    View courseAndroidProgress, courseJavaProgress;
    public static final int TOTAL_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    int androidCourseCompletedMaxWidth;
    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize(v);


        Button logoutButton = v.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view -> {
                    Misc.clearDataFromSP(getContext(), Constants.USERDATA);
                    startActivity(new Intent(getContext(), LoginActivity.class));

                    //Start service to show notification after a few hours
                    ComponentName componentName = new ComponentName(getContext(), NotificationService.class);
                    JobInfo info = new JobInfo.Builder(123, componentName)
                            .setPersisted(true)
                            .build();
                    JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                    int code = scheduler.schedule(info);

                    if(code == JobScheduler.RESULT_SUCCESS)
                        Log.d("TAG","JOB scheduled successfully");
                    else
                        Log.d("TAG","JOB scheduled failed");

/*
            //Setting up notificaiton
            notificationManagerCompat =  notificationManagerCompat.from(getContext());
            notification = new NotificationCompat.Builder(getContext(), NotificationMaker.Channel_LoginBack)
                    .setContentTitle("Code School Login Reminder")
                    .setContentText("Come back and practice multiplayer quizzes with real players online!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setSmallIcon(R.drawable.android_image)
                    .build();

            notificationManagerCompat.notify(1, notification);*/

                    getActivity().finish();
                }
        );

        return v;
    }


    public void initialize(View v) {
        gson = new Gson();

        welcomeText = v.findViewById(R.id.welcomeText);
        singlePlayerStreakText = v.findViewById(R.id.singlePlayerStreakText);
        multiPlayerStreakText = v.findViewById(R.id.multiPlayerStreakText);
        androidCompletedPercent = v.findViewById(R.id.androidCompletedPercent);
        javaCompletedPercent = v.findViewById(R.id.javaCompletedPercent);
        courseAndroidProgress = v.findViewById(R.id.courseAndroidProgress);
        courseJavaProgress = v.findViewById(R.id.courseJavaProgress);


        //Width for progress
        androidCourseCompletedMaxWidth = (int) ((TOTAL_WIDTH * 2) / 3);

        //Getting db Instance
        dbHelper = DBHelper.getInstance(getContext());


        //Getting username from SP
        String loginStatusModelJson = Misc.getStringFromSharedPref(getContext(), Constants.USERDATA, Constants.USERDATA);
        LoginStatusModel loginStatusModel = gson.fromJson(loginStatusModelJson, LoginStatusModel.class);

        welcomeText.setText(loginStatusModel.getUserData().getUsername());
        singlePlayerStreakText.setText(loginStatusModel.getUserData().getSinglePlayerStreak());
        multiPlayerStreakText.setText(loginStatusModel.getUserData().getMultiPlayerStreak());

        //Getting percentage completed data from DB

        int androidPecent = dbHelper.totalCompletionPercent("Android");
        int javaPecent = dbHelper.totalCompletionPercent("Java");

        //Completed Text
        animatePercent(androidPecent, androidCompletedPercent, courseAndroidProgress);
        animatePercent(javaPecent, javaCompletedPercent, courseJavaProgress);

    }

    public void animatePercent(int percent, TextView textView, View progressView) {
        mHandler = new Handler();
        new Thread(() -> {
            try {
                int currentPercent = 0;

                while (currentPercent < percent) {
                    Thread.sleep(10);
                    currentPercent += 1;
                    String percentString = currentPercent + "%";

                    int valueOfCurrentProgress = (androidCourseCompletedMaxWidth * currentPercent) / 100;

                    mHandler.post(() -> {
                        textView.setText(percentString);
                        progressView.getLayoutParams().width = valueOfCurrentProgress;
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }


}
