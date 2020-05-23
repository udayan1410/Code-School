package com.codeschool.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView msgs;
    Button btn;
    Socket mSocket;
    EditText edtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mSocket = IO.socket("http://10.0.0.217:3000");

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "Error Connection");
        }

        mSocket.connect();
        mSocket.on("newmessage",new OnNewMessageHandler());

        msgs = findViewById(R.id.msgs);
        btn = findViewById(R.id.btn);
        edtMessage = findViewById(R.id.edtMessage);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Button Clicked");
                String msg = edtMessage.getText().toString();

                edtMessage.setText("");
                msgs.append("\nYou : "+msg);
                mSocket.emit("message", msg);
            }
        });

    }

    private class OnNewMessageHandler implements Emitter.Listener {
        @Override
        public void call(final Object... args) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                try {
                    JSONObject jsonObject = (JSONObject) args[0];
                    String message = jsonObject.getString("message");
                    msgs.append("\n"+message);
                }catch (Exception e){e.printStackTrace();}
                }
            });
        }
    }
}
