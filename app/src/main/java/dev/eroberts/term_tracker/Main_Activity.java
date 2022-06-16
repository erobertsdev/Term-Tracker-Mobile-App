package dev.eroberts.term_tracker;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;

/**
 * The type Main activity.
 */
public class Main_Activity extends AppCompatActivity {

    /**
     * The constant REQUEST.
     */
    public static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView terms = findViewById(R.id.terms_img);
        terms.setOnClickListener((view) -> {
            Intent intent = new Intent( Main_Activity.this, Terms_Activity.class);
            startActivityForResult(intent, REQUEST);
        });
        ImageView courses = findViewById(R.id.courses_img);
        courses.setOnClickListener((view) -> {
            Intent intent = new Intent( Main_Activity.this, Courses_Activity.class);
            startActivityForResult(intent, REQUEST);
        });
        ImageView assessments = findViewById(R.id.assessments_img);
        assessments.setOnClickListener((view) -> {
            Intent intent = new Intent( Main_Activity.this, Assessments_Activity.class);
            startActivityForResult(intent, REQUEST);
        });
        ImageView mentors = findViewById(R.id.mentors_img);
        mentors.setOnClickListener((view) -> {
            Intent intent = new Intent( Main_Activity.this, Mentors_Activity.class);
            startActivityForResult(intent, REQUEST);
        });
    }
}
