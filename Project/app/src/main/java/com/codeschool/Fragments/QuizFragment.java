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
import com.codeschool.Network.WebSocket;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;
import com.codeschool.project.MultiplayerQuiz;
import com.codeschool.project.R;
import com.codeschool.project.SingleplayerQuiz;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuizFragment extends Fragment {

    CardView multiplayerQuiz, singlePlayerQuiz;
    NetworkCient.ServerCommunicator communicator;
    Dialog dialog;
    FindMatchModel findMatchModel;
    int timerCounter = 0;
    boolean matchFound = false;
    Dialog courseSelectionDialog;
    Socket mSocket;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz, container, false);
        dialog = Misc.createDialog(getContext(), R.layout.dialog_progress, "Finding Match");

        connectToServer();

        multiplayerQuiz = v.findViewById(R.id.multiPlayer);
        singlePlayerQuiz = v.findViewById(R.id.singlePlayer);

        multiplayerQuiz.setOnClickListener(clickListener -> {
            courseSelectionDialog = Misc.createDialog(getContext(), course -> {
                //Start Timer. If timer more than 20seconds then cancel find
                Thread thread = new Thread(new Timer());
                thread.start();
                getActivity().runOnUiThread(() -> {
                            //Showing loading dialog
                            dialog.show();

                            Gson gson = new Gson();
                            //Getting the saved json in String format
                            String loginStatusJson = Misc.getStringFromSharedPref(getContext(), Constants.USERDATA, Constants.USERDATA);

                            //Converting String back to json
                            LoginStatusModel loginStatusModel = gson.fromJson(loginStatusJson, LoginStatusModel.class);

                            //Getting id from userdata model
                            findMatchModel = new FindMatchModel(course, loginStatusModel.getUserData().getId(), loginStatusModel.getUserData().getUsername());

                            //Sending signal to find match
                            String gsonString = gson.toJson(findMatchModel);
                            mSocket.emit("findMatch", gsonString);

                            courseSelectionDialog.dismiss();
                        }
                );
            });
            courseSelectionDialog.show();
        });

        singlePlayerQuiz.setOnClickListener(clickListener -> {
            courseSelectionDialog = Misc.createDialog(getContext(), course -> {
                Intent intent = new Intent(getContext(), SingleplayerQuiz.class);
                intent.putExtra("course",course);



            });
            courseSelectionDialog.show();
        });

        return v;
    }

    public void connectToServer() {


        try {

            Log.d("TAG","Connecting to server");
            mSocket = IO.socket(Constants.REALTIME_SERVER_URL);
            mSocket.connect();
            //Setting the socket object to be used later
            WebSocket.setSocket(mSocket);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mSocket.on("findSuccess", new FindMatchSuccessHandler());
    }

    private class FindMatchSuccessHandler implements Emitter.Listener {
        @Override
        public void call(Object... args) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Setting bit that match found so toast wont be shown
                            matchFound = true;
                            //Getting json data
                            JSONObject findMatchStatusObject = (JSONObject) args[0];

                            //Making model
                            FindMatchStatusModel findMatchStatusModel = new FindMatchStatusModel(findMatchStatusObject.getString("status"), findMatchStatusObject.getString("sessionID"));

                            //Save Session ID to shared Preferences
                            Gson gson = new Gson();
                            Misc.addStringToSharedPref(getContext(), Constants.SESSIONDATA, Constants.SESSIONDATA, gson.toJson(findMatchStatusModel));

                            //Dismissing dialog and launching next activity
                            dialog.dismiss();
                            startActivity(new Intent(getActivity(), MultiplayerQuiz.class));

                        } catch (
                                Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private class FindMatchHandler implements Callback<FindMatchStatusModel> {
        @Override
        public void onResponse(Call<FindMatchStatusModel> call, Response<FindMatchStatusModel> response) {

            FindMatchStatusModel findMatchStatusModel = response.body();

            //Save Session ID to shared Preferences
            Gson gson = new Gson();
            Misc.addStringToSharedPref(getContext(), Constants.SESSIONDATA, Constants.SESSIONDATA, gson.toJson(findMatchStatusModel));

            //Setting bit that match found so toast wont be shown
            matchFound = true;

            dialog.dismiss();
            startActivity(new Intent(getActivity(), MultiplayerQuiz.class));
            getActivity().onBackPressed();
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
                if (!matchFound)
                    cancelFindingMatch();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void cancelFindingMatch() {
        //Reset the counter
        timerCounter = 0;
        Gson gson = new Gson();

        //Send cancel find request
        mSocket.emit("cancelFind", gson.toJson(findMatchModel));
        getActivity().runOnUiThread(() -> {
            Misc.showToast(getContext(), "No match Found Try again later");
            dialog.dismiss();
        });

    }


}
