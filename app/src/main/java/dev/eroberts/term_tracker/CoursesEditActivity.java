package dev.eroberts.term_tracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import dev.eroberts.term_tracker.Entities.entity_course;

import dev.eroberts.term_tracker.ViewModel.course_view_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import java.util.Objects;

public class CoursesEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private course_view_model mCourseViewModel;
    private EditText mEditName;
    private EditText mEditStart;
    private EditText mEditEnd;
    private EditText mEditNotes;
    EditText StartDateTxt;
    EditText EndDateTxt;
    ImageView calStartDP;
    ImageView calEndDP;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCourseViewModel = new ViewModelProvider(this).get(course_view_model.class);
        setContentView(R.layout.activity_courses_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditName=findViewById(R.id.courseNameTxt);
        mEditStart=findViewById(R.id.startDateTxt);
        mEditEnd=findViewById(R.id.endDateTxt);
        mEditNotes=findViewById(R.id.courseNotesTxt);

        Spinner spinner = findViewById(R.id.courseStatusSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(this);


        int spinnerSelect = 0;

        if(Objects.equals(getIntent().getStringExtra("Status"), "Not Started")) {
             spinnerSelect = 0; }
        else if(Objects.equals(getIntent().getStringExtra("Status"), "In Progress")) {
            spinnerSelect = 1;
        }
        else {
            spinnerSelect = 2;
        }

        spinner.setSelection(spinnerSelect);
        mEditName.setText(getIntent().getStringExtra("Name"));
        mEditStart.setText(getIntent().getStringExtra("Start"));
        mEditEnd.setText(getIntent().getStringExtra("End"));
        mEditNotes.setText(getIntent().getStringExtra("Notes"));

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
                        CoursesEditActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        CoursesEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = month+"/"+dayOfMonth+"/"+year;
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
                        String spinnerTxt = spinner.getSelectedItem().toString();
                        entity_course course = new entity_course(getIntent().getIntExtra("courseID", 0), mEditName.getText().toString(), mEditStart.getText().toString(),
                                mEditEnd.getText().toString(), spinnerTxt, mEditNotes.getText().toString(), getIntent().getIntExtra("termID", 0));
                        mCourseViewModel.insert(course);
                        Intent intent = new Intent(CoursesEditActivity.this, CoursesActivity.class);
                        startActivity(intent);
                    }
                }
            });

        }
        catch (NullPointerException e) {

        }


        FloatingActionButton fab3 = findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spinnerTxt = spinner.getSelectedItem().toString();
                if(getIntent().getIntExtra("numAssessments", 1)==0 && getIntent().getIntExtra("numMentors", 1)==0) {
                    entity_course course = new entity_course(getIntent().getIntExtra("courseID", 0), mEditName.getText().toString(), mEditStart.getText().toString(),
                            mEditEnd.getText().toString(), spinnerTxt, mEditNotes.getText().toString(), getIntent().getIntExtra("termID", 0));
                    mCourseViewModel.delete(course);
                    Toast.makeText(getApplicationContext(), "Course Deleted",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CoursesEditActivity.this, CoursesActivity.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Cannot Delete Course with Assessments or Mentors",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( CoursesEditActivity.this, CoursesDetailsActivity.class);
        intent.putExtra("courseID", getIntent().getIntExtra("courseID", 0));
        intent.putExtra("courseName", getIntent().getStringExtra("Name"));
        intent.putExtra("courseStart", getIntent().getStringExtra("Start"));
        intent.putExtra("courseEnd", getIntent().getStringExtra("End"));
        intent.putExtra("courseNotes", getIntent().getStringExtra("Notes"));
        intent.putExtra("courseStatus", getIntent().getStringExtra("Status"));
        startActivity(intent);
        super.onBackPressed();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
