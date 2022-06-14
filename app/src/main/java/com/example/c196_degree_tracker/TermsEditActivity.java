package com.example.c196_degree_tracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.c196_degree_tracker.Entities.CourseEntity;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TermsEditActivity extends AppCompatActivity {
    private TermViewModel mTermViewModel;
    private EditText mEditName;
    private EditText mEditStart;
    private EditText mEditEnd;
    EditText StartDateTxt;
    EditText EndDateTxt;
    ImageView calStartDP;
    ImageView calEndDP;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        setContentView(R.layout.activity_terms_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mEditName=findViewById(R.id.termNameTxt);
        mEditStart=findViewById(R.id.startDateTxt);
        mEditEnd=findViewById(R.id.endDateTxt);

        mEditName.setText(getIntent().getStringExtra("Name"));
        mEditStart.setText(getIntent().getStringExtra("Start"));
        mEditEnd.setText(getIntent().getStringExtra("End"));

        StartDateTxt = findViewById(R.id.startDateTxt);
        EndDateTxt = findViewById(R.id.endDateTxt);
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
                        TermsEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        StartDateTxt.setText(date);
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
                        TermsEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month + "/" + dayOfMonth + "/" + year;
                        EndDateTxt.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();
            }
        });


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
                        TermEntity term = new TermEntity(getIntent().getIntExtra("termID", 0), mEditName.getText().toString(), mEditStart.getText().toString(),
                                mEditEnd.getText().toString());
                        mTermViewModel.insert(term);
                        Intent intent = new Intent(TermsEditActivity.this, TermsActivity.class);
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
            if(getIntent().getIntExtra("numCourses", 0)==0) {
                TermEntity term = new TermEntity(getIntent().getIntExtra("termID", 0), mEditName.getText().toString(), mEditStart.getText().toString(),
                        mEditEnd.getText().toString());
                mTermViewModel.delete(term);

                Toast.makeText(getApplicationContext(), "Term Deleted",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TermsEditActivity.this, TermsActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Cannot Delete Term with Courses",Toast.LENGTH_LONG).show();
            }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( TermsEditActivity.this, TermsDetailsActivity.class);
        intent.putExtra("termName", getIntent().getStringExtra("Name"));
        intent.putExtra("termStart", getIntent().getStringExtra("Start"));
        intent.putExtra("termEnd", getIntent().getStringExtra("End"));
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
