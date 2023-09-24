package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;

/**
 * Class that lists out all users that have been flagged by other users.
 * Admins review over and create a report/warning for the flagged user.
 */
public class UsersFlaggedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_flagged);
    }
}