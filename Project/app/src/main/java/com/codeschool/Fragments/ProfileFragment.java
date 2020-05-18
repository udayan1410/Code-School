package com.codeschool.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeschool.Models.LoginStatusModel;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;
import com.codeschool.project.LoginActivity;
import com.codeschool.project.R;
import com.codeschool.project.SignupActivity;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    TextView welcomeText,multiPlayerStreakText,singlePlayerStreakText;
    Gson gson;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_profile,container,false);
        Button button = v.findViewById(R.id.show);

        initialize(v);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        return v;
    }


    public void initialize(View v){
        gson = new Gson();

        welcomeText = v.findViewById(R.id.welcomeText);
        singlePlayerStreakText = v.findViewById(R.id.singlePlayerStreakText);
        multiPlayerStreakText = v.findViewById(R.id.multiPlayerStreakText);

        //Getting username from SP
        String loginStatusModelJson = Misc.getStringFromSharedPref(getContext(), Constants.USERDATA,Constants.USERDATA);

        LoginStatusModel loginStatusModel = gson.fromJson(loginStatusModelJson,LoginStatusModel.class);

        welcomeText.setText(loginStatusModel.getUserData().getUsername());
        singlePlayerStreakText.setText(loginStatusModel.getUserData().getSinglePlayerStreak());
        multiPlayerStreakText.setText(loginStatusModel.getUserData().getMultiPlayerStreak());

    }

}
