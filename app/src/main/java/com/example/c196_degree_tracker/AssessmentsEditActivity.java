package com.example.c196_degree_tracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.c196_degree_tracker.Entities.AssessmentEntity;
import com.example.c196_degree_tracker.Entities.CourseEntity;
import com.example.c196_degree_tracker.Entities.TermEntity;
import com.example.c196_degree_tracker.ViewModel.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

public class AssessmentsEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AssessmentViewModel mAssessmentViewModel;
    private EditText mEditName;
    private EditText mEditDate;
    EditText DateTxt;
    ImageView calStartDP;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        setContentView(R.layout.activity_assessments_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditName=findViewById(R.id.assessmentNameTxt);
        mEditDate=findViewById(R.id.assessmentDateTxt);

        Spinner spinner = findViewById(R.id.assessmentTypeSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(this);

        int spinnerSelect = 0;

        if(Objects.equals(getIntent().getStringExtra("Type"), "Objective Assessment")) {
            spinnerSelect = 0;}
        else {
            spinnerSelect = 1;
        }

        spinner.setSelection(spinnerSelect);
        mEditName.setText(getIntent().getStringExtra("Name"));
        mEditDate.setText(getIntent().getStringExtra("Date"));

        DateTxt = findViewById(R.id.assessmentDateTxt);
        calStartDP = findViewById(R.id.calStartDP);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        calStartDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AssessmentsEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        DateTxt.setText(date);
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
                    String date = mEditDate.getText().toString();

                    if(name.matches("") || date.matches("")) {
                        Toast.makeText(getApplicationContext(), "Name and Date fields cannot be blank.",Toast.LENGTH_LONG).show();
                    }
                    else {
                        String spinnerTxt = spinner.getSelectedItem().toString();
                        AssessmentEntity assessment = new AssessmentEntity(getIntent().getIntExtra("assessmentID", 0), mEditName.getText().toString(), mEditDate.getText().toString(),
                                spinnerTxt, getIntent().getIntExtra("courseID", 0));
                        mAssessmentViewModel.insert(assessment);
                        Intent intent = new Intent(AssessmentsEditActivity.this, AssessmentsActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
        catch(NullPointerException e) {

        }

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            String spinnerTxt = spinner.getSelectedItem().toString();
            @Override
            public void onClick(View view) {
                AssessmentEntity assessment = new AssessmentEntity(getIntent().getIntExtra("assessmentID", 0), mEditName.getText().toString(), mEditDate.getText().toString(),
                        spinnerTxt, getIntent().getIntExtra("courseID", 0));
                mAssessmentViewModel.delete(assessment);
                Toast.makeText(getApplicationContext(), "Assessment Deleted",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AssessmentsEditActivity.this, AssessmentsActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( AssessmentsEditActivity.this, AssessmentsDetailsActivity.class);
        intent.putExtra("assessmentName", getIntent().getStringExtra("Name"));
        intent.putExtra("assessmentDate", getIntent().getStringExtra("Date"));
        intent.putExtra("assessmentType", getIntent().getStringExtra("Type"));
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
