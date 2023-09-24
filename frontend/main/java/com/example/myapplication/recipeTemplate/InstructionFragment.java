package com.example.myapplication.recipeTemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.RecipeObject;
import com.example.myapplication.utils.GlobalVar;
import com.example.myapplication.utils.RecipeConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to add the instructions given by the user
 * and post to the backend
 */
public class InstructionFragment extends Fragment {

    EditText userInstruction;
    LinearLayout linearLayout;
    ImageButton btnAdd;
    Button save;
    Context context;
    ArrayList<String> instructionList = new ArrayList<>();
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    RecipeObject recipe = new RecipeObject();

    boolean view_Recipe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = inflater.getContext();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instruction, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        userInstruction = view.findViewById(R.id.instructionBox);
        linearLayout = view.findViewById(R.id.linearLayoutInstruction);
        btnAdd = view.findViewById(R.id.addBttn2);
        save = view.findViewById(R.id.saveChangeInstruction);

        Intent intent = getActivity().getIntent();
        int num = intent.getIntExtra("recipeID", 1);
        view_Recipe = intent.getBooleanExtra("flag", false);
        if (view_Recipe) {
            getRecipeInstructions(num);
            GlobalVar.r_Id = num;
        }

        //everytime image button btnAdd is clicked, the user input is add into an arraylist
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instruction = userInstruction.getText().toString();
                if (!instruction.isEmpty()) {
                    CheckBox checkBox = new CheckBox(context);
                    checkBox.setText(instruction);

                    removeCheckbox(instruction);
                    //add instruction to instructionList array and clear editText
                    userInstruction.getText().clear();
                }
                else {
                    Toast.makeText(context, "Please put in instruction", Toast.LENGTH_LONG).show();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructionList.clear();
                for (int i = 0; i < checkBoxes.size(); i++) {
                    instructionList.add(checkBoxes.get(i).getText().toString());
                }
                instructionListData(instructionList);
            }
        });
    }

    /**
     * Deletes checkbox from view
     */
    public void removeCheckbox(String text) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setText(text);
        linearLayout.addView(checkBox);
        checkBoxes.add(checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    checkBoxes.remove(checkBox);
                    linearLayout.removeView(buttonView);
                }
            }
        });
    }

    //Volley Functions

    /**
     * Gets recipe's instructions
     * @param r_Id
     */
    private void getRecipeInstructions(int r_Id) {
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
                            removeCheckbox(instruct);
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

    /**
     * Update instruction list in user's recipe
     * @param instruction
     */
    private void instructionListData(ArrayList<String> instruction) {
        // creating a new variable for our request queue
        RequestQueue requests = Volley.newRequestQueue(context);
        JSONArray jsonArray = new JSONArray();

        //loop through the instruction list array
        for (int i = 0; i < instruction.size(); i++) {
            jsonArray.put(instruction.get(i));
        }

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, RecipeConstants.URL_RECIPE + GlobalVar.r_Id + "/update-recipe-instructions", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("instructions", jsonArray.toString());
                return params;
            }
        };
        requests.add(stringRequest);
    }
}