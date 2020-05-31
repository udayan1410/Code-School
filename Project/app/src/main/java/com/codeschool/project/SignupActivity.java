package com.codeschool.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codeschool.Models.SignupModel;
import com.codeschool.Models.SignupStatusModel;
import com.codeschool.Network.NetworkCient;
import com.codeschool.Utils.Constants;
import com.codeschool.Utils.Misc;

public class SignupActivity extends AppCompatActivity {

    Button signupButton;
    EditText username,email,password;
    NetworkCient.ServerCommunicator communicator;
    Dialog dialog;
    ImageView signupImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle("Signup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Showing Dialog
                dialog.show();

                //Give url
                communicator = NetworkCient.getClient(Constants.SERVER_URL);

                //Create Model
                SignupModel model = new SignupModel(email.getText().toString(),password.getText().toString(),username.getText().toString());

                //Call function
                Call<SignupStatusModel> call = communicator.sendSignupData(model);

                //Handle return
                call.enqueue(new SignupStatusListener());
            }
        });

    }

    public void init(){
        signupButton = findViewById(R.id.signupButton);
        email = findViewById(R.id.signupEmail);
        password = findViewById(R.id.signupPassword);
        username = findViewById(R.id.signupUsername);
        signupImage = findViewById(R.id.signupImage);

        Glide.with(SignupActivity.this).load(R.drawable.logo).into(signupImage);
        dialog = Misc.createDialog(SignupActivity.this,R.layout.dialog_progress,"Signing up");
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private class SignupStatusListener implements Callback<SignupStatusModel>{
        @Override
        public void onResponse(Call<SignupStatusModel> call, Response<SignupStatusModel> response) {
            dialog.dismiss();
            SignupStatusModel signupStatusModel = response.body();

            if(!signupStatusModel.getStatus().equals("Success"))
                Misc.showToast(SignupActivity.this,"User already exists");

            else {
                Misc.showToast(SignupActivity.this, "Signup Successfull");
                finish();
            }

        }

        @Override
        public void onFailure(Call<SignupStatusModel> call, Throwable t) {
            dialog.dismiss();
            Misc.showToast(SignupActivity.this,"Failed To signup");
        }
    }

}
