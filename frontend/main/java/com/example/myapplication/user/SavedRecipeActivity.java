package com.example.myapplication.user;

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
import com.example.myapplication.recipeView.RecipeViewActivity;
import com.example.myapplication.utils.GlobalVar;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Class that lists all of user's saved recipes
 */
public class SavedRecipeActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    ArrayList<String> userRecipes = new ArrayList<>();
    ArrayList<Integer> recipeIDs = new ArrayList<>();
    ImageButton backArrow;
    Button bttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipe);

        linearLayout = findViewById(R.id.linearLayoutSaveRecipes);
        backArrow = findViewById(R.id.saveRecipeArrow);

        getRecipeNames();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SavedRecipeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    View.OnClickListener handOnClick(int rID) {
        return new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(SavedRecipeActivity.this, RecipeViewActivity.class);
                boolean viewRecipe = true;
                intent.putExtra("flag", viewRecipe);
                intent.putExtra("recipeID", rID);
                startActivity(intent);
            }
        };
    }

    //Volley functions
    /**
     * Get all recipes that user have saved
     */
    private void getRecipeNames() {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(SavedRecipeActivity.this);

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, RecipeConstants.URL_RECIPE_USERS + GlobalVar.userName + "/saved-recipe-names-and-ids", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        String r_Name = response.getJSONObject(i).getString("recipeName");
                        Integer r_id = response.getJSONObject(i).getInt("recipeID");
                        userRecipes.add(r_Name);
                        recipeIDs.add(r_id);
                    }
                    Toast.makeText(SavedRecipeActivity.this, "Good", Toast.LENGTH_SHORT).show();

                    if (!userRecipes.isEmpty()) {
                        //creates cardview of listed recipes
                        for (int i = 0; i < userRecipes.size(); i++) {
                            String recipeName = userRecipes.get(i);
                            bttn = new Button(SavedRecipeActivity.this);
                            bttn.setId(i);
                            final int id = bttn.getId();
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
                Toast.makeText(SavedRecipeActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(jsonArray);

    }
}