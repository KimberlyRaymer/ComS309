package com.example.myapplication.follow_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.myapplication.User;
import com.example.myapplication.home.ScrollingActivity;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

//this class handles the following list for whatever user page is currently viewed

public class FollowingList extends AppCompatActivity
{
    ListView followList;
    ArrayList<User> userList;
    LinearLayout linearLayout;

    //USER WHO OWNS THIS FOLLOWING LIST
    String users_following;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_list);
        Intent intent = getIntent();

        users_following = intent.getStringExtra("un");
        getFollowing(users_following);

        ImageButton menu = findViewById(R.id.menuBttn);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowingList.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        ImageButton backArrow = findViewById(R.id.profileAccountArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FollowingList.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     *
     * @param usrnme
     * @return
     */
    View.OnClickListener handOnClick(String usrnme) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FollowingList.this, MenuActivity.class);
                intent.putExtra("oun", usrnme);
                startActivity(intent);
            }
        };
    }


    public void getFollowing(String username) {
        RequestQueue queue = Volley.newRequestQueue(FollowingList.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, RecipeConstants.URL_USER_BLANK + username + RecipeConstants.URL_USER_FOLLOWING_CAT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        userList.add(new User(response.getJSONObject(i).getString("username")));
                    }
                    Toast.makeText(FollowingList.this, "Good", Toast.LENGTH_SHORT).show();

                    if (!userList.isEmpty()) {
                        //creates cardview of listed recipes
                        for (int i = 0; i < userList.size(); i++) {
                            String usrnme = userList.get(i).getUsername();
                            Button bttn = new Button(FollowingList.this);
                            bttn.setText(usrnme);
                            bttn.setOnClickListener(handOnClick(usrnme));
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
                Toast.makeText(FollowingList.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }


}
