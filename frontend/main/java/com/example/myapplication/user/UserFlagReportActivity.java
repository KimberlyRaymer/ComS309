package com.example.myapplication.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.utils.GlobalVar;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class that allows a user to flag another user.
 * User makes a report as to what was wrong with
 * the recipe or profile
 */
public class UserFlagReportActivity extends AppCompatActivity {

    EditText reportTitle;
    EditText userFlagBox;
    EditText userTask;
    Button userFlagBttn;
    Button adminReportBttn;
    ImageButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_flag_report);

        reportTitle = findViewById(R.id.reportTitle);
        userFlagBox = findViewById(R.id.userFlagBox);
        userFlagBttn = findViewById(R.id.userFlagBttn);
        userTask = findViewById(R.id.userTask);
        adminReportBttn = findViewById(R.id.adminReportBttn);
        backArrow = findViewById(R.id.userFlagArrow);

        Intent intent = getIntent();
        int num = intent.getIntExtra("flagRecipeID", 1);
        //for admin
        int num2 = intent.getIntExtra("flagRecipeReportID", 1);
        int num3 = intent.getIntExtra("recipeReportID", 1);

        if (GlobalVar.userType == 2) {
            recipeReport(num2, num3);
            userTask.setVisibility(View.VISIBLE);
            userFlagBttn.setVisibility(View.INVISIBLE);
            adminReportBttn.setVisibility(View.VISIBLE);
        }
        else {
            userTask.setVisibility(View.INVISIBLE);
            userFlagBttn.setVisibility(View.VISIBLE);
            adminReportBttn.setVisibility(View.INVISIBLE);
        }

        userFlagBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = reportTitle.getText().toString();
                String detail = userFlagBox.getText().toString();
                postFlagRecipe(title, detail, num);
                Intent intent = new Intent(UserFlagReportActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        adminReportBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usersTasks = userTask.getText().toString();
                sendReport(usersTasks, num2, num3);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
    }

    //Volley Functions
    /**
     * Updates report then sent to user who was flagged
     */
    private void sendReport(String adminReport, int flagID, int reportID) {
        try {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(UserFlagReportActivity.this);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("adminReply", adminReport);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, RecipeConstants.URL_BLANK + "api/recipes/" + flagID + "/reports/" + reportID + "/admin", jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(UserFlagReportActivity.this, "Good", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserFlagReportActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a specific recipe report
     */
    private void recipeReport(int flagID, int reportID) {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(UserFlagReportActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RecipeConstants.URL_BLANK + "api/recipes/" + flagID + "/reports/" + reportID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String r_Name = response.getString("reportTitle");
                    String summary = response.getString("reportDetail");
                    if (GlobalVar.userType == 2) {
                        String adminReply = response.getString("adminReply");
                        userTask.setText(adminReply);
                    }

                    reportTitle.setText(r_Name);
                    userFlagBox.setText(summary);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserFlagReportActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(jsonObjectRequest);
    }

    /**
     * User creates a flag report for a recipe or user
     * @param title
     * @param detail
     * @param r_ID
     */
    private void postFlagRecipe(String title, String detail, int r_ID) {
        try {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(UserFlagReportActivity.this);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("title", title);
        jsonBody.put("detail", detail);
        jsonBody.put("recipeID", r_ID);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, RecipeConstants.URL_BLANK + "api/" + GlobalVar.userName + "/recipe/report", jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(UserFlagReportActivity.this, "Good", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserFlagReportActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}