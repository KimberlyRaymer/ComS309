package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.user.UserFlagReportActivity;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class that lists out all recipes that have been flagged by users.
 * Admins review over and create a report/warning for the creator of the recipe.
 */
public class FlaggedRecipeActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    ArrayList<String> flaggedRecipes = new ArrayList<>();
    ArrayList<Integer> recipeIDs = new ArrayList<>();
    ArrayList<Integer> reportIDs = new ArrayList<>();
    Button bttn;
    ImageButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flagged_recipe);

        linearLayout = findViewById(R.id.linearLayoutMyRecipes);
        backArrow = findViewById(R.id.recipeFlaggedArrow);

        getFlagReports();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FlaggedRecipeActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    View.OnClickListener handOnClick(final Button button, int rID, int reportID) {
        return new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(FlaggedRecipeActivity.this, UserFlagReportActivity.class);
                intent.putExtra("flagRecipeReportID", rID);
                intent.putExtra("recipeReportID", reportID);
                startActivity(intent);
            }
        };
    }

    //Volley functions

    /**
     * Gets all flag reports
     */
    private void getFlagReports() {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(FlaggedRecipeActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, RecipeConstants.URL_BLANK + "api/admin/recipeReports/", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //loops through users ingredient list
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject recipe = response.getJSONObject(i);
                        String r_Name = recipe.getString("reportTitle");
                        Integer r_id = response.getJSONObject(i).getInt("recipeID");
                        Integer report_id = response.getJSONObject(i).getInt("reportID");
                        flaggedRecipes.add(r_Name);
                        recipeIDs.add(r_id);
                        reportIDs.add(report_id);
                    }
                    if (!flaggedRecipes.isEmpty()) {
                        //creates cardview of listed recipes
                        for (int i = 0; i < flaggedRecipes.size(); i++) {
                            String recipeName = flaggedRecipes.get(i);
                            bttn = new Button(FlaggedRecipeActivity.this);
                            bttn.setId(i);
                            final int id = bttn.getId();
                            bttn.setText(recipeName);
                            int r_ID = recipeIDs.get(i);
                            int report_ID = reportIDs.get(i);
                            bttn.setOnClickListener(handOnClick(bttn, r_ID, report_ID));
                            linearLayout.addView(bttn);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FlaggedRecipeActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(jsonArrayRequest);

    }
}