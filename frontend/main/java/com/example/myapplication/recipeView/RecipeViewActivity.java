package com.example.myapplication.recipeView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.user.ProfileActivity;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

/**
 * Creates the tab function and handles
 * which fragment content is shown
 */
public class RecipeViewActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TabAdapter2 adapter;
    ImageButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        tabLayout = findViewById(R.id.tabLayout2);
        viewPager2 = findViewById(R.id.viewPager2_2);
        backArrow = findViewById(R.id.recipeViewArrow);

        //Creates tabs
        tabLayout.addTab(tabLayout.newTab().setText("Recipe"));
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Instructions"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new TabAdapter2(fragmentManager, getLifecycle());

        //identifies which tab was pressed according to position
        viewPager2.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RecipeViewActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }



}