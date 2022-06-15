package dev.eroberts.term_tracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import dev.eroberts.term_tracker.Entities.entity_assessment;

import dev.eroberts.term_tracker.ViewModel.assessment_view_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

public class AssessmentsAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private assessment_view_model mAssessmentViewModel;
    private EditText mEditName;
    private EditText mEditDate;
    private EditText DateTxt;
    private ImageView calStartDP;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAssessmentViewModel = new ViewModelProvider(this).get(assessment_view_model.class);
        setContentView(R.layout.activity_assessments_add);
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
                        AssessmentsAddActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                mAssessmentViewModel.getAllAssessments().observe(this, new Observer<List<entity_assessment>>() {
                    @Override
                    public void onChanged(@Nullable final List<entity_assessment> words) {

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
                        String date = mEditDate.getText().toString();

                        if(name.matches("") || date.matches("")) {
                            Toast.makeText(getApplicationContext(), "Name and Date fields cannot be blank.",Toast.LENGTH_LONG).show();
                        }
                    else {
                            String spinnerTxt = spinner.getSelectedItem().toString();
                            entity_assessment assessment = new entity_assessment(mAssessmentViewModel.lastID() + 1, mEditName.getText().toString(), mEditDate.getText().toString(),
                                    spinnerTxt, 0);
                            mAssessmentViewModel.insert(assessment);
                            Intent intent = new Intent(AssessmentsAddActivity.this, AssessmentsActivity.class);
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
        Intent intent = new Intent( AssessmentsAddActivity.this, AssessmentsActivity.class);
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
