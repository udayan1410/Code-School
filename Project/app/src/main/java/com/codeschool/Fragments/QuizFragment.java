package com.codeschool.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codeschool.Interfaces.CourseSelectedHandler;
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
    FindMatchModel findMatchModel;
    int timerCounter = 0;
    boolean matchFound = false;
    Dialog courseSelectionDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz, container, false);
        dialog = Misc.createDialog(getContext(), R.layout.dialog_progress, "Finding Match");

        multiplayerQuiz = v.findViewById(R.id.multiPlayer);
        multiplayerQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseSelectionDialog = Misc.createDialog(getContext(), course -> {
                    //Start Timer. If timer more than 20seconds then cancel find
                    Thread thread = new Thread(new Timer());
                    thread.start();

                    dialog.show();

                    Gson gson = new Gson();
                    //Getting the saved json in String format
                    String loginStatusJson = Misc.getStringFromSharedPref(getContext(), Constants.USERDATA, Constants.USERDATA);

                    //Converting String back to json
                    LoginStatusModel loginStatusModel = gson.fromJson(loginStatusJson, LoginStatusModel.class);

                    //Getting id from userdata model
                    findMatchModel = new FindMatchModel(course, loginStatusModel.getUserData().getId());

                    communicator = NetworkCient.getClient(Constants.SERVER_URL);
                    Call<FindMatchStatusModel> call = communicator.findMatch(findMatchModel);
                    call.enqueue(new FindMatchHandler());

                    courseSelectionDialog.dismiss();
                });

                courseSelectionDialog.show();

            }
        });

        return v;
    }


    private class FindMatchHandler implements Callback<FindMatchStatusModel> {
        @Override
        public void onResponse(Call<FindMatchStatusModel> call, Response<FindMatchStatusModel> response) {

            FindMatchStatusModel findMatchStatusModel = response.body();

            //Save Session ID to shared Preferences
            Gson gson = new Gson();
            Misc.addStringToSharedPref(getContext(),Constants.SESSIONDATA,Constants.SESSIONDATA,gson.toJson(findMatchStatusModel));

            //Setting bit that match found so toast wont be shown
            matchFound=true;

            dialog.dismiss();
            startActivity(new Intent(getActivity(), MultiplayerQuiz.class));
        }

        @Override
        public void onFailure(Call<FindMatchStatusModel> call, Throwable t) {

        }
    }

    private class Timer implements Runnable {
        @Override
        public void run() {
            try {
                //Looping till 20 seconds
                while (timerCounter < 20) {
                    Thread.sleep(1000);
                    timerCounter += 1;
                }
                if(!matchFound)
                    cancelFindingMatch();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelFindingMatch() {
        //Reset the counter
        timerCounter = 0;

        Call<FindMatchStatusModel> call = communicator.cancelFindMatch(findMatchModel);
        call.enqueue(new Callback<FindMatchStatusModel>() {
            @Override
            public void onResponse(Call<FindMatchStatusModel> call, Response<FindMatchStatusModel> response) {
                dialog.dismiss();
                Misc.showToast(getContext(), "No Match Found");
            }

            @Override
            public void onFailure(Call<FindMatchStatusModel> call, Throwable t) {
                dialog.dismiss();
                Misc.showToast(getContext(), "No Match Found");
            }
        });
    }

}
