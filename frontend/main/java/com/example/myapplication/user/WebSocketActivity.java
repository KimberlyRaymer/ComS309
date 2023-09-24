package com.example.myapplication.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.utils.RecipeConstants;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;



/**
 * Websocket implementation
 */
public class WebSocketActivity extends AppCompatActivity {

    Button connect;
    Button send;
    EditText userID;
    EditText message;
    TextView history;

    private WebSocketClient cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websocket);

        connect = findViewById(R.id.connectBttn);
        send = findViewById(R.id.sendBttn);
        userID = findViewById(R.id.user_idBox);
        message = findViewById(R.id.messageBox);
        history = findViewById(R.id.historyText);

        /**
         * Updates the conversation
         */
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Draft[] drafts = {
                        new Draft_6455()
                };

                //change url
                String w = RecipeConstants.URL_WEBSOCKET + userID.getText().toString();

                try {
                    Log.d("Socket:", "Trying socket");
                    cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                        @Override
                        public void onMessage(String message) {
                            Log.d("", "run() returned: " + message);
                            String s = history.getText().toString();
                            history.setText(s + "\nServer:" + message);
                        }

                        @Override
                        public void onOpen(ServerHandshake handshake) {
                            Log.d("OPEN", "run() returned: " + "is connecting");
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.d("CLOSE", "onClose() returned: " + reason);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Exception:", e.toString());
                        }
                    };
                } catch (URISyntaxException e) {
                    Log.d("Exception:", e.getMessage().toString());
                    e.printStackTrace();
                }
                cc.connect();

            }
        });

        /**
         * Sends user message to other user
         */
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cc.send(message.getText().toString());
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }
        });

    }

}