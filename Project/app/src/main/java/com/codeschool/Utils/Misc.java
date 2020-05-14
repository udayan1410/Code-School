package com.codeschool.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.codeschool.project.R;

import java.util.zip.Inflater;

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
