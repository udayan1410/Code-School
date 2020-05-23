package com.codeschool.Models;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.util.Log;

import com.codeschool.project.NotificationMaker;
import com.codeschool.project.R;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationService extends JobService {

    public boolean jobCancelled = false;
    NotificationManagerCompat notificationManagerCompat;
    Notification notification;
    Context context;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("TAG", "JOB Started");

        context = this;
        doBackgroundWork(params);
        //Return true for running background. Device will be awake
        return true;
    }


    public void doBackgroundWork(JobParameters params) {
        new Thread(new Runnable() {
            int totalTime = 5;

            @Override
            public void run() {
                int i = 0;
                try {
                    while (i != totalTime) {
                        if (jobCancelled)
                            return;
                        Thread.sleep(1000);
                        i++;
                        Log.d("TAG", "performing job " + i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("TAG", "JOB FInished");


                //Setting up notificaiton
                notificationManagerCompat = notificationManagerCompat.from(context);
                notification = new NotificationCompat.Builder(context, NotificationMaker.Channel_LoginBack)
                        .setContentTitle("We are missing you")
                        .setContentText("Come back and practice multiplayer quizzes")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setSmallIcon(R.drawable.notification_icon)
                        .build();

                notificationManagerCompat.notify(1, notification);
                jobFinished(params, false);
            }
        }).start();


    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("TAG", "JOB Stopped");
        jobCancelled = true;
        return true;
    }
}
