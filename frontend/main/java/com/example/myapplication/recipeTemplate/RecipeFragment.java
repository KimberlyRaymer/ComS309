package com.example.myapplication.recipeTemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.user.ProfileActivity;
import com.example.myapplication.R;
import com.example.myapplication.RecipeObject;
import com.example.myapplication.utils.GlobalVar;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for the first fragment: Recipe
 * get the image, servings, cooking time,
 * description, and what tags were selected
 * (highlighted in blue for selected)
 */
public class RecipeFragment extends Fragment {

    Context context;
    TextView recipeName;
    TextView resultRecipe;
    EditText servings;
    EditText cookTime;
    EditText description;
    Button deleteRecipe;
    Button saveChanges;

    String serves = " ";
    String c_Time = " ";
    String descript = " ";

    boolean view_Recipe;

    RecipeObject recipe;

    //Tag buttons
    //cuisines
    Button chinese;
    Button indian;
    Button korean;
    Button japanese;
    Button italian;
    Button french;
    Button vietnamese;
    Button mexican;
    Button american;

    //diets
    Button glutenFree;
    Button ketogenic;
    Button vegetarian;
    Button whole30;
    Button paleo;
    Button vegan;

    //courseType
    Button breakfast;
    Button mainCourse;
    Button dessert;
    Button appetizer;
    Button salad;
    Button sideDish;
    Button snack;
    Button beverage;
    Button soup;
    //end of tag buttons

    ArrayList<Button> buttonList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = inflater.getContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        deleteRecipe = view.findViewById(R.id.deleteRecipe);
        saveChanges = view.findViewById(R.id.saveChangeRecipe);
        recipeName = view.findViewById(R.id.recipeName);
        servings = view.findViewById(R.id.servings);
        cookTime = view.findViewById(R.id.cookTime);
        description = view.findViewById(R.id.description);
        resultRecipe = view.findViewById(R.id.resultRecipe);

        //tag buttons
        //cuisines
        chinese = view.findViewById(R.id.chineseTag);
        buttonList.add(chinese);

        indian = view.findViewById(R.id.indianTag);
        buttonList.add(indian);

        korean = view.findViewById(R.id.koreanTag);
        buttonList.add(korean);

        japanese = view.findViewById(R.id.japaneseTag);
        buttonList.add(japanese);

        italian = view.findViewById(R.id.italianTag);
        buttonList.add(italian);

        french = view.findViewById(R.id.frenchTag);
        buttonList.add(french);

        vietnamese = view.findViewById(R.id.vietnameseTag);
        buttonList.add(vietnamese);

        mexican = view.findViewById(R.id.mexicanTag);
        buttonList.add(mexican);

        american = view.findViewById(R.id.americanTag);
        buttonList.add(american);

        //diets
        glutenFree = view.findViewById(R.id.glutenFreeTag);
        buttonList.add(glutenFree);

        ketogenic = view.findViewById(R.id.ketogenicTag);
        buttonList.add(ketogenic);

        vegetarian = view.findViewById(R.id.vegetarianTag);
        buttonList.add(vegetarian);

        whole30 = view.findViewById(R.id.whole30Tag);
        buttonList.add(whole30);

        paleo = view.findViewById(R.id.paleoTag);
        buttonList.add(paleo);

        vegan = view.findViewById(R.id.veganTag);
        buttonList.add(vegan);

        //courseType
        breakfast = view.findViewById(R.id.breakfastTag);
        buttonList.add(breakfast);

        mainCourse = view.findViewById(R.id.mainCourseTag);
        buttonList.add(mainCourse);

        dessert = view.findViewById(R.id.dessertTag);
        buttonList.add(dessert);

        appetizer = view.findViewById(R.id.appetizerTag);
        buttonList.add(appetizer);

        salad = view.findViewById(R.id.saladTag);
        buttonList.add(salad);

        sideDish = view.findViewById(R.id.sideDishTag);
        buttonList.add(sideDish);

        snack = view.findViewById(R.id.snackTag);
        buttonList.add(snack);

        beverage = view.findViewById(R.id.beverageTag);
        buttonList.add(beverage);

        soup = view.findViewById(R.id.soupTag);
        buttonList.add(soup);


        //applies button color method to all buttons
        //cuisines
        setupColorButton(chinese);
        setupColorButton(indian);
        setupColorButton(korean);
        setupColorButton(japanese);
        setupColorButton(italian);
        setupColorButton(french);
        setupColorButton(vietnamese);
        setupColorButton(mexican);
        setupColorButton(american);

        //diets
        setupColorButton(glutenFree);
        setupColorButton(ketogenic);
        setupColorButton(vegetarian);
        setupColorButton(whole30);
        setupColorButton(paleo);
        setupColorButton(vegan);

        //courseTypes
        setupColorButton(breakfast);
        setupColorButton(mainCourse);
        setupColorButton(dessert);
        setupColorButton(appetizer);
        setupColorButton(salad);
        setupColorButton(sideDish);
        setupColorButton(snack);
        setupColorButton(beverage);
        setupColorButton(soup);
        //end of tag buttons

        Intent intent = getActivity().getIntent();
        String result = intent.getStringExtra("resultRecipe");
        resultRecipe.setText(result);

