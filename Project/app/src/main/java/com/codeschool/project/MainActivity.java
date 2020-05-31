package com.codeschool.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;

import com.codeschool.Fragments.CatalogFragment;
import com.codeschool.Fragments.HomeFragment;
import com.codeschool.Fragments.ProfileFragment;
import com.codeschool.Fragments.QuizFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private int saveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        copyDB();

    }


    //Initializing the views
    public void initialize() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationItems());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment(), "Home").commit();
        getSupportActionBar().setTitle("Home");
    }

    //Copying the DB to file
    public void copyDB() {
        Log.d("dbcopy", "in if");
        try {
            Context myContext = MainActivity.this;
            String DB_path = this.getCacheDir().getAbsolutePath();
            String DB_name = "CodeSchool.sqlite";
            String outFileName = DB_path + DB_name;

            File f = this.getDatabasePath(outFileName);
            if (!f.exists()) {
                InputStream myInput = myContext.getAssets().open(DB_name);
                OutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0)
                    myOutput.write(buffer, 0, length);

                myOutput.flush();
                myOutput.close();
                myInput.close();
                Log.d("dbcopy", "dbabsent");
            } else {
                Log.d("dbcopy", "dbpresent");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Button click listener for bottom navigation view
    private class BottomNavigationItems implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            switch (item.getItemId()) {
                //If id is home load the home fragment
                case R.id.nav_home:
                    transaction.replace(R.id.fragmentContainer, new HomeFragment(), "Home");
                    getSupportActionBar().setTitle("Home");
                    break;

                //If id is catalog load the catalog fragment
                case R.id.nav_catalog:
                    transaction.replace(R.id.fragmentContainer, new CatalogFragment(), "Catalog");
                    getSupportActionBar().setTitle("Catalog");
                    break;

                //If id is quiz load the quiz fragment
                case R.id.nav_quiz:
                    transaction.replace(R.id.fragmentContainer, new QuizFragment(), "Quiz");
                    getSupportActionBar().setTitle("Quiz");
                    break;

                //If id is profile load the profile fragment
                case R.id.nav_profile:
                    transaction.replace(R.id.fragmentContainer, new ProfileFragment(), "Profile");
                    getSupportActionBar().setTitle("Profile");
                    break;
            }
            transaction.commit();
            return true;
        }
    }

    //Overriding the on back pressed method to redirect user back to home fragment so that user doenst quit app
    @Override
    public void onBackPressed() {
        Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        //If current fragment is not home fragment then replace current with home
        if (!(fragment instanceof HomeFragment)) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new HomeFragment(), "Home").commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }

        //call back button code
        else
            super.onBackPressed();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

}
