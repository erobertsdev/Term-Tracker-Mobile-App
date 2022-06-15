package dev.eroberts.term_tracker;
import android.content.Intent;
import android.os.Bundle;
import dev.eroberts.term_tracker.Entities.entity_mentor;
import dev.eroberts.term_tracker.ViewModel.mentor_view_model;
import dev.eroberts.term_tracker.UI.mentor_adapter;
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
 * The type Mentors activity.
 */
public class MentorsActivity extends AppCompatActivity {
    private mentor_view_model mentore_view_model_e;
    /**
     * The constant REQUEST.
     */
    public static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mentore_view_model_e =new ViewModelProvider(this).get(mentor_view_model.class);
        setContentView(R.layout.activity_mentors);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        ImageView mentorsAdd = findViewById(R.id.fab);
        mentorsAdd.setOnClickListener((view) -> {
            Intent intent = new Intent( MentorsActivity.this, MentorsAddActivity.class);
            startActivityForResult(intent, REQUEST);
        });
        RecyclerView recyclerView = findViewById(R.id.mentorsRV);
        final mentor_adapter adapter = new mentor_adapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mentore_view_model_e.getAllMentors().observe(this, new Observer<List<entity_mentor>>() {
            @Override
            public void onChanged(@Nullable final List<entity_mentor> words) {
                adapter.setWords(words);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {entity_mentor mentor = new entity_mentor(mentore_view_model_e.lastID()+1, data.getStringExtra("mentorName"), data.getStringExtra("mentorEmail"), data.getStringExtra("mentorPhone"), data.getIntExtra("courseID", 0));
            mentore_view_model_e.insert(mentor);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( MentorsActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
