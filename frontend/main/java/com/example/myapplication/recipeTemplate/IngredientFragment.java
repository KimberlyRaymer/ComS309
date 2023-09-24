package com.example.myapplication.recipeTemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
 * Class to add the ingredients given by the user
 * and post to the backend
 */
public class IngredientFragment extends Fragment {

    EditText userIngredient;
    LinearLayout linearLayout;
    ImageButton btnAdd;
    Button save;
    Context context;
    ArrayList<String> ingredientList = new ArrayList<>();
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    RecipeObject recipe = new RecipeObject();

    boolean view_Recipe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = inflater.getContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        userIngredient = view.findViewById(R.id.ingredientBox);
        linearLayout = view.findViewById(R.id.linearLayout);
        btnAdd = view.findViewById(R.id.addBttn);
        save = view.findViewById(R.id.saveChangeIngredient);

        Intent intent = getActivity().getIntent();
        int num = intent.getIntExtra("recipeID", 1);
        view_Recipe = intent.getBooleanExtra("flag", false);
        if (view_Recipe) {
            getRecipeIngredients(num);
            GlobalVar.r_Id = num;
        }

        //everytime image button btnAdd is clicked, the user input is add into an arraylist
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingredient = userIngredient.getText().toString();
                if (!ingredient.isEmpty()) {

                    removeCheckbox(ingredient);
                    //add ingredient to ingredientList array and clear editText
                    userIngredient.getText().clear();

                } else {
                    Toast.makeText(context, "Please put in ingredient", Toast.LENGTH_LONG).show();
                }
            }
        });

        //saves changes and updates array list
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientList.clear();
                for (int i = 0; i < checkBoxes.size(); i++) {
                        ingredientList.add(checkBoxes.get(i).getText().toString());
                }
                ingredientListData(ingredientList);
            }
        });
    }

    /**
     * Deletes checkbox from view
     */
    public void removeCheckbox(String text) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setText(text);
        linearLayout.addView(checkBox);
        checkBoxes.add(checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    checkBoxes.remove(checkBox);
                    linearLayout.removeView(buttonView);
                }
            }
        });
    }


    //Volley Functions

    /**
     * Gets recipe ingredients
     * @param r_Id
     */
    private void getRecipeIngredients(int r_Id) {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RecipeConstants.URL_RECIPE + r_Id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    //get ingredients
                    ArrayList<String> ingredients = new ArrayList<>();
                    JSONArray jsonArray = response.getJSONArray("ingredientList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String ingredient = jsonObject.getString("ingredientName");
                        String amt = String.valueOf(jsonObject.getInt("amount"));
                        String unit = jsonObject.getString("unit");
                        ingredients.add(amt + " " + unit + " " + ingredient);
                    }

                    recipe.setIng_List(ingredients);

                    String ing;
                    if (!recipe.getIng_List().isEmpty()) {
                        for (int i = 0; i < recipe.getIng_List().size(); i++) {
                            ing = recipe.getIng_List().get(i);
                            removeCheckbox(ing);
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
     * Updates ingredients in user's own recipe
     * @param ingredient
     */
    private void ingredientListData(ArrayList<String> ingredient) {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(context);
        JSONArray jsonArray = new JSONArray();

        //loops through users ingredient list
        for (int i = 0; i < ingredient.size(); i++) {
            JSONObject jsonBody = new JSONObject();

            try {
                //parses the amt, unit, and the ingredient name given by user
                String arr[] = ingredient.get(i).split(" ", 3);
                int amt = Integer.parseInt(arr[0]);
                String unit = arr[1];
                String ingredientName = arr[2];

                jsonBody.put("name", ingredientName);
                jsonBody.put("amount", amt);
                jsonBody.put("unit", unit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonBody);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, RecipeConstants.URL_RECIPE + GlobalVar.r_Id + "/update-recipe-ingredients", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "GOOD", Toast.LENGTH_SHORT).show();

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
                params.put("ingredients", jsonArray.toString());
                return params;
            }
        };
        requests.add(stringRequest);
    }
}