package com.example.myapplication.recipeView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.RecipeObject;
//import com.example.myapplication.user.UserFlagReportActivity;
import com.example.myapplication.user.UserFlagReportActivity;
import com.example.myapplication.utils.GlobalVar;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class that allows users to look, save, and/or comment on a recipe
 */
public class RecipeViewFragment extends Fragment {

    Context context;
    TextView recipeName;
    TextView userProfile;
    TextView tagsTitle;
    TextView serving;
    TextView cookingTime;
    TextView description;
    EditText commentBox;
    Button add;
    Button flagBttn;
    Button saveRecipeBttn;
    LinearLayout linearLayout;

    RecipeObject recipe;
    String comment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = inflater.getContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recipeName = view.findViewById(R.id.recipeName2);
        userProfile = view.findViewById(R.id.userProfile);
        tagsTitle = view.findViewById(R.id.tagsTitle);
        serving = view.findViewById(R.id.servings2);
        cookingTime = view.findViewById(R.id.cookingTime2);
        description = view.findViewById(R.id.description2);
        commentBox = view.findViewById(R.id.commentBox);
        add = view.findViewById(R.id.addCommentBttn);
        linearLayout = view.findViewById(R.id.linearLayoutView);
        flagBttn = view.findViewById(R.id.flagBttn);
        saveRecipeBttn = view.findViewById(R.id.saveRecipeBttn);

        Intent intent = getActivity().getIntent();
        int num = intent.getIntExtra("recipeID", 1);

        //ADD A BOOLEAN FROM MYRECIPESACTIVITY TO CHECK IF THE RECIPEVIEW IS FROM MYRECIPEACTIVITY OTHERWISE GETRECIPE R_ID FROM RANDOM SEARCH

        getRecipe(num);
        getComments(num);

        //need to post comment!
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment = commentBox.getText().toString();
                if (!comment.isEmpty()) {
                    postUserComment(num, comment);
                } else {
                    Toast.makeText(context, "Please put in comment", Toast.LENGTH_LONG).show();
                }
            }
        });

        flagBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserFlagReportActivity.class);
                intent.putExtra("flagRecipeID", num);
                startActivity(intent);
            }
        });

        saveRecipeBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecipe(num);
            }
        });
    }

    //Volley functions

    /**
     * Saves recipe into user's profile account
     */
    private void saveRecipe(int r_Id) {
        RequestQueue requests = Volley.newRequestQueue(context);
        StringRequest string = new StringRequest(Request.Method.POST, RecipeConstants.URL_RECIPE_USERS + GlobalVar.userName + "/recipes/" + r_Id + "/save", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(string);
    }

    /**
     * Gets comments posted on a specific recipe
     * @param r_Id
     */
    private void getComments(int r_Id) {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, RecipeConstants.URL_BLANK + "api/recipes/" + r_Id + "/comments", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        String u_Name = response.getJSONObject(i).getString("name");
                        String comment = response.getJSONObject(i).getString("body");

                        View view = getLayoutInflater().inflate(R.layout.comment_card, null);
                        TextView userName = view.findViewById(R.id.commenterName);
                        userName.setText(u_Name);
                        TextView commentText = view.findViewById(R.id.commentText);
                        commentText.setText(comment);
                        linearLayout.addView(view);
                    }
                    Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(jsonArray);

    }

    /**
     * Posts user's comment to any recipe
     * @param r_Id
     * @param body
     */
    private void postUserComment(int r_Id, String body) {
        try {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", GlobalVar.userName);
        jsonBody.put("body", body);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, RecipeConstants.URL_BLANK + "api/recipes/" + r_Id + "/comments", jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a the recipe content
     * @param r_Id
     */
    private void getRecipe(int r_Id) {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RecipeConstants.URL_RECIPE + r_Id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String r_Name = response.getString("title");
                    String summary = response.getString("summary");
                    int cookMin = response.getInt("cookingMinutes");
                    int servings = response.getInt("servings");

                    //tags
                    ArrayList<String> tagsList = new ArrayList<>();
                    JSONArray temp2 = response.getJSONArray("tags");
                    int length2 = temp2.length();
                    if (length2 > 0) {
                        for (int i = 0; i < length2; i++) {
                            tagsList.add(temp2.getString(i));
                        }
                    }

                    recipe = new RecipeObject();
                    recipe.setRecipeName(r_Name);
                    recipe.setSummary(summary);
                    recipe.setCookingMin(cookMin);
                    recipe.setServings(servings);
//                    recipe.setIng_List(ingredients);
//                    recipe.setInstruct_List(instructionsList);
                    recipe.setTags_List(tagsList);

                    recipeName.setText(recipe.getRecipeName());
                    //NEED TO PUT IN USERNAME
                    serving.setText(String.valueOf(recipe.getServings()));
                    cookingTime.setText(String.valueOf(recipe.getCookingMin()));
                    description.setText(recipe.getSummary());

                    String tag = "No tags";
                    String tagsListed = " ";
                    if (!recipe.getTags_List().isEmpty()) {
                        for (int i = 1; i < recipe.getTags_List().size(); i++) {
                            tagsListed = recipe.getTags_List().get(0);
                            tagsListed += ", " + recipe.getTags_List().get(i);
                        }
                        tagsTitle.setText(tagsListed);
                    } else {
                        tagsTitle.setText(tag);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(jsonObjectRequest);
    }
}