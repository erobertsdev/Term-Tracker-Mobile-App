package com.example.c196_degree_tracker;

import android.content.Intent;
import android.os.Bundle;

import com.example.c196_degree_tracker.Entities.TermEntity;
import com.example.c196_degree_tracker.ViewModel.TermViewModel;
import com.example.c196_degree_tracker.ui.TermAdapter;
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

public class TermsActivity extends AppCompatActivity {
    private TermViewModel mTermViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTermViewModel=new ViewModelProvider(this).get(TermViewModel.class);
        setContentView(R.layout.activity_terms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);

        ImageView termsAdd = findViewById(R.id.fab);
        termsAdd.setOnClickListener((view) -> {
            Intent intent = new Intent( TermsActivity.this, TermsAddActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
        RecyclerView recyclerView = findViewById(R.id.termsRV);
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> words) {
                adapter.setWords(words);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {

            TermEntity term = new TermEntity(mTermViewModel.lastID()+1, data.getStringExtra("termName"), data.getStringExtra("termStart"), data.getStringExtra("termEnd"));
            mTermViewModel.insert(term);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( TermsActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
