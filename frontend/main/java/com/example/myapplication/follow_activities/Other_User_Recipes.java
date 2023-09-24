package com.example.myapplication.follow_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.myapplication.MenuActivity;
import com.example.myapplication.R;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Other_User_Recipes extends AppCompatActivity {
    //put in other activity
    ArrayList<String> userRecipes;
    ArrayList<Integer> recipeIDs;
    LinearLayout linearLayout;
    //




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_recipes);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("oun");


        userRecipes(userName);

    }


    /**
     * Helper method to allow dynamically created
     * bttn move to another screen
     * @param recipeID
     */
    View.OnClickListener handOnClick(int recipeID) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Other_User_Recipes.this, MenuActivity.class);
                intent.putExtra("user_recipeID", recipeID);
                startActivity(intent);
            }
        };
    }

    //Volley functions
    public void userRecipes(String username) {
        RequestQueue queue = Volley.newRequestQueue(Other_User_Recipes.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, RecipeConstants.URL_RECIPE_USERS + username + "/recipe-names-and-ids", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        String r_Name = response.getJSONObject(i).getString("recipeName");
                        Integer r_id = response.getJSONObject(i).getInt("recipeID");
                        userRecipes.add(r_Name);
                        recipeIDs.add(r_id);
                    }
                    Toast.makeText(Other_User_Recipes.this, "Good", Toast.LENGTH_SHORT).show();

                    if (!userRecipes.isEmpty()) {
                        //creates cardview of listed recipes
                        for (int i = 0; i < userRecipes.size(); i++) {
                            String recipeName = userRecipes.get(i);
                            Button bttn = new Button(Other_User_Recipes.this);
                            bttn.setId(i);
                            bttn.setText(recipeName);
                            int r_ID = recipeIDs.get(i);
                            bttn.setOnClickListener(handOnClick(r_ID));
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
                Toast.makeText(Other_User_Recipes.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }
}
