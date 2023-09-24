package com.example.myapplication;

public class Ingredient {
    String ingredientName, inPantry;//, username;

    public Ingredient(String ingredientName)
    {
        super();
        this.ingredientName = ingredientName;
    }

    public String getInPantry(){
        return inPantry;
    }
    //if in pantry > post username to ingredient table
    public void setInPantry(int id){
        this.inPantry = inPantry;
    }

    public String getIngredientName(){
        return ingredientName;
    }
    public void setIngredientName(String name){
        this.ingredientName = ingredientName;
    }

 /*   public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }*/

    @Override
    public String toString(){
        return ingredientName;
    }
}
