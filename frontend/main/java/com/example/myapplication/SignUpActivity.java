package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.admin.AdminActivity;
import com.example.myapplication.home.ScrollingActivity;
import com.example.myapplication.user.ProfileActivity;
import com.example.myapplication.utils.GlobalVar;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.toolbox.JsonObjectRequest;

import java.io.UnsupportedEncodingException;

/**
 * Takes in a new user's information and allows them to
 * create an account
 */
public class SignUpActivity extends AppCompatActivity {

    EditText name2;
    EditText password2;
    Button login2;
    Button signup2;
    Button signBttn;
    ImageButton backArrow;
    TextView errorText;

    String userName;
    String userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name2 = findViewById(R.id.name2);
        password2 = findViewById(R.id.password2);
        login2 = findViewById(R.id.login2);
        signup2 = findViewById(R.id.signup2);
        signBttn = findViewById(R.id.signBttn);
        backArrow = findViewById(R.id.signUpArrow);
        errorText = findViewById(R.id.errorText2);

        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SignUpActivity.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });

        signBttn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                userName = name2.getText().toString();
                userPass = password2.getText().toString();

//                u_Name.setUserName(userName);

                if (userName.isEmpty() && userPass.isEmpty()) {
//                    Toast.makeText(SignUpActivity.this, "Please fill in both name and password", Toast.LENGTH_SHORT).show();
                    errorText.setText("Please fill in both name and password");
                    return;
                }
                if (userName.isEmpty() || userPass.isEmpty()) {
//                    Toast.makeText(SignUpActivity.this, "Please fill in the blank", Toast.LENGTH_SHORT).show();
                    errorText.setText("Please fill in the blank");
                    return;
                }
                userSignUp(userName, userPass);
//                Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
//                startActivity(intent);
            }
        });

    }

    //Volley Functions
    private void userSignUp(String name, String password) {
        try {
            // creating a new variable for our request queue
            RequestQueue requests = Volley.newRequestQueue(SignUpActivity.this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", name);
            jsonBody.put("password", password);
            jsonBody.put("user_type", 1);
            final String requestBody = jsonBody.toString();

            Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, RecipeConstants.URL_SIGNUP, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    errorText.setText(response.toString());
                    startActivity(intent);
                    try {

                        //if-else statement to check request was received
                        if (response.get("status").toString().equals("success")) {
                            Toast.makeText(SignUpActivity.this, response.get("status").toString(), Toast.LENGTH_SHORT).show();

                            GlobalVar.userType = 1;
                            Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
                            GlobalVar.userName = name;
                            intent.putExtra("resultSign", response.get("status").toString());
                            startActivity(intent);

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
                    Toast.makeText(SignUpActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
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