package com.example.c196_degree_tracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.c196_degree_tracker.Entities.MentorEntity;
import com.example.c196_degree_tracker.ViewModel.MentorViewModel;
import com.example.c196_degree_tracker.ui.MentorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;

import java.util.List;

public class MentorsActivity extends AppCompatActivity {
    private MentorViewModel mMentorViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMentorViewModel=new ViewModelProvider(this).get(MentorViewModel.class);
        setContentView(R.layout.activity_mentors);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);

        ImageView mentorsAdd = findViewById(R.id.fab);
        mentorsAdd.setOnClickListener((view) -> {
            Intent intent = new Intent( MentorsActivity.this, MentorsAddActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
        RecyclerView recyclerView = findViewById(R.id.mentorsRV);
        final MentorAdapter adapter = new MentorAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMentorViewModel.getAllMentors().observe(this, new Observer<List<MentorEntity>>() {
            @Override
            public void onChanged(@Nullable final List<MentorEntity> words) {
                adapter.setWords(words);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {

            MentorEntity mentor = new MentorEntity(mMentorViewModel.lastID()+1, data.getStringExtra("mentorName"), data.getStringExtra("mentorEmail"),
                                                    data.getStringExtra("mentorPhone"), data.getIntExtra("courseID", 0));
            mMentorViewModel.insert(mentor);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( MentorsActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
