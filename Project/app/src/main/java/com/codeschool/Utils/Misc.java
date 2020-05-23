package com.codeschool.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.codeschool.Interfaces.CourseSelectedHandler;
import com.codeschool.project.LoginActivity;
import com.codeschool.project.R;

import java.util.zip.Inflater;

import androidx.cardview.widget.CardView;

public class Misc {

    public static Dialog createDialog(Context context,int layoutFile,String message){

        View view = LayoutInflater.from(context).inflate(layoutFile,null,false);
        TextView loadingText = view.findViewById(R.id.loadingText);
        loadingText.setText(message);

        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        return dialog;

    }

    //Quiz fragment where we show dialog to choose subject
    public static Dialog createDialog(Context context, CourseSelectedHandler handler){
        View view = LayoutInflater.from(context).inflate(R.layout.choose_quiz_course,null,false);
        CardView courseJava = view.findViewById(R.id.courseJava);
        CardView courseAndroid = view.findViewById(R.id.courseAndroid);

        courseJava.setOnClickListener(new CardClickedHandler(handler));
        courseAndroid.setOnClickListener(new CardClickedHandler(handler));

        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        return dialog;
    }


    //Card click listener for the dialog. Used for quiz fragment where we show dialog to choose subject
    private static class CardClickedHandler implements  View.OnClickListener{
        CourseSelectedHandler handler;
        CardClickedHandler(CourseSelectedHandler handler){
            this.handler = handler;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.courseAndroid:
                handler.onCourseSelected("android");
                break;

                case R.id.courseJava:
                handler.onCourseSelected("java");
                break;
            }
        }
    }

    //Clearing session Data
    public static void clearDataFromSP(Context context,String sharedPreferenceName){
        SharedPreferences preferences = context.getSharedPreferences(sharedPreferenceName,Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

    public static void showToast(Context context,String toastMessage){
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public static void addStringToSharedPref(Context context,String sharedPreferenceName,String key,String value){
        SharedPreferences preferences = context.getSharedPreferences(sharedPreferenceName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static void addIntToSharedPref(Context context,String sharedPreferenceName,String key,int value){
        SharedPreferences preferences = context.getSharedPreferences(sharedPreferenceName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static void addBooleanToSharedPref(Context context,String sharedPreferenceName,String key,boolean value){
        SharedPreferences preferences = context.getSharedPreferences(sharedPreferenceName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static String getStringFromSharedPref(Context context,String sharedPreferenceName,String key){
        SharedPreferences preferences = context.getSharedPreferences(sharedPreferenceName,Context.MODE_PRIVATE);
        return preferences.getString(key,"Default Value");
    }

    public static int getIntFromSharedPref(Context context,String sharedPreferenceName,String key){
        SharedPreferences preferences = context.getSharedPreferences(sharedPreferenceName,Context.MODE_PRIVATE);
        return preferences.getInt(key,-1);
    }

    public static boolean getBooleanFromSharedPref(Context context,String sharedPreferenceName,String key){
        SharedPreferences preferences = context.getSharedPreferences(sharedPreferenceName,Context.MODE_PRIVATE);
        return preferences.getBoolean(key,false);
    }




}
