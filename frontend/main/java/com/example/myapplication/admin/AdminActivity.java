package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.home.ScrollingActivity;
import com.example.myapplication.utils.GlobalVar;

/**
 * Creates Admin screen profile
 */
public class AdminActivity extends AppCompatActivity {

    Button flaggedRecipes;
    TextView resultAdmin;
    TextView adminName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        resultAdmin = findViewById(R.id.resultAdmin);
        flaggedRecipes = findViewById(R.id.flaggedBttn);
        adminName = findViewById(R.id.adminName);
        adminName.setText(GlobalVar.adminName);

        Intent intent = new Intent();
        String result = intent.getStringExtra("resultAdmin");
        resultAdmin.setText(result);

        flaggedRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminActivity.this, FlaggedRecipeActivity.class);
                startActivity(intent);
            }
        });
    }
}