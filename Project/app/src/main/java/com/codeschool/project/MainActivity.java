package com.codeschool.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.codeschool.Fragments.CatalogFragment;
import com.codeschool.Fragments.HomeFragment;
import com.codeschool.Fragments.ProfileFragment;
import com.codeschool.Fragments.QuizFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    //Initializing the views
    public void initialize(){
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationItems());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment(),"Home").commit();
    }

    //Button click listener for bottom navigation view
    private class BottomNavigationItems implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            switch(item.getItemId()){
                //If id is home load the home fragment
                case R.id.nav_home:
                    transaction.replace(R.id.fragmentContainer,new HomeFragment(),"Home");
                    break;

                //If id is catalog load the catalog fragment
                case R.id.nav_catalog:
                    transaction.replace(R.id.fragmentContainer,new CatalogFragment(),"Catalog");
                    break;

                //If id is quiz load the quiz fragment
                case R.id.nav_quiz:
                    transaction.replace(R.id.fragmentContainer,new QuizFragment(),"Quiz");
                    break;

                //If id is profile load the profile fragment
                case R.id.nav_profile:
                    transaction.replace(R.id.fragmentContainer,new ProfileFragment(),"Profile");
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
        if(!(fragment instanceof HomeFragment)) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new HomeFragment(), "Home").commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }

        //call back button code
        else
            super.onBackPressed();
    }
}
