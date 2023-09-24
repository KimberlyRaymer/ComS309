package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.admin.AdminActivity;
import com.example.myapplication.home.ScrollingActivity;
import com.example.myapplication.user.ProfileActivity;
import com.example.myapplication.utils.GlobalVar;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.JsonObjectRequest;

/**
 * Takes in user information and allows them to log back into their profile
 */
public class LoginActivity extends AppCompatActivity {

    EditText name;
    EditText password;
    Button login;
    Button signup;
    Button logBttn;
    ImageButton backArrow;
    TextView errorText;
    String userName;
    String userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        logBttn = findViewById(R.id.logBttn);
        backArrow = findViewById(R.id.logInArrow);
        errorText = findViewById(R.id.errorText);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });

        logBttn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userName = name.getText().toString();
                userPass = password.getText().toString();

                if (userName.isEmpty() && userPass.isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "Please fill in both name and password", Toast.LENGTH_SHORT).show();
                    errorText.setText("Please fill in both name and password");
                    return;
                }
                if (userName.isEmpty() || userPass.isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "Please fill in the blank", Toast.LENGTH_SHORT).show();
                    errorText.setText("Please fill in the blank");
                    return;
                }
                userLogin(userName, userPass);
            }
        });

    }

    //Volley Functions

    /**
     * Posts user login information
     * @param name
     * @param password
     */
    private void userLogin(String name, String password) {
        try {
            // creating a new variable for our request queue
            RequestQueue requests = Volley.newRequestQueue(LoginActivity.this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", name);
            jsonBody.put("password", password);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, RecipeConstants.URL_LOGIN, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        //if-else statement to check request was received
                        if (response.get("status").toString().equals("success")) {
                            Toast.makeText(LoginActivity.this, response.get("status").toString(), Toast.LENGTH_SHORT).show();

                            int userType = response.getInt("userType");
                            GlobalVar.userType = userType;
                            //if userType 1 go to ProfileActivity
                            //else AdminActivity
                            if (userType == 1) {
                                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                GlobalVar.userName = name;
                                intent.putExtra("result", response.get("status").toString());
                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                intent.putExtra("resultAdmin", response.get("status").toString());
                                GlobalVar.adminName = userName;
                                startActivity(intent);
                            }

                        } else {
                            String text = "Error with login, please try again";
                            errorText.setText(text);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                }
            });
//            {
//                @Override
//                public byte[] getBody() {
//                    try {
//                        return requestBody == null ? null : requestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                        return null;
//                    }
//                }
//            };
            requests.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}