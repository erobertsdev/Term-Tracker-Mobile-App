package dev.eroberts.term_tracker;
import android.widget.ImageView;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import java.util.Objects;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import dev.eroberts.term_tracker.Entities.entity_assessment;
import dev.eroberts.term_tracker.UI.assessment_adapter;
import dev.eroberts.term_tracker.ViewModel.assessment_view_model;

/**
 * The type Assessments activity.
 */
public class Assessments_Activity extends AppCompatActivity {
    private assessment_view_model assessment_view_model_e;
    /**
     * The constant REQUEST.
     */
    public static final int REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assessment_view_model_e = new ViewModelProvider(this).get(assessment_view_model.class);
        setContentView(R.layout.activity_assessments);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        findViewById(R.id.fab);
        ImageView assessmentsAdd = findViewById(R.id.fab);
        assessmentsAdd.setOnClickListener((view) -> {
            Intent intent = new Intent( Assessments_Activity.this, Assessments_Add_Activity.class);
            startActivityForResult(intent, REQUEST);
        });

        RecyclerView recyclerView = findViewById(R.id.assessmentsRV);
        final assessment_adapter adapter = new assessment_adapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessment_view_model_e.getAllAssessments().observe(this, words -> adapter.setWords(words));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) { entity_assessment assessment = new entity_assessment(assessment_view_model_e.lastID()+1, data.getStringExtra("assessmentName"), data.getStringExtra("assessmentDate"), data.getStringExtra("assessmentType"), data.getIntExtra("courseID", 0));
            assessment_view_model_e.insert(assessment);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( Assessments_Activity.this, Main_Activity.class);
        startActivity(intent);
        super.onBackPressed();
        return true;
    }
}
