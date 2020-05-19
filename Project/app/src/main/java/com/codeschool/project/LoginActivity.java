package com.codeschool.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codeschool.Models.LoginModel;
import com.codeschool.Models.LoginStatusModel;
import com.codeschool.Network.NetworkCient;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;
import com.google.gson.Gson;


public class LoginActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    TextView signUpTextView;
    Button loginButton;
    Dialog loadingDialog;
    NetworkCient.ServerCommunicator communicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializing all views
        init();

        //Setting on click listener on button
        loginButton.setOnClickListener(v -> {
                    //Opening a dialog box to show loading
                    loadingDialog.show();

                    //Making a post request to API
                    LoginModel loginModel = new LoginModel(loginEmail.getText().toString(), loginPassword.getText().toString());
                    communicator = NetworkCient.getClient(Constants.SERVER_URL);
                    Call<LoginStatusModel> loginStatusModelCall = communicator.sendLoginData(loginModel);
                    loginStatusModelCall.enqueue(new LoginCallbackHandler());
                }
        );

        // Setting on click listener on SignUp
        signUpTextView.setOnClickListener(v -> {
                    startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                }
        );

        //Get data from SP
        String loginStatusJson = Misc.getStringFromSharedPref(this,Constants.USERDATA,Constants.USERDATA);
        Gson gson = new Gson();

        //User already logged in
        if(!loginStatusJson.equalsIgnoreCase("Default Value")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }


    public void init() {
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.loginCreate);

        loadingDialog = Misc.createDialog(LoginActivity.this, R.layout.dialog_progress, "Logging in");
    }


    //Handling the callback of the post request
    private class LoginCallbackHandler implements Callback<LoginStatusModel> {
        @Override
        public void onResponse(Call<LoginStatusModel> call, Response<LoginStatusModel> response) {

            //Dismiss dialog box
            loadingDialog.dismiss();
            //Parse the data
            LoginStatusModel loginStatusModel = response.body();

            //Login status failed
            if (!loginStatusModel.getStatus().equals("Success"))
                Misc.showToast(LoginActivity.this, loginStatusModel.getStatus());

            //Login success
            else {
                Misc.showToast(LoginActivity.this, "Login Success");
                //Saving data to shared Prefs
                Gson gson = new Gson();
                Misc.addStringToSharedPref(LoginActivity.this, Constants.USERDATA, Constants.USERDATA, gson.toJson(loginStatusModel));
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }

        //Failed to retrieve data
        @Override
        public void onFailure(Call<LoginStatusModel> call, Throwable t) {
            loadingDialog.dismiss();
            Misc.showToast(LoginActivity.this, "Failed to Get Data. Check Internet");
        }
    }

}
