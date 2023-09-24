package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.myapplication.recipeTemplate.RecipeActivity;

public class PopUpActivity extends AppCompatActivity {

    EditText recipeName;
    TextView errorTextPop;
    boolean filled = false;
    Button popUp;

    public void showPopUp(final View view) {

        //inflate the layout of the popup screen
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_pop_up, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        recipeName = popupView.findViewById(R.id.popUpName);
        errorTextPop = popupView.findViewById(R.id.errorTextPop);

        String userInput = recipeName.getText().toString();
        String errorMssg = "Please fill in the blank";
        if (recipeName.getText().toString().trim().length() > 0) {
            filled = true;
        }
        else {
            filled = false;
            errorTextPop.setText(errorMssg);
        }

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public boolean isFilled() {
        return filled;
    }
}