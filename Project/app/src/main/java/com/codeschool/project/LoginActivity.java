package com.codeschool.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;

import com.codeschool.Models.LoginModel;
import com.codeschool.Models.LoginStatusModel;
import com.codeschool.Network.NetworkCient;
import com.codeschool.Utils.Misc;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginModel loginModel = new LoginModel("udayanbirajdar@gmail.com","1234");

        NetworkCient.ServerCommunicator communicator = NetworkCient.getClient("http://10.0.0.217:8000/");
        Call<LoginStatusModel> loginStatusModelCall = communicator.sendLoginData(loginModel);

        loginStatusModelCall.enqueue(new Callback<LoginStatusModel>() {
            @Override
            public void onResponse(Call<LoginStatusModel> call, Response<LoginStatusModel> response) {
                LoginStatusModel loginStatusModel = response.body();

                if(!loginStatusModel.getStatus().equals("Success")){
                    Misc.showToast(LoginActivity.this,loginStatusModel.getStatus());
                }
                else{
                    Misc.showToast(LoginActivity.this,"Login Success");
                }


            }

            @Override
            public void onFailure(Call<LoginStatusModel> call, Throwable t) {
                Log.d("TAG","ERROR "+t.getMessage());
            }
        });


    }
}
