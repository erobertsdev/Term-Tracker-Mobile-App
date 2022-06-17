package dev.eroberts.term_tracker;
import android.content.Intent;
import android.os.Bundle;

import dev.eroberts.term_tracker.Entities.entity_instructor;
import dev.eroberts.term_tracker.ViewModel.instructor_view_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

/**
 * The type Instructors add activity.
 */
public class Instructors_Add_Activity extends AppCompatActivity {
    private instructor_view_model instructor_view_model_e;
    private EditText edit_name_text;
    private EditText edit_email_text;
    private EditText edit_phone_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instructor_view_model_e = new ViewModelProvider(this).get(instructor_view_model.class);
        setContentView(R.layout.activity_instructors_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        edit_name_text =findViewById(R.id.instructor_name_text_field);
        edit_email_text =findViewById(R.id.instructor_email_text_field);
        edit_phone_text =findViewById(R.id.instructor_phone_text_field);
        try {
            instructor_view_model_e.getAllInstructors().observe(this, new Observer<List<entity_instructor>>() {
                @Override
                public void onChanged(List<entity_instructor> mentorEntities) {
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
                    String email = edit_email_text.getText().toString();
                    String phone = edit_phone_text.getText().toString();
                    if(name.matches("") || email.matches("") || phone.matches("")) {
                        Toast.makeText(getApplicationContext(), "All fields are required.",Toast.LENGTH_LONG).show();
                    }
                    else {
                        entity_instructor mentor = new entity_instructor(instructor_view_model_e.lastID() + 1, edit_name_text.getText().toString(), edit_email_text.getText().toString(), edit_phone_text.getText().toString(), 0);
                        instructor_view_model_e.insert(mentor);
                        Intent intent = new Intent(Instructors_Add_Activity.this, Instructors_Activity.class);
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