        String recipeTitle = intent.getStringExtra("recipeTitle");
        int num = intent.getIntExtra("recipeID", 0);
        view_Recipe = intent.getBooleanExtra("flag", false);
        if (view_Recipe) {
            getRecipe(num);
            GlobalVar.r_Id = num;
            GlobalVar.recipeName = recipeTitle;
        }
        else {
            recipeName.setText(GlobalVar.recipeName);
        }
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!servings.getText().toString().isEmpty()) {
                    serves = servings.getText().toString();
                }
                if (!cookTime.getText().toString().isEmpty()) {
                    c_Time = cookTime.getText().toString();
                }
                if (!description.getText().toString().isEmpty()) {
                    descript = description.getText().toString();
                }
                postRecipe(GlobalVar.recipeName, descript, c_Time, serves, getBttns());
            }
        });

        deleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view_Recipe) {
                    deleteRecipe(num);
                }
                else {
                    deleteRecipe(GlobalVar.r_Id);
                }
                Intent intent = new Intent(context, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Iterates through all tag buttons and checks the color.
     * If colored blue, add to tags list
     */
    ArrayList<String> tags = new ArrayList<>();
    private ArrayList<String> getBttns() {
        for (int i = 0; i < buttonList.size(); i++) {
            if (Integer.parseInt(buttonList.get(i).getTag().toString()) == 1) {
                tags.add(buttonList.get(i).getText().toString());
            }
        }
        return tags;
    }

    /**
     * Method to change the button color
     * Grey being not selected and blue being selected
     * @param button
     */
    private void setupColorButton(Button button) {
        button.setTag(0);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int status = (Integer) v.getTag();
                switch (status) {
                    case 0:
                        //pressed color (blue)
                        button.setBackgroundColor(0xFF5FA2B8);
                        v.setTag(1); //pause
                        break;
                    case 1:
                        //default color (grey)
                        button.setBackgroundColor(0xFFD4D5D5);
                        v.setTag(0); //pause
                        break;
                }
            }
        });
    }

    //Volley Functions

    /**
     * Gets general information of recipe
     * @param r_Id
     */
    private void getRecipe(int r_Id) {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RecipeConstants.URL_RECIPE + r_Id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    String r_Name = response.getString("title");
                    String summary = response.getString("summary");
                    int cookMin = response.getInt("cookingMinutes");
                    int numServings = response.getInt("servings");

                    //tags
                    ArrayList<String> tagsList = new ArrayList<>();
                    ArrayList<Button> tagBttns = new ArrayList<>();
                    JSONArray temp2 = response.getJSONArray("tags");
                    int length2 = temp2.length();
                    if (length2 > 0) {
                        for (int i = 0; i < length2; i++) {
                            tagsList.add(temp2.getString(i));
                            Button bttn = new Button(context);
                            bttn.setText(tagsList.get(i));
                            tagBttns.add(bttn);
                        }
                    }

                    recipe = new RecipeObject();
//                    recipe.setRecipeName(r_Name);
                    recipe.setSummary(summary);
                    recipe.setCookingMin(cookMin);
                    recipe.setServings(numServings);
//                    recipe.setIng_List(ingredients);
//                    recipe.setInstruct_List(instructionsList);
                    recipe.setTags_List(tagsList);

//                    recipeName.setText(recipe.getRecipeName());
                    //NEED TO PUT IN USERNAME
                    recipeName.setText(recipe.getRecipeName());
                    servings.setText(String.valueOf(recipe.getServings()));
                    cookTime.setText(String.valueOf(recipe.getCookingMin()));
                    description.setText(recipe.getSummary());

                    if (!tagBttns.isEmpty()) {
                        for (int i = 0; i < buttonList.size(); i++) {
                            for (int j = 0; j < tagBttns.size(); j++) {
                                if (buttonList.get(i).getText().toString().equals(tagBttns.get(j).getText().toString())) {
                                    buttonList.get(i).setBackgroundColor(0xFF5FA2B8);
                                }
                            }
                        }
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

    /**
     * Updates a blank recipe with general information of a user's recipe
     * @param title
     * @param description
     * @param cookingMin
     * @param servings
     * @param tags
     */
    private void postRecipe(String title, String description, String cookingMin, String servings, ArrayList<String> tags) {

        RequestQueue requests = Volley.newRequestQueue(context);
        JSONArray jsonArray = new JSONArray();

        //loop through the instruction list array
        for (int i = 0; i < tags.size(); i++) {
            jsonArray.put(tags.get(i));
        }

        StringRequest string = new StringRequest(Request.Method.PUT, RecipeConstants.URL_RECIPE  + GlobalVar.r_Id + "/update-general-info", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
                String result = "Saved";
                resultRecipe.setText(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("recipeName", title);
                params.put("summary", description);
                params.put("cookingTime", cookingMin);
                params.put("servings", servings);
                params.put("tags", jsonArray.toString());
                return params;
            }
        };
        requests.add(string);
    }

    /**
     * Deletes currently view recipe in user's own recipe creations
     * @param num
     */
    private void deleteRecipe(int num) {
        RequestQueue requests = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, RecipeConstants.URL_RECIPE + num, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "GOOD", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(stringRequest);
    }
}