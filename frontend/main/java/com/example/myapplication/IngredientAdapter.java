package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.utils.GlobalVar;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class IngredientAdapter extends BaseAdapter
{

    LayoutInflater inflater;
    List<Ingredient> ingredientList;
    ArrayList<Ingredient> ingredientArrayList;
    Context con;

    /**
     *
     * @param c - context for the object
     * @param list - ingredient list to
     */
    public IngredientAdapter(Context c, List<Ingredient> list)
    {
        con = c;
        ingredientList = list;
        inflater = LayoutInflater.from(c);
        ingredientArrayList = new ArrayList<Ingredient>();
        ingredientArrayList.addAll(ingredientList);
    }

    private class ViewHolder
    {
        LinearLayout ll;
        TextView ingName;
    }


    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public Ingredient getItem(int i) {
        return ingredientList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * getView
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        if(view == null)
        {
            vh = new ViewHolder();
            view = inflater.inflate(R.layout.content_pantry_list, null);
            vh.ll = view.findViewById(R.id.contentlayout);
            vh.ingName = view.findViewById(R.id.ingredientName);
            view.setTag(vh);
        }
        else
        {
          vh = (ViewHolder) view.getTag();
        }

        vh.ingName.setText(ingredientList.get(i).getIngredientName());
        vh.ingName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postIngredients(ingredientList.get(i));
                Toast.makeText(con, ingredientList.get(i).getIngredientName(), Toast.LENGTH_SHORT);
            }
        });
        return view;
    }

    /**
     *
     * @param text
     */
    public void filter(String text)
    {
        text = text.toUpperCase(Locale.getDefault());
        ingredientList.clear();
        if(text.length() == 0)
        {
            ingredientList.addAll(ingredientArrayList);
        }
        else
        {
            for(Ingredient ing : ingredientArrayList)
            {
                if(ing.getIngredientName().toUpperCase(Locale.getDefault()).contains(text))
                {
                    ingredientList.add(ing);
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     *
     * @param ingredient
     */
    private void postIngredients(Ingredient ingredient) {
        try {
            RequestQueue queue = Volley.newRequestQueue(con);

            JSONObject body = new JSONObject();
            body.put("ingredientName", ingredient);
            body.put("username", GlobalVar.userName);

            final String requestBody = body.toString();

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, RecipeConstants.USER_PANTRY + GlobalVar.userName + "/ingredients", body,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            //Intent i = new Intent(IngredientAdapter.this, InPantryAdapter.class);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(con, "Fail to get response = " + error, Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public byte[] getBody() {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            queue.add(request); // send request
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }




}
