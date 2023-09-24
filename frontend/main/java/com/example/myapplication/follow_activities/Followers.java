package com.example.myapplication.follow_activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

//this class handles the other user's page (viewing, not active-session)

public class Followers extends AppCompatActivity
{
    String active_userName, other_userName;
    ArrayList<User> ufl;

    TextView profileTitle;
    TextView profileUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);

        Intent intent = getIntent();

        active_userName = intent.getStringExtra("un"); //active user
        other_userName = intent.getStringExtra("oun"); //the user who is being viewed

        profileTitle = findViewById(R.id.profileTitle);
        profileTitle.setText(other_userName);
        profileUserName = findViewById(R.id.profileUsername);
        profileUserName.setText(other_userName);

        other_userName = getResources().getString(R.string.user_profile_name);

        ufl = null;

        getFollowingStrings(other_userName);

        //for dealing with follow/unfollow button
        if(ufl.contains(active_userName))//user in following list
        {
            getResources().getString(R.string.follow_unfollow, "Unfollow");
        }
            else
        {
            getResources().getString(R.string.follow_unfollow, "Follow");
        }
        Button followButton = (Button) findViewById(R.id.followButton);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ufl.contains(active_userName))//user in following list
                {
                    unfollow(active_userName, other_userName); ///api/users/{current_username}/followings/{following_username}/unfollow
                }
                else
                {
                    follow(active_userName, other_userName); ///api/users/{current_username}/follow/{following_username}
                }
            }
        });

        //button to show followers list of user
        Button followers = (Button) findViewById(R.id.followersBttn);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Followers.this, FollowerList.class);
                startActivity(intent);

            }
        });
        //button to show following list of user
        Button following = (Button) findViewById(R.id.followingBttn);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Followers.this, FollowingList.class);
                startActivity(intent);
            }
        });

        //take to direct message
        Button message = (Button) findViewById(R.id.message_user);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: connect to DMs
            }
        });

        //take to view user's saved recipes
        Button view_recipes = (Button) findViewById(R.id.user_RecipeBttn);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Followers.this, Other_User_Recipes.class);
                intent.putExtra("oun", other_userName);
                startActivity(intent);
        }});

        //flags user
        Button report = (Button) findViewById(R.id.flag);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: put intention to user flag report activity
            }
        });

//        ImageButton menu = findViewById(R.id.menuBttn);
//        menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Followers.this, MenuActivity.class);
//                startActivity(intent);
//            }
//        });

        ImageButton backArrow = findViewById(R.id.profileArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Followers.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });
    }

    //follows other user
    public void follow(String active_userName, String other_userName)
    {
        RequestQueue queue = Volley.newRequestQueue(Followers.this);
        JsonArrayRequest jsonAR = new JsonArrayRequest(Request.Method.POST, RecipeConstants.URL_USER_BLANK + "api/users/" + active_userName + "/follow/" + other_userName, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Followers.this, "FAILED, ERROR IS: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonAR);
    }

    //unfollows other user
    public void unfollow(String active_userName, String other_userName)
    {
        RequestQueue queue = Volley.newRequestQueue(Followers.this);
        JsonArrayRequest jsonAR = new JsonArrayRequest(Request.Method.DELETE, RecipeConstants.URL_USER_BLANK + "/api/users/" + active_userName + "/follow/" + other_userName, null, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Followers.this, "FAILED, ERROR IS: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonAR);
    }

    //provides list of usernames to determine follow/unfollow function
    public void getFollowingStrings(String username) {
        RequestQueue queue = Volley.newRequestQueue(Followers.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, RecipeConstants.URL_USER_BLANK + username + RecipeConstants.URL_USER_FOLLOWING_CAT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        ufl.add(new User(response.getJSONObject(i).getString("username")));
                    }
                    Toast.makeText(Followers.this, "Good", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Followers.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }


    /*
    * get follower list
    * get following list
    * show user profile
    * follow/unfollow button
    *
    *
    *
    *
    * */


}
