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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.List;

/**
 * The type Terms add activity.
 */
public class Terms_Add_Activity extends AppCompatActivity {
    private term_view_model term_view_model_e;
    private EditText edit_name_text;
    private EditText edit_start_text;
    private EditText edit_end_text;
    private ImageView start_dp;
    private ImageView end_dp;
    /**
     * The On date set listener.
     */
    DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        term_view_model_e = new ViewModelProvider(this).get(term_view_model.class);
        setContentView(R.layout.activity_terms_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        edit_name_text =findViewById(R.id.term_name_text_field);
        edit_start_text = findViewById(R.id.term_start_text_field);
        edit_end_text = findViewById(R.id.term_end_text_field);
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
                        Terms_Add_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month + "/" +dayOfMonth+ "/" +year;
                        edit_start_text.setText(date);
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
                        Terms_Add_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month + "/" + dayOfMonth + "/" + year;
                        edit_end_text.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();
            }
        });
        try {
            term_view_model_e.getAllTerms().observe(this, new Observer<List<entity_term>>() {
                @Override
                public void onChanged(List<entity_term> termEntities) {
                }
            });
        } catch (NullPointerException e) {
        }
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
                        entity_term term = new entity_term(term_view_model_e.lastID() + 1, edit_name_text.getText().toString(), edit_start_text.getText().toString(),
                                edit_end_text.getText().toString());
                        term_view_model_e.insert(term);
                        Intent intent = new Intent(Terms_Add_Activity.this, Terms_Activity.class);
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
