package com.codeschool.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codeschool.Models.LoginModel;
import com.codeschool.Models.LoginStatusModel;
import com.codeschool.Network.NetworkCient;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;


public class LoginActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
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
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Opening a dialog box to show loading
                loadingDialog.show();

                //Making a post request to API
                LoginModel loginModel = new LoginModel(loginEmail.getText().toString(), loginPassword.getText().toString());
                communicator = NetworkCient.getClient(Constants.SERVER_URL);
                Call<LoginStatusModel> loginStatusModelCall = communicator.sendLoginData(loginModel);
                loginStatusModelCall.enqueue(new LoginCallbackHandler());
            }
        });
    }


    public void init(){
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);

        loadingDialog = Misc.createDialog(LoginActivity.this,R.layout.dialog_progress);
    }


    //Handling the callback of the post request
    private class LoginCallbackHandler implements Callback<LoginStatusModel>{
        @Override
        public void onResponse(Call<LoginStatusModel> call, Response<LoginStatusModel> response) {
            //Dismiss dialog box
            loadingDialog.dismiss();

            //Parse the data
            LoginStatusModel loginStatusModel = response.body();

            //Checking if status is success
            if (!loginStatusModel.getStatus().equals("Success"))
                Misc.showToast(LoginActivity.this, loginStatusModel.getStatus());

             else
                Misc.showToast(LoginActivity.this, "Login Success");

        }

        //Failed to retrieve data
        @Override
        public void onFailure(Call<LoginStatusModel> call, Throwable t) {
            loadingDialog.dismiss();
            Misc.showToast(LoginActivity.this,"Failed to Get Data. Check Internet");
        }
    }

}
