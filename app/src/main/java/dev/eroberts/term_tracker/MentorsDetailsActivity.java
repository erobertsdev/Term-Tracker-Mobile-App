package dev.eroberts.term_tracker;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import dev.eroberts.term_tracker.ViewModel.mentor_view_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

/**
 * The type Mentors details activity.
 */
public class MentorsDetailsActivity extends AppCompatActivity {
    private mentor_view_model mentor_view_model_e;
    /**
     * The constant REQUEST.
     */
    public static final int REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mentor_view_model_e = new ViewModelProvider(this).get(mentor_view_model.class);
        setContentView(R.layout.activity_mentors_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        EditText edit_name_text = findViewById(R.id.assessment_details_name);
        EditText edit_email_text = findViewById(R.id.assessment_details_date);
        EditText edit_phone_text = findViewById(R.id.assessment_details_type);
        String temp=getIntent().getStringExtra("mentorName");
        if(getIntent().getStringExtra("mentorName")!=null) {
            edit_name_text.setText(getIntent().getStringExtra("mentorName"));
            edit_email_text.setText(getIntent().getStringExtra("mentorEmail"));
            edit_phone_text.setText(getIntent().getStringExtra("mentorPhone"));
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView mentorsEdit = findViewById(R.id.fab);
        mentorsEdit.setOnClickListener((view) -> {
            Intent intent = new Intent( MentorsDetailsActivity.this, MentorsEditActivity.class);
            startActivityForResult(intent, REQUEST);
            intent.putExtra("Name", temp);
            intent.putExtra("Email", getIntent().getStringExtra("mentorEmail"));
            intent.putExtra("Phone", getIntent().getStringExtra("mentorPhone"));
            intent.putExtra("mentorID", getIntent().getIntExtra("mentorID", 0));
            intent.putExtra("courseID", getIntent().getIntExtra("courseID", 0));
            startActivity(intent);
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( MentorsDetailsActivity.this, MentorsActivity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
