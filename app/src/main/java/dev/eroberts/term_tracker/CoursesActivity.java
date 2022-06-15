package dev.eroberts.term_tracker;
import android.content.Intent;
import android.os.Bundle;
import dev.eroberts.term_tracker.Entities.entity_course;
import dev.eroberts.term_tracker.ViewModel.course_view_model;
import dev.eroberts.term_tracker.UI.course_adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import java.util.List;

/**
 * The type Courses activity.
 */
public class CoursesActivity extends AppCompatActivity {
    private course_view_model course_view_model_e;
    /**
     * The constant REQUEST.
     */
    public static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course_view_model_e =new ViewModelProvider(this).get(course_view_model.class);
        setContentView(R.layout.activity_courses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView coursesAdd = findViewById(R.id.fab);
        coursesAdd.setOnClickListener((view) -> {
            Intent intent = new Intent( CoursesActivity.this, CoursesAddActivity.class);
            startActivityForResult(intent, REQUEST);
        });
        RecyclerView recyclerView = findViewById(R.id.coursesRV);
        final course_adapter adapter = new course_adapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        course_view_model_e.getAllCourses().observe(this, new Observer<List<entity_course>>() {
            @Override
            public void onChanged(@Nullable final List<entity_course> words) {adapter.setWords(words);}
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            entity_course course = new entity_course(course_view_model_e.lastID()+1, data.getStringExtra("courseName"), data.getStringExtra("courseStart"), data.getStringExtra("courseEnd"), data.getStringExtra("status"), data.getStringExtra("notes"), data.getIntExtra("termID", 0));
            course_view_model_e.insert(course);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( CoursesActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
