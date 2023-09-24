package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Class to contain all information pertaining
 * to a recipe
 */
public class RecipeObject {
    String recipeName;
    String summary;
    int cookingMin;
    int servings;
    ArrayList<String> ing_List;
    ArrayList<String> instruct_List;
    ArrayList<String> tags_List;

    public RecipeObject(){

    }

    //recipe name
    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String r_Name) {
        recipeName = r_Name;
    }


    //description of recipe
    public String getSummary() {
        return summary;
    }
    public void setSummary(String sum) {
        summary = sum;
    }


    //total cooking time
    public int getCookingMin() {
        return cookingMin;
    }
    public void setCookingMin(int cookMin) {
        cookingMin = cookMin;
    }


    //total servings
    public int getServings() {
        return servings;
    }
    public void setServings(int serves) {
        servings = serves;
    }


    //ingredient list
    public ArrayList<String> getIng_List() {
        return ing_List;
    }
    public void setIng_List(ArrayList<String> ingredients) {
        ing_List = ingredients;
    }


    //instructions list
    public ArrayList<String> getInstruct_List() {
        return instruct_List;
    }
    public void setInstruct_List(ArrayList<String> instructions) {
        instruct_List = instructions;
    }

    //tags list
    public ArrayList<String> getTags_List() {
        return tags_List;
    }
    public void setTags_List(ArrayList<String> tags) {
        tags_List = tags;
    }

}
