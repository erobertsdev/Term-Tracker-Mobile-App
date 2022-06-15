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

/**
 * The type Courses edit activity.
 */
public class CoursesEditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private course_view_model course_view_model_e;
    private EditText edit_name_text;
    private EditText edit_start_text;
    private EditText edit_end_text;
    private EditText edit_notes_text;
    /**
     * The Start date text.
     */
    EditText start_date_text;
    /**
     * The End date txt.
     */
    EditText end_date_txt;
    /**
     * The Start dp.
     */
    ImageView start_dp;
    /**
     * The End dp.
     */
    ImageView end_dp;
    /**
     * The Date listener.
     */
    DatePickerDialog.OnDateSetListener date_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course_view_model_e = new ViewModelProvider(this).get(course_view_model.class);
        setContentView(R.layout.activity_courses_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        edit_name_text =findViewById(R.id.courseNameTxt);
        edit_start_text =findViewById(R.id.startDateTxt);
        edit_end_text =findViewById(R.id.endDateTxt);
        edit_notes_text =findViewById(R.id.courseNotesTxt);
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
        edit_name_text.setText(getIntent().getStringExtra("Name"));
        edit_start_text.setText(getIntent().getStringExtra("Start"));
        edit_end_text.setText(getIntent().getStringExtra("End"));
        edit_notes_text.setText(getIntent().getStringExtra("Notes"));
        start_date_text = findViewById(R.id.startDateTxt);
        end_date_txt = findViewById(R.id.endDateTxt);
        start_dp = findViewById(R.id.calStartDP);
        end_dp = findViewById(R.id.calEndDP);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        start_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CoursesEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month + "/" +dayOfMonth + "/" + year;
                        start_date_text.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();
            }
        });
        end_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CoursesEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        end_date_txt.setText(date);
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
                    String name = edit_name_text.getText().toString();
                    String start = edit_start_text.getText().toString();
                    String end = edit_end_text.getText().toString();
                    if(name.matches("") || start.matches("") || end.matches("")) {
                        Toast.makeText(getApplicationContext(), "All fields are required.",Toast.LENGTH_LONG).show();
                    }
                    else {
                        String spinnerTxt = spinner.getSelectedItem().toString();
                        entity_course course = new entity_course(getIntent().getIntExtra("courseID", 0), edit_name_text.getText().toString(), edit_start_text.getText().toString(),
                                edit_end_text.getText().toString(), spinnerTxt, edit_notes_text.getText().toString(), getIntent().getIntExtra("termID", 0));
                        course_view_model_e.insert(course);
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
                    entity_course course = new entity_course(getIntent().getIntExtra("courseID", 0), edit_name_text.getText().toString(), edit_start_text.getText().toString(),
                            edit_end_text.getText().toString(), spinnerTxt, edit_notes_text.getText().toString(), getIntent().getIntExtra("termID", 0));
                    course_view_model_e.delete(course);
                    Toast.makeText(getApplicationContext(), "Course Deleted",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CoursesEditActivity.this, CoursesActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "All mentors and assessments must be deleted first.",Toast.LENGTH_LONG).show();
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
