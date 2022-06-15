package dev.eroberts.term_tracker;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import dev.eroberts.term_tracker.Entities.entity_term;
import dev.eroberts.term_tracker.ViewModel.term_view_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Calendar;

/**
 * The type Terms edit activity.
 */
public class TermsEditActivity extends AppCompatActivity {
    private term_view_model view_term_model_e;
    private EditText edit_name_text;
    private EditText edit_start_text;
    private EditText edit_end_text;
    /**
     * The Start date text.
     */
    EditText start_date_text;
    /**
     * The End date text.
     */
    EditText end_date_text;
    /**
     * The Start dp.
     */
    ImageView start_dp;
    /**
     * The End dp.
     */
    ImageView end_dp;
    /**
     * The On date set listener.
     */
    DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view_term_model_e = new ViewModelProvider(this).get(term_view_model.class);
        setContentView(R.layout.activity_terms_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        edit_name_text =findViewById(R.id.term_name_text_field);
        edit_start_text =findViewById(R.id.term_start_date_text_field);
        edit_end_text =findViewById(R.id.term_end_date_text_field);
        edit_name_text.setText(getIntent().getStringExtra("Name"));
        edit_start_text.setText(getIntent().getStringExtra("Start"));
        edit_end_text.setText(getIntent().getStringExtra("End"));
        start_date_text = findViewById(R.id.term_start_date_text_field);
        end_date_text = findViewById(R.id.term_end_date_text_field);
        start_dp = findViewById(R.id.start_date_picker);
        end_dp = findViewById(R.id.end_date_picker);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        start_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TermsEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month+ "/" +dayOfMonth+ "/" +year;
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
                        TermsEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month + "/" + dayOfMonth + "/" + year;
                        end_date_text.setText(date);
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
                        Toast.makeText(getApplicationContext(), "Name and Date fields cannot be blank.",Toast.LENGTH_LONG).show();
                    }
                    else {
                        entity_term term = new entity_term(getIntent().getIntExtra("termID", 0), edit_name_text.getText().toString(), edit_start_text.getText().toString(),
                                edit_end_text.getText().toString());
                        view_term_model_e.insert(term);
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
                entity_term term = new entity_term(getIntent().getIntExtra("termID", 0), edit_name_text.getText().toString(), edit_start_text.getText().toString(),
                        edit_end_text.getText().toString());
                view_term_model_e.delete(term);

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
