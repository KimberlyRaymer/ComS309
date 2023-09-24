package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.recipeView.RecipeViewActivity;
import com.example.myapplication.utils.GlobalVar;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeSearch extends AppCompatActivity
{
//    ArrayList<Ingredient> ingredients;
//    ArrayList<String> ingstring;
//    ArrayList<String> recipe_ingredients;
//    ArrayList<RecipeObject> recipeList;
//    ArrayList<RecipeObject> recipeListResult;
//    String username;
//
//    LinearLayout linearLayout;
//    SearchView sv;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recipe_search);
//
//        Intent intent = getIntent();
//        username = intent.getStringExtra("un");
//        getIngredients(username);
//
//        sv = findViewById(R.id.recipeSearch);
//        sv.setOnQueryTextListener(RecipeSearch.class);
//
//        linearLayout = findViewById(R.id.linearLayoutResults);
//
//
//    }
//    /**
//     *
//     * @param query
//     * @return
//     */
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        getRecipe_And_Ingredients(query);
//        filter(query);
//        return true;
//    }
//
//    /**
//     *
//     * @param query
//     * @return
//     */
//    @Override
//    public boolean onQueryTextChange(String query)
//    {
//        return false;
//    }
//
//    /**
//     *retrieves user's ingredients
//     * @param user
//     */
//    private void getIngredients(String user) {
//        RequestQueue queue = Volley.newRequestQueue(RecipeSearch.this);
//        JsonArrayRequest jsonAR = new JsonArrayRequest(Request.Method.GET, RecipeConstants.USER_PANTRY + user + "/ingredients", null, new Response.Listener<JSONArray>()
//        {
//            @Override
//            public void onResponse(JSONArray response)
//            {
//                System.out.println(response);
//                try {
//                    for (int i = 0; i < response.length(); i++)
//                    {
//                        ingstring.add(response.getJSONObject(i).getString("name"));
//                    }
//                    System.out.println(ingredients);
//                } catch (JSONException exception) {
//                    Toast.makeText(RecipeSearch.this, "JAVA CANNOT PARSE THIS RESPONSE" + exception.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                Toast.makeText(RecipeSearch.this, "FAILED, ERROR IS: " + error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//        queue.add(jsonAR);
//    }
//
//
//
//    public void filter(String text)
//    {
//        text = text.toUpperCase(Locale.getDefault());
//        if(text.length() == 0)
//        {
//            ingredientList.addAll(ingredientArrayList);
//        }
//        else
//        {
//            for(Ingredient ing : ingredientArrayList)
//            {
//                if(ing.getIngredientName().toUpperCase(Locale.getDefault()).contains(text))
//                {
//                    ingredientList.add(ing);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
//
//    View.OnClickListener handOnClick(final Button button, int rID) {
//        return new View.OnClickListener(){
//            public void onClick(View view) {
//                Intent intent = new Intent(RecipeSearch.this, RecipeViewActivity.class);
//                boolean viewRecipe = true;
//                intent.putExtra("flag", viewRecipe);
//                intent.putExtra("recipeID", rID);
//                startActivity(intent);
//            }
//        };
//    }
//
//    private void getRecipe_And_Ingredients() {
//        // creating a new variable for our request queue
//        RequestQueue requests = Volley.newRequestQueue(RecipeSearch.this);
//
//        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, RecipeConstants.URL_BLANK + "recipe-search/random", null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                try {
//                    for (int i = 0; i < response.length(); i++)
//                    {
//                        String recipeIngredients = response.getJSONObject(i).getJSONObject("ingredientList").getString("ingredientName");
//
//                        String r_Name = response.getJSONObject(i).getString("recipeName");
//                        Integer r_id = response.getJSONObject(i).getInt("recipeID");
//                        userRecipes.add(r_Name);
//                        recipeIDs.add(r_id);
//                    }
//                    Toast.makeText(RecipeSearch.this, "Good", Toast.LENGTH_SHORT).show();
//
//                    if (!userRecipes.isEmpty()) {
//                        //creates cardview of listed recipes
//                        for (int i = 0; i < userRecipes.size(); i++) {
//                            String recipeName = userRecipes.get(i);
//                            bttn = new Button(RecipeSearch.this);
//                            bttn.setId(i);
//                            final int id = bttn.getId();
//                            bttn.setText(recipeName);
//                            int r_ID = recipeIDs.get(i);
//                            bttn.setOnClickListener(handOnClick(bttn, r_ID));
//                            linearLayout.addView(bttn);
//
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(RecipeSearch.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//        requests.add(jsonArray);
//
//    }
//
    }
