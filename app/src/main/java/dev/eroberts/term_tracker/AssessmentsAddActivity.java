package dev.eroberts.term_tracker;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import dev.eroberts.term_tracker.Entities.entity_assessment;
import dev.eroberts.term_tracker.ViewModel.assessment_view_model;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Calendar;
import java.util.List;

/**
 * The type Assessments add activity.
 */
public class AssessmentsAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private assessment_view_model assessment_view_model_e;
    private EditText edit_name_e;
    private EditText edit_date_e;
    private EditText date_text;
    private ImageView cal_start_dp;
    /**
     * The Set date listener.
     */
    DatePickerDialog.OnDateSetListener set_date_listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assessment_view_model_e = new ViewModelProvider(this).get(assessment_view_model.class);
        setContentView(R.layout.activity_assessments_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        edit_name_e = findViewById(R.id.assessment_name_text);
        edit_date_e = findViewById(R.id.assessment_date_text);
        Spinner spinner = findViewById(R.id.assessment_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(this);
        date_text = findViewById(R.id.assessment_date_text);
        cal_start_dp = findViewById(R.id.start_date_picker);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        cal_start_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AssessmentsAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month+ "/" +dayOfMonth+ "/" +year;
                        date_text.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();
            }
        });
            try {
                assessment_view_model_e.getAllAssessments().observe(this, new Observer<List<entity_assessment>>() {
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
                        String name = edit_name_e.getText().toString();
                        String date = edit_date_e.getText().toString();
                        if(name.matches("") || date.matches("")) {
                            Toast.makeText(getApplicationContext(), "All fields are required.",Toast.LENGTH_LONG).show();
                        }
                    else {
                            String spinnerTxt = spinner.getSelectedItem().toString();
                            entity_assessment assessment = new entity_assessment(assessment_view_model_e.lastID() + 1, edit_name_e.getText().toString(), edit_date_e.getText().toString(),
                                    spinnerTxt, 0);
                            assessment_view_model_e.insert(assessment);
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
