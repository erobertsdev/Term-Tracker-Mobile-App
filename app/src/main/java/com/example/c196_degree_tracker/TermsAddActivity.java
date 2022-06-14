package com.example.c196_degree_tracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.c196_degree_tracker.Entities.MentorEntity;
import com.example.c196_degree_tracker.Entities.TermEntity;
import com.example.c196_degree_tracker.ViewModel.CourseViewModel;
import com.example.c196_degree_tracker.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class TermsAddActivity extends AppCompatActivity {
    private TermViewModel mTermViewModel;
    private EditText mEditName;
    private EditText mEditStart;
    private EditText mEditEnd;
    private ImageView calStartDP;
    private ImageView calEndDP;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        setContentView(R.layout.activity_terms_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditName=findViewById(R.id.termNameTxt);

        mEditStart = findViewById(R.id.termStartTxt);
        mEditEnd = findViewById(R.id.termEndTxt);
        calStartDP = findViewById(R.id.calStartDP);
        calEndDP = findViewById(R.id.calEndDP);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        calStartDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TermsAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        mEditStart.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();
            }
        });

        calEndDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TermsAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        mEditEnd.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();
            }
        });

        try {

            mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
                @Override
                public void onChanged(List<TermEntity> termEntities) {

                }
            });

        } catch (NullPointerException e) {

        }
        try {
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String name = mEditName.getText().toString();
                    String start = mEditStart.getText().toString();
                    String end = mEditEnd.getText().toString();

                    if(name.matches("") || start.matches("") || end.matches("")) {
                        Toast.makeText(getApplicationContext(), "Name and Date fields cannot be blank.",Toast.LENGTH_LONG).show();
                    }
                    else {
                        TermEntity term = new TermEntity(mTermViewModel.lastID() + 1, mEditName.getText().toString(), mEditStart.getText().toString(),
                                mEditEnd.getText().toString());
                        mTermViewModel.insert(term);
                        Intent intent = new Intent(TermsAddActivity.this, TermsActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
        catch(NullPointerException e) {

        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
