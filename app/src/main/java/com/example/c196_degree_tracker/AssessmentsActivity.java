package com.example.c196_degree_tracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.c196_degree_tracker.Entities.AssessmentEntity;
import com.example.c196_degree_tracker.ViewModel.AssessmentViewModel;
import com.example.c196_degree_tracker.ui.AssessmentAdapter;
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

public class AssessmentsActivity extends AppCompatActivity {
    private AssessmentViewModel mAssessmentViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAssessmentViewModel=new ViewModelProvider(this).get(AssessmentViewModel.class);
        setContentView(R.layout.activity_assessments);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView assessmentsAdd = findViewById(R.id.fab);
        assessmentsAdd.setOnClickListener((view) -> {
            Intent intent = new Intent( AssessmentsActivity.this, AssessmentsAddActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        RecyclerView recyclerView = findViewById(R.id.assessmentsRV);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AssessmentEntity> words) {
                adapter.setWords(words);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {

            AssessmentEntity assessment = new AssessmentEntity(mAssessmentViewModel.lastID()+1, data.getStringExtra("assessmentName"), data.getStringExtra("assessmentDate"),
                    data.getStringExtra("assessmentType"), data.getIntExtra("courseID", 0));
            mAssessmentViewModel.insert(assessment);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( AssessmentsActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
