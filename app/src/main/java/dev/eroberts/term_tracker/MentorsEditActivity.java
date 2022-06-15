package dev.eroberts.term_tracker;
import android.content.Intent;
import android.os.Bundle;
import dev.eroberts.term_tracker.Entities.entity_mentor;
import dev.eroberts.term_tracker.ViewModel.mentor_view_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The type Mentors edit activity.
 */
public class MentorsEditActivity extends AppCompatActivity {
    private mentor_view_model mentor_view_model_e;
    private EditText edit_name_text;
    private EditText edit_email_text;
    private EditText edit_phone_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mentor_view_model_e = new ViewModelProvider(this).get(mentor_view_model.class);
        setContentView(R.layout.activity_mentors_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        edit_name_text = findViewById(R.id.mentorNameTxt);
        edit_email_text = findViewById(R.id.mentorEmailTxt);
        edit_phone_text = findViewById(R.id.mentorPhoneTxt);
        edit_name_text.setText(getIntent().getStringExtra("Name"));
        edit_email_text.setText(getIntent().getStringExtra("Email"));
        edit_phone_text.setText(getIntent().getStringExtra("Phone"));
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
                        entity_mentor mentor = new entity_mentor(getIntent().getIntExtra("mentorID", 0), edit_name_text.getText().toString(), edit_email_text.getText().toString(),
                                edit_phone_text.getText().toString(), getIntent().getIntExtra("courseID", 0));
                        mentor_view_model_e.insert(mentor);
                        Intent intent = new Intent(MentorsEditActivity.this, MentorsActivity.class);
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
                entity_mentor mentor = new entity_mentor(getIntent().getIntExtra("mentorID", 0), edit_name_text.getText().toString(), edit_email_text.getText().toString(),
                        edit_phone_text.getText().toString(), getIntent().getIntExtra("courseID", 0));
                mentor_view_model_e.delete(mentor);
                Toast.makeText(getApplicationContext(), "Mentor Deleted",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MentorsEditActivity.this, MentorsActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( MentorsEditActivity.this, MentorsDetailsActivity.class);
        intent.putExtra("mentorName", getIntent().getStringExtra("Name"));
        intent.putExtra("mentorEmail", getIntent().getStringExtra("Email"));
        intent.putExtra("mentorPhone", getIntent().getStringExtra("Phone"));
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
