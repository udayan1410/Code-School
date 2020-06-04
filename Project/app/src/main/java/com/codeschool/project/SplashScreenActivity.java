package com.codeschool.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    Intent i;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        logo = (ImageView) findViewById(R.id.logo);
        getSupportActionBar().hide();

        this.i = new Intent(this, LoginActivity.class);
        new Thread() {
            public void run() {
                try {
                    sleep(1800L);
                    SplashScreenActivity.this.startActivity(SplashScreenActivity.this.i);
                    SplashScreenActivity.this.finish();
                    return;
                } catch (InterruptedException localInterruptedException) {
                    localInterruptedException = localInterruptedException;
                    SplashScreenActivity.this.startActivity(SplashScreenActivity.this.i);
                    SplashScreenActivity.this.finish();
                    return;
                }

            }
        }.start();
    }
}
