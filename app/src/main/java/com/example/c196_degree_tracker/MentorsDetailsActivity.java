package com.example.c196_degree_tracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.c196_degree_tracker.ViewModel.MentorViewModel;
import com.example.c196_degree_tracker.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MentorsDetailsActivity extends AppCompatActivity {
    private MentorViewModel mMentorViewModel;
    private EditText mEditName;
    private EditText mEditEmail;
    private EditText mEditPhone;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        setContentView(R.layout.activity_mentors_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditName=findViewById(R.id.textView9);
        mEditEmail=findViewById(R.id.textView10);
        mEditPhone=findViewById(R.id.textView11);

        String temp=getIntent().getStringExtra("mentorName");
        if(getIntent().getStringExtra("mentorName")!=null) {
            mEditName.setText(getIntent().getStringExtra("mentorName"));
            mEditEmail.setText(getIntent().getStringExtra("mentorEmail"));
            mEditPhone.setText(getIntent().getStringExtra("mentorPhone"));
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView mentorsEdit = findViewById(R.id.fab);
        mentorsEdit.setOnClickListener((view) -> {
            Intent intent = new Intent( MentorsDetailsActivity.this, MentorsEditActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            intent.putExtra("Name", temp);
            intent.putExtra("Email", getIntent().getStringExtra("mentorEmail"));
            intent.putExtra("Phone", getIntent().getStringExtra("mentorPhone"));
            intent.putExtra("mentorID", getIntent().getIntExtra("mentorID", 0));
            intent.putExtra("courseID", getIntent().getIntExtra("courseID", 0));
            startActivity(intent);
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( MentorsDetailsActivity.this, MentorsActivity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
