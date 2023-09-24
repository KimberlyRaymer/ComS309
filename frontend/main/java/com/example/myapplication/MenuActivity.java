package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.home.ScrollingActivity;
import com.example.myapplication.user.ProfileActivity;

public class MenuActivity extends AppCompatActivity
{

    Button home, login, pantry, profile, recipes;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        home = findViewById(R.id.homeSelection);
        login = findViewById(R.id.loginSelection);
        pantry = findViewById(R.id.pantrySelection);
        profile = findViewById(R.id.profileSelection);
        recipes = findViewById(R.id.recipesSelection);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuActivity.this, ScrollingActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        pantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuActivity.this, PantryListActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
//        recipes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent(MenuActivity.this, RecipeSearchActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    //Volley Functions
    private void getUserId(String username) {
        //
    }

}
