package com.example.c196_degree_tracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView terms = findViewById(R.id.terms_img);
        terms.setOnClickListener((view) -> {
            Intent intent = new Intent( MainActivity.this, TermsActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        ImageView courses = findViewById(R.id.courses_img);
        courses.setOnClickListener((view) -> {
            Intent intent = new Intent( MainActivity.this, CoursesActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        ImageView assessments = findViewById(R.id.assessments_img);
        assessments.setOnClickListener((view) -> {
            Intent intent = new Intent( MainActivity.this, AssessmentsActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        ImageView mentors = findViewById(R.id.mentors_img);
        mentors.setOnClickListener((view) -> {
            Intent intent = new Intent( MainActivity.this, MentorsActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }
}
