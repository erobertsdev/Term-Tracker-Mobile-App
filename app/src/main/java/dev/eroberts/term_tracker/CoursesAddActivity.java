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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;
import java.util.List;

/**
 * The type Courses add activity.
 */
public class CoursesAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private course_view_model course_view_model_e;
    private EditText edit_name_e;
    private EditText edit_start_e;
    private EditText edit_end_e;
    private EditText edit_notes_e;
    private ImageView start_dp;
    private ImageView end_dp;
    /**
     * The Set listener.
     */
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course_view_model_e = new ViewModelProvider(this).get(course_view_model.class);
        setContentView(R.layout.activity_courses_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        edit_name_e =findViewById(R.id.courseNameTxt);
        edit_notes_e =findViewById(R.id.courseNotesTxt);
        Spinner spinner = findViewById(R.id.courseStatusSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(this);
        edit_start_e = findViewById(R.id.courseStartTxt);
        edit_end_e = findViewById(R.id.courseEndTxt);
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
                        CoursesAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month + "/" +dayOfMonth+ "/" +year;
                        edit_start_e.setText(date);
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
                        CoursesAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        edit_end_e.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();
            }
        });
        try {
            course_view_model_e.getAllCourses().observe(this, new Observer<List<entity_course>>() {
                @Override
                public void onChanged(List<entity_course> courseEntities) {
                }
            });
        } catch (NullPointerException e) {
        }
        try {
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = edit_name_e.getText().toString();
                    String start = edit_start_e.getText().toString();
                    String end = edit_end_e.getText().toString();
                    if(name.matches("") || start.matches("") || end.matches("")) {
                        Toast.makeText(getApplicationContext(), "All fields are required.",Toast.LENGTH_LONG).show();
                    }
                    else {
                        String spinnerTxt = spinner.getSelectedItem().toString();
                        entity_course course = new entity_course(course_view_model_e.lastID() + 1, edit_name_e.getText().toString(), edit_start_e.getText().toString(), edit_end_e.getText().toString(),spinnerTxt, edit_notes_e.getText().toString(), 0);
                        course_view_model_e.insert(course);
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
