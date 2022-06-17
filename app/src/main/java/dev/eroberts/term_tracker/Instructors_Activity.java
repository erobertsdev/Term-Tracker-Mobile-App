package dev.eroberts.term_tracker;
import android.content.Intent;
import android.os.Bundle;
import dev.eroberts.term_tracker.Entities.entity_instructor;
import dev.eroberts.term_tracker.ViewModel.instructor_view_model;
import dev.eroberts.term_tracker.UI.instructor_adapter;
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
 * The type instructors activity.
 */
public class Instructors_Activity extends AppCompatActivity {
    private instructor_view_model instructor_view_model_e;
    /**
     * The constant REQUEST.
     */
    public static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instructor_view_model_e =new ViewModelProvider(this).get(instructor_view_model.class);
        setContentView(R.layout.activity_instructors);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView instructors_add = findViewById(R.id.fab);
        instructors_add.setOnClickListener((view) -> {
            Intent intent = new Intent( Instructors_Activity.this, Instructors_Add_Activity.class);
            startActivityForResult(intent, REQUEST);
        });
        RecyclerView recyclerView = findViewById(R.id.instructorsRV);
        final instructor_adapter adapter = new instructor_adapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        instructor_view_model_e.getAllInstructors().observe(this, new Observer<List<entity_instructor>>() {
            @Override
            public void onChanged(@Nullable final List<entity_instructor> words) {
                adapter.setWords(words);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            entity_instructor instructor = new entity_instructor(instructor_view_model_e.lastID()+1, data.getStringExtra("mentorName"), data.getStringExtra("mentorEmail"), data.getStringExtra("mentorPhone"), data.getIntExtra("courseID", 0));
            instructor_view_model_e.insert(instructor);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( Instructors_Activity.this, Main_Activity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
