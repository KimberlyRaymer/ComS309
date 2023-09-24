package com.example.myapplication.recipeTemplate;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.recipeTemplate.IngredientFragment;
import com.example.myapplication.recipeTemplate.InstructionFragment;
import com.example.myapplication.recipeTemplate.RecipeFragment;

/**
 * Class file for finding the positions of the tabs
 * and associating which fragment content goes under
 * what tab selected
 */
public class TabAdapter extends FragmentStateAdapter {


    public TabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    //finds the tab position and shows the fragment content that corresponds
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new RecipeFragment();
            case 1:
                return new IngredientFragment();
            case 2:
                return new InstructionFragment();
        }
        return null;
    }

    //gets how many positions there are
    @Override
    public int getItemCount() {
        return 3;
    }
}
