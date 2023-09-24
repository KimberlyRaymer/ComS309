package com.example.myapplication.recipeView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.RecipeObject;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class to get instruction list from an already created recipe
 */
public class InstructionViewFragment extends Fragment {

    Context context;
    LinearLayout linearLayout;
    RecipeObject recipe = new RecipeObject();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = inflater.getContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instruction_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        linearLayout = view.findViewById(R.id.linearLayoutInst);

        Intent intent = getActivity().getIntent();
        int num = intent.getIntExtra("recipeID", 1);

        getRecipe(num);

    }

    //Volley functions

    /**
     * Get the ingredient list for a recipe
     * @param r_Id
     */
    private void getRecipe(int r_Id) {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RecipeConstants.URL_RECIPE + r_Id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    //get instructions
                    ArrayList<String> instructionsList = new ArrayList<>();
                    JSONArray temp = response.getJSONArray("instructionsList");
                    int length = temp.length();
                    if (length > 0) {
                        for (int i = 0; i < length; i++) {
                            instructionsList.add(temp.getString(i));
                        }
                    }

                    recipe.setInstruct_List(instructionsList);


                    String instruct;
                    if (!recipe.getInstruct_List().isEmpty()) {
                        for (int i = 0; i < recipe.getInstruct_List().size(); i++) {
                            instruct = recipe.getInstruct_List().get(i);
                            CheckBox checkBox = new CheckBox(context);
                            checkBox.setText(instruct);
                            linearLayout.addView(checkBox);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requests.add(jsonObjectRequest);
    }


}