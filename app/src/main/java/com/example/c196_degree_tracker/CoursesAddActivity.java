package com.example.c196_degree_tracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.c196_degree_tracker.Entities.AssessmentEntity;
import com.example.c196_degree_tracker.Entities.CourseEntity;
import com.example.c196_degree_tracker.ViewModel.AssessmentViewModel;
import com.example.c196_degree_tracker.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class CoursesAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private CourseViewModel mCourseViewModel;
    private EditText mEditName;
    private EditText mEditStart;
    private EditText mEditEnd;
    private EditText mEditNotes;
    private ImageView calStartDP;
    private ImageView calEndDP;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        setContentView(R.layout.activity_courses_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditName=findViewById(R.id.courseNameTxt);
        mEditNotes=findViewById(R.id.courseNotesTxt);

        Spinner spinner = findViewById(R.id.courseStatusSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(this);

        mEditStart = findViewById(R.id.courseStartTxt);
        mEditEnd = findViewById(R.id.courseEndTxt);
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
                        CoursesAddActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        CoursesAddActivity.this, new DatePickerDialog.OnDateSetListener() {
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

            mCourseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
                @Override
                public void onChanged(List<CourseEntity> courseEntities) {

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
                        String spinnerTxt = spinner.getSelectedItem().toString();
                        CourseEntity course = new CourseEntity(mCourseViewModel.lastID() + 1, mEditName.getText().toString(), mEditStart.getText().toString(),
                                mEditEnd.getText().toString(),spinnerTxt, mEditNotes.getText().toString(), 0);
                        mCourseViewModel.insert(course);
                        Intent intent = new Intent(CoursesAddActivity.this, CoursesActivity.class);
                        startActivity(intent);
                    }
                    }
            });
        }
        catch (NullPointerException e) {

        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
