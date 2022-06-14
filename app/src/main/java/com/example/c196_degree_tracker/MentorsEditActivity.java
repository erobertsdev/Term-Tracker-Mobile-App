package com.example.c196_degree_tracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.c196_degree_tracker.Entities.MentorEntity;
import com.example.c196_degree_tracker.Entities.TermEntity;
import com.example.c196_degree_tracker.ViewModel.MentorViewModel;
import com.example.c196_degree_tracker.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MentorsEditActivity extends AppCompatActivity {
    private MentorViewModel mMentorViewModel;
    private EditText mEditName;
    private EditText mEditEmail;
    private EditText mEditPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);
        setContentView(R.layout.activity_mentors_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditName=findViewById(R.id.mentorNameTxt);
        mEditEmail=findViewById(R.id.mentorEmailTxt);
        mEditPhone=findViewById(R.id.mentorPhoneTxt);

        mEditName.setText(getIntent().getStringExtra("Name"));
        mEditEmail.setText(getIntent().getStringExtra("Email"));
        mEditPhone.setText(getIntent().getStringExtra("Phone"));

        try {
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String name = mEditName.getText().toString();
                    String email = mEditEmail.getText().toString();
                    String phone = mEditPhone.getText().toString();

                    if(name.matches("") || email.matches("") || phone.matches("")) {
                        Toast.makeText(getApplicationContext(), "Name, Email, & Phone fields cannot be blank.",Toast.LENGTH_LONG).show();
                    }
                    else {
                        MentorEntity mentor = new MentorEntity(getIntent().getIntExtra("mentorID", 0), mEditName.getText().toString(), mEditEmail.getText().toString(),
                                mEditPhone.getText().toString(), getIntent().getIntExtra("courseID", 0));
                        mMentorViewModel.insert(mentor);
                        Intent intent = new Intent(MentorsEditActivity.this, MentorsActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
        catch(NullPointerException e) {

        }

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MentorEntity mentor = new MentorEntity(getIntent().getIntExtra("mentorID", 0), mEditName.getText().toString(), mEditEmail.getText().toString(),
                        mEditPhone.getText().toString(), getIntent().getIntExtra("courseID", 0));
                mMentorViewModel.delete(mentor);
                Toast.makeText(getApplicationContext(), "Mentor Deleted",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MentorsEditActivity.this, MentorsActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( MentorsEditActivity.this, MentorsDetailsActivity.class);
        intent.putExtra("mentorName", getIntent().getStringExtra("Name"));
        intent.putExtra("mentorEmail", getIntent().getStringExtra("Email"));
        intent.putExtra("mentorPhone", getIntent().getStringExtra("Phone"));
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
