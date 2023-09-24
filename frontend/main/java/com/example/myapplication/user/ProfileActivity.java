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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.MenuActivity;
import com.example.myapplication.follow_activities.FollowingList;
import com.example.myapplication.PantryListActivity;
import com.example.myapplication.R;
import com.example.myapplication.home.ScrollingActivity;
import com.example.myapplication.recipeTemplate.RecipeActivity;
import com.example.myapplication.utils.GlobalVar;
import com.example.myapplication.utils.RecipeConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to keep track of user's own created recipes,
 * saved recipes, and account information. Also, allows
 * changes to account and create new recipes.
 */
public class ProfileActivity extends AppCompatActivity {

    Button followers;
    Button following;
    Button w_Box;
    Button p_List;
    Button s_Recipes;
    Button m_Recipes;
    Button c_Recipes;
    Button editAccount;
    ImageButton backArrow;
    EditText createName;
    TextView profileUsername;
    TextView result;

    String rName;
    String resultText;
    int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        followers = findViewById(R.id.followersBttn);
        following = findViewById(R.id.followingBttn);

        w_Box = findViewById(R.id.w_boxBttn);
        p_List = findViewById(R.id.p_ListBttn);
        s_Recipes = findViewById(R.id.s_RecipeBttn);
        m_Recipes = findViewById(R.id.m_RecipeBttn);
        c_Recipes = findViewById(R.id.c_RecipeBttn);
        createName = findViewById(R.id.createName);
        editAccount = findViewById(R.id.e_AccountBttn);
        backArrow = findViewById(R.id.profileAccountArrow);
        profileUsername = findViewById(R.id.profileUsername);
        result = findViewById(R.id.result);

        profileUsername.setText(GlobalVar.userName);

        Intent intent = getIntent();
        resultText = intent.getStringExtra("result");
        result.setText(resultText);

        /**
         * Makes the volley response from
         * login activity disappear after
         * 1000 ms
         */
        result.postDelayed(new Runnable(){
            @Override
            public void run()
            {
                result.setVisibility(View.GONE);
            }
        }, 1000);

        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this, FollowingList.class);
                startActivity(intent);
            }
        });

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this, FollowingList.class);
                startActivity(intent);
            }
        });

        w_Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this, WebSocketActivity.class);
                startActivity(intent);
            }
        });

        p_List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this, PantryListActivity.class);
                startActivity(intent);
            }
        });

        s_Recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this, SavedRecipeActivity.class);
                startActivity(intent);
            }
        });

        m_Recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this, MyRecipesActivity.class);
                startActivity(intent);
            }
        });

        c_Recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rName = createName.getText().toString();
                if (rName.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please fill in the blank", Toast.LENGTH_SHORT).show();
                } else {
                    postBlankRecipe(rName);
                    GlobalVar.recipeName = rName;
                    Intent intent = new Intent(ProfileActivity.this, RecipeActivity.class);
                    intent.putExtra("resultRecipe", "Created");
                    startActivity(intent);
                }
            }
        });

        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });

        ImageButton menu = findViewById(R.id.menuBttn);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    //Volley function
    /**
     * User creates a blank recipe by giving a recipe name
     * @param recipeName
     */
    private void postBlankRecipe(String recipeName) {
        RequestQueue requests = Volley.newRequestQueue(ProfileActivity.this);
        StringRequest string = new StringRequest(Request.Method.POST, RecipeConstants.URL_RECIPE_USERS + GlobalVar.userName + "/recipe", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                recipeId = Integer.parseInt(response);
                GlobalVar.r_Id = recipeId;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("recipeName", recipeName);
                return params;
            }
        };
        requests.add(string);
    }
}