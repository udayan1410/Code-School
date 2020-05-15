package com.codeschool.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeschool.Models.FindMatchModel;
import com.codeschool.Models.FindMatchStatusModel;
import com.codeschool.Models.LoginStatusModel;
import com.codeschool.Network.NetworkCient;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;
import com.codeschool.project.MultiplayerQuiz;
import com.codeschool.project.R;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuizFragment extends Fragment {

    CardView multiplayerQuiz;
    NetworkCient.ServerCommunicator communicator;
    Dialog dialog;
    boolean foundMatch=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_quiz,container,false);
        dialog = Misc.createDialog(getContext(),R.layout.dialog_progress,"Finding Match");

        multiplayerQuiz = v.findViewById(R.id.multiPlayer);
        multiplayerQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                Gson gson = new Gson();
                //Getting the saved json in String format
                String loginStatusJson = Misc.getStringFromSharedPref(getContext(),Constants.USERDATA,Constants.USERDATA);

                //Converting String back to json
                LoginStatusModel loginStatusModel = gson.fromJson(loginStatusJson,LoginStatusModel.class);

                //Getting id from userdata model
                FindMatchModel findMatchModel = new FindMatchModel("android",loginStatusModel.getUserData().getId());

                communicator = NetworkCient.getClient(Constants.SERVER_URL);
                Call<FindMatchStatusModel> call = communicator.findMatch(findMatchModel);
                call.enqueue(new FindMatchHandler());

            }
        });

        return v;
    }


    private class FindMatchHandler implements Callback<FindMatchStatusModel>{
        @Override
        public void onResponse(Call<FindMatchStatusModel> call, Response<FindMatchStatusModel> response) {
            dialog.dismiss();
            startActivity(new Intent(getActivity(), MultiplayerQuiz.class));
        }

        @Override
        public void onFailure(Call<FindMatchStatusModel> call, Throwable t) {

        }
    }

}
