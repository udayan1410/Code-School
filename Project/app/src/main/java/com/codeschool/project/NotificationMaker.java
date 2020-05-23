package com.codeschool.project;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.github.nkzawa.socketio.client.Socket;

import androidx.annotation.RequiresApi;

public class NotificationMaker extends Application {

    public static final String Channel_LoginBack="LoginBack";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotification(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Channel_LoginBack, "Login To Code School", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Login Back to Code School");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


}

