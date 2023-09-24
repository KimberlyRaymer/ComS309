package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.home.ScrollingActivity;
import com.example.myapplication.utils.GlobalVar;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 *
 */
public class PantryListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ArrayList<Ingredient> ingredients;
    ArrayList<Ingredient> ingredientsIP;
    ListView pl_listView;
    IngredientAdapter adapter;
    SearchView search_view;
    LinearLayout linearLayout;
    Button bttn;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);

        user = getIntent().getStringExtra("un");

        getIPIngredients(user);
        pl_listView = findViewById(R.id.ingredientList);
        search_view = findViewById(R.id.search_view);
        search_view.setOnQueryTextListener(PantryListActivity.this);
        pl_listView.setAdapter(adapter);
        linearLayout = findViewById(R.id.linearLayoutPantry);

        ImageButton menu = findViewById(R.id.menuBttn);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantryListActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        ImageButton backArrow = findViewById(R.id.profileAccountArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(PantryListActivity.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     *
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     *
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextChange(String query)
    {
        getIngredients(query);
        adapter = new IngredientAdapter(PantryListActivity.this, ingredients);
        pl_listView.setAdapter(adapter);
        adapter.filter(query);

        return true;
    }

    /**
     *
     * @param query
     */
    private void getIngredients(String query) {
        RequestQueue queue = Volley.newRequestQueue(PantryListActivity.this);
        JsonArrayRequest jsonAR = new JsonArrayRequest(Request.Method.GET, RecipeConstants.JSON_DATA_URL + query, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                System.out.println(response);
                try {
                    for (int i = 0; i < response.length(); i++)
                    {
                        ingredients.add(new Ingredient(response.getJSONObject(i).getString("name")));
                    }
                    System.out.println(ingredients);

                } catch (JSONException exception) {
                    Toast.makeText(PantryListActivity.this, "JAVA CANNOT PARSE THIS RESPONSE" + exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(PantryListActivity.this, "FAILED, ERROR IS: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonAR);
    }


    View.OnClickListener handOnClick(final Button button, String ing) {
        return new View.OnClickListener(){
            public void onClick(View view) {
                deleteIngredients(ing);
            }
        };
    }

    private void getIPIngredients(String username) {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(PantryListActivity.this);

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, RecipeConstants.URL_RECIPE + "getRecipeNamesAndIDs/" + username, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        ingredientsIP.add(new Ingredient(response.getJSONObject(i).getString("name")));
                    }
                    Toast.makeText(PantryListActivity.this, "Good", Toast.LENGTH_SHORT).show();

                    if (!ingredientsIP.isEmpty()) {
                        //creates cardview of listed recipes
                        for (int i = 0; i < ingredientsIP.size(); i++) {
                            String ingredientName = ingredientsIP.get(i).getIngredientName();
                            bttn = new Button(PantryListActivity.this);
                            bttn.setId(i);
                            final int id = bttn.getId();
                            bttn.setText(ingredientName);
                            bttn.setOnClickListener(handOnClick(bttn, ingredientName));
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
                Toast.makeText(PantryListActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(jsonArray);

    }

    private void deleteIngredients(String ing) {
        try {
            RequestQueue queue = Volley.newRequestQueue(PantryListActivity.this);
            JSONObject body = new JSONObject();
            body.put("ingredientName", ing);
            body.put("username", user);

            JsonObjectRequest jsonAR = new JsonObjectRequest(Request.Method.DELETE, RecipeConstants.USER_PANTRY + GlobalVar.userName + "/ingredients", body, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //do something
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(PantryListActivity.this, "FAILED, ERROR IS: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            queue.add(jsonAR);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
