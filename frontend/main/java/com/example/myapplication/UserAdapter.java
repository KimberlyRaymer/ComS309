package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserAdapter extends BaseAdapter
{

    LayoutInflater inflater;
    List<Ingredient> usersList;
    ArrayList<Ingredient> ingredientArrayList;

    public UserAdapter(Context c, List<Ingredient> list)
    {
        usersList = list;
        inflater = LayoutInflater.from(c);
        ingredientArrayList = new ArrayList<Ingredient>();
        ingredientArrayList.addAll(usersList);
    }

    public class ViewHolder
    {
        TextView tv;
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Ingredient getItem(int i) {
        return usersList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.content_pantry_list, null);
            holder.tv = (TextView) view.findViewById(R.id.ingredientName);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv.setText(usersList.get(i).getIngredientName());
        notifyDataSetChanged();
        return view;

    }



}
