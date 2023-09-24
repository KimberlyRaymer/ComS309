package com.example.myapplication.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.MenuActivity;
import com.example.myapplication.PantryListActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityScrollingBinding;
//import com.google.android.material.appbar.AppBarLayout;

/**
 *
 */
public class ScrollingActivity extends AppCompatActivity {

//    ActivityScrollingBinding binding;
    //AppBarLayout appbar;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_scrolling);


        ImageButton menu = findViewById(R.id.menuBttn);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScrollingActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });


}
}

