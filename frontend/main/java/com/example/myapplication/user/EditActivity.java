package com.example.myapplication.user;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Class that allows user to change the username and
 * password
 */
public class EditActivity extends AppCompatActivity {

    EditText editOldName;
    EditText editName;
    EditText editOldPassword;
    EditText editPassword;
    ImageButton editArrow;
    Button saveChanges;
    TextView errorText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editOldName = findViewById(R.id.editOldName);
        editName = findViewById(R.id.editName);
        editOldPassword = findViewById(R.id.editOldPassword);
        editPassword = findViewById(R.id.editPassword);
        editArrow = findViewById(R.id.editArrow);
        saveChanges = findViewById(R.id.saveChanges);
        errorText3 = findViewById(R.id.errorText3);

        editArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String oldUsername = editOldName.getText().toString();
                String userName = editName.getText().toString();
                String userPass = editPassword.getText().toString();

                if (userName.isEmpty() && userPass.isEmpty()) {
                    Toast.makeText(EditActivity.this, "Please fill in both name and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                editAccount(userName, userPass, oldUsername);

                Intent intent = new Intent(EditActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //Volley Functions

    /**
     * Takes user's changes and updates their profile information for backend
     * @param name
     * @param password
     * @param oldUsername
     */
    private void editAccount(String name, String password, String oldUsername) {
        try {
            // creating a new variable for our request queue
            RequestQueue requests = Volley.newRequestQueue(EditActivity.this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", name);
            jsonBody.put("password", password);
            jsonBody.put("user_type", 1);
            final String requestBody = jsonBody.toString();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, RecipeConstants.URL_EDIT + oldUsername, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    errorText3.setText(response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EditActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                public byte[] getBody() {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            requests.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}